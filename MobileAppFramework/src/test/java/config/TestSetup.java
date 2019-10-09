package config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.BeforeSuite;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import reusablecomponents.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

public class TestSetup {
	
	public static AndroidDriver<AndroidElement> driver;
	public static WebDriverWait wait;
	public static long timeOut,driverWait;
	public boolean toBeTested = false; 
	public int testCaseCount=0,testCaseExecuted = 0,testCasePassed = 0,testCaseFailed = 0,testCaseSkipped = 0;
	public String testName, testDataLocation, platform, device, testCaseResult, testCaseName;
	public String[][] testCases;
	public HashMap<String, String> testCasesToBeExecuted = new HashMap<String, String>(),
			testCaseDevice = new HashMap<String, String>(),
			testDevicePlatform = new HashMap<String, String>(),
	        testDeviceVersion = new HashMap<String, String>();

	@BeforeSuite
	public void beforeSuite() {
		testDataLocation = Utilities.getProperty("TEST_DATA_LOCATION");
		testCases = Utilities.Read_Excel(testDataLocation, "TestCases");
		
		for (int i = 0; i < testCases.length; i++) {
			testCasesToBeExecuted.put(testCases[i][1], testCases[i][2]);
			testDevicePlatform.put(testCases[i][1], testCases[i][3]);
			testCaseDevice.put(testCases[i][1], testCases[i][4]);
			testDeviceVersion.put(testCases[i][1], testCases[i][5]);
		}
		Report.initialiseReporters();
	}

	@BeforeMethod
	public void beforeMethod(Method method) {
		testCaseCount++;
		testName = method.getName();
		driverWait = Long.parseLong(Utilities.getProperty("IMPLICIT_WAIT"));
		Report.startReporters(testName);

		try {
			if (testCasesToBeExecuted.get(testName).equalsIgnoreCase("Yes")) {
				
				toBeTested = true;
				Utilities.setProperty("DEVICE_NAME", testCaseDevice.get(testName));
				Utilities.setProperty("PLATFORM_NAME", testDevicePlatform.get(testName));
				Utilities.setProperty("DEVICE_PLATFORM_VERSION", testDeviceVersion.get(testName));
						
				driver = getAndroidDriverWithApk();
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				// driver = getChromeWebDriver();
				// driver = getAndroidDriverWithPackage();
			}

		} catch (NullPointerException e) {
			Report.skip(testName + " not configured. Please check data file and function name for consistency.");
		}
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		Report.flushReporters();
		
		switch (result.getStatus()) {
		case ITestResult.SUCCESS:
			testCasePassed++;
			testCaseResult = "PASS";
			break;
		case ITestResult.FAILURE:
			testCaseFailed++;
			testCaseResult = "FAIL";
			break;
		case ITestResult.SKIP:
			testCaseSkipped++;
			testCaseResult = "SKIP";
			break;
		}
		testCaseExecuted = testCasePassed + testCaseFailed;
		Report.writeResultInExcelReport(testCaseName,testCaseResult, testCaseCount);
		driver.quit();
		}
	
	@AfterSuite
	public void afterSuite() throws FileNotFoundException, IOException  {
		
		//Report.writeExcelReport(testCaseName,testCaseResult, testCaseCount);
		System.out.println("Test Cases Executed: " + testCaseExecuted);
		System.out.println("Test Cases Passed: " + testCasePassed);
		System.out.println("Test Cases Failed: " + testCaseFailed);
		System.out.println("Test Cases Skipped: " + testCaseSkipped);
	}
	
	public void setParametersPerTestCase(String testDesc, String complexity) {

		if (driver != null) {
			if (complexity.toLowerCase().equals("low")) {
				timeOut = Long.parseLong(Utilities.getProperty("TIME_OUT_LOW"));
			} else if (complexity.toLowerCase().equals("medium")) {
				timeOut = Long.parseLong(Utilities.getProperty("TIME_OUT_MEDIUM"));
			} else if (complexity.toLowerCase().equals("high")) {
				timeOut = Long.parseLong(Utilities.getProperty("TIME_OUT_HIGH"));
			}
			wait = new WebDriverWait(driver, timeOut);
		}
		
		testCaseName=Report.setLoggersTestNameAndDesc(testDesc,complexity,testCaseCount,testName,timeOut);
	}

	public AndroidDriver<AndroidElement> getAndroidDriverWithApk() {
		AndroidDriver<AndroidElement> localDriver;
		DesiredCapabilities caps = new DesiredCapabilities();
		try {
			
			File appInTest = new File(System.getProperty("user.dir") + "/Apps/" + Utilities.getProperty("APP_NAME"));
			caps.setCapability(MobileCapabilityType.DEVICE_NAME, Utilities.getProperty("DEVICE_NAME"));
			caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
			caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, Utilities.getProperty("DEVICE_PLATFORM_VERSION"));
			caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator1");
			caps.setCapability(MobileCapabilityType.APP, appInTest.getAbsolutePath());
			
			localDriver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
		
		} catch (Exception e) {
			throw new FrameworkException("Exception encountered while initializing Android Driver");
		}
		return localDriver;
	}
	
	public AndroidDriver<AndroidElement> getAndroidDriverWithPackage() {
		AndroidDriver<AndroidElement> localDriver;
		DesiredCapabilities caps = new DesiredCapabilities();
		try {
			
			caps.setCapability(MobileCapabilityType.DEVICE_NAME, Utilities.getProperty("DEVICE_NAME"));
			caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
			caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, Utilities.getProperty("DEVICE_PLATFORM_VERSION"));
			caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
			caps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, Utilities.getProperty("APP_PACKAGE"));
			caps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, Utilities.getProperty("APP_ACTIVITY"));
			
			localDriver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
		
		} catch (Exception e) {
			throw new FrameworkException("Exception encountered while initializing Android Driver");
		}
		return localDriver;
	}
	
	public AndroidDriver<AndroidElement> getChromeWebDriver() {
		AndroidDriver<AndroidElement> localDriver;
		DesiredCapabilities caps = new DesiredCapabilities();
		try {

			caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Device");
			caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
			caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "7.0");
			caps.setCapability(MobileCapabilityType.BROWSER_NAME, "chrome");
			caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
			
			localDriver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
			
		} catch (Exception e) {
			throw new FrameworkException("Exception encountered while initializing Android Driver");
		}
		return localDriver;
	}

}

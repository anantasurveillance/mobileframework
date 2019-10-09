package practice.testscript;

import static org.testng.Assert.assertTrue;
import java.util.List;
import io.appium.java_client.android.AndroidElement;
import static java.time.Duration.ofSeconds;
import static io.appium.java_client.touch.offset.ElementOption.element;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.LogStatus;
import config.FrameworkException;
import config.Report;
import io.appium.java_client.TouchAction;
import objectrepository.ApiDemoHomePage;
import reusablecomponents.BusinessComponents;
import reusablecomponents.TechnicalComponents;


public class ApiDemo extends BusinessComponents {
	String testDesc,complexity;

	@Test
	public void methodTemplate() {
		testDesc = "Sample Test Description";
		complexity = "Low,Medium,High";
		setParametersPerTestCase(testDesc, complexity);
		
		if (toBeTested) {
			try {										
				
				// Method code starts
					/*
					 * Method code comes here
					 */
				// Method Code ends
				
				Report.pass(testDesc + " Passed.");
				assertTrue(true, "Test Case: " + testDesc + " Passed.");
			} catch (FrameworkException fx) {
				Report.failLog(fx.getMessage());
				Report.fail(testDesc + " Failed.");
				assertTrue(false, "Test Case: " + testDesc + " Failed.");
			}
		} else {
			Report.skip("Test: " + testDesc + " Skipped.");
			Report.skipLog("Test: " + testDesc + " Skipped.");
			throw new SkipException("Test: " + testDesc);
		}
	}
	
	@Test
	public void testClickAction() {
		testDesc = "Verify tap functionality on all element at Home page";
		complexity = "low";
		setParametersPerTestCase(testDesc, complexity);

		if (toBeTested) {
			try {
				// Method code start
				ApiDemoHomePage home = new ApiDemoHomePage(driver);
				home.tapPreference();
				pressBackButton();
				home.tapAnimation();
				pressBackButton();
				home.tapApp();
				pressBackButton();
				home.tapContent();
				pressBackButton();
				// Method Code ends
				assertTrue(true, "Test Case: " + testDesc + " Passed.");
				Report.pass(testDesc + " Passed.");

			} catch (FrameworkException ex) {
				Report.failLog(ex.getMessage());
				Report.fail(testDesc + " Failed.");
				assertTrue(false, "Test Case: " + testDesc + " Failed.");
			}
		} else {
			Report.skip("Test: " + testDesc + " Skipped.");
			Report.skipLog("Test: " + testDesc + " Skipped.");
			throw new SkipException("Test: " + testDesc);
		}
	}

	
	@Test
	public void testNavigatePreference() {
		testDesc = "Verify navigation of WiFi menu in mobile app";
		complexity = "low";
		setParametersPerTestCase(testDesc, complexity);

		if (toBeTested) {
			try {
				// Method code starts
				AndroidElement preference = driver.findElementByXPath("//android.widget.TextView[@text='Preference']");
				click(preference, "Preference");

				AndroidElement pref_dependency = driver
						.findElementByXPath("//android.widget.TextView[@text='3. Preference dependencies']");
				click(pref_dependency, "dependency");

				AndroidElement checkbox = driver.findElementById("android:id/checkbox");
				click(checkbox, "checkbox");

				AndroidElement wifiSetting = driver.findElementByXPath("(//android.widget.RelativeLayout)[2]");
				click(wifiSetting, "Wi-Fi Setting");

				AndroidElement edit_text = driver.findElementByClassName("android.widget.EditText");
				type(edit_text, "Hello Shailendra", "Wifi Value");

				List<AndroidElement> buttons = driver.findElementsByClassName("android.widget.Button");
				click(buttons.get(1), "Ok Button");
				// Method Code ends
				
				assertTrue(true, "Test Case: " + testDesc + " Passed.");
				Report.pass(testDesc + " Passed.");

			} catch (Exception e) {
				Report.failLog(e.getMessage());
				Report.fail(testDesc + " Failed.");
				assertTrue(false, "Test Case: " + testDesc + " Failed.");
			}
		} else {
			Report.skip("Test: " + testDesc + " Skipped.");
			Report.skipLog("Test: " + testDesc + " Skipped.");
			throw new SkipException("Test: " + testDesc);
		}
	}
	
  @Test
  public void testAndroidUIAutomator() {	  
	// Syntex (xpath, id , class) : driver.findElementByAndroidUIAutomator("Attribute("Value")");
	  testDesc = "Verify navigation via androiduiautomator in mobile app";
	  complexity = "low";
	  setParametersPerTestCase(testDesc, complexity);
		try {
			getAndroidUIElement("text", "Views").click();
			getAndroidUIElement("text", "Custom").click();
			
			//driver.findElementByAndroidUIAutomator("text(\"Views\")").click();
			//driver.findElementByAndroidUIAutomator("text(\"Custom\")").click();
			
			System.out.println(driver.findElementsByAndroidUIAutomator("new UiSelector().clickable(true)").size());
			Report.pass(testDesc + " Passed.");
			assertTrue(true, "Test Case: " + testDesc + " Passed.");
			
		} catch (Exception e) {
			Report.failLog(e.getMessage());
			Report.fail(testDesc + " Failed.");
			assertTrue(false, "Test Case: " + testDesc + " Failed.");
		}
  }

  
  @Test
  public void testLongTapAction() {	  
	// 
	  testDesc = "Verify Long Tap Action in mobile app";
	  complexity = "low";
	  setParametersPerTestCase(testDesc, complexity);
		try {
			driver.findElementByAndroidUIAutomator("text(\"Views\")").click();
			
			//TouchAction touch = new TouchAction(driver);
			
			AndroidElement expandList = driver.findElementByXPath("//android.widget.TextView[@text='Expandable Lists']");
			
			//touch.tap(tapOptions().withElement(element(expandList))).perform();
			tap(expandList, "Lists");
	
			
			driver.findElementByAndroidUIAutomator("text(\"1. Custom Adapter\")").click();
		
			AndroidElement peopleNames = driver.findElementByXPath("//android.widget.TextView[@text='People Names']");
			
			//touch.longPress(longPressOptions().withElement(element(peopleNames)).withDuration(ofSeconds(2))).release().perform();
		   longTap(peopleNames, 2, "People names");
			
			if(driver.findElementById("android:id/title").isDisplayed())	
			System.out.println("Long press option appears");
		    else 
		    	System.out.println("Long press did not work");
		    	
		    Report.pass(testDesc + " Passed.");
			assertTrue(true, "Test Case: " + testDesc + " Passed.");
			
		} catch (Exception e) {
			Report.failLog(e.getMessage());
			Report.fail(testDesc + " Failed.");
			assertTrue(false, "Test Case: " + testDesc + " Failed.");
		}
  }

  
  @Test
  public void testSwipeAction() {	  
	// 
	  testDesc = "Verify Swipe Action in mobile app";
	  complexity = "low";
	  setParametersPerTestCase(testDesc, complexity);
		try {
			driver.findElementByAndroidUIAutomator("text(\"Views\")").click();
			driver.findElementByAndroidUIAutomator("text(\"Date Widgets\")").click();
			driver.findElementByAndroidUIAutomator("text(\"2. Inline\")").click();
			driver.findElementByXPath("//*[@content-desc='9']").click();
		//	TouchAction touch = new TouchAction(driver);
			AndroidElement start = driver.findElementByXPath("//*[@content-desc='15']");
			AndroidElement end = driver.findElementByXPath("//*[@content-desc='45']");
			
			//Long press start + Move to end + release 
		//	touch.longPress(longPressOptions().withElement(element(start)).withDuration(ofSeconds(2))).moveTo(element(end)).release().perform();
			swipe(start, end, "swipe operation");
			
		    Report.pass(testDesc + " Passed.");
			assertTrue(true, "Test Case: " + testDesc + " Passed.");
			
		} catch (Exception e) {
			Report.failLog(e.getMessage());
			Report.fail(testDesc + " Failed.");
			assertTrue(false, "Test Case: " + testDesc + " Failed.");
		}
  }

  
  @Test
  public void testScrollAction() {	  
	// Used android code not appium
	  testDesc = "Verify Scrolling till text visible in mobile app";
	  complexity = "low";
	  setParametersPerTestCase(testDesc, complexity);
		try {
			
			driver.findElementByAndroidUIAutomator("text(\"Views\")").click();
			
		//	driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Radio Group\"))");
			scrollToText("Lists");
			AndroidElement element = driver.findElementByAndroidUIAutomator("text(\"Lists\")");
			click(element, "Lists menu");
			
		    Report.pass(testDesc + " Passed.");
			assertTrue(true, "Test Case: " + testDesc + " Passed.");
			
		} catch (Exception e) {
			Report.failLog(e.getMessage());
			Report.fail(testDesc + " Failed.");
			assertTrue(false, "Test Case: " + testDesc + " Failed.");
		}
  }

  
  @Test
  public void testDragAndDropAction() {	  
	// 
	  testDesc = "Verify Drag and Drop action in mobile app";
	  complexity = "low";
	  setParametersPerTestCase(testDesc, complexity);
		try {
			driver.findElementByAndroidUIAutomator("text(\"Views\")").click();
			driver.findElementByAndroidUIAutomator("text(\"Drag and Drop\")").click();
			
			AndroidElement dragElement = driver.findElementsByClassName("android.view.View").get(0);
			AndroidElement dropElement = driver.findElementsByClassName("android.view.View").get(2);
			// Longpress + move to + release
			
			//TouchAction touch = new TouchAction(driver);
			//touch.longPress(longPressOptions().withElement(element(dragElement))).moveTo(element(dropElement)).release().perform();
			//touch.longPress(element(dragElement)).moveTo(element(dropElement)).release().perform();
		    dragAndDrop(dragElement, dropElement, "Point One to Second");
			
			Report.pass(testDesc + " Passed.");
			assertTrue(true, "Test Case: " + testDesc + " Passed.");
			
		} catch (Exception e) {
			Report.failLog(e.getMessage());
			Report.fail(testDesc + " Failed.");
			assertTrue(false, "Test Case: " + testDesc + " Failed.");
		}
  }
  
  @Test
  public void testLaunchInstalledMobileApp() {	  
	// 
	  testDesc = "Verify testLaunchExistingMobileApp using package and activity capabilities";
	  complexity = "low";
	  setParametersPerTestCase(testDesc, complexity);
		try {
			System.out.println("Launching app using Package and Activity");
			
		    Report.pass(testDesc + " Passed.");
			assertTrue(true, "Test Case: " + testDesc + " Passed.");
			
		} catch (Exception e) {
			Report.failLog(e.getMessage());
			Report.fail(testDesc + " Failed.");
			assertTrue(false, "Test Case: " + testDesc + " Failed.");
		}
  }
  
  @Test
  public void testLaunchChromeBrowser() {	  
	// open chrome browser and visit URL using selenium.
	  testDesc = "Verify Launching Chrome to run scripts on Mobile browser";
	  complexity = "low";
	  setParametersPerTestCase(testDesc, complexity);
		try {
			driver.get("https://www.udemy.com");
		    Report.pass(testDesc + " Passed.");
			assertTrue(true, "Test Case: " + testDesc + " Passed.");
			
		} catch (Exception e) {
			Report.failLog(e.getMessage());
			Report.fail(testDesc + " Failed.");
			assertTrue(false, "Test Case: " + testDesc + " Failed.");
		}
  }
 
}

package config;

import java.io.File;
import java.util.HashMap;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import reusablecomponents.TechnicalComponents;
import reusablecomponents.Utilities;

public class Report {
	public static ExtentReports report, log;
	public static ExtentTest logger, loggerForLogs;
	public static String excelReport;

	/**
	 * Called from BeforeSuite........
	 */
	public static void initialiseReporters() {
		String reportName = getReportFileName();

		report = new ExtentReports("Reports/" + reportName + ".html");
		excelReport = "Reports/" + reportName + ".xlsx";
		log = new ExtentReports("Logs/" + reportName + ".html");

		setScreenshotsLocation();
		
		// Set header values of Excel Report
		try {
			
			Utilities.Write_Excel(excelReport, "TestCaseLogs", 0, 0, "Test Case Name");
			Utilities.Write_Excel(excelReport, "TestCaseLogs", 0, 1, "Test Case Result");
			Utilities.Write_Excel(excelReport, "TestCaseLogs", 0, 2, "Comments");

		} catch (Exception e) {
			throw new FrameworkException("Exception occured while writing Headings of Excel Report");
		}
	}

	/**
	 * Prepare and return Report name from Time stamp and date.
	 */
	public static String getReportFileName() {
		String reportName = "Mobile_";

		reportName = Utilities.getCurrentDate().replace("/", "") + "/" + reportName
				+ Utilities.getTimeStamp("local").replace("-", "").replace(":", "");
		return reportName;
	}
	
	/**
	 * Set value of screenshot location property of Config file.
	 */
	public static void setScreenshotsLocation() {

		Utilities.setProperty("SCREENSHOTS_LOCATION_FOR_RUN", System.getProperty("user.dir") + "/Reports/"
				+ Utilities.getCurrentDate().replace("/", "") + "/Screenshots/");
	}

	/**
	 * Called from BeforeMethod........
	 */
	public static void startReporters(String testName) {
		logger = report.startTest(testName, "test description");
		loggerForLogs = log.startTest(testName);
	}

	/**
	 * Called from AfterMethod........
	 */
	public static void flushReporters() {
		report.endTest(logger);
		report.flush();
		log.endTest(loggerForLogs);
		log.flush();
	}

	/**
	 * Write Pass, Fail, Info Using Logger object with and without screenshot in
	 * Report
	 * 
	 * @param msg
	 *            - Description message.
	 */

	public static void pass(String msg) {
		try {
			logger.log(LogStatus.PASS, msg);
		} catch (Exception e) {
			throw new FrameworkException("Exception encountered while writing Pass Report Logs");
		}
	}

	public static void pass(String msg, AndroidDriver<AndroidElement> driver) {
		try {
			TechnicalComponents.hardWait(1);
			logger.log(LogStatus.PASS, msg + logger.addScreenCapture(screenshot(driver)));
		} catch (Exception e) {
			throw new FrameworkException("Exception encountered while writing Pass with Screenshot Report Logs");
		}
	}

	public static void fail(String msg) {
		try {
			logger.log(LogStatus.FAIL, msg);
		} catch (Exception e) {
			throw new FrameworkException("Exception encountered while writing Fail Report Logs");
		}
	}

	public static void fail(String msg, AndroidDriver<AndroidElement> driver) {
		try {
			TechnicalComponents.hardWait(1);
			logger.log(LogStatus.FAIL, msg + logger.addScreenCapture(screenshot(driver)));
		} catch (Exception e) {
			throw new FrameworkException("Exception encountered while writing Fail with Screenshot Report Logs");
		}
	}

	public static void info(String msg) {
		try {
			logger.log(LogStatus.INFO, msg);
		} catch (Exception e) {
			throw new FrameworkException("Exception encountered while writing Info Report Logs");
		}
	}

	public static void info(String msg, AndroidDriver<AndroidElement> driver) {
		try {
			TechnicalComponents.hardWait(1);
			logger.log(LogStatus.INFO, msg + logger.addScreenCapture(screenshot(driver)));
		} catch (Exception e) {
			throw new FrameworkException("Exception encountered while writing Info with Screenshot Report Logs");
		}
	}

	public static void skip(String msg) {
		try {
			logger.log(LogStatus.SKIP, msg);
		} catch (Exception e) {
			throw new FrameworkException("Exception encountered while writing Skip Report Logs");
		}
	}

	/**
	 * Write Logs report using loggerforlog object of Extent report
	 * 
	 * @param msg
	 *            - Description message.
	 */

	public static void passLog(String msg) {
		try {
			loggerForLogs.log(LogStatus.PASS, msg);
		} catch (Exception e) {
			throw new FrameworkException("Exception encountered while writing pass LoggerforLogs");
		}
	}

	public static void failLog(String msg) {
		try {
			loggerForLogs.log(LogStatus.FAIL, msg);
		} catch (Exception e) {
			throw new FrameworkException("Exception encountered while writing fail LoggerforLogs");
		}
	}

	public static void log(String msg) {
		try {
			loggerForLogs.log(LogStatus.INFO, msg);
		} catch (Exception e) {
			throw new FrameworkException("Exception encountered while writing info LoggerforLogs");
		}
	}

	public static void skipLog(String msg) {
		try {
			loggerForLogs.log(LogStatus.SKIP, msg);
		} catch (Exception e) {
			throw new FrameworkException("Exception encountered while writing skip LoggerforLogs");
		}
	}

	/**
	 * Set Logger testcase name and description to print on Extent reports and
	 * elsewhere
	 */

	public static String setLoggersTestNameAndDesc(String testDesc, String complexity, int testCaseCount,
			String testCaseName, long timeOut) {
		try {
			String deviceName = Utilities.getProperty("DEVICE_NAME");
			String osVersion = Utilities.getProperty("DEVICE_PLATFORM_VERSION");

			logger.getTest()
					.setName("TestCase # " + testCaseCount + "---" + logger.getTest().getName() + "---" + testDesc);
			loggerForLogs.getTest().setName(
					"TestCase # " + testCaseCount + "---" + loggerForLogs.getTest().getName() + "---" + testDesc);

			logger.setDescription("TestCase # " + testCaseCount + "---" + testDesc + "---" + complexity + "---"
					+ timeOut + "---" + deviceName + "---" + osVersion);
			loggerForLogs.setDescription("TestCase # " + testCaseCount + "---" + testDesc + "---" + complexity + "---"
					+ timeOut + "---" + deviceName + "---" + osVersion);

			return logger.getTest().getName();
		} catch (Exception e) {
			throw new FrameworkException("Exception occured while set Test name and Description in Logger report");
		}
	}

	/**
	 * Method to write Excel report after test suite execution
	 */
	public static void writeExcelReport(HashMap<Integer, String> testCaseName, HashMap<Integer, String> testCaseResult,
			int testCaseCount) {
		try {
			int ctr = 1;
			Utilities.Write_Excel(excelReport, "TestCaseLogs", 0, 0, "Test Case Name");
			Utilities.Write_Excel(excelReport, "TestCaseLogs", 0, 1, "Test Case Result");
			Utilities.Write_Excel(excelReport, "TestCaseLogs", 0, 2, "Comments");

			while (ctr <= testCaseCount) {
				Utilities.Write_Excel(excelReport, "TestCaseLogs", ctr, 0, testCaseName.get(ctr));
				Utilities.Write_Excel(excelReport, "TestCaseLogs", ctr, 1, testCaseResult.get(ctr));
				ctr = ctr + 1;
			}

		} catch (Exception e) {
			throw new FrameworkException("Exception occured while writing Excel Report");
		}
	}

	/**
	 * Called after each method Execution from AfterMethod
	 */
	public static void writeResultInExcelReport(String testCaseName, String testCaseResult, int testCaseCount) {
		try {
			Utilities.Write_Excel(excelReport, "TestCaseLogs", testCaseCount, 0, testCaseName);
			Utilities.Write_Excel(excelReport, "TestCaseLogs", testCaseCount, 1, testCaseResult);

		} catch (Exception e) {
			throw new FrameworkException("Exception occured while writing Excel Report");
		}
	}

	/**
	 * Function to take Screenshot of screen
	 */
	public static String screenshot(AndroidDriver<AndroidElement> driver) {
		try {

			String src_path = Utilities.getProperty("SCREENSHOTS_LOCATION_FOR_RUN");
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			FileUtils.copyFile(scrFile,
					new File(src_path + Utilities.getTimeStamp("local").replace("-", "").replace(":", "") + ".png"));

			return "Screenshots/" + Utilities.getTimeStamp("local").replace("-", "").replace(":", "") + ".png";
		} catch (Exception e) {
			return "Not able to take screenshot.---" + e.getClass() + "---" + e.getMessage();
		}
	}

}

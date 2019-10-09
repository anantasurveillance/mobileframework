package practice.testscript;

import static org.testng.Assert.assertTrue;

import java.util.List;
import org.testng.SkipException;
import org.testng.annotations.Test;
import config.FrameworkException;
import config.Report;
import io.appium.java_client.android.AndroidElement;
import objectrepository.ApiDemoHomePage;
import reusablecomponents.BusinessComponents;

public class AdvanceTest extends BusinessComponents {
	String testDesc, complexity;

	@Test
	public void testClickAction() {
		testDesc = "Verify tap functionality on all element at Home page";
		complexity = "low";
		setParametersPerTestCase(testDesc, complexity);
		if (toBeTested) {
			try {
				// ---------------Method code start
				ApiDemoHomePage home = new ApiDemoHomePage(driver);
				home.tapPreference();
				pressBackButton();
				home.tapAnimation();
				pressBackButton();
				home.tapApp();
				pressBackButton();
				home.tapContent();
				pressBackButton();
				// ----------------Method Code ends
	
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

	@Test(dataProvider = "testNavigatePreferenceToWiFi", dataProviderClass = data.TestData.class)
	public void testNavigatePreferenceToWiFi(String testDesc, String complexity, String wifiName) {
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
				type(edit_text, wifiName, "Wifi Value");

				List<AndroidElement> buttons = driver.findElementsByClassName("android.widget.Button");
				click(buttons.get(1), "Ok Button");
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
	public void testDragAndDropAction() {
		testDesc = "Verify Drag and Drop action in mobile app";
		complexity = "low";
		setParametersPerTestCase(testDesc, complexity);
		if (toBeTested) {
			try {
				// Method code starts
				driver.findElementByAndroidUIAutomator("text(\"Views\")").click();
				driver.findElementByAndroidUIAutomator("text(\"Drag and Drop\")").click();
				
				AndroidElement dragElement = driver.findElementsByClassName("android.view.View").get(0);
				AndroidElement dropElement = driver.findElementsByClassName("android.view.View").get(2);
				// Longpress + move to + release

				//TouchAction touch = new TouchAction(driver);
				// touch.longPress(longPressOptions().withElement(element(dragElement))).moveTo(element(dropElement)).release().perform();
				// touch.longPress(element(dragElement)).moveTo(element(dropElement)).release().perform();
				dragAndDrop(dragElement, dropElement, "Point One to Second");

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
	public void testScrollAction() {
		testDesc = "Verify Scrolling till text visible in mobile app";
		complexity = "low";
		setParametersPerTestCase(testDesc, complexity);
		if (toBeTested) {
			try {
				// Method code starts
				driver.findElementByAndroidUIAutomator("text(\"Views\")").click();
				// driver.findElementByAndroidUIAutomator("new UiScrollable(new
				// UiSelector()).scrollIntoView(text(\"Radio Group\"))");
				scrollToText("Lists");
				AndroidElement element = driver.findElementByAndroidUIAutomator("text(\"Lists\")");
				click(element, "Lists menu");
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
}

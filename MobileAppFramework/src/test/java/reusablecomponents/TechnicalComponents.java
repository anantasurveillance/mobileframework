package reusablecomponents;

import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static java.time.Duration.ofSeconds;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import config.FrameworkException;
import config.Report;
import config.TestSetup;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

/************************************************
 *  Click, Scroll, HardWait, Tap, LongTap, Swipe, Drag and Drop, PressBackButton, Type
 *  
 *  ClearField, GetText, 
 * 
 *************************************************/

public class TechnicalComponents extends TestSetup {
	
	/**
	 * Wait till defined time limit. Time unit is in seconds.
	 * @param time
	 *            - Expected wait time in seconds.
	 */
	public static void hardWait(int time) {
		try {
			
			Thread.sleep(time * 1000);
		
		} catch (InterruptedException e) {
			throw new FrameworkException(
					"Exception occured while waiting for defined time limit :" + time + " Seconds");
		}
	}
		
	/**
	 * Function to wait for any element to be visbile, invisible or enable.
	 * 
	 * @param element
	 *            - Element to be looked for.
	 * @param state
	 *            - Expected state of Element. Expected values: "visible", "enable",
	 *            "invisible"
	 * @throws FrameworkException
	 *             - in case of error.
	 */
	
	public static void waitTill(WebElement element, String state) {
		try {
			switch (state.toLowerCase()) {
			case "visible":
				wait.until(ExpectedConditions.visibilityOf(element));
				break;
			case "enable":
				wait.until(ExpectedConditions.elementToBeClickable(element));
				break;
			case "invisible":
				wait.until(ExpectedConditions.invisibilityOf(element));
				break;
			default:
				wait.until(ExpectedConditions.visibilityOf(element));
			}
			Report.log("Element " + element.toString() + " " + state);
		
		} catch (StaleElementReferenceException e) {
			if (timeOut > 0) {
				timeOut--;
				waitTill(element, state);
			} else {
				throw new FrameworkException(
						"Page refreshed while waiting for element : *  '" + element.toString() + "'");
			}
		} catch (TimeoutException e) {
			throw new FrameworkException(
					"Element : *  '" + element.toString() + "' not found within defined time limit.");
		} catch (NoSuchElementException e) {
			throw new FrameworkException("Element : *  '" + element.toString() + "' not found in DOM.");
		} catch (Exception e) {
			throw new FrameworkException("Unknown exception occured while waiting for element: *  '"
					+ element.toString() + "'---" + e.getClass() + "---" + e.getMessage());
		}
	}
	
	/**
	 * Function to Press android device back button
	 */
	public static void pressBackButton() {
		try {

			driver.pressKey(new KeyEvent(AndroidKey.BACK));
			Report.log("Device back button press successfully");

		} catch (Exception e) {
			throw new FrameworkException("Unknown exception while pressing device back button" + "'---" + e.getClass()
					+ "---" + e.getMessage());
		}
	}
	
	/**
	 * Scroll the page till find the "ElementText".
	 */
	public static void scrollToText(String ElementText) {
		try {

			String str = "new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"" + ElementText
					+ "\"))";
			driver.findElementByAndroidUIAutomator(str);
			Report.log("Scrolled till text : " + ElementText);

		} catch (Exception e) {
			throw new FrameworkException("Unknown exception occurs while Scrolled to element : " + ElementText + "---"
					+ e.getClass() + "---" + e.getMessage());
		}
	}

	/**
	 * Function to click on particular element.
	 */	
	public static void click(WebElement element, String desc) {
		try {

			if (element.isDisplayed()) {
				if (element.isEnabled()) {
					element.click();
					Report.log("Element : " + desc + " is Clicked.");
				} else
					throw new FrameworkException("Element : " + desc + " is appears but Disabled.");
			} else
				throw new FrameworkException("Element : " + desc + " is not Displayed.");

		} catch (Exception e) {
			throw new FrameworkException("Unknown exception occured while clicking: " + desc + "---" + e.getClass()
					+ "---" + e.getMessage());
		}
	}

	/**
	 * Function to Tap on particular element.
	 */	
	public static void tap(WebElement element, String desc) {
		try {

			TouchAction touch = new TouchAction(driver);
			if (element.isDisplayed()) {
				if (element.isEnabled()) {
					touch.tap(tapOptions().withElement(element(element))).perform();
					Report.log("Element : " + desc + " is Tapped.");
				} else
					throw new FrameworkException("Element : " + desc + " is appears but Disabled.");
			} else
				throw new FrameworkException("Element : " + desc + " is not Displayed.");

		} catch (Exception e) {
			throw new FrameworkException(
					"Unknown exception occured while tapping: " + desc + "---" + e.getClass() + "---" + e.getMessage());
		}
	}

	/**
	 * Function to Long Tap on particular element for mentioned Duration.
	 */	
	public static void longTap(WebElement element, int duration, String desc) {
		try {

			TouchAction touch = new TouchAction(driver);
			if (element.isDisplayed()) {
				if (element.isEnabled()) {
					touch.longPress(longPressOptions().withElement(element(element)).withDuration(ofSeconds(duration)))
							.release().perform();
					Report.log("Element : " + desc + " is Long Tapped.");
				} else
					throw new FrameworkException("Element : " + desc + " is appears but Disabled.");
			} else
				throw new FrameworkException("Element : " + desc + " is not Displayed.");

		} catch (Exception e) {
			throw new FrameworkException("Unknown exception occured while Long tapping: " + desc + "---" + e.getClass()
					+ "---" + e.getMessage());
		}
	}

	/**
	 * Function to swipe from point start to point End.
	 */	
	public static void swipe(WebElement startPoint, WebElement endPoint, String desc) {
		try {

			TouchAction touch = new TouchAction(driver);
			if (startPoint.isDisplayed()) {
				if (startPoint.isEnabled()) {
					touch.longPress(longPressOptions().withElement(element(startPoint)).withDuration(ofSeconds(2)))
							.moveTo(element(endPoint)).release().perform();
					Report.log("Successfully swiped on screen");
				} else
					throw new FrameworkException("Element : " + startPoint.toString() + " is appears but Disabled.");
			} else
				throw new FrameworkException("Element : " + startPoint.toString() + " is not Displayed.");

		} catch (Exception e) {
			throw new FrameworkException(
					"Unknown exception occured while swiping" + desc + "---" + e.getClass() + "---" + e.getMessage());
		}
	}
	
	/**
	 * Function to Drag and Drop Element.
	 */	
	public static void dragAndDrop(WebElement dragElement, WebElement dropElement, String desc) {
		try {

			TouchAction touch = new TouchAction(driver);
			if (dragElement.isDisplayed()) {
				if (dragElement.isEnabled()) {
					touch.longPress(element(dragElement)).moveTo(element(dropElement)).release().perform();
					Report.log("Successfully Drag and Drop Element");
				} else
					throw new FrameworkException("Element : " + dragElement.toString() + " is appears but Disabled.");
			} else
				throw new FrameworkException("Element : " + dragElement.toString() + " is not Displayed.");

		} catch (Exception e) {
			throw new FrameworkException(
					"Unknown exception occured while swiping" + desc + "---" + e.getClass() + "---" + e.getMessage());
		}
	}

	/**
	 * Function to frame Android UIAutomator Element from Attribute and Value.
	 */	
	public static WebElement getAndroidUIElement(String attribute, String value) {
		try {
			
			String args = attribute + "(\"" + value + "\")";
			WebElement androidElement = driver.findElementByAndroidUIAutomator(args);
			return androidElement;
			
		} catch (Exception e) {
			throw new FrameworkException("Unknown exception occured while framing Android UI Automator Element." + "---"
					+ e.getClass() + "---" + e.getMessage());
		}
	}
	
	/**
	 * Function to send keys or type string in Element.
	 */	
	public static void type(WebElement element, String text, String desc) {
		try {

			if (element.isDisplayed()) {
				if (element.isEnabled()) {
					element.sendKeys(text);
					Report.log(desc + " successfully typed");
				} else
					throw new FrameworkException("Element : " + element.toString() + " is appears but Disabled.");
			} else
				throw new FrameworkException("Element : " + element.toString() + " is not Displayed.");

		} catch (Exception e) {
			throw new FrameworkException(
					"Unknown exception occured while Typing :" + desc + "---" + e.getClass() + "---" + e.getMessage());
		}
	}
	
}

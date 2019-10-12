package reusablecomponents;

import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static java.time.Duration.ofSeconds;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import config.FrameworkException;
import config.Report;
import config.TestSetup;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

/************************************************
 *  Method Completed....
 *  Click, Scroll, HardWait, Tap, LongTap, Swipe, Drag and Drop, PressBackButton, Type , Clear
 *  verifyAttribute, getAttribute, verifyTextAttribute, 
 *  
 *  Pending.....
 *   
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
	 * Function to Hide keyboard on screen.
	 * 
	 */
	public static void hideKeyboard() {
		try {
			driver.hideKeyboard();
			Report.log("Keyboard is hidden successfully.");
			
		} catch (Exception e) {
			throw new FrameworkException(
					"Exception occured while hiding keyboard on screen");
		}
	}
		
	/**
	 * Function to wait for any element to be visbile, invisible or enable.
	 * 
	 */	
	public static void waitTill(AndroidElement element, String state) {
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
					"Element state: *  '"+state + element.toString() + "' not found within defined time limit.");
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
	public static void click(AndroidElement element, String desc) {
		try {

			if (element.isDisplayed()) {
				if (element.isEnabled()) {
					element.click();
					Report.log("Element : " + element.toString() + " is Clicked.");
				} else
					throw new FrameworkException("Element : " +  element.toString() + " is appears but Disabled.");
			} else
				throw new FrameworkException("Element : " +  element.toString() + " is not Displayed.");

		} catch (Exception e) {
			throw new FrameworkException("Unknown exception occured while clicking: " +  element.toString() + "---" + e.getClass()
					+ "---" + e.getMessage());
		}
	}

	/**
	 * Function to Tap on particular element.
	 */	
	public static void tap(AndroidElement element, String desc) {
		try {

			TouchAction touch = new TouchAction(driver);
			if (element.isDisplayed()) {
				if (element.isEnabled()) {
					touch.tap(tapOptions().withElement(element(element))).perform();
					Report.log("Element : " +  element.toString() + " is Tapped.");
				} else
					throw new FrameworkException("Element : " +  element.toString() + " is appears but Disabled.");
			} else
				throw new FrameworkException("Element : " +  element.toString() + " is not Displayed.");

		} catch (Exception e) {
			throw new FrameworkException(
					"Unknown exception occured while tapping: " +  element.toString() + "---" + e.getClass() + "---" + e.getMessage());
		}
	}

	/**
	 * Function to Long Tap on particular element for mentioned Duration.
	 */	
	public static void longTap(AndroidElement element, int duration, String desc) {
		try {

			TouchAction touch = new TouchAction(driver);
			if (element.isDisplayed()) {
				if (element.isEnabled()) {
					touch.longPress(longPressOptions().withElement(element(element)).withDuration(ofSeconds(duration)))
							.release().perform();
					Report.log("Element : " +  element.toString() + " is Long Tapped.");
				} else
					throw new FrameworkException("Element : " +  element.toString() + " is appears but Disabled.");
			} else
				throw new FrameworkException("Element : " +  element.toString() + " is not Displayed.");

		} catch (Exception e) {
			throw new FrameworkException("Unknown exception occured while Long tapping: " +  element.toString() + "---" + e.getClass()
					+ "---" + e.getMessage());
		}
	}

	/**
	 * Function to swipe from point start to point End.
	 */	
	public static void swipe(AndroidElement startPoint, AndroidElement endPoint, String desc) {
		try {

			TouchAction touch = new TouchAction(driver);
			if (startPoint.isDisplayed()) {
				if (startPoint.isEnabled()) {
					touch.longPress(longPressOptions().withElement(element(startPoint)).withDuration(ofSeconds(2)))
							.moveTo(element(endPoint)).release().perform();
					Report.log("Successfully swiped on screen" + startPoint.toString());
				} else
					throw new FrameworkException("Element : " + startPoint.toString() + " is appears but Disabled.");
			} else
				throw new FrameworkException("Element : " + startPoint.toString() + " is not Displayed.");

		} catch (Exception e) {
			throw new FrameworkException(
					"Unknown exception occured while swiping" + startPoint.toString() + "---" + e.getClass() + "---" + e.getMessage());
		}
	}
	
	/**
	 * Function to Drag and Drop Element.
	 */	
	public static void dragAndDrop(AndroidElement dragElement, AndroidElement dropElement, String desc) {
		try {
			
			TouchAction touch = new TouchAction(driver);
			if (dragElement.isDisplayed()) {
				if (dragElement.isEnabled()) {
					touch.longPress(element(dragElement)).moveTo(element(dropElement)).release().perform();
					Report.log("Successfully Drag and Drop Element : " +dragElement.toString());
				} else
					throw new FrameworkException("Element : " + dragElement.toString() + " is appears but Disabled.");
			} else
				throw new FrameworkException("Element : " + dragElement.toString() + " is not Displayed.");

		} catch (Exception e) {
			throw new FrameworkException(
					"Unknown exception occured while swiping" + dragElement.toString() + "---" + e.getClass() + "---" + e.getMessage());
		}
	}

	/**
	 * Function to frame Android UIAutomator Element from Attribute and Value.
	 */	
	public static AndroidElement getAndroidUIElement(String attribute, String value) {
		try {
			
			String args = attribute + "(\"" + value + "\")";
			AndroidElement androidElement = driver.findElementByAndroidUIAutomator(args);
			Report.log("framed AndroidUIAutomator : " + androidElement.toString());
			return androidElement;
			
		} catch (Exception e) {
			throw new FrameworkException("Unknown exception occured while framing Android UI Automator Element." + "---"
					+ e.getClass() + "---" + e.getMessage());
		}
	}
	
	/**
	 * Function to send keys or type string in Element.
	 */	
	public static void type(AndroidElement element, String text, String desc) {
		try {

			if (element.isDisplayed()) {
				if (element.isEnabled()) {
					clear(element, desc);
					element.sendKeys(text);
					hideKeyboard();
					Report.log(desc + " successfully typed: " + text);
				} else
					throw new FrameworkException("Element : " + element.toString() + " is appears but Disabled.");
			} else
				throw new FrameworkException("Element : " + element.toString() + " is not Displayed.");

		} catch (Exception e) {
			throw new FrameworkException(
					"Unknown exception occured while Typing :" + element.toString() + "---" + e.getClass() + "---" + e.getMessage());
		}
	}

	/**
	 * Function to clear Edit text field.
	 * 
	 */
	public static void clear(AndroidElement element, String desc) {
		try {
			
			tap(element, desc);
			element.clear();
			Report.log("Element TEXT for" + element.toString() + " is Cleared");
		
		} catch (Exception e) {
			throw new FrameworkException("Unknown exception encountered while clearing " + desc + "---" + e.getClass()
					+ "---" + e.getMessage());
		}
	}
	

	/**
	 * Function to verify all attributes like class,id,name except text of element.
	 * 
	 */
	// verifyAttribute(element, className-equals,"Your Name","Name label value");
	public static void verifyAttribute(AndroidElement element, String attributeDesc, String value, String desc) {
		try {
			
			boolean result = false;
			if (value != null) {
				String attribute;
				String matchType;
				if (attributeDesc.split("-").length > 1) {
					attribute = attributeDesc.split("-")[0];
					matchType = attributeDesc.split("-")[1];
					
					switch (matchType.toLowerCase()) {
					case "equals":
							result = element.getAttribute(attribute).toLowerCase().trim().equals(value.toLowerCase().trim());
						break;
					case "contains":
							result = element.getAttribute(attribute).toLowerCase().replace(" ", "")
									.replaceAll("\u00a0", "").replaceAll("&nbsp", "").contains(value.toLowerCase()
											.replace(" ", "").replaceAll("\u00a0", "").replaceAll("&nbsp", ""));
						break;
					default:
						throw new FrameworkException("Attribute matching criteria not configured.");
					}
				} else {
					attribute = attributeDesc;
						result = element.getAttribute(attribute).toLowerCase().trim().replaceAll("\u00a0", "")
								.replaceAll("&nbsp", "").equalsIgnoreCase(
										value.toLowerCase().trim().replaceAll("\u00a0", "").replaceAll("&nbsp", ""));
				}
			
				if (result) {
					Report.pass(attribute.toUpperCase() + " matched for " + desc.toUpperCase() + " and is '" + value + "'");
					Report.log("Element Attribute" +attribute.toUpperCase()+" for " +element.toString() + " is Matched");
				} else {
					throw new FrameworkException("Expected " + attribute.toUpperCase() + " for " + desc + " is: '"
							+ value + "' Actual " + attribute.toUpperCase() + " is: --- text = '" + element.getText()
							+ "' ---- attribute ='" + element.getAttribute(attribute) + "'");
				}
			} else {
				throw new FrameworkException("Value to be matched of attribute can not be Null for: " + desc);
			}
		
		} catch (Exception e) {
			throw new FrameworkException("Unknown exception occured while verifying attribute for: " + desc + "---"
					+ e.getClass() + "---" + e.getMessage());
		}
	}
	
	/**
	 * Function to verify text of element.
	 * verifyAttribute(element, "equals","Your Name","Name label value");
	 */

	public static void verifyTextAttribute(AndroidElement element, String matchType, String value, String desc) {
		try {
			
			boolean result = false;
			if (value != null) {
				switch (matchType.toLowerCase()) {
				case "equals":
					result = element.getText().toLowerCase().trim().equals(value.toLowerCase().trim());
					break;
				case "contains":
					result = element.getText().toLowerCase().replace(" ", "").contains(value.toLowerCase().replace(" ", ""));
					break;
				default:
					throw new FrameworkException("Text matching criteria not properly configured.");
				}

				if (result) {
					Report.pass("Element text matched " + matchType.toUpperCase() + " for " + desc.toUpperCase()
							+ " and is '" + value + "'");
					Report.log("Element text matched " + matchType.toUpperCase() + " for " + element.toString());
				} else {
					throw new FrameworkException("Expected Text for " + desc + " is: '" + value
							+ "' but found Actual text is: --- text = '" + element.getText() + "'");
				}
			} else {
				throw new FrameworkException(
						"Exception occured due to value to be matched of attribute can not be Null for: " + desc);
			}
			
		} catch (Exception e) {
			throw new FrameworkException("Unknown exception occured while verifying text for: " + desc + "---"
					+ e.getClass() + "---" + e.getMessage());
		}
	}

	/**
	 * Function to get Attribute values of element like text,class name,id.
	 * 
	 */
	public static String getAttribute(AndroidElement element, String attribute, String desc) {
		try {
			
			String value;
			if (attribute.equals("text")) {
				if (element.getText() != null) {
					value = element.getText();
				} else {
					value = "";
				}
			} else {
				if (element.getAttribute(attribute) != null) {
					value = element.getAttribute(attribute);
				} else {
					value = "";
				}
			}
			Report.log("Attribute " + attribute + " for " + desc + " returned " + value);
			return value;
		
		} catch (Exception e) {
			throw new FrameworkException("Unknown exception occured while retrieving Attribute: "
					+ attribute.toUpperCase() + " value for " + desc + "---" + e.getClass() + "---" + e.getMessage());
		}
	}
}

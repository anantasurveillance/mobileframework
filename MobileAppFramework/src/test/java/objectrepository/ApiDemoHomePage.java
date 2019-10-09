package objectrepository;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import reusablecomponents.TechnicalComponents;

public class ApiDemoHomePage {
	
	AndroidDriver<AndroidElement> driver;
	AndroidElement preference;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Preference']")
	public static WebElement preference_menu;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Accessibility']")
	public static WebElement accessibility_menu;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Animation']")
	public static WebElement animation_menu;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='App']")
	public static WebElement app_menu;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Content']")
	public static WebElement content_menu;
	
	
	public ApiDemoHomePage(AndroidDriver<AndroidElement> driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		preference = driver.findElementByAndroidUIAutomator("text(\"Preference\")");
	}
	
	public void tapPreference() {
	 //	TechnicalComponents.click(preference_menu, "Preference menu");
		TechnicalComponents.click(preference, "Preference menu");
	}
	
	public void tapAccessibility() {
		accessibility_menu.click();
	}
	public void tapAnimation() {
		animation_menu.click();
	}
	public void tapApp() {
		app_menu.click();
	}
	public void tapContent() {
		content_menu.click();
	}
	
}

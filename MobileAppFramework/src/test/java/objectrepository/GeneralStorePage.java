package objectrepository;

import org.openqa.selenium.support.PageFactory;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import reusablecomponents.TechnicalComponents;

public class GeneralStorePage {
	
	public GeneralStorePage(AndroidDriver<AndroidElement> driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	AndroidDriver<AndroidElement> driver;
	String activity_title="General Store";
	
	@AndroidFindBy(id="com.androidsample.generalstore:id/nameField")
	public AndroidElement editName;
	
	@AndroidFindBy(id = "com.androidsample.generalstore:id/radioFemale")
	public AndroidElement radioFemale;
	
	@AndroidFindBy(xpath = "//android.widget.RadioButton[@text='Male']")
	public AndroidElement radioMale;
	
	@AndroidFindBy(id = "com.androidsample.generalstore:id/btnLetsShop")
	public AndroidElement buttonLetsShop;
	
	@AndroidFindBy(id = "com.androidsample.generalstore:id/spinnerCountry")
	public AndroidElement ListboxCountry;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='General Store']")
	public AndroidElement labelTitle;
	
	public void enterName(String name) {
		
	}
	
	public void goToProductPage(String name, String gender, String country) {
		
		TechnicalComponents.waitTill(labelTitle, "visible");
		TechnicalComponents.type(editName, name, "User's Name");
		TechnicalComponents.click(ListboxCountry, "country Listbox");
		TechnicalComponents.scrollToText(country);
		TechnicalComponents.click(TechnicalComponents.getAndroidUIElement("text",country), "country value");
		if(gender.equalsIgnoreCase("male"))
			TechnicalComponents.tap(radioMale, "Radio button Male");
		else
			TechnicalComponents.tap(radioFemale, "Radio button Female");
		
		TechnicalComponents.click(buttonLetsShop, "Lets Shop button");
	}
		
}

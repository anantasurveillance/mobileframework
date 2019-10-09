package reusablecomponents;

import objectrepository.GeneralStorePage;

public class BusinessComponents extends TechnicalComponents {

public void goToProductPage(String name, String gender,String country) {
		
		GeneralStorePage store = new GeneralStorePage(driver);
		store.goToProductPage(name, gender, country);
		
	}
	
}

package practice.testscript;

import static org.testng.Assert.assertTrue;

import org.testng.SkipException;
import org.testng.annotations.Test;

import config.FrameworkException;
import config.Report;
import objectrepository.GeneralStorePage;
import reusablecomponents.BusinessComponents;

public class Ecommerce_Tests extends BusinessComponents {
	String testDesc,complexity;

	@Test
	public void testProductNavigation() {
		testDesc = "Verify user is able to navigate to product page after filling form.";
		complexity = "Medium";
		setParametersPerTestCase(testDesc, complexity);
		
		if (toBeTested) {
			try {
				// Method Code starts
				
				goToProductPage("Appium", "female", "Albania");
				
				// Method Code ends

				Report.pass(testDesc + " Passed.", driver);
				assertTrue(true, "Test Case: " + testDesc + " Passed.");
			} catch (FrameworkException fx) {
				Report.failLog(fx.getMessage());
				Report.fail(testDesc + " Failed.", driver);
				assertTrue(false, "Test Case: " + testDesc + " Failed.");
			}
		} else {
			Report.skip("Test: " + testDesc + " Skipped.");
			Report.skipLog("Test: " + testDesc + " Skipped.");
			throw new SkipException("Test: " + testDesc);
		}
	}
	
	@Test
	public void testVariousscenarios() {
		testDesc = "Clear text field";
		complexity = "Medium";
		setParametersPerTestCase(testDesc, complexity);
		
		if (toBeTested) {
			try {
				// Method Code starts
				GeneralStorePage store = new GeneralStorePage(driver);
				store.clearName("Shailendra");
				hardWait(10);
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

package salesforceTestSuite;

import org.testng.Assert;
import org.testng.annotations.Test;

import commonLibraries.Common;
import commonLibraries.TestEnvironment;


public class MySuite extends TestEnvironment {	
	
		
	
	@Test(priority = 1)
	public void test1() {
				
		String expectedAccountName = "John Smith";
		String expectedStatus = "Draft";
		String expectedTerm = "12";
		String screenshotPath = "screenshot.png";

		//Initiate Renew Contract
		Common.navigateTo(SuiteConstants.accountURL);
		Common.isRedirected(SuiteConstants.accountURL, 20);
		Common.waitElementToBeDisplayed(SuiteConstants.mainLogo_XPATH, 20);
		driver.switchTo().frame("iFrameResizer0");
		Common.jsClick(SuiteConstants.renewContract_XPATH, 20);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("iFrameResizer1");
		//Create New Contract
		String actualTitle = Common.getElementText(SuiteConstants.createNewContractTitle_XPATH, 20);
		Assert.assertEquals(actualTitle, SuiteConstants.createNewContractTitle);
		Common.enterFieldValue(SuiteConstants.contractName_XPATH, 20, "test contract 01");
		Common.selectDropDownItemByText(SuiteConstants.termDropdown_XPATH, "1 year");
		Common.jsClick(SuiteConstants.draftStatusRadioBtn_XPATH, 10);
		Common.jsClick(SuiteConstants.doneBtn_XPATH, 10);
		driver.switchTo().defaultContent();
		//Field assertions
		Common.waitElementToBeDisplayed(SuiteConstants.contractNoLabel_XPATH, 20);
		Common.takeScreenshot(screenshotPath);
		String ActualContractNo = Common.getElementText(SuiteConstants.contractNoLabel_XPATH, 20);
		String ActualAccountName = Common.getElementText(SuiteConstants.accountNameLabel_XPATH, 10);
		String ActualStatus = Common.getElementText(SuiteConstants.statusLabel_XPATH, 10);
		String ActualTerm = Common.getElementText(SuiteConstants.termLabel_XPATH, 10);
		
		Common.logit("ContractNo: " + ActualContractNo);
		Common.logit("AccountName: " + ActualAccountName);
		Common.logit("Status: " + ActualStatus);
		Common.logit("Term: " + ActualTerm);
		
		Assert.assertFalse(ActualContractNo.isEmpty());
		Assert.assertEquals(ActualAccountName, expectedAccountName);
		Assert.assertEquals(ActualStatus, expectedStatus);
		Assert.assertEquals(ActualTerm, expectedTerm);

	
	}
	
	
	@Test(priority = 2)
	public void test2() {
		
		String expectedPopupErrorMessage = "Error : Please fix all the fields with errors.";
		String expectedPageErrorMessage = "Choose a valid contract status and save your changes. Ask your admin for details.";
				
		//Initiate Renew Contract
		Common.navigateTo(SuiteConstants.accountURL);
		Common.isRedirected(SuiteConstants.accountURL, 20);
		Common.waitElementToBeDisplayed(SuiteConstants.mainLogo_XPATH, 20);
		driver.switchTo().frame("iFrameResizer0");
		Common.jsClick(SuiteConstants.renewContract_XPATH, 20);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("iFrameResizer1");
		//Create New Contract
		String actualTitle = Common.getElementText(SuiteConstants.createNewContractTitle_XPATH, 20);
		Assert.assertEquals(actualTitle, SuiteConstants.createNewContractTitle);
		Common.enterFieldValue(SuiteConstants.contractName_XPATH, 20, "test contract 02");
		Common.selectDropDownItemByText(SuiteConstants.termDropdown_XPATH, "2 years");
		Common.jsClick(SuiteConstants.signedStatusRadioBtn_XPATH, 10);
		Common.jsClick(SuiteConstants.doneBtn_XPATH, 10);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("iFrameResizer1");		
		//Verify POPUP content
		Common.waitElementToBeDisplayed(SuiteConstants.popupOKBtn_XPATH, 10);
		String actualPopupErrorMessage = Common.getElementText(SuiteConstants.popupMessage_XPATH, 10);
		Assert.assertEquals(actualPopupErrorMessage, expectedPopupErrorMessage);
		Common.jsClick(SuiteConstants.popupOKBtn_XPATH, 10);
		String actualPageErrorMessage = Common.getElementText(SuiteConstants.errorMessageOnThePage_XPATH, 10);		
		Assert.assertEquals(actualPageErrorMessage, expectedPageErrorMessage);
		
		
	}
	
	

	

	
}
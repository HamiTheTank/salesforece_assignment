package myTestSuite;

import org.testng.Assert;
import org.testng.annotations.Test;

import commonLibraries.Common;
import commonLibraries.TestEnvironment;


public class suite_Template extends TestEnvironment {	
	
		
	
	@Test(priority = 1)
	public void test1() {
				
		String expectedAccountName = "John Smith";
		String expectedStatus = "Draft";
		String expectedTerm = "12";
		String screenshotPath = "screenshot.png";

		//Initiate Renew Contract
		Common.navigateTo(suite_Constants.accountURL);
		Common.isRedirected(suite_Constants.accountURL, 20);
		Common.waitElementToBeDisplayed(suite_Constants.mainLogo_XPATH, 20);
		driver.switchTo().frame("iFrameResizer0");
		Common.jsClick(suite_Constants.renewContract_XPATH, 20);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("iFrameResizer1");
		//Create New Contract
		String actualTitle = Common.getElementText(suite_Constants.createNewContractTitle_XPATH, 20);
		Assert.assertEquals(actualTitle, suite_Constants.createNewContractTitle);
		Common.enterFieldValue(suite_Constants.contractName_XPATH, 20, "test contract 01");
		Common.selectDropDownItemByText(suite_Constants.termDropdown_XPATH, "1 year");
		Common.jsClick(suite_Constants.draftStatusRadioBtn_XPATH, 10);
		Common.jsClick(suite_Constants.doneBtn_XPATH, 10);
		driver.switchTo().defaultContent();
		//Field assertions
		Common.waitElementToBeDisplayed(suite_Constants.contractNoLabel_XPATH, 20);
		Common.takeScreenshot(screenshotPath);
		String ActualContractNo = Common.getElementText(suite_Constants.contractNoLabel_XPATH, 20);
		String ActualAccountName = Common.getElementText(suite_Constants.accountNameLabel_XPATH, 10);
		String ActualStatus = Common.getElementText(suite_Constants.statusLabel_XPATH, 10);
		String ActualTerm = Common.getElementText(suite_Constants.termLabel_XPATH, 10);
		
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
		Common.navigateTo(suite_Constants.accountURL);
		Common.isRedirected(suite_Constants.accountURL, 20);
		Common.waitElementToBeDisplayed(suite_Constants.mainLogo_XPATH, 20);
		driver.switchTo().frame("iFrameResizer0");
		Common.jsClick(suite_Constants.renewContract_XPATH, 20);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("iFrameResizer1");
		//Create New Contract
		String actualTitle = Common.getElementText(suite_Constants.createNewContractTitle_XPATH, 20);
		Assert.assertEquals(actualTitle, suite_Constants.createNewContractTitle);
		Common.enterFieldValue(suite_Constants.contractName_XPATH, 20, "test contract 02");
		Common.selectDropDownItemByText(suite_Constants.termDropdown_XPATH, "2 years");
		Common.jsClick(suite_Constants.signedStatusRadioBtn_XPATH, 10);
		Common.jsClick(suite_Constants.doneBtn_XPATH, 10);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("iFrameResizer1");		
		//Verify POPUP content
		Common.waitElementToBeDisplayed(suite_Constants.popupOKBtn_XPATH, 10);
		String actualPopupErrorMessage = Common.getElementText(suite_Constants.popupMessage_XPATH, 10);
		Assert.assertEquals(actualPopupErrorMessage, expectedPopupErrorMessage);
		Common.jsClick(suite_Constants.popupOKBtn_XPATH, 10);
		String actualPageErrorMessage = Common.getElementText(suite_Constants.errorMessageOnThePage_XPATH, 10);		
		Assert.assertEquals(actualPageErrorMessage, expectedPageErrorMessage);
		
		
	}
	
	

	

	
}
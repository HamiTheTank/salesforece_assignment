package salesforceTestSuite;

public class SuiteConstants {
	
	//=====================================================================================================================
	//======================================== GENERAL ====================================================================
	//=====================================================================================================================

	public static final String accountURL = "https://knowledge-web-14204.lightning.force.com/lightning/r/Account/0014x000009sUZOAA2/view";
	public static final String createNewContractTitle = "Create New Contract";	
		
	//=====================================================================================================================
	//======================================== LOCATORS ===================================================================
	//=====================================================================================================================

	public static final String mainLogo_XPATH = "//*[@id='oneHeader']/div[2]/div[1]/div";
	//account page
	public static final String renewContract_XPATH = "/html/body/div[1]/div[1]/ng-include/div/div/div/div/div[2]/div[2]/div[2]/a";
	//new contract page
	public static final String createNewContractTitle_XPATH = "/html/body/span[1]/div/span/div/ng-view/div/div/bptree/child[1]/div/section/section/div/div/div/h1";
	public static final String contractName_XPATH = "//*[@id='ContractName']";
	public static final String termDropdown_XPATH = "//*[@id='SelectOption']";	
	public static final String draftStatusRadioBtn_XPATH = "(//*[@id='Status'])[1]";
	public static final String signedStatusRadioBtn_XPATH = "(//*[@id='Status'])[2]";
	public static final String activeStatusRadioBtn_XPATH = "(//*[@id='Status'])[3]";
	public static final String doneBtn_XPATH = "//*[@id='CreateNew_nextBtn']";	
	//draft page
	public static final String contractNoLabel_XPATH = "//*[@id='brandBand_2']/div/div/div[3]/div/div[1]/div/div[1]/div[1]/header/div[2]/div/div[1]/div[2]/h1/div[2]/span";
	public static final String accountNameLabel_XPATH = "/html/body/div[4]/div[1]/section/div[1]/div[2]/div[2]/div[1]/div/div/div/div[3]/div/div[1]/div/div[1]/div[1]/header/div[2]/ul/li[1]/div/div/div/div/a";
	public static final String statusLabel_XPATH = "//*[@id='brandBand_2']/div/div/div[3]/div/div[1]/div/div[1]/div[1]/header/div[2]/ul/li[2]/div/div/div/span";
	public static final String termLabel_XPATH = "//*[@id='brandBand_2']/div/div/div[3]/div/div[1]/div/div[1]/div[1]/header/div[2]/ul/li[5]/div/div/div/span";
	//error messages
	public static final String popupMessage_XPATH = "/html/body/span[1]/div/div[1]/div/div[2]/p";
	public static final String popupOKBtn_XPATH = "//*[@id='alert-ok-button']";
	public static final String errorMessageOnThePage_XPATH = "/html/body/span[1]/div/span/div/ng-view/div/div/bptree/child[1]/div/section/form/div[1]/div/child[4]/div/ng-form/div/div[2]/div/small";

	

}

/**
 * 
 */
package global;


public enum Locators {	
	
	
	//=====================================================================================================================
	//======================================== GENERAL ====================================================================
	//=====================================================================================================================

	globalConstant1("value1"),
	globalConstant2("value2"),
	
	//=====================================================================================================================
	//======================================== LOCATORS ===================================================================
	//=====================================================================================================================


	SSOBoxXPATH(""),
	PassBoxXPATH("");
	
	
	
	Locators(String xpath) {
		valueOf(xpath);
	}
	
	static String get(String xpath) {
		return valueOf("xpath").toString();
	}

}

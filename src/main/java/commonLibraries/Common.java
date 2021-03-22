/**
 * Contains general methods used in the test automation.
 * Author: Viktor.Torma@ge.com
 */

package commonLibraries;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import global.Constants;
import org.apache.commons.io.FileUtils;


public class Common extends TestEnvironment {
	
	
	
	public WebDriver getDriver() {
		
		return TestEnvironment.driver;
		
	}
	

	
	public static String getEnvironment() {
		
		return TestEnvironment.environment;
	}
	

	
	public static String getBrowser() {
		return TestEnvironment.browser;
	}
	
	
	
	public static String getOS() {
		
		return TestEnvironment.os;
		
	}
	
	
 	 	
 	public static void navigateTo(String URL) {
 		
 		System.out.println("Navigating to: " + URL);
 		driver.get(URL);
 		
 	}

	
 	
	public static String getTitle() {
		
		return driver.getTitle();
		
	}
	
	
	
	public static String getURL() {
		
		return driver.getCurrentUrl();
		
	}
	
	
	
	public static void switchToFrame(String frame) {
		
		try {			
			TestEnvironment.driver.switchTo().frame(frame);
		} catch (Exception e) {			
			logit(4, "Switching to frame '" + frame + "' failed");
			e.printStackTrace();			
		}
		
	}
	
	
	
	public static void switchToDefaultFrame() {
		
		try {
			TestEnvironment.driver.switchTo().defaultContent();
		} catch (Exception e) {			
			logit(4, "Switching to default frame failed");
			e.printStackTrace();
		}
		
	}



	/** 
	 * @param type
	 * 0 - stdout only;
	 * 1 - stdout and HTML report;
	 * 2 - stderr only;
	 * 3 - stderr and HTML report;
	 * 4 - stderr, HTML report and fails the current test;
	 * Any other number logs into stdout only;
	 * @param message - The message to be printed.
	 */
	public static void logit(Integer type, String message) {
		

		switch (type) {
		
		case 0:
			System.out.println(message);
			break;
		case 1:
			Reporter.log(message, true);
			break;			
		case 2:
			System.err.println(message);
			break;
		case 3:
			Reporter.log(message, false);
			System.err.println(message);
			break;
		case 4:
			Reporter.log(message, false);
			System.err.println(message);
			Assert.fail(message);
			break;			
		default:
			System.out.println(message);
			return;
		}

	}
	
	
	
	/**
	 * Logs the message to stdout and HTML report
	 * @param message
	 */
	public static void logit(String message) {
		
		logit(1, message);
		
	}
	
	
	
	public static WebElement findByCSS(String cssSelector, Integer timeout) {
		
		if (Double.isNaN(timeout) || timeout < 1) {			
			timeout = global.Constants.defaultTimeout;
			
		}
		
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.ignoring(StaleElementReferenceException.class);
		
		try {
			
			WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelector)));			
			logit("Element by CSS selector found: \"" + cssSelector + "\"");
			return element;	
			
		} catch (Exception e) {
			
			logit("Unable to find element within " + timeout.toString() + " seconds by CSS selector: \"" + cssSelector + "\"");
			e.printStackTrace();
			return null;
			
		}		
		
	}
	
	
	
	public static WebElement findByCSS(String cssSelector) {
		
		return findByCSS(cssSelector, global.Constants.defaultTimeout);
		
	}
	
	
	
	public static WebElement findByXPATH(String xpath, Integer timeout) {
		
		if (Double.isNaN(timeout) || timeout < 1) {			
			timeout = global.Constants.defaultTimeout;
		}
		
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.ignoring(StaleElementReferenceException.class);
		
		try {
			
			WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
			System.out.println("Element by xpath found: \"" + xpath + "\"");
			return element;			
			
		} catch (Exception e) {			
			System.out.println("Unable to find element within " + timeout.toString() + " seconds by xpath: \"" + xpath + "\"");
			//e.printStackTrace();
			return null;			
		}
		
	}
	
	
	
	public static WebElement findByXPATH(String xpath) {		
		return findByXPATH(xpath, global.Constants.defaultTimeout);		
	}
	
	
	public static List<WebElement> findByXPATH_multiple(String xpath){		
		return driver.findElements(By.xpath(xpath));		
	}
	

	

	public static WebElement findByID(String id, Integer timeout) {
		
		if (Double.isNaN(timeout) || timeout < 1) {			
			timeout = global.Constants.defaultTimeout;			
		}		
		
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.ignoring(StaleElementReferenceException.class);
			
		try {
			
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));			
			logit("Element by id found: \"" + id + "\"");
			return element;			
			
		} catch (Exception e) {
			
			logit("Unable to find element within " + timeout.toString() + " seconds by id: \"" + id + "\"");
			return null;
			
		}
		
	}
	
	
	
	public static WebElement findByID(String id) {
		
		return findByID(id, global.Constants.defaultTimeout);
		
	}
	
	
	
	public static void jsClick(WebElement element) {
		
		try {
			
			if (waitElementToBeClickable(element, 10)) {
				scrollIntoView(element);
				TestEnvironment.jse.executeScript("arguments[0].click();", element);
			}
			
		} catch (Exception e) {
			
			logit("Invoking javaScript click() method failed"); 
			e.printStackTrace();
			
		}
		
	}
	
	
	public static void jsClick(String xpath, Integer timeout) {
		
		WebElement element = findByXPATH(xpath, timeout);		
		jsClick(element);
		System.out.println("Element was scrolled into view and clicked: " + xpath);
		
	}
	
	
	public static void jsClick(String xpath) {
		jsClick(xpath, 10);
	}
			
	
	public static boolean isDisappeared(WebElement element, Integer timeout) {
		
		if (element == null) {
			logit("Element disappeared");
			return true;
		}
		
		
		if (Double.isNaN(timeout) || timeout < 1) {
			timeout = global.Constants.defaultTimeout;
		}
		
		
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		
		try	{
			
			logit("Waiting for element to disappear...");
						
			if(wait.until(ExpectedConditions.invisibilityOf(element))) {
				logit("Element disappeared");
				return true;
			}
			
		} catch (Exception e) {
			logit("Element did not disappear");			
		}
		
		return false;
		
	}
	
	
	
	public static boolean isDisappeared(String xpath, Integer timeout) {
			
		WebElement element = findByXPATH(xpath, 1);
		return isDisappeared(element, timeout);
		
	}
	
	
	
	public static boolean isDisplayed(WebElement element, boolean expected) {
		

		if (element == null) {
			logit("Element not displayed (not found)");
			
			if (expected == true) {				
				logit("Element not displayed (not found)");
			}
			
			return false;
			
		}
		
		if (element.isDisplayed()) {
			logit("Element displayed");
			return true;
		} else {
			logit("Element not displayed (exists but isdisplayed property = false)");
			return false;
		}		
	}
	
	
	
	public static boolean isDisplayed(String xpath, boolean expected, Integer timeout) {
			
		if (isDisplayed(findByXPATH(xpath, timeout), expected))	{
			logit("Element by XPATH displayed: \"" + xpath + "\"");
			return true;
		} else {
			logit("Element by XPATH not displayed: \"" + xpath + "\"");
			return false;
		}

	}
	
	
	
	public static void SSOlogin() {
		
		try {
			
			navigateTo(Constants.qaURL);

			logit("Logging in...");		
			Common.enterFieldValue(global.Constants.userInput_XPATH, 20, TestEnvironment.testSSO);
			Common.enterPassword(global.Constants.passwordInput_XPATH, 20, TestEnvironment.testPassword);
			logit("Submitting form");
			Common.jsClick(global.Constants.loginBtn_XPATH, 10);
			logit("Waiting to be redirected to the home page");
			
			if (!Common.isRedirected(Constants.dashboardURL, 60)) {
				logit(3, "Navigating to the home page failed. Terminating.");
				return;
			} else {
				logit("Logged in as " + TestEnvironment.testSSO);
			}
			
		} catch (Exception e) {
			
			logit(3, "Login failed");
			logit(3, "Current URL: " + driver.getCurrentUrl());
			e.printStackTrace();
			return;
			
		}
		
	}
		
	
	
	public static void scrollIntoView(WebElement element) {
		
		try {
			
			((JavascriptExecutor) TestEnvironment.driver).executeScript("arguments[0].scrollIntoView();", element);
			
		} catch (Exception e) {

			logit(4, "Failed to scroll element into view.");
			e.printStackTrace();
			
		}
		
	}
	
	
	
	public static void scrollIntoView(String xpath) {
			
			scrollIntoView(findByXPATH(xpath));
		
	}
	
	
	
	/**
	 * 
	 * @param destinationURL can be regex.
	 * @param timeout
	 * @return
	 */
	public static boolean isRedirected(String destinationURL, Integer timeout) {
		
		try {
			
			WebDriverWait wait = new WebDriverWait(driver, timeout);			
			logit("Waiting to be redirected to: " + destinationURL);			
			wait.until(ExpectedConditions.urlMatches(destinationURL));
			logit("Successfully redirected to: " + destinationURL);
			return true;
			
		} catch (org.openqa.selenium.TimeoutException e) {			
			logit("Failed to redirect within " + timeout + " seconds");
			return false;			
		}
		
	}	
	  
    
    public static void sleep(Integer ms) {
    	
		try {			
			totalSleep += ms;
			System.out.println("Sleeping " + ms + " ms");			
			Thread.sleep(ms);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
    
    public static void scrollToBottom() {
    	
		((JavascriptExecutor) TestEnvironment.driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
    	
    }
    
    
    public static void scrollToTop() {
    	
		((JavascriptExecutor) TestEnvironment.driver).executeScript("window.scrollTo(0, -document.body.scrollHeight)");
    	
    }
    
    
    public static boolean isHomePageLoaded(Integer timeout) {
    	
    	boolean isLoaded = false;
    	
    	
    	logit("Waiting for the home page to be loaded");
    	
    	String spinner_xpath = "/html/body/app-root/ngx-spinner/div/div/div";
    	
    	if (!waitSpinnerDisappear(spinner_xpath, timeout)) {
    		
    		logit(3, "Home Page not loaded");
    	
    	} else {
    		
    		if (isDisplayed("/html/body/app-root/div/div/app-project-info/mat-card/div/mat-toolbar/div[1]/span", true, 15)) {
    			
    			logit("Home page successfully loaded");
        		isLoaded = true;
        		
        	}
    		
    	}
    	
    	return isLoaded;
    	
    }
     
    
    public static Boolean waitSpinnerDisappear(String spinnerXPATH, Integer timeout) {
    	
    	Boolean result = false;
    	
    	WebDriverWait wait = new WebDriverWait(TestEnvironment.driver, timeout);
		wait.ignoring(StaleElementReferenceException.class);
		
		logit("Loading...");
		
		try {
			
			WebElement spinner = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(spinnerXPATH)));
			
			if ( !spinner.equals(null) ) {
				
				
				if (wait.until(ExpectedConditions.stalenessOf(spinner))) {
					logit("Loaded");
					result = true;
				} else {
					logit(3, "ERROR: spinner did not disappear within timeout: " + spinnerXPATH);
				}
				
				
			} else {
				logit(3, "ERROR: spinner not found: " + spinnerXPATH);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
		return result;
    	
    }
    
    
    public static Boolean waitSpinnerDisappear(String spinnerXPATH, Integer appear, Integer disappear) {
    	
    	Boolean result = false;    	
		
		WebElement spinner = findByXPATH(spinnerXPATH, appear);		
		if (spinner == null) {
			return true;
		}
		
		try {
			logit("Loading...");
	    	WebDriverWait wait = new WebDriverWait(TestEnvironment.driver, disappear);
			if (wait.until(ExpectedConditions.stalenessOf(spinner))) {
				logit("Loaded");
				result = true;
			} else {
				logit(3, "ERROR: spinner did not disappear within timeout: " + spinnerXPATH);
				result = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
		return result;
    	
    }
    
    
    public static void waitSpinnerDisappear(int appear, int disappear) {    	
    	waitSpinnerDisappear(global.Constants.spinnerXPATH, appear, disappear);    	
    }
    
    
    public static void selectDropDownItemByText(String xpath, String text) {
		
		try {
			
			Select dropdown = new Select(findByXPATH(xpath));
			Common.scrollIntoView(findByXPATH(xpath));
			dropdown.selectByVisibleText(text);
			
		} catch (Exception e) {
			
			logit(3, " Selecting dropdown value \"" + text + "\" failed");
			e.printStackTrace();
			
		}		
		
	}
    
    
	public static void selectDropdownItemByValue(String value, String dropdown_xpath) {
		
		System.out.println("Selecting dropdown by value:  " + value);		
		WebElement dropdown =  findByXPATH(dropdown_xpath, 10);
		scrollIntoView(dropdown);		
		Select select = new Select(dropdown);		
		select.selectByValue(value);
		
	}
	
	public static void selectDropdownItemByIndex(Integer index, String dropdown_xpath) {

		System.out.println("Selecting dropdown by index:  " + index);
		WebElement dropdown =  findByXPATH(dropdown_xpath, 10);
		scrollIntoView(dropdown);
		Select select = new Select(dropdown);		
		select.selectByIndex(index);

	}
    
    
    public static Integer getDropdownItemCount(String xpath) {
    	
    	return getDropDownItemCount(new Select(findByXPATH(xpath)));
    	    	
    }
    
    
    public static Integer getDropDownItemCount(Select dropdown) {
    	
    	Integer result = -1;
    	
    	try {
			result = dropdown.getOptions().size();
			logit("Dropdown item count: " + result.toString());
		} catch (Exception e) {
			logit(3, "Failed to get dropdown item count");
			e.printStackTrace();
		}    	
    	
    	return result;    	
    	
    }
        
    
    public static boolean waitElementTextToMatch(String XPATH, Pattern pattern, Integer timeout) {
		
		boolean result = false;
		
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(TestEnvironment.driver, timeout);
		wait.ignoring(StaleElementReferenceException.class);
		
		Common.logit("Waiting element by xpath \"" + XPATH + "\" text to match: \"" + pattern.toString()  +  "\"");
		
		try {
			if (wait.until(ExpectedConditions.textMatches( By.xpath(XPATH), pattern ))) {			
				result = true;
				logit("Match");			
			} else {			
				logit(3, "No match within " + timeout.toString() + "seconds timeout");
				
			}
		} catch (TimeoutException te) {
			System.out.println("No match within " + timeout.toString() + " seconds timeout");
		} finally {
			driver.manage().timeouts().implicitlyWait(Constants.implicitTimeout, TimeUnit.SECONDS);
		}
		
		return result;
		
	}
    
    
    public static void waitElementTextToBeNotEmpty(String xpath, int timeout) {
    	System.out.println("Waiting element text to be non-empty: " + xpath);
    	Common.waitElementTextToMatch(xpath, Pattern.compile("^(?!\\s*$).+"), timeout);
    }
    
    
	public static boolean waitElementToBeClickable(WebElement element, Integer timeout) {
		
		boolean isClickable = false;
		
		System.out.println("Waiting for element to be clickable: ");
		
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.ignoring(StaleElementReferenceException.class);
		
		try {
			
			WebElement elementToClick = wait.until(ExpectedConditions.elementToBeClickable(element));
			
			if (!elementToClick.equals(null)) {
				System.out.println("Element is clickable");
				isClickable = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return isClickable;
		
	}
	
	
	public static boolean waitElementToBeClickable(String xpath, Integer timeout) {
		return waitElementToBeClickable(findByXPATH(xpath, 5), timeout);
	}

	
	public static String generateRandomString(Integer length) {		
		return RandomStringUtils.randomAlphabetic(length);
	}

	
	public static void waitElementToBeDisplayed(String xpath, int timeout) {

		WebDriverWait wait = new WebDriverWait(TestEnvironment.driver, timeout);
		wait.ignoring(StaleElementReferenceException.class);

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			Common.logit("Element displayed: \"" + xpath + "\"");
		} catch (Exception e) {
			Common.logit("Element not displayed in " + timeout + " seconds: \"" + xpath + "\"");
		}

	}
	
	
	public static boolean waitForDownload(File file, int timeoutMS) {
		
		int i = 0;
		int step = 2000;//polling frequency
		
		System.out.println("Waiting for the file to be downloaded: " + file.getPath());
		
		while (i < timeoutMS) {
			
			if (file.exists()) {
				System.out.println("File downloaded successfully");
				return true;
			} else {
				Common.sleep(step);
				i += step;
			}
			
		}
		
		System.out.println("File did not download within timeout");
		return false;
		
	}
	
	
	public static boolean waitForDownload(String filePath, int timeoutMS) {
		
		File file = new File(filePath);
		return waitForDownload(file, timeoutMS);
		
	}
	
	
	public static boolean waitForDownload(Pattern filePath, int timeoutMS) {
	
		File dir = new File(TestEnvironment.downloadPath);						
		int counter = 0;
		int step = 2000;//polling frequency
		
		System.out.println("Waiting for the file to be downloaded by regex: " + filePath.toString());
		
		while (counter < timeoutMS) {
		
			File[] dir_contents = dir.listFiles();
			
			for (int i = 0; i < dir_contents.length; i++) {	

				if (dir_contents[i].getName().matches(filePath.toString())) {
					System.out.println("File downloaded successfully");
					return true;
				}
				
			}
			
			Common.sleep(step);
			counter += step;			
		
		}
		
		System.out.println("File did not downoad within timeout");
		return false;
	
	}
	
	
	public static boolean isEnabled(String xpath, Integer timeout) {
		
		WebElement element = findByXPATH(xpath, timeout);

		if (element != null) {
			
			if (element.isEnabled()) {				
				System.out.println("element is enabled: " + xpath);
				return true;
			} else {
				System.out.println("element is disabled: " + xpath);
				return false;
			}
			
		}
		
		System.out.println("Element not found (cannot determine isEnabled(): " + xpath);
		return false;
		
	}
	
	
	public static boolean isEnabled(String xpath) {		
		return isEnabled(xpath, 10);		
	}
	
	
	public static void waitAttributeToBe(WebElement element, String attributeName, String expectedValue, int timeout) {
		
		 
		System.out.println("Waiting attribute " + attributeName + " to be " + expectedValue + "...");		
		System.out.println("Value before: " + element.getAttribute(attributeName));
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		try {
			wait.until(ExpectedConditions.attributeToBe(element, attributeName, expectedValue));
			System.out.println("Attribute matches");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Attribute does not match the expected value within timeout");
			System.out.println("Value after: " + element.getAttribute(attributeName));
		}
		
		
	}
	
	
	public static String getFirstSelectedOptionText(String dropdown_xpath, int timeout) {
		
		String text = null;
		
		System.out.println("Obtaining first selected option for dropdown: " + dropdown_xpath);		
		WebElement dropdownElement = findByXPATH(dropdown_xpath, timeout);
		
		if (dropdownElement != null) {	
			Common.scrollIntoView(dropdownElement);
			Select mySelect = new Select(dropdownElement);
			text = mySelect.getFirstSelectedOption().getText();
			System.out.println("First selected option: " + text);			
		} else {
			System.out.println("cannot locate dropdown element: " + dropdown_xpath);			
		}	
		
		return text;
		
	}
	
	
	public static boolean waitPageTitleToContain(String title, int timeout) {
		
		System.out.println("Waiting page title to contain: \"" + title + "\"");		
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		
		if (wait.until(ExpectedConditions.titleContains(title))) {
			System.out.println("Match");
			return true;
		}
		
		return false;
		
	}
	
	
	public static void enterFieldValue(WebElement field, String value) {
		
		System.out.println("Entering field value: " + value);
		
		if (!field.equals(null)) {
						
			jsClick(field);
			field.clear();
			field.sendKeys(value);
			
		} else {
			System.out.println("Failed to enter value to the field");
		}		
		
	}
	
	//dont log the value entered
	public static void enterPassword(WebElement field, String password) {
		
		System.out.println("Entering password *****");
		
		if (!field.equals(null)) {
						
			jsClick(field);
			field.clear();
			field.sendKeys(password);
			
		} else {
			System.out.println("Failed to enter password to the field");
		}		
		
	}
	
	public static void enterFieldValue(String xpath, String value) {
		enterFieldValue(findByXPATH(xpath, 10), value);
	}
	
	public static void enterFieldValue(String xpath, int timeout, String value) {
		enterFieldValue(findByXPATH(xpath, timeout), value);
	}
	
	public static void enterPassword(String xpath, int timeout, String password) {
		enterPassword(findByXPATH(xpath, timeout), password);
	}
	
	
	public static boolean deleteFile(String fullPath) {
			
		File myfile = new File(fullPath);
		System.out.println("Deleting file: " + fullPath);
		
		if (!myfile.exists()) {
			return true;
		}
		
		
		if (myfile.delete()) {
			System.out.println("success");
			return true;
		} else {
			System.out.println("fail");
			return false;
		}		
		
	}
	
	
	public static void waitUntilDropdownIsPopulated(String xpath, int minItemCount, int timeout) {
		
		System.out.println("Waiting dropdown to be populated: " + xpath);		
		WebElement dropdown = Common.findByXPATH(xpath);
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.ignoring(StaleElementReferenceException.class);
		
		if (dropdown == null) {
			System.out.println("Dropdown not populated");
			return;
		}
		
		if (Common.isDisplayed(xpath, true, 10)) {
			wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(xpath + "/option"), minItemCount));
			System.out.println("Dropdown populated");
		} else {
			System.out.println("Dropdown not populated");
		}
		
		
	}
	
	
	public static List<String> getDropdownOptions(String xpath, int timeout) {
		
		List<String> options = new ArrayList<String>();		
		WebElement dropdown = Common.findByXPATH(xpath, timeout);
		Common.jsClick(dropdown);
		Common.waitUntilDropdownIsPopulated(xpath, 0, 20);
		Select select = new Select(dropdown);
		List<WebElement> all_options = select.getOptions();
		
		for (int i = 0; i < all_options.size(); i++) {
			options.add(all_options.get(i).getText());
		}
			
		return options;
		
	}
	
	
	public static String getElementText(String xpath, int timeout) {
		
		String text = null;		
		WebElement element = findByXPATH(xpath, timeout);
		scrollIntoView(element);
		text = element.getText();		
		return text;
		
	}
	
	
	public static String getElementValue(String xpath, int timeout) {
		
		String value = null;		
		WebElement element = findByXPATH(xpath, timeout);
		scrollIntoView(element);
		value = element.getAttribute("value");		
		return value;
		
	}



	public static void waitUntilDropdownSelectedOptionIsChanges(String xpath, String oldOption, int timeout) {

		System.out.println("Vaiting dropdown first selected option to change...");
		
		int counter = 0;		
		while (counter <= timeout * 1000) {
			
			System.out.println("Current value: " + Common.getFirstSelectedOptionText(xpath, 5));
			
			if (Common.getFirstSelectedOptionText(xpath, 5) != oldOption) {
				System.out.println("Selected option changed");
				return;
			}			
			counter += 1000;
			
		}
		
		
		
	}
	
	public static void takeScreenshot(String DestinationPath) {
		//Convert web driver object to TakeScreenshot
		TakesScreenshot scrShot =((TakesScreenshot)driver);
		//Call getScreenshotAs method to create image file
		File SrcFile =scrShot.getScreenshotAs(OutputType.FILE);
		//Move image file to new destination
		
		try {
		File DestFile = new File(DestinationPath);
		//Copy file at destination
		FileUtils.copyFile(SrcFile, DestFile);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
		

}

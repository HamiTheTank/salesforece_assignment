package commonLibraries;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;


public class TestEnvironment {
	
	
	protected static WebDriver driver = null;
	protected static Actions action = null;
	protected static JavascriptExecutor jse = null;
	protected static String os = "";
	protected static String browser = "";
	protected static String environment = "";
	protected static boolean remoteExecution = false;
	protected static boolean headless = false;
	protected static boolean incognito = false;
	protected static String baseURL = global.Constants.qaURL;
	protected static String seleniumURL = "";
	protected static String testSSO = "";
	protected static String testPassword = "";
	protected static boolean teardown = true;
	protected static String downloadPath = "";
	protected static int totalSleep = 0;
	


	@BeforeTest
	public void beforeClass() {
		Common.SSOlogin();
	}
		
	@BeforeSuite(alwaysRun=true)
	@Parameters({
		"environment",
		"remote-execution",
		"selenium-URL",
		"browser",
		"headless",
		"incognito",
		"sso",
		"teardown"
		})		
	public void setup(
	    	@Optional("dev") String environment,
	    	@Optional("false") String remoteExecution,
	    	@Optional("") String seleniumURL,
	    	@Optional("chrome") String browser,
			@Optional("false") String headless,
			@Optional("false") String incognito,
			@Optional("123456789") String sso,
			@Optional("true") Boolean teardown
		) {
		
	
		Common.logit(0, "\n=TestNG PARAMETERS=");
		Common.logit(0, "Environment: " + environment);
		Common.logit(0, "Remote execution: " + remoteExecution);
		Common.logit(0, "Selenium Standalone address: " + seleniumURL);
		Common.logit(0, "Browser: " + browser);
		Common.logit(0, "headless: " + headless);
		Common.logit(0, "incognito: " + incognito);
		Common.logit(0, "SSO: " + sso);
		Common.logit(0, "teardown: " + teardown.toString() + "\n");
		
		
		TestEnvironment.testSSO = sso;
		TestEnvironment.testPassword = global.Constants.password;
		TestEnvironment.environment = environment.toLowerCase();		
		TestEnvironment.browser = browser.toLowerCase();
		TestEnvironment.os = getOS();
		TestEnvironment.seleniumURL = seleniumURL;
		TestEnvironment.remoteExecution = Boolean.parseBoolean(remoteExecution);
		TestEnvironment.headless = Boolean.parseBoolean(headless);
		TestEnvironment.incognito = Boolean.parseBoolean(incognito);
		TestEnvironment.teardown = teardown;
		TestEnvironment.downloadPath = getDownloadPath();
	
				
		switch (TestEnvironment.environment.toLowerCase()){
		
			case "dev":
				Common.logit("Running in Dev environment");
				TestEnvironment.baseURL = global.Constants.devURL;
				break;
				
			case "qa":
				Common.logit("Running in QA environment");
				TestEnvironment.baseURL = global.Constants.qaURL;
				break;
				
			default:
				Common.logit(4, "Test automation in " + TestEnvironment.environment + " environment is not supported. Terminating.");
				return;
				
		}
		
		
		switch (TestEnvironment.os) {
		
			case "windows":
				Common.logit("Detected OS: Windows");
				break;
				
			case "linux":
				Common.logit("Detected OS: Linux");
				break;
				
			default:				
				Common.logit(4, "Only Windows and Linux are supported. Terminating.");				
				return;
				
		}
		
		
		
		switch (browser.toLowerCase()) {
		
			case "chrome":	
				driver = setupDriver();
				TestEnvironment.jse = (JavascriptExecutor)TestEnvironment.driver;
				break;
				
			default:
				Common.logit(4, "Only Chrome browser is supported. Terminating.");
				return;
		}
		
		
		TestEnvironment.action = new Actions(driver);
				
		Common.logit("Deleting cookies");
		TestEnvironment.driver.manage().deleteAllCookies();
		
		Common.logit("Maximizing browser window");
		TestEnvironment.driver.manage().window().maximize();
				
		Common.logit("Setting implicit timeout to " + global.Constants.implicitTimeout + " seconds");
		TestEnvironment.driver.manage().timeouts().implicitlyWait(global.Constants.implicitTimeout, TimeUnit.SECONDS);
		
		Common.logit("Setting page load timeout to " + global.Constants.pageloadTimeout + " seconds");
		TestEnvironment.driver.manage().timeouts().pageLoadTimeout(global.Constants.pageloadTimeout, TimeUnit.SECONDS);
	
	}
	
	
	
	private String getOS() {
		
		String OSType = null;
		OSType = System.getProperty("os.name").toLowerCase();			
		
		if (OSType.indexOf("win") >= 0) {
			OSType = "windows";
		} else if (OSType.indexOf("nux") >= 0) {
			OSType = "linux";
		}
		
		return OSType;
		
	}
	
	
	private String getDownloadPath() {
		
		String downloadPath = "";
		
		if (TestEnvironment.os.toLowerCase().contains("win")) {
			return System.getProperty("user.home") + File.separator + "Downloads";
		}
		else if (TestEnvironment.os.toLowerCase().contains("lin")) {
			return "/home/myProject";
		}

		return downloadPath;
		
	}
	
	
	
	private String getPasswordBySSO(String SSO) {
		
		String password = "";
				
		File myFile = new File(System.getenv("CNF_KEY_LOCATION") + "/" + SSO + ".txt");
		
		if (!myFile.exists()) {
			Common.logit(3, "Failed to obtain credentials for " + SSO + ". Key file not found.");
			return password;
		}
			
		try {
			password = new String(Files.readAllBytes(Paths.get(myFile.getPath()))).trim();
		} catch (IOException e) {
			Common.logit(3, "Failed to obtain credentials for " + SSO);
			e.printStackTrace();
		}
		
		return password;
		
	}

	

    private WebDriver setupDriver() {  	
    	
    	
    	System.setProperty("webdriver.chrome.silentOutput", "true");  //Suppress ChromeDriver warnings in the log
    	
    	ChromeOptions options = new ChromeOptions();
    	
    	if (TestEnvironment.headless) {
    		
    		options.addArguments(
    				"--headless",
    				"--disable-gpu",
    				"--enable-automation",
    				"--disable-notifications",
    				"--no-sandbox",
    				"--disable-dev-shm-usage",
    				"--window-size=1920,1080",
    				"--disable-infobars",
    				"--remote-debugging-port=9222",
    				"--disable-browser-side-navigation",
    				"--disable-site-isolation-trials"
    				);
    	}
    	
    	
    	//NORMAL: This strategy causes Selenium to wait for the full page loading (html content and sub resources downloaded and parsed).
    	//EAGER : This strategy causes Selenium to wait for the DOMContentLoaded event (html content downloaded and parsed only).
    	//NONE : This strategy causes Selenium to return immediately after the initial page content is fully received (html content downloaded).
    	options.setPageLoadStrategy(PageLoadStrategy.EAGER);
    	options.addArguments("--ignore-certificate-errors");
    	options.addArguments("--allow-insecure-localhost");    	
		options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
    	
    	if (TestEnvironment.incognito) {
    		options.addArguments("--incognito");
    	}
    	
    	//file download solution
		Map<String, Object> prefs = new HashMap<String, Object>();
		Map<String, Object> content_setting = new HashMap<String, Object>();
		content_setting.put("multiple-automatic-downloads", Integer.valueOf(1));
		prefs.put("profile.default_content_settings", content_setting);
		prefs.put("download_restrictions", 0);
		prefs.put("download.prompt_for_download", false);
		prefs.put("download.default_directory", TestEnvironment.downloadPath);
		prefs.put("profile.content_settings.pattern_pairs.*.multiple-automatic-downloads", Integer.valueOf(1));
		prefs.put("profile.default_content_setting_values.automatic_downloads", 1);
		prefs.put("notifications", 1);
		
		
		
		
		
		
		options.setExperimentalOption("prefs", prefs);
    	
    	
    	try {
    		
    		Common.logit("launching " + TestEnvironment.browser);
			
			if (TestEnvironment.remoteExecution) {				
				
				Common.logit("Runnig remotely on " + TestEnvironment.seleniumURL);
				//options.setProxy(null);
				options.addArguments("--proxy-server='direct://'");
				options.addArguments("--proxy-bypass-list=*");
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				return new RemoteWebDriver(new URL(TestEnvironment.seleniumURL), capabilities);
				
			}
			else {
				Common.logit("Running locally");
				return new ChromeDriver(options);
				
			}

    	}
    	catch (Exception e)
    	{    		
    		Common.logit(3, "Error while initializing chromedriver:\n");
    		e.printStackTrace();
    		return null;
    	}
    	
    }    
    


    @AfterTest(alwaysRun=true)
    public void tearDown() {
    	
    	if (TestEnvironment.teardown) {
    		
    		Common.logit("Performing driver.quit()");
    		driver.close();
			driver.quit();
			
    	} else {
    		Common.logit("WARNING: Teardown is turned off. Browser session will not be closed");
    	}
    	
    	Common.logit("Total sleep time: " + TestEnvironment.totalSleep + " ms.");

	}


}






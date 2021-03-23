## Installlation
Follow the instructions below to run and develop test automation locally.

### Prerequisites :
1. **JDK 1.8** or newer
2. **Maven 3.6.3** or newer
3. **Google Chrome** and compatible **[ChromeDriver](https://chromedriver.chromium.org/downloads)**

### Environment Variables
The following environment variables must be set in order to make the automation work
- **PATH** - add the location of ChromeDriver to the path variable.
- **CNF_KEY_LOCATION** - the path of the directory containing  the credentials used by the automation. The credentials should be stored in text files in the following format: "username.txt",  and the content of the text file is the password (**not used in this project!**).

## Running Tests

 - Open a terminal
 - Navigate to the project root directory
 - Tests can be executed by the following command:


```sh
mvn -s settings.xml -B -Dsuite_name=salesforce clean integration-test
```

Where "suite_name" is the name to the testNG xml to be executed.

> Note: Alternatively you can run tests from the IDE too: [Tutorial](https://www.toolsqa.com/testng/install-testng/).

## TestNG XML Parameters

```xml
	<parameter name="environment" value="dev" />
	<parameter name="remote-execution" value="false" />
	<parameter name="sso" value="123456789" />
	<parameter name="browser" value="chrome" />
	<parameter name="headless" value="true" />
	<parameter name="incognito" value="true" />
	<parameter name="teardown" value="true" />
```

- **environment** (qa | dev) - In which environment to execute the tests.
- **remote-execution** (true | false) - Tests can be executed on a remote machine instead of the local computer but you need a configured server for this purpose.
- **sso** - User to be used by the automation. The password is retrieved automatically from the credentials for the given username.
- **browser** (chrome) - Browser to be used by the automation. Only chrome is supported and it is the default value.
- **headless** (true | false) - Run the the browser in headless mode.
- **incognito** (true | false) - Run the the browser in incognito mode.
- **teardown** (true | false) - Close all sessions and browsers opened by the automation after its execution. You may want disable this when running locally to prevent the browser to be closed when the script finished running or encountered an error.

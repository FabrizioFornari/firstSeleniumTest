package proslab.unicam.firstSeleniumTest;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

//import org.junit.runner.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.restassured.RestAssured;
import proslab.unicam.SeleniumTests.HttpResponse;

class HttpResponseTest {
	static String browser;
	static String pathToMacDrivers;
	static WebDriver driver;
	static String projectPath;
    int statusCode;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
        projectPath = System.getProperty("user.dir");
        
		//Read properties from file
	   try (InputStream input = new FileInputStream( projectPath+"/src/main/java/proslab/unicam/SeleniumTests/config.properties")) {

	            Properties prop = new Properties();

	            // load a properties file
	            prop.load(input);

	            // get the property value and print it out 
	            browser=prop.getProperty("browser");
	            pathToMacDrivers=prop.getProperty("pathToMacDrivers");

	    } catch (IOException ex) {
	            ex.printStackTrace();
	    }
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	    setBrowserConfig();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		closeDriver();
	}

	

	public static void setBrowserConfig() {
		
		if(browser.contains("Chrome")) {
			
			//System.out.println("Setting Browser Configuration");
			
			//Chrome
			//This condition block sets config for Chrome browser
			System.setProperty("webdriver.chrome.driver", projectPath+pathToMacDrivers+"/chromedriver");
			driver = new ChromeDriver();
		}
		
		if(browser.contains("Firefox")) {
				
			//Firefox	
			//This condition block sets config for Firefox browser
			FirefoxOptions options = new FirefoxOptions();
			options.setCapability("marionette", true);
			System.setProperty("webdriver.gecko.driver", projectPath+pathToMacDrivers+"/geckodriver");
			driver = new FirefoxDriver(options);

		}		
	}
		
	
	public static void closeDriver() {
		driver.close();
		driver.quit();
	}
	

	@Test
	//@Disabled
	void checkNoBrokenLinksArePresent() {

        driver.get("http://pros.unicam.it");

//Get all the links on the page
        List<WebElement> links = driver.findElements(By.cssSelector("a"));

        //System.out.println(links.size());
        String href;

        List<String> Brokenlinks = new ArrayList<String>();

        for(WebElement link : links) {
        	
        	href=isABrokenLink(link);
        	if(!(href.equals("ok")))Brokenlinks.add(href);

        }	
        
        for(String link : Brokenlinks) {
        	System.out.println("Broken Link: "+link);
        }
        
        driver.close();
        assertNull(Brokenlinks);

	}
	
	String isABrokenLink(WebElement link) {
        String href;
		href = link.getAttribute("href");
        //System.out.println("href: "+href);
        try
    	{
            if(!href.contains("mailto")) {	
	            statusCode = RestAssured.get(href).statusCode();
	            //System.out.println("statusCode: "+statusCode);
            }
            
//            if(200 != statusCode) {
//                System.out.println(href + " gave a response code of " + statusCode);
//            }
    	}
    	catch (Exception e)
    	{
    		//tearDown();
    		System.out.println(e.getMessage());		
    		//throw new AssertionError("An href link is broken, check: "+href, e);
    		return "An href link is broken, check: "+href +" "+ e;
    	}
		
		return "ok";
	
	}

}

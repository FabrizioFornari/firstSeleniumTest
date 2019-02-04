package proslab.unicam.firstSeleniumTest;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

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

class HttpResponseTest {

	static WebDriver driver;
    
    int statusCode;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@Disabled
	void checkBrokenLinks() {
        String href = null;
	
    	//code to test
    	String projectPath = System.getProperty("user.dir");  
//		System.setProperty("webdriver.chrome.driver", projectPath+"/drivers/mac/chromedriver");
//        driver = new ChromeDriver();
        
    	//FirefoxOptions options = new FirefoxOptions();
    	//options.setCapability("marionette", true);
		System.setProperty("webdriver.gecko.driver", projectPath+"/drivers/linux/geckodriver");
		//driver = new FirefoxDriver(options);
		driver = new FirefoxDriver();
    	
    	driver.navigate().to("http://pros.unicam.it");
        
        
        
        
        //driver.get("http://pros.unicam.it");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//Get all the links on the page
        List<WebElement> links = driver.findElements(By.cssSelector("a"));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
        
		
//        System.out.println(driver.getTitle());
	}
	
	String isABrokenLink(WebElement link) {
        String href;
		href = link.getAttribute("href");
        System.out.println("href: "+href);
        try
    	{
            if(!href.contains("mailto")) {	
	            statusCode = RestAssured.get(href).statusCode();
	            System.out.println("statusCode: "+statusCode);
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

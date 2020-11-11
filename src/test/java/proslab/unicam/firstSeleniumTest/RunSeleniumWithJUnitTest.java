package proslab.unicam.firstSeleniumTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


class RunSeleniumWithJUnitTest {
	
	static String browser;
	static String rightPassword;
	static String wrongPassword;
	static String pathToMacDrivers;
	
	static WebDriver driver;
	
	static String projectPath;
	
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
	            wrongPassword=prop.getProperty("wrongPassword");
	            rightPassword=prop.getProperty("rightPassword");
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
	void checkProsFirstResultOnGoogle() throws InterruptedException {
		
		//Query google chrome
		driver.get("http://www.google.com");
		driver.findElement(By.name("q")).sendKeys("PROS Lab");
		
		//Using WebDriverWait, not a good practice
		WebDriverWait wait = new WebDriverWait(driver, 10);
		System.out.println("HEre");
		//driver.findElement(By.name("btnK")).click();
		
		
	    //grabbing the first frame with the help of index
	    driver.switchTo().frame(0);
	      
	    // switching back to the parent web page
	    //driver.switchTo().defaultContent();
	    //driver.quit();
	      
	      
		WebElement cookies = driver.findElement(By.className("A28uDc"));
		cookies.click();

		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("btnK")));
		element.click();
		
		String at=driver.findElement(By.className("LC20lb")).getText();
		String et = "PROS PROcesses and Services Lab: Home";
		
		System.out.println(at);
		Assert.assertEquals(et,at);
	}
	
	@Test
	void checkUserLoginGmail() throws InterruptedException {
		
		// navigate your driver to mail.google.com
		driver.get("http://mail.google.com");

		//findElement by id
		driver.findElement(By.id("identifierId")).sendKeys("john.luiz.2001@gmail.com");
			
		
		//Find Element by className
		driver.findElement(By.className("VfPpkd-RLmnJb")).click();
		
		Thread.sleep(1000);
		
		//Find Element by className
		driver.findElement(By.name("password")).sendKeys(wrongPassword);	
		//driver.findElement(By.name("password")).sendKeys(rightPassword);	
		
		Thread.sleep(1000);
		
		driver.findElement(By.className("VfPpkd-RLmnJb")).click();
		Thread.sleep(3000);
		
		String at = driver.getTitle();
		String et = "Inbox (1) - john.luiz.2001@gmail.com - Gmail";
		
		System.out.println(at);		
		Thread.sleep(1000);

		Assert.assertEquals(et,at);
	}
	
	@Test
	void checkProsTitle() throws InterruptedException {
		driver.get("http://pros.unicam.it/");
		driver.manage().window().maximize();
		
		String at = driver.getTitle();
		String et = "PROS";
		//et="Home - PROS PROcesses and Services Lab";
		
		System.out.println(at);
//		Thread.sleep(1000);
		
		Assert.assertEquals(et,at);
		
	}
	

	@Test
	void verifyNumberrOfInputElementsOnProsWebsiteIs8() throws InterruptedException {
		driver.get("http://pros.unicam.it/");
		
		//Estrae gli elementi che possono essere utilizzati come input
		List<WebElement> listOfInputElements = driver.findElements(By.xpath("//input"));
		
		int count = listOfInputElements.size();

		Assert.assertEquals(8,count);
		System.out.println("Count of Input elements : "+count);
		
		
	}
	
	
}

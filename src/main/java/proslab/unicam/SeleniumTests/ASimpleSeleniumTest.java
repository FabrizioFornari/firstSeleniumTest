package proslab.unicam.SeleniumTests;

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

public class ASimpleSeleniumTest {

	static String browser;
	static String rightPassword;
	static String wrongPassword;
	static String pathToMacDrivers;
	
	static WebDriver driver;
	
	static String projectPath = System.getProperty("user.dir"); 
	
	
	public static void main(String[] args) throws InterruptedException {
		
		
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
		
		
	    setBrowserConfig();

//Uncomment to run 
		//checkProsFirstResultOnGoogle();
		//checkUserLoginGmail();
		//checkProsTitle();
		//countInputElementsOnProsWebsite();
		
		closeDriver();
		
	}
	
	

	public static void setBrowserConfig() {
		
		if(browser.contains("Chrome")) {
			
			System.out.println("Setting Browser COnfiguration");
			
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
	
	
	public static void checkProsFirstResultOnGoogle() throws InterruptedException {
		
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
		if(at.equalsIgnoreCase(et)) {
			System.out.println("Test Successful");
		}else{
			System.out.println("Test Failure");
		}
	}
	
	public static void checkUserLoginGmail() throws InterruptedException {
		
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

		if(at.equalsIgnoreCase(et)) {
			System.out.println("Test Successful");
		}else{
			System.out.println("Test Failure");
		}
	}
	
	public static void checkProsTitle() throws InterruptedException {
		driver.get("http://pros.unicam.it/");
		driver.manage().window().maximize();
		
		String at = driver.getTitle();
		String et = "PROS";
		//et="Home - PROS PROcesses and Services Lab";
		
		System.out.println(at);
//		Thread.sleep(1000);
		
		if(at.equalsIgnoreCase(et)) {
			System.out.println("Test Successful");
		}else{
			System.out.println("Test Failure");
		}
	}
	

	
	public static void countInputElementsOnProsWebsite() throws InterruptedException {
		driver.get("http://pros.unicam.it/");
		
		//Estrae gli elementi che possono essere utilizzati come input
		List<WebElement> listOfInputElements = driver.findElements(By.xpath("//input"));
		
		int count = listOfInputElements.size();

		System.out.println("Count of Input elements : "+count);
		
		
	}
	
	
}

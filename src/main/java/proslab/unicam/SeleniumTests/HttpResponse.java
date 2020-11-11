package proslab.unicam.SeleniumTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.restassured.RestAssured;

import java.util.List;


public class HttpResponse {

	
    WebDriver driver;
    int statusCode;
    
    public static void main(String args[]) {
        new HttpResponse().checkNoBrokenLinksArePresent();
    }
    

    public void checkNoBrokenLinksArePresent() {
    	String projectPath = System.getProperty("user.dir");  
		System.setProperty("webdriver.chrome.driver", projectPath+"/drivers/mac/chromedriver");
		
		
        driver = new ChromeDriver();
        driver.get("http://pros.unicam.it");

//Get all the links on the page
        List<WebElement> links = driver.findElements(By.cssSelector("a"));

        System.out.println(links.size());
        String href;

        
        for(WebElement link : links) {
        	
	            href = link.getAttribute("href");
	            //System.out.println(href);
	            
	            try{
	            	statusCode = new HttpResponse().httpResponseCodeViaGet(href);
	            }catch(Exception e) {
	            	System.out.println("ERROR trying to contact: " +href);
	            }
	
//	            if(200 != statusCode) {
//	                System.out.println(href + " gave a response code of " + statusCode);
//	            }
        	
        		
        	
        }
    }
    
    public int httpResponseCodeViaGet(String url) {
        return RestAssured.get(url).statusCode();
	}
	
	public int httpResponseCodeViaPost(String url) {
	    return RestAssured.post(url).statusCode();
	}
	
    
}
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

    public void checkBrokenLinks() {
    	String projectPath = System.getProperty("user.dir");  
		System.setProperty("webdriver.chrome.driver", projectPath+"/drivers/chromedriver");
		
		
        driver = new ChromeDriver();
        driver.get("http://pros.unicam.it");

//Get all the links on the page
        List<WebElement> links = driver.findElements(By.cssSelector("a"));

        String href;

        for(WebElement link : links) {
            href = link.getAttribute("href");
            statusCode = new HttpResponse().httpResponseCodeViaGet(href);

            if(200 != statusCode) {
                System.out.println(href + " gave a response code of " + statusCode);
            }
        }
    }
    
    public int httpResponseCodeViaGet(String url) {
        return RestAssured.get(url).statusCode();
	}
	
	public int httpResponseCodeViaPost(String url) {
	    return RestAssured.post(url).statusCode();
	}
	
    public static void main(String args[]) {
        new HttpResponse().checkBrokenLinks();
    }
}
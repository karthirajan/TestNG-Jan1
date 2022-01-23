package com.flipkart.stepdefinition;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TVPurchase {
	
	static WebDriver driver;
	static long start;
	
	@BeforeClass(groups="default")
	public static void launch() {
		
		System.out.println("browser launch");
		WebDriverManager.chromedriver().setup();
	    driver = new ChromeDriver();
		driver.get("https://www.flipkart.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);

	}
	
	@AfterClass(groups="default")
	public static void close() {
		
		driver.quit();

	}
	
	@BeforeMethod(groups="default")
	public void startTime() {
		
		 start = System.currentTimeMillis();

	}
	
	@AfterMethod(groups="default")
	public void endTime() {
		
		long end = System.currentTimeMillis();
		long tt = end - start;
		
		System.out.println("Time taken :"+ tt);

	}
	
	@Test(priority = 1,groups = "smoke")
	public void loginValidationTV() {
		
		System.out.println("login test");
		
       try {
			 
			   WebElement button = driver.findElement(By.xpath("//button[text()='✕']"));
			   button.isDisplayed();
			   button.click();
			
	    	} catch (Exception e) {
	    		
			System.out.println("pop up is not displayed");
			
	    	}

	}
	
	@Test(priority = 2,groups = "smoke")
	public void tvSearch() {
		
		System.out.println("search mobile");
		
		 WebElement search = driver.findElement(By.name("q"));
		   search.sendKeys("SAMSUNG TV",Keys.ENTER);
		   
		   WebElement mobileName = driver.findElement(By.xpath("(//div[contains(text(),'SAMSUNG')])[2]"));   
		   mobileName.click();

	}

}

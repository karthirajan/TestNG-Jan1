package com.flipkart.stepdefinition;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MobilePurchase {
	
	@DataProvider(name="mobileName")
	public Object[][] getMobileName() {
		
		
		return new Object[][] {{"iPhone"}};

	}
	
	@DataProvider(name="login")
	public Object[][] getCredentials() {
		
		
		return new Object[][] {{"karthi","xxxxxx"}};

	}
	
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
	
	@Parameters({"username","password"})
	@Test(priority = 1,groups = "smoke")
	public void loginValidation(String user, String pass) {
		
		System.out.println("login test");
		
		System.out.println(user);
		System.out.println(pass);
		
       try {
			 
			   WebElement button = driver.findElement(By.xpath("//button[text()='✕']"));
			   button.isDisplayed();
			   button.click();
			
	    	} catch (Exception e) {
	    		
			System.out.println("pop up is not displayed");
			
	    	}

	}
	
	@Test(priority = 2,groups = "smoke",dataProvider = "mobileName")
	public void mobileSearch(String mn1) throws InterruptedException {
		
		System.out.println("search mobile");
		
		   WebElement search = driver.findElement(By.name("q"));
		   search.sendKeys(mn1,Keys.ENTER);
		   
		   WebElement mobileName = driver.findElement(By.xpath("(//div[contains(text(),'"+mn1+"')])[2]"));   
		   mobileName.click();
		   Thread.sleep(2000);

	}
	
	@Test(priority = 3)
	public void windowsHandling() throws InterruptedException {
		
		System.out.println("windows handling");
		
		String parentURL = driver.getWindowHandle();
		   
		   Set<String> childURL = driver.getWindowHandles();
		   for (String child : childURL) {
			   
			   if(!parentURL.equals(child)) {
				   
				   driver.switchTo().window(child);
				   
			   }
			
		
		   }
		   Thread.sleep(2000);

	}
	
	@Test(priority = 4,groups="sanity",dataProvider = "mobileName")
	public void buyNow(String mn1) {
		
		
		   WebElement mobileName2 = driver.findElement(By.xpath("//span[contains(text(),'"+mn1+"')]"));
		   String name = mobileName2.getText();
		   System.out.println(name);
		   
		   Assert.assertTrue(mobileName2.isDisplayed());
		   Assert.assertEquals("realme Narzo 50A (Oxygen Green, 128 GB)  (4 GB RAM)", name);
		   
		   System.out.println("passed");
		
	}
	
	
	@Test(priority = 5,invocationCount = 2,enabled = false)
	public void screenShot() throws IOException, InterruptedException {
		
		Thread.sleep(2000);
		
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("ss");
		String strDate= formatter.format(date);
		System.out.println(strDate);
		
		
		    TakesScreenshot tk = (TakesScreenshot)driver;	
			File source = tk.getScreenshotAs(OutputType.FILE);
			File dest = new File(".//target//report"+strDate+".png");		
			FileUtils.copyFile(source, dest);
		
	}

}

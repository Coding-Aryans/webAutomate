package com.BaseClass;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.utilites.ExcelReader;
import com.utilites.ExtentManager;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	
	//this is my base class
	public static WebDriver driver;
	public static Logger log=Logger.getLogger("devpinoyLogger");
	public static Properties OR=new Properties();
	public static Properties Config=new Properties();
	public static FileInputStream fis;
	public static ExcelReader excel=new ExcelReader("./src/test/resources/excel/testdata.xlsx");
	
	public static WebDriverWait wait;
	public ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;
	
	
	
	 public static boolean isElementPresent(String locatorKey) {

			try {
				if (locatorKey.endsWith("_XPATH")) {

					driver.findElement(By.xpath(OR.getProperty(locatorKey)));
				} else if (locatorKey.endsWith("_CSS")) {
					driver.findElement(By.cssSelector(OR.getProperty(locatorKey)));
				} else if (locatorKey.endsWith("_ID")) {
					driver.findElement(By.id(OR.getProperty(locatorKey)));
				}

				log.info("Finding an Element : " + locatorKey);
				return true;
			} catch (Throwable t) {

				log.info("Error while finding an Element : " + locatorKey + " error message is : " + t.getMessage());
				return false;
			}

		}
	  
	 
    public static void type(String locatorKey, String value) {

		if (locatorKey.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locatorKey))).sendKeys(value);
		} else if (locatorKey.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locatorKey))).sendKeys(value);
		} else if (locatorKey.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locatorKey))).sendKeys(value);
		}

		//log.info("Typing in an Element : " + locatorKey + " entered value as : " + value);
		test.log(LogStatus.INFO, "Typing in : " + locatorKey + " entered value as " + value);
	}
    
    
    
    public static void click(String locatorKey) {

		if (locatorKey.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locatorKey))).click();
		} else if (locatorKey.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locatorKey))).click();
		} else if (locatorKey.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locatorKey))).click();
		}
		test.log(LogStatus.INFO, "Clicking on : " + locatorKey);
		//log.info("Clicking on an Element : " + locatorKey);

	}
	
    public static void verifyEquals(String expected, String actual) throws IOException {

		try {

			Assert.assertEquals(actual, expected);

		} catch (Throwable t) {

			TestDataUtility.captureScreenshot();
			// ReportNG
			Reporter.log("<br>" + "Verification failure : " + t.getMessage() + "<br>");
			Reporter.log("<a target=\"_blank\" href=" + TestDataUtility.screenshotName + "><img src=" + TestDataUtility.screenshotName
					+ " height=200 width=200></img></a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
			// Extent Reports
			test.log(LogStatus.FAIL, " Verification failed with exception : " + t.getMessage());
			test.log(LogStatus.FAIL, test.addScreenCapture(TestDataUtility.screenshotName));

		}
    
    
    
    
    }
    static WebElement dropdown;
    public void Select(String locator,String value) {

		if (locator.endsWith("_XPATH")) {
			dropdown=driver.findElement(By.xpath(OR.getProperty(locator)));
		} else if (locator.endsWith("_CSS")) {
			dropdown=driver.findElement(By.cssSelector(OR.getProperty(locator)));
		} else if (locator.endsWith("_ID")) {
			dropdown=driver.findElement(By.id(OR.getProperty(locator)));
		}

    	Select select=new Select(dropdown);
    	select.selectByVisibleText(value);
    	test.log(LogStatus.INFO, "Selecting from dropdown : " + locator + " value as " + value);
    }
    
    
    
    
	@BeforeSuite
	public void setup() {
		PropertyConfigurator.configure("./src/test/resources/properties/log4j.properties");
		log.info("LOG file configure");
		try {
			fis=new FileInputStream("./src/test/resources/properties/Config.properties");
			
		} catch (FileNotFoundException e) {
			System.out.println("Config file not found");
			//e.printStackTrace();
		}
		try {
			Config.load(fis);
			log.info("Config file configure");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			fis=new FileInputStream("./src/test/resources/properties/OR.properties");
			
		} catch (FileNotFoundException e) {
			System.out.println("OR file not found");
			//e.printStackTrace();
		}
		try {
			OR.load(fis);
			log.info("OR file configure");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		 if(Config.getProperty("browser").equals("chrome")) {
	     		WebDriverManager.chromedriver().setup();
	     		 driver=new ChromeDriver();
	     		log.info("CHROME browser launched !!!");
	     	 }
	     	else if (Config.getProperty("browser").equals("firefox")) {

				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				log.info("Firefox browser launched !!!");

			}
	     	driver.get(Config.getProperty("testsiteurl"));
	     	driver.manage().timeouts().implicitlyWait(Integer.parseInt(Config.getProperty("implicit.wait")),TimeUnit.SECONDS);	
	     	wait = new WebDriverWait(driver,Integer.parseInt(Config.getProperty("explicit.wait")));
	}
	
	
	
	
	
	
	
	  @AfterSuite 
	  public void teardown() {
	  
	  driver.quit(); 
	  log.info("Test execution completed !!!"); }
	 
	
}

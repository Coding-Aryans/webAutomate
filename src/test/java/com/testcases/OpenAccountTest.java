package com.testcases;

import org.openqa.selenium.Alert;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.BaseClass.BaseClass;
import com.BaseClass.TestDataUtility;

public class OpenAccountTest extends BaseClass {

	@Test(dataProviderClass = TestDataUtility.class, dataProvider = "dp")

	public void OpenAccount(String CustomerName, String Currency) throws InterruptedException {

		if (!(TestDataUtility.isTestRunnable("openAccountTest", excel))) {

			throw new SkipException("Skipping the test " + "openAccountTest".toUpperCase() + "as the Run mode is NO");
		}

		click("openAccountBtn_CSS");
		Select("customer_CSS", CustomerName);
		Select("currency_CSS", Currency);
		click("process_CSS");

		Thread.sleep(3000);
		Alert alert = driver.switchTo().alert();
		Assert.assertTrue(alert.getText().contains("Account created successfully with account Number"),"not open account");
		Thread.sleep(3000);
		alert.accept();

	}

}

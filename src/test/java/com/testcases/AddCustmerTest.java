package com.testcases;

import java.io.IOException;

import org.openqa.selenium.Alert;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.BaseClass.BaseClass;
import com.BaseClass.TestDataUtility;


public class AddCustmerTest extends BaseClass {
	@Test(dataProviderClass=TestDataUtility.class,dataProvider="dp")
	public void AddCustmerLoginTest(String firstName,String lastName,String postCode) throws InterruptedException, IOException {
		click("addCustomer_CSS");
		//verifyEquals("x", "y");
		type("firstName_CSS",firstName);
		type("lastName_CSS",lastName);
		type("postCode_CSS",postCode);
		click("addCustomerBtn_CSS");
	//	Assert.assertTrue(isElementPresent("addCustomer_CSS")," login  not sucess");
		Thread.sleep(3000);
		Alert alert = driver.switchTo().alert();
		
		Assert.assertTrue(alert.getText().contains("Customer added successfully"),"Customer not added successfully");
		Thread.sleep(3000);
		alert.accept();
	}

}

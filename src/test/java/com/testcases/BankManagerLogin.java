package com.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.BaseClass.BaseClass;

public class BankManagerLogin  extends BaseClass{

	@Test
	public void LoginTest() {
		click("bmlBtn_CSS");
		Assert.assertTrue(isElementPresent("addCustomer_CSS")," login  not sucess");
		
	}
}

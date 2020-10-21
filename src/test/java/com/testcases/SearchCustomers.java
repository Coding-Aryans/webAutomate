package com.testcases;

import org.testng.annotations.Test;

import com.BaseClass.BaseClass;
import com.BaseClass.TestDataUtility;

public class SearchCustomers extends BaseClass{

	
	@Test(dataProviderClass =TestDataUtility.class,dataProvider = "dp" )
	public void SearchCustomersTest(String Name) throws InterruptedException {
		click("customersBtn_CSS");
		type("SearchInput_CSS",Name);
		Thread.sleep(3000);
		click("DeleteBtton_CSS");
		
	}
}

package com.BaseClass;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import com.utilites.ExcelReader;

public class TestDataUtility  extends BaseClass{
	public static  String screenshotPath;
	public static String screenshotName;

	public static void captureScreenshot() throws IOException {

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		Date d = new Date();
		screenshotName = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";

		FileUtils.copyFile(scrFile,
				new File(System.getProperty("user.dir") + "\\target\\surefire-reports\\html\\" + screenshotName));

	}
	
	@DataProvider(name="dp")
	public static Object[][] getData(Method m){
		
		String sheetName = m.getName();
		System.out.println(sheetName);
		
		int row = excel.getRowCount(sheetName);//2
		/* System.out.println(row); */
		int col = excel.getColumnCount(sheetName);//3

		

		Object[][] data = new Object[row-1][col]; //2 2
		/* System.out.println(data.length); */
		for(int r=2;r<=row;r++) {
			for(int c=0;c<col;c++) {
				data[r-2][c]=excel.getCellData(sheetName, c, r); 
			}
		}
		
		return data;
			
		
		
		
	}
public static boolean isTestRunnable(String testName, ExcelReader excel){
		
		String sheetName="testsuite";
		int rows = excel.getRowCount(sheetName);
		
		
		for(int rNum=2; rNum<=rows; rNum++){
			
			String testCase = excel.getCellData(sheetName, "TCID", rNum);
			
			if(testCase.equalsIgnoreCase(testName)){
				
				String runmode = excel.getCellData(sheetName, "Runmode", rNum);
				
				if(runmode.equalsIgnoreCase("Y"))
					return true;
				else
					return false;
			}
			
			
		}
		return false;
	}
	

}

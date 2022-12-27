package com.qa.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.pages.HomePage;

public class TestClass extends TestBase {
	HomePage homePage;
	
	public TestClass() {
		super();
	}
	
	@BeforeMethod
	public void setup() {
		initialization();
		homePage = new HomePage();
	}
	
	@Test
	public void HomePageTest() {
		Assert.assertEquals("test","Amazon");
	}
	
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}

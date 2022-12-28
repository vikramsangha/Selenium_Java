package com.qa.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.pages.HomePage;
import com.qa.pages.ItemPage;
import com.qa.pages.TelevisionsPage;

public class TestClass extends TestBase {
	HomePage homePage;
	TelevisionsPage tv;
	ItemPage ip;
	
	public TestClass() {
		super();
	}
	
	@BeforeMethod
	public void setup() {
		initialization();
		homePage = new HomePage();
	}
	
	@Test
	public void HomePageTest() throws Exception {
		Assert.assertEquals(homePage.validatePageTitle(),"Online Shopping site in India: Shop Online for Mobiles, Books, Watches, Shoes and More - Amazon.in");
		Assert.assertTrue(homePage.validateLogo());	
		Assert.assertTrue(homePage.validatehamburgerMenu());
		homePage.clickhamburgerMenu();
		homePage.clickTVAppliancesAndElectronics();
		tv = homePage.clickOnTelevision();
		tv.selectSamsungBrand();
		
		tv.selectHighToLowOption();
		ip = tv.clickSecondHighestPricedItem();
		
		Assert.assertTrue(ip.aboutItemPresent());
		ip.getaboutItemText();
		
	}
		
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}

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
	
	@Test( description = "Validate the environment availabilty" , priority=1)
	public void smokeTest() throws Exception {
		
		log.info("Executing smoke test");
		Assert.assertEquals(homePage.validatePageTitle(),"Online Shopping site in India: Shop Online for Mobiles, Books, Watches, Shoes and More - Amazon.in","Verify home page title");
		Assert.assertTrue(homePage.validateLogo(), "Verify that amazon logo is visible successfully");
		log.info("Smoke test completed successfully");
	}
	
	@Test(description = "Navigate to the second highest samsung television" , priority=2)
	public void secondHighestTelevision() throws Exception {
		
		log.info("Executing end to end test to validate second highest samsung television");
		Assert.assertEquals(homePage.validatePageTitle(),"Online Shopping site in India: Shop Online for Mobiles, Books, Watches, Shoes and More - Amazon.in", "Verify home page title");	
		Assert.assertTrue(homePage.validatehamburgerMenu() ,"Verify hambuger menu");
		log.info("Home page launched successfully");
		
		homePage.clickhamburgerMenu();
		homePage.clickTVAppliancesAndElectronics();
		tv = homePage.clickOnTelevision();
		log.info("Landed on television page");
		
		tv.selectSamsungBrand();
		log.info("Selected samsung checkbox successfully");
		
		tv.selectHighToLowOption();
		log.info("Price filter applied successfully");
		
		ip = tv.clickSecondHighestPricedItem();
		Assert.assertTrue(ip.aboutItemPresent());
		log.info("Switched to Item page successfully");
		
		log.info("Getting Details about the product");
		System.out.println(ip.getaboutItemText());
		
	}
		
	
	@AfterMethod
	public void tearDown() {
		driver.close();
	}

}

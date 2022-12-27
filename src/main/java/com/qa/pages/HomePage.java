package com.qa.pages;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.base.TestBase;
import com.qa.utils.TestUtil;

public class HomePage extends TestBase {
	
	@FindBy(id="nav-logo-sprites")
	WebElement logo;
	
	@FindBy(id="nav-hamburger-menu")
	WebElement hamburgerMenu;
	
	@FindBy(xpath="//a[div[contains(text(),'TV, Appliances')]]")
	WebElement electronicsMenu;
	
	@FindBy(xpath="//a[contains(text(),'Televisions')]")
	WebElement television;
	
	public HomePage(){
		PageFactory.initElements(driver, this);
	}
	
	//Actions:
	public String validatePageTitle(){
		return driver.getTitle();
	}
		
	public boolean validatehamburgerMenu(){
		return hamburgerMenu.isDisplayed();
	}
	
	public void clickhamburgerMenu() throws Exception{
		hamburgerMenu.click();
		TestUtil.waitForJSandJQueryToLoad(Duration.ofSeconds(10));
		
	}
	
	//a[div[contains(text(),'TV, Appliances')]]
	
	public void clickTVAppliancesAndElectronics(){
		electronicsMenu.click();
		
	}
	
	public boolean validateLogo(){
		return logo.isDisplayed();
	}
	
	public TelevisionsPage clickOnTelevision() {
		television.click();
		return new TelevisionsPage();
	}

}

package com.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.base.TestBase;

public class HomePage extends TestBase {
	
	@FindBy(id="nav-logo-sprites")
	WebElement logo;
	
	@FindBy(id="nav-hamburger-menu")
	WebElement hamburgerMenu;
	
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
	
	public boolean validateLogo(){
		return logo.isDisplayed();
	}

}

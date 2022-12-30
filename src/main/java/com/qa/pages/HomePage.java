package com.qa.pages;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.base.TestBase;
import com.qa.utils.TestUtil;

public class HomePage extends TestBase {

	TestUtil utils = new TestUtil();

	@FindBy(id = "nav-logo-sprites")
	WebElement logo;

	@FindBy(id = "nav-hamburger-menu")
	WebElement hamburgerMenu;

	@FindBy(id = "hmenu-content")
	WebElement menuContent;

	@FindBy(xpath = "//a[div[contains(text(),'TV, Appliances')]]")
	WebElement electronicsMenu;

	@FindBy(xpath = "//a[contains(text(),'Televisions')]")
	WebElement television;

	public HomePage() {
		PageFactory.initElements(driver, this);
	}

	// Actions:
	public String validatePageTitle() {
		return driver.getTitle();
	}

	public boolean validatehamburgerMenu() {
		return utils.waitForElementToBeDisplayed(hamburgerMenu, Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
	}

	public void clickhamburgerMenu() {
		utils.clickAndWait(hamburgerMenu, menuContent);
	}

	public void clickTVAppliancesAndElectronics() {
		utils.scrollToViewAndClickAndWait(electronicsMenu, television);
	}

	public boolean validateLogo() {
		return utils.waitForElementToBeDisplayed(logo, Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
	}

	public TelevisionsPage clickOnTelevision() {
		utils.click(television);
		utils.waitForJSandJQueryToLoad(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
		return new TelevisionsPage();
	}

}

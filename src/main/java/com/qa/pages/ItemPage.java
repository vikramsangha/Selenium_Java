package com.qa.pages;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.base.TestBase;
import com.qa.utils.TestUtil;

public class ItemPage extends TestBase {

	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	TestUtil utils = new TestUtil();

	@FindBy(xpath = "//h1[contains(text(),'About this item')]")
	WebElement aboutItem;

	@FindAll({ @FindBy(xpath = "//div[@id='feature-bullets']//span[@class='a-list-item']") })
	List<WebElement> aboutmetext;

	@FindBy(id = "nav-logo-sprites")
	WebElement logo;

	public ItemPage() {
		PageFactory.initElements(driver, this);
	}

	public Boolean aboutItemPresent() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-logo-sprites")));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", aboutItem);
		return utils.waitForElementToBeDisplayed(aboutItem, Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
	}

	public String getaboutItemText() {
		String text = "";
		for (WebElement element : aboutmetext) {
			text = text.concat(element.getText());
		}

		return text;

	}

}

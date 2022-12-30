package com.qa.pages;

import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.base.TestBase;
import com.qa.utils.TestUtil;

public class TelevisionsPage extends TestBase {

	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	TestUtil utils = new TestUtil();

	@FindBy(xpath = "//a[span[contains(text(),'Samsung')]]/div/label/i")
	WebElement samsungBrand;

	@FindBy(id = "//span[span[@class='a-button-text a-declarative']]/i")
	WebElement sortByFilter;

	@FindBy(xpath = "//a[contains(text(),'Price: Low to High')]")
	WebElement priceHighToLow;

	@FindBy(xpath = "//div[@data-index='2']//a[span[contains(text(),'Samsung')]]")
	WebElement secondHighestItem;

	@FindBy(xpath = "//span[contains(text(),'RESULTS')]")
	WebElement results;

	@FindBy(id = "s-result-sort-select")
	WebElement selectFilter;

	public TelevisionsPage() {
		PageFactory.initElements(driver, this);
	}

	public void selectSamsungBrand() {
		utils.scrollToViewAndClickAndWait(samsungBrand, results);
	}

	public void selectHighToLowOption() {
		utils.selectDropDownAndOption(selectFilter, "Price: High to Low");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'RESULTS')]")));
	}

	public ItemPage clickSecondHighestPricedItem() {
		utils.click(secondHighestItem);

		String parent = driver.getWindowHandle();
		Set<String> windowHandles = driver.getWindowHandles();

		Iterator<String> I1 = windowHandles.iterator();

		while (I1.hasNext()) {
			String child_window = I1.next();

			if (!parent.equals(child_window)) {
				driver.switchTo().window(child_window);
			}

		}

		utils.waitForJSandJQueryToLoad(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
		return new ItemPage();

	}

}

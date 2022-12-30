package com.qa.utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.base.TestBase;

public class TestUtil extends TestBase {

	public static long PAGE_LOAD_TIMEOUT = 20;
	public static long IMPLICIT_WAIT = 20;

	public void click(WebElement element) {
		boolean result = false;
		int attempts = 0;
		while (attempts < 2) {
			try {
				element.click();
				result = true;
				log.info("Successfully Clicked on the element with attempt " + attempts);
				break;
			} catch (StaleElementReferenceException e) {
				log.info("Stale element during retryclick, attempt " + attempts);
			} catch (ElementNotInteractableException e) {
				log.info("Element not interactable during retryclick, attempt " + attempts);
			}

			attempts++;
		}
		// return result;
	}

	public void clickAndWait(WebElement element, WebElement nextExpectedElement) {
		boolean result = false;
		int attempts = 0;
		while (attempts < 3) {
			try {
				element.click();
				result = true;
				log.info("Successfully Clicked on the element with attempt " + attempts);
				break;
			} catch (StaleElementReferenceException e) {
				log.info("Stale element during retryclick, attempt " + attempts);
			} catch (ElementNotInteractableException e) {
				log.info("Element not interactable during retryclick, attempt " + attempts);
			}

			attempts++;
		}
		// return result;
		if (waitForElementToBeDisplayed(nextExpectedElement, Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT))) {
			log.info("Element " + nextExpectedElement + " found");
		} else {
			log.severe("Element " + nextExpectedElement + " not found");
		}

	}

	public boolean waitForElementToBeDisplayed(final WebElement elementToWaitFor, Duration timeoutPeriod) {
		WebDriverWait wait = new WebDriverWait(driver, timeoutPeriod);
		ExpectedCondition<Boolean> elementDisplayed = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver arg0) {
				try {
					log.info("Waiting for element to be displayed");
					elementToWaitFor.isDisplayed();
					return true;
				} catch (Exception e) {
					return false;
				}
			}
		};

		try {
			wait.until(elementDisplayed);
			return true;
		} catch (Exception e2) {
			log.severe(
					"Element " + elementToWaitFor + " was not displayed after wait of " + timeoutPeriod.getSeconds());
			return false;
		}
	}

	public void scrollToViewAndClick(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		click(element);
	}

	public void scrollToViewAndClickAndWait(WebElement element, WebElement nextExpectedElement) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		clickAndWait(element, nextExpectedElement);
	}

	public void selectDropDownAndOption(WebElement element, String option) {
		Select select = new Select(element);
		select.selectByVisibleText(option);
	}

	public boolean waitForJSandJQueryToLoad(Duration timeoutPeriod) {

		WebDriverWait wait = new WebDriverWait(driver, timeoutPeriod);

		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {

					return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					// no jQuery present
					return true;
				}
			}
		};

		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};

		return wait.until(jQueryLoad) && wait.until(jsLoad);

	}

}

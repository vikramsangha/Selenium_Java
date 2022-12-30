package com.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.qa.utils.TestUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {

	public static WebDriver driver;
	public static Properties prop;
	public static WebDriverManager webDriverManagerObject = null;
	private static String OS = System.getProperty("os.name").toLowerCase();
	public static Logger log = Logger.getLogger(TestBase.class.getName());

	public TestBase() {
		try {
			prop = new Properties();
			if (OS.contains("win")) {
				FileInputStream ip = new FileInputStream(
						System.getProperty("user.dir") + "/src/main/java/com" + "/qa/config/config.properties");
				prop.load(ip);
				log.info("Properties File Loaded Successfully");
			} else {
				FileInputStream ip = new FileInputStream("./src/main/java/com" + "/qa/config/config.properties");
				prop.load(ip);
				log.info("Properties File Loaded Successfully");
			}

		} catch (FileNotFoundException e) {
			log.severe("Properties File not found");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void initialization() {
		String browserName = prop.getProperty("browser");
		log.info("Platform : " + OS);
		if (browserName.equals("chrome")) {
			log.info("Browser Name : " + browserName);
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("safebrowsing.enabled", "true");
			ChromeOptions options = new ChromeOptions();
			// options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
			options.addArguments("disable-popup-blocking");
			options.addArguments("--disable-backgrounding-occluded-windows");
			options.addArguments("--no-sandbox");

			if (prop.getProperty("headless").contains("true")) {
				log.info("Launching the browser is headless mode");
				options.addArguments("--headless");
			}

			if (!prop.getProperty("remote_execution").contains("true")) {
				webDriverManagerObject = WebDriverManager.chromedriver();
				if (OS.contains("win")) {
					webDriverManagerObject.cachePath(System.getProperty("user.dir") + "/src/main/java/com");
					webDriverManagerObject.setup();
				}

				driver = new ChromeDriver(options);
			} else {
				log.info("Test will be executed on remote url: " + prop.getProperty("remote_selenium_url"));
				try {
					driver = new RemoteWebDriver(new URL(prop.getProperty("remote_selenium_url")), options);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		} else {
			log.severe("please pass the right browser name......");
		}

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIMEOUT));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtil.IMPLICIT_WAIT));

		log.info("Implicit Wait : " + TestUtil.IMPLICIT_WAIT);

		log.info("Launch URL : " + prop.getProperty("url"));
		driver.get(prop.getProperty("url"));

	}

}

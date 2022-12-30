package com.qa.extentreport;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.qa.base.TestBase;



public class ExtentReportListener extends TestBase implements ITestListener {

	private static final String OUTPUT_FOLDER = "./reports/";
	private static final String FILE_NAME = "TestExecutionReport.html";

	private static ExtentReports extent = init();
	public static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();
	private static ExtentReports extentReports;
	

	private static ExtentReports init() {

		Path path = Paths.get(OUTPUT_FOLDER);
		// if directory exists?
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				// fail to create directory
				e.printStackTrace();
			}
		}
		
		extentReports = new ExtentReports();
		ExtentSparkReporter reporter = new ExtentSparkReporter(OUTPUT_FOLDER + FILE_NAME);
		reporter.config().setReportName(" Automation Test Results");
		extentReports.attachReporter(reporter);
		extentReports.setSystemInfo("System", System.getProperty("os.name"));
		extentReports.setSystemInfo("Author", "Vikramjeet Sangha");
		extentReports.setSystemInfo("Build#", "1.1");
		extentReports.setSystemInfo("Team", "QA Team");
		
		return extentReports;
	}

	@Override
	public synchronized void onStart(ITestContext context) {
		System.out.println("Test Suite started!");
		
	}

	@Override
	public synchronized void onFinish(ITestContext context) {
		System.out.println(("Test Suite is ending!"));
		extent.flush();
		test.remove();
	}

	@Override
	public synchronized void onTestStart(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		String qualifiedName = result.getMethod().getQualifiedName();
		int last = qualifiedName.lastIndexOf(".");
		int mid = qualifiedName.substring(0, last).lastIndexOf(".");
		String className = qualifiedName.substring(mid + 1, last);

		System.out.println(methodName + " started!");
		ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),
				result.getMethod().getDescription());

		extentTest.assignCategory(result.getTestContext().getSuite().getName());
		/*
		 * methodName = StringUtils.capitalize(StringUtils.join(StringUtils.
		 * splitByCharacterTypeCamelCase(methodName), StringUtils.SPACE));
		 */
		extentTest.assignCategory(className);
		test.set(extentTest);
		test.get().getModel().setStartTime(getTime(result.getStartMillis()));
	}

	public synchronized void onTestSuccess(ITestResult result) {
		System.out.println((result.getMethod().getMethodName() + " passed!"));
		test.get().pass("Test passed");
		String methodName = result.getMethod().getMethodName();
		File scr = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		if(System.getProperty("os.name").contains("win")) {
			String dest = System.getProperty("user.dir") + "/reports/"+methodName+".png";
			File finalDestination = new File(dest);
			try {
				FileUtils.copyFile(scr, finalDestination);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			String dest = "./reports/"+methodName+".png";
			File finalDestination = new File(dest);
			try {
				FileUtils.copyFile(scr, finalDestination);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//test.get().pass(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(getScreenshot()).build());
		test.get().getModel().setEndTime(getTime(result.getEndMillis()));
	}

	public synchronized void onTestFailure(ITestResult result) {
		System.out.println((result.getMethod().getMethodName() + " failed!"));
		String methodName = result.getMethod().getMethodName();
		File scr = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		if(System.getProperty("os.name").contains("win")) {
			String dest = System.getProperty("user.dir") + "/reports/"+methodName+getTime(result.getEndMillis())+".png";
			File finalDestination = new File(dest);
			try {
				FileUtils.copyFile(scr, finalDestination);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			String dest = "./reports/"+methodName+getTime(result.getEndMillis())+".png";
			File finalDestination = new File(dest);
			try {
				FileUtils.copyFile(scr, finalDestination);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		test.get().getModel().setEndTime(getTime(result.getEndMillis()));
	}

	public synchronized void onTestSkipped(ITestResult result) {
		System.out.println((result.getMethod().getMethodName() + " skipped!"));
		test.get().getModel().setEndTime(getTime(result.getEndMillis()));
	}

	public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));
	}

	private Date getTime(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}

}

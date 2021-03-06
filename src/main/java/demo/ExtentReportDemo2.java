package demo;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ExtentReportDemo2 {
	private static final Utility Utility = null;
	// Create global variable which will be used in all method
	ExtentReports extent;
	ExtentTest logger;
	WebDriver driver;

	// This code will run before executing any testcase
	@BeforeMethod
	public void setup() {
		ExtentHtmlReporter reporter = new ExtentHtmlReporter("./Reports/learn_automation2.html");

		extent = new ExtentReports();

		extent.attachReporter(reporter);

		logger = extent.createTest("LoginTest");
	}

	// Actual Test which will start the application and verify the title
	@Test
	public void loginTest() throws IOException {
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://www.google.com");
		System.out.println("title is " + driver.getTitle());
	//	System.out.println("title is " + driver.getTitle());
		Assert.assertTrue(driver.getTitle().contains("Googlee"));

	}

	// This will run after testcase and it will capture screenshot and add in report
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {

		if (result.getStatus() == ITestResult.FAILURE) {
			String temp = Utility.getScreenshot(driver);

			logger.fail(result.getThrowable().getMessage(),
					MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			String temp2 = Utility.getScreenshot(driver);
			logger.pass("Test Case passed " + result.getName(),	MediaEntityBuilder.createScreenCaptureFromPath(temp2).build());
			logger.pass(result.getName(), MediaEntityBuilder.createScreenCaptureFromPath(temp2).build());
		}

	}

	@AfterTest
	public void endReport() {
		// writing everything to document
		// flush() - to write or update test information to your report.

		// Call close() at the very end of your session to clear all resources.
		// If any of your test ended abruptly causing any side-affects (not all logs
		// sent to ExtentReports, information missing), this method will ensure that the
		// test is still appended to the report with a warning message.
		// You should call close() only once, at the very end (in @AfterSuite for
		// example) as it closes the underlying stream.
		// Once this method is called, calling any Extent method will throw an error.
		// close() - To close all the operation

		extent.flush();
		driver.quit();
	}

}
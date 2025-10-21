package com.booker.listeners;

import org.apache.logging.log4j.LogManager;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExtentReportListener implements ITestListener {

	// extent - object that coordinates the entire report
	// test - object that represents a single test within a report, every time a new
	// Test runs, a new object is created.

	private ExtentReports extent;
	private ExtentTest test;
	private static final Logger logger = LogManager.getLogger(ExtentReportListener.class);

	@Override
	public void onStart(ITestContext context) {

		// This runs once before the entire test suite starts.
		// We set up the HTML report file here.

		ExtentSparkReporter spark = new ExtentSparkReporter("test-output/extent-report.html");
		extent = new ExtentReports();
		extent.attachReporter(spark);
		logger.info("Report setup initialized.");
	}

	@Override
	public void onFinish(ITestContext context) {

		// This runs once after the entire test suite finishes.
		// We write the report to the file.

		extent.flush(); // key to generating the final report at the end
		logger.info("Report generation finished.");
	}

	@Override
	public void onTestStart(ITestResult result) {

		// This runs before each @Test method.
		// We create a new entry in the report for the test.
		
		logger.info("=================================================================");
        logger.info("STARTING TEST: {}", result.getMethod().getMethodName());
        logger.info("=================================================================");

		test = extent.createTest(result.getMethod().getMethodName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {

		// This runs when a test passes.
		// We log the 'PASS' status in the report.

		test.log(Status.PASS, "Test Passed");
		logger.info("TEST PASSED: {}", result.getMethod().getMethodName());
        logger.info("=================================================================\n");
		
		
	}

	@Override
	public void onTestFailure(ITestResult result) {

		// This runs when a test fails.
		// We log the 'FAIL' status and the error details.

		test.log(Status.FAIL, "Test Failed");
		test.log(Status.FAIL, result.getThrowable());
		
		logger.error("TEST FAILED: {}", result.getMethod().getMethodName());
        logger.error("=================================================================\n");
	}

}

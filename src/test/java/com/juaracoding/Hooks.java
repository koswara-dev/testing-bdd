package com.juaracoding;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.juaracoding.drivers.DriverSingleton;
import com.juaracoding.utils.Constants;
import com.juaracoding.utils.ExtentReportManager;
import com.juaracoding.utils.Utils;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;

public class Hooks {

    static WebDriver driver;
    private static final ExtentReports extentReports = ExtentReportManager.getInstance();
    private static final ThreadLocal<ExtentTest> scenarioTest = new ThreadLocal<>();

    static Logger logger = org.slf4j.LoggerFactory.getLogger(Hooks.class);

    @Before
    public void setUp(Scenario scenario) {
        logger.info("Setting up the WebDriver");
        DriverSingleton.getInstance(Constants.FIREFOX);
        driver = DriverSingleton.getDriver();
        ExtentTest extentTest = extentReports.createTest(scenario.getName());
        scenarioTest.set(extentTest);
        extentTest.info("Scenario started");
    }

    @BeforeStep
    public void beforeStep() {
        logger.info("Before step: " + driver.getCurrentUrl());
        System.out.println("Before step: " + driver.getCurrentUrl());
        scenarioTest.get().log(Status.INFO, "Current URL: " + driver.getCurrentUrl());
    }

    @After
    public void afterScenario(Scenario scenario) {
        logger.info("After scenario: " + driver.getCurrentUrl());
        System.out.println("After scenario: " + driver.getCurrentUrl());

        ExtentTest extentTest = scenarioTest.get();
        if (scenario.isFailed()) {
            extentTest.fail("Scenario failed");
            String screenshotPath = Utils.captureScreenshot(scenario.getName());
            if (screenshotPath != null) {
                try {
                    extentTest.addScreenCaptureFromPath(screenshotPath.replace("target\\extent-report\\", ""));
                } catch (Exception e) {
                    logger.error("Failed to attach screenshot to Extent Report", e);
                    extentTest.warning("Screenshot captured but could not be attached");
                }
            }
        } else {
            extentTest.pass("Scenario passed");
        }

        scenarioTest.remove();
    }

    @AfterAll
    public static void tearDown() {
        logger.info("Tearing down the WebDriver");
        if (driver != null) {
            DriverSingleton.closeObjectInstance();
        }
        ExtentReportManager.flush();
    }

}

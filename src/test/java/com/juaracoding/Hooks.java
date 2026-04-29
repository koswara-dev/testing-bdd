package com.juaracoding;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.gherkin.model.And;
import com.aventstack.extentreports.gherkin.model.But;
import com.aventstack.extentreports.gherkin.model.Given;
import com.aventstack.extentreports.gherkin.model.Then;
import com.aventstack.extentreports.gherkin.model.When;
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
    private static final ThreadLocal<ExtentTest> currentStepTest = new ThreadLocal<>();

    static Logger logger = org.slf4j.LoggerFactory.getLogger(Hooks.class);

    @Before
    public void setUp(Scenario scenario) {
        logger.info("Setting up the WebDriver");
        DriverSingleton.getInstance(Constants.FIREFOX_HEADLESS);
        driver = DriverSingleton.getDriver();
        ExtentTest extentTest = extentReports.createTest(scenario.getName());
        scenarioTest.set(extentTest);
        extentTest.info("Scenario started");
    }

    @BeforeStep
    public void beforeStep() {
        logger.info("Before step: " + driver.getCurrentUrl());
        ExtentTest stepNode = currentStepTest.get();
        if (stepNode != null) {
            stepNode.log(Status.INFO, "Current URL: " + driver.getCurrentUrl());
        } else {
            scenarioTest.get().log(Status.INFO, "Current URL: " + driver.getCurrentUrl());
        }
    }

    @After
    public void afterScenario(Scenario scenario) {
        logger.info("After scenario: " + driver.getCurrentUrl());

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

        currentStepTest.remove();
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

    public static ExtentTest getScenarioTest() {
        return scenarioTest.get();
    }

    public static ExtentTest startStepNode(String keyword, String stepText) {
        ExtentTest parent = scenarioTest.get();
        if (parent == null) {
            return null;
        }

        ExtentTest stepNode;
        switch (keyword.trim()) {
            case "Given":
                stepNode = parent.createNode(Given.class, stepText);
                break;
            case "When":
                stepNode = parent.createNode(When.class, stepText);
                break;
            case "Then":
                stepNode = parent.createNode(Then.class, stepText);
                break;
            case "And":
                stepNode = parent.createNode(And.class, stepText);
                break;
            case "But":
                stepNode = parent.createNode(But.class, stepText);
                break;
            default:
                stepNode = parent.createNode(stepText);
                break;
        }

        currentStepTest.set(stepNode);
        return stepNode;
    }

    public static ExtentTest getCurrentStepTest() {
        return currentStepTest.get();
    }

    public static void clearCurrentStepTest() {
        currentStepTest.remove();
    }

}

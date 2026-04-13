package com.juaracoding.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import com.juaracoding.drivers.DriverSingleton;

public class Utils {

    private static final String SCREENSHOT_DIRECTORY = "target/extent-report/screenshots";
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(Utils.class);

    private Utils() {
    }

    public static String captureScreenshot(String scenarioName) {
        WebDriver driver = DriverSingleton.getDriver();
        if (!(driver instanceof TakesScreenshot)) {
            return null;
        }

        try {
            Path screenshotDir = Paths.get(SCREENSHOT_DIRECTORY);
            Files.createDirectories(screenshotDir);

            String safeScenarioName = scenarioName.replaceAll("[^a-zA-Z0-9-_]", "_");
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            String fileName = safeScenarioName + "_" + timestamp + ".png";
            Path targetPath = screenshotDir.resolve(fileName);

            File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(sourceFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            return targetPath.toString();
        } catch (IOException e) {
            logger.error("Failed to capture screenshot for scenario {}", scenarioName, e);
            return null;
        }
    }
}

package com.juaracoding.utils;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public final class ExtentReportManager {

    private static final String REPORT_DIRECTORY = "target/extent-report";
    private static final String REPORT_FILE = "extent-report.html";
    private static ExtentReports extentReports;

    private ExtentReportManager() {
    }

    public static synchronized ExtentReports getInstance() {
        if (extentReports == null) {
            File reportDir = new File(REPORT_DIRECTORY);
            if (!reportDir.exists()) {
                reportDir.mkdirs();
            }

            ExtentSparkReporter sparkReporter =
                new ExtentSparkReporter(REPORT_DIRECTORY + "/" + REPORT_FILE);
            sparkReporter.config().setReportName("BDD Automation Report");
            sparkReporter.config().setDocumentTitle("Extent Report");

            extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter);
            extentReports.setSystemInfo("Project", "testing-bdd");
            extentReports.setSystemInfo("Generated", LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            extentReports.setSystemInfo("Browser", System.getProperty("browser", Constants.FIREFOX));
        }

        return extentReports;
    }

    public static synchronized void flush() {
        if (extentReports != null) {
            extentReports.flush();
        }
    }
}

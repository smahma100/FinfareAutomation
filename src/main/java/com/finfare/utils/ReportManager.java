
package com.finfare.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ReportManager {
    private static ExtentReports extentReports;
    private static ExtentTest extentTest;

    public static void initReports() {

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("reports/extent-report.html");
        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
    }

    public static void createTest(String testName) {
        extentTest = extentReports.createTest(testName);
    }

    public static void flushReports() {
        extentReports.flush();
    }

    public static ExtentTest getTest() {
        return extentTest;
    }
}

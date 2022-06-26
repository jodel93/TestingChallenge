package com.testing.listener;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.testing.base.BaseTest;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Objects;

public class TestListener extends BaseTest implements ITestListener {

    private ExtentReports extent;
    private ExtentSparkReporter spark;

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    @Override
    public void onStart(ITestContext iTestContext){
        extent = new ExtentReports();
        spark = new ExtentSparkReporter(String.format("extent-reports/%s.html",iTestContext.getName()));
        extent.attachReporter(spark);
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        System.out.println(
                String.format("Stating test: %s", getTestMethodName(iTestResult))
        );
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        extent.createTest(getTestMethodName(iTestResult))
                .log(Status.PASS, "PASSED");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        Object testClass = iTestResult.getInstance();
        WebDriver driver = ((BaseTest) testClass).getDriver();
        String base64Screenshot =
                "data:image/png;base64," + ((TakesScreenshot) Objects.requireNonNull(driver)).getScreenshotAs(OutputType.BASE64);
        extent.createTest(getTestMethodName(iTestResult))
                .log(Status.FAIL, "FAILED").addScreenCaptureFromBase64String(base64Screenshot).getModel().getMedia().get(0);
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        extent.createTest(getTestMethodName(iTestResult))
                .log(Status.SKIP, "SKIPPED");
    }

    @Override
    public void onFinish(ITestContext iTestContext){
        extent.flush();
    }

}

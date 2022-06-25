package com.testing.utils;

import com.testing.enums.Browser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {

    private static final String localDriverPath = System.getProperty("user.dir") + "/src/test/resources/drivers/%s";
    private static WebDriver driver;

    public static WebDriver setUpDriver(Browser browser){
        if(Browser.chrome == browser){
            System.setProperty("webdriver.chrome.driver",
                    String.format(localDriverPath, Browser.chrome.label));
            driver = new ChromeDriver();
        }
        if(Browser.firefox == browser){
            System.setProperty("webdriver.gecko.driver",
                    String.format(localDriverPath, Browser.firefox.label));
            driver = new FirefoxDriver();
        }
        if(Browser.ie == browser){
            System.setProperty("webdriver.edge.driver", String.format(localDriverPath, "msedgediver.exe"));
            driver = new EdgeDriver();
        }

        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();

        return driver;
    }
}

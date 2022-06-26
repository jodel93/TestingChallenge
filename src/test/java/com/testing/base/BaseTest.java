package com.testing.base;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@Component
public class BaseTest extends AbstractTestNGSpringContextTests {

    /*
    This class is intended to inherit from AbstractTestNGSpringContextTests
    in order to use the Spring Context in our test, it also can be used to
    configure our test
     */

    @Autowired
    protected WebDriver driver;
    public WebDriver getDriver() { return driver; }
}
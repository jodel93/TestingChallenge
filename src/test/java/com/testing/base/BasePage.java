package com.testing.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    @Autowired
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 10);
        PageFactory.initElements(this.driver, this);
    }

    public WebDriver getDriver() {
        return this.driver;
    }

    public void navigateTo(String url){
        driver.get(url);
    }

    public void waitClickableAndClick(WebElement elementToClick){
        wait.until(ExpectedConditions.elementToBeClickable(elementToClick));
        elementToClick.click();
    }
}
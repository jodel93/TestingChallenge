package com.testing.pages;

import com.testing.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HomePage extends BasePage {

    @FindBy(id="inputImage")
    private WebElement imageInput;
    @FindBy(tagName="textarea")
    private WebElement textAreaInput;
    @FindBy(xpath="//button[contains(text(),'Create Item')]")
    private WebElement createItemButton;
    @FindBy(xpath = "//body/div[@id='content']/div[1]/div[1]/ul[1]/li")
    private List<WebElement> listOfItems;
    @FindBy(className = "modal-dialog")
    private WebElement modalBox;

    private final String ITEM_LIST_XPATH = "//body/div[@id='content']/div[1]/div[1]/ul[1]/li";
    private final String DELETE_ITEM_BUTTON =   ".//div[1]/div[1]/div[1]/button[2]";
    private final String MODAL_DELETE_BUTTON = "//button[contains(text(),'Yes, delete it!')]";

    private int itemCount;

    public int getItemCount(){ return itemCount; }
    public void setItemCount(int itemCount){ this.itemCount = itemCount; }

    @Autowired
    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void uploadImage(String filePath){
        wait.until(ExpectedConditions.elementToBeClickable(imageInput));
        imageInput.sendKeys(filePath);
    }

    public void fillDescription(String textDescriptions){ textAreaInput.sendKeys(textDescriptions); }

    public void clickCreateItemButton(){
        setItemCount(getNewNumberOfItems());
        waitClickableAndClick(createItemButton);
    }

    public String getImageSrcFromLastItem(){
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                By.xpath(ITEM_LIST_XPATH), itemCount));
        return getLastItem().findElement(By.tagName("img")).getAttribute("src");
    }

    public String getTextFromLastItem(){
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                By.xpath(ITEM_LIST_XPATH), itemCount));
        return getLastItem().findElement(By.tagName("p")).getText();
    }

    public void deleteLastElementFromList(){
        waitClickableAndClick(getLastItem().findElement(By.xpath(DELETE_ITEM_BUTTON)));
        wait.until(ExpectedConditions.visibilityOf(modalBox));
        modalBox.findElement(By.xpath(MODAL_DELETE_BUTTON)).click();
        wait.until(ExpectedConditions.numberOfElementsToBe(
                By.xpath(ITEM_LIST_XPATH), itemCount));
    }

    public int getNewNumberOfItems(){
        wait.until(ExpectedConditions.visibilityOfAllElements(listOfItems));
        return listOfItems.size();
    }

    private WebElement getLastItem(){
        wait.until(ExpectedConditions.visibilityOfAllElements(listOfItems));
        return listOfItems.get(listOfItems.size() - 1);
    }
}

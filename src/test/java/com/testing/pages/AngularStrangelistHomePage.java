package com.testing.pages;

import com.testing.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AngularStrangelistHomePage extends BasePage {

    //region Constants
    private final String ITEM_LIST_XPATH = "//body/div[@id='content']/div[1]/div[1]/ul[1]/li";
    private final String DELETE_ITEM_BUTTON = ".//div[1]/div[1]/div[1]/button[2]";
    private final String EDIT_ITEM_BUTTON = ".//div[1]/div[1]/div[1]/button[1]";
    private final String MODAL_DELETE_BUTTON = "//button[contains(text(),'Yes, delete it!')]";
    //endregion

    //region WebElements
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
    @FindBy(xpath = "//button[contains(text(),'Update Item')]")
    private WebElement updateItemButton;
    //endregion

    //region Private Variables
    private int itemCount;
    //endregion

    //region Getters and Setters
    public int getItemCount(){ return itemCount; }
    public void setItemCount(int itemCount){ this.itemCount = itemCount; }
    //endregion

    //region Constructors
    @Autowired
    public AngularStrangelistHomePage(WebDriver driver) {
        super(driver);
    }
    //endregion

    //region Void Methods
    public void uploadImage(String filePath){
        wait.until(ExpectedConditions.elementToBeClickable(imageInput));
        imageInput.sendKeys(filePath);
    }

    public void fillDescription(String textDescriptions){ textAreaInput.sendKeys(textDescriptions); }

    public void clickCreateItemButton(){
        setItemCount(getNewNumberOfItems());
        waitClickableAndClick(createItemButton);
    }

    public void deleteLastElementFromList(){
        waitClickableAndClick(getLastItem().findElement(By.xpath(DELETE_ITEM_BUTTON)));
        waitClickableAndClick(modalBox.findElement(By.xpath(MODAL_DELETE_BUTTON)));
        wait.until(ExpectedConditions.numberOfElementsToBe(
                By.xpath(ITEM_LIST_XPATH), itemCount));
    }

    public int getNewNumberOfItems(){
        wait.until(ExpectedConditions.visibilityOfAllElements(listOfItems));
        return listOfItems.size();
    }

    public void editElementFromList(int index){
        getItem(index).findElement(By.xpath(EDIT_ITEM_BUTTON)).click();
    }

    public void updateDescription(String textToAdd){
        wait.until(ExpectedConditions.visibilityOf(textAreaInput));
        textAreaInput.sendKeys(textToAdd);
    }

    public void clickUpdateDescriptionButton(){
        waitClickableAndClick(updateItemButton);
    }
    //endregion

    //region String Methods
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

    public String getTextFromItem(int index){
        return getItem(index).findElement(By.tagName("p")).getText();
    }
    //endregion

    //region boolean Methods
    public boolean isButtonActive(){ return createItemButton.isEnabled(); }
    //endregion

    //region WebElements Methods
    public WebElement searchItemInListByText(String searchCriteria){
        for (int i=0; i < getNewNumberOfItems(); i++){
            String itemText = listOfItems.get(i).findElement(By.tagName("p")).getText();
            if (itemText.contains(searchCriteria))
                return listOfItems.get(i);
        }
        return null;
    }

    private WebElement getLastItem(){
        wait.until(ExpectedConditions.visibilityOfAllElements(listOfItems));
        return listOfItems.get(listOfItems.size() - 1);
    }

    private WebElement getItem(int index){
       // wait.until(ExpectedConditions.visibilityOfAllElements(listOfItems));
        return listOfItems.get(index);
    }
    //endregion
}

package com.testing.tests;


import com.testing.Driver;
import com.testing.base.BaseTest;
import com.testing.pages.HomePage;
import com.testing.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

@DirtiesContext
@ContextConfiguration(classes = {Driver.class})
public class AngularStrangelistTest extends BaseTest {

    @Autowired
    private HomePage homePage;

    @Test(description = "Create new item using valid data")
    @Parameters({"textDescription", "imagePath"})
    public void create_new_item_with_valid_data(String textDescription, String imagePath){
        homePage.navigateTo("http://immense-hollows-74271.herokuapp.com/");
        homePage.uploadImage(System.getProperty("user.dir") + imagePath);
        homePage.fillDescription(textDescription);
        homePage.clickCreateItemButton();
        Assert.assertTrue(homePage.getImageSrcFromLastItem().contains(Utils.getFileNameFromPath(imagePath)));
        Assert.assertTrue(homePage.getTextFromLastItem().contains(textDescription));
    }

    @Test(dependsOnMethods = "create_new_item_with_valid_data")
    public void delete_newly_created_item(){
        homePage.deleteLastElementFromList();
        Assert.assertEquals(homePage.getNewNumberOfItems(), homePage.getItemCount());
    }

    @Test(dependsOnMethods = "delete_newly_created_item")
    @Parameters({"imagePath", "maxTextDescription"})
    public void create_item_with_more_than_max_characters_description(String imagePath, String maxTextDescription){
//        homePage.uploadImage(System.getProperty("user.dir") + imagePath);
        homePage.fillDescription(Utils.getRandomText(Integer.parseInt(maxTextDescription) + 1));
        Assert.assertFalse(homePage.isButtonActive());
    }

    @Test(dependsOnMethods = "create_item_with_more_than_max_characters_description")
    @Parameters({"searchCriteria"})
    public void search_for_existing_item_in_list(String searchCriteria){
        Assert.assertNotNull(homePage.searchItemInListByText(searchCriteria));
    }

    @Test(dependsOnMethods = "search_for_existing_item_in_list")
    @Parameters({"textDescription", "itemIndex"})
    public void edit_an_existing_item(String textDescription, String itemIndex){
        homePage.editElementFromList(Integer.parseInt(itemIndex));
        homePage.updateDescription(textDescription);
        homePage.clickUpdateDescriptionButton();
        Assert.assertTrue(homePage.getTextFromItem(4).contains(textDescription));
    }

    @AfterClass(alwaysRun = true)
    public void tearDown(){
        homePage.getDriver().quit();
    }
}
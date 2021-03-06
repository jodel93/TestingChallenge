package com.testing.tests;


import com.testing.Driver;
import com.testing.base.BaseTest;
import com.testing.listener.TestListener;
import com.testing.pages.AngularStrangelistHomePage;
import com.testing.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

@DirtiesContext
@ContextConfiguration(classes = {Driver.class})
@PropertySource("classpath:/properties/config.properties")
@Listeners(TestListener.class)
public class AngularStrangelistTest extends BaseTest {

    @Value("${home.url}")
    private String url;

    @Autowired
    private AngularStrangelistHomePage homePage;

    @Test(priority = 0, description = "Create new item using valid data")
    @Parameters({"textDescription", "imagePath"})
    public void create_new_item_with_valid_data(String textDescription, String imagePath){
        homePage.navigateTo(url);
        homePage.uploadImage(System.getProperty("user.dir") + imagePath);
        homePage.fillDescription(textDescription);
        homePage.clickCreateItemButton();
        Assert.assertTrue(homePage.getImageSrcFromLastItem().contains(Utils.getFileNameFromPath(imagePath)));
        Assert.assertTrue(homePage.getTextFromLastItem().contains(textDescription));
    }

    @Test(priority = 0, dependsOnMethods = "create_new_item_with_valid_data")
    public void delete_newly_created_item(){
        homePage.deleteLastElementFromList();
        Assert.assertEquals(homePage.getNewNumberOfItems(), homePage.getItemCount());
    }

    @Test(priority = 1)
    @Parameters({"imagePath", "maxTextDescription"})
    public void create_item_with_more_than_max_characters_description(String imagePath, String maxTextDescription){
        homePage.uploadImage(System.getProperty("user.dir") + imagePath);
        homePage.fillDescription(Utils.getRandomText(Integer.parseInt(maxTextDescription) + 1));
        Assert.assertFalse(homePage.isButtonActive());
    }

    @Test(priority = 2)
    @Parameters({"searchCriteria"})
    public void search_for_existing_item_in_list(String searchCriteria){
        Assert.assertNotNull(homePage.searchItemInListByText(searchCriteria));
    }

    @Test(priority = 3)
    @Parameters({"textDescription", "itemIndex"})
    public void edit_an_existing_item(String textDescription, String itemIndex){
        homePage.editElementFromList(Integer.parseInt(itemIndex));
        homePage.updateDescription(textDescription);
        homePage.clickUpdateDescriptionButton();
        Assert.assertTrue(homePage.getTextFromItem(Integer.parseInt(itemIndex)).contains(textDescription));
    }

    @AfterClass(alwaysRun = true)
    public void tearDown(){
        homePage.getDriver().quit();
    }
}
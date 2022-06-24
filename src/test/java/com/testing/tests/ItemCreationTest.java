package com.testing.tests;

import com.testing.Driver;
import com.testing.base.BaseTest;
import com.testing.pages.HomePage;
import com.testing.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

@ContextConfiguration(classes = {Driver.class})
public class ItemCreationTest extends BaseTest {

    @Autowired
    private HomePage homePage;

    @Test
    public void navigate_home(){
        homePage.navigateTo("http://immense-hollows-74271.herokuapp.com/");
    }

    @Test(dependsOnMethods = "navigate_home")
    public void upload_image_and_add_description(){
        homePage.uploadImage("C:/Users/jorge/OneDrive/Documentos/Test.jpg");
        homePage.fillDescription("TEST");
    }

    @Test(dependsOnMethods = "upload_image_and_add_description")
    public void create_item() throws InterruptedException {
        homePage.clickCreateItemButton();
        Assert.assertTrue(homePage.getImageSrcFromLastItem().contains(Utils.getFileNameFromPath("C:/Users/jorge/OneDrive/Documentos/Test.jpg")));
    }

    @Test(dependsOnMethods = "create_item")
    public void delete_newly_created_item(){
        homePage.deleteLastElementFromList();
        Assert.assertEquals(homePage.getNewNumberOfItems(), homePage.getItemCount());
    }
}

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

    @Test(description = "Create new item using valid data")
    public void create_new_item_with_valid_data(){
        homePage.navigateTo("http://immense-hollows-74271.herokuapp.com/");
        homePage.uploadImage("C:/Users/jorge/OneDrive/Documentos/Test.jpg");
        homePage.fillDescription("TEST");
        homePage.clickCreateItemButton();
        Assert.assertTrue(homePage.getImageSrcFromLastItem().contains(Utils.getFileNameFromPath("C:/Users/jorge/OneDrive/Documentos/Test.jpg")));
        Assert.assertTrue(homePage.getTextFromLastItem().contains("TEST"));
    }

    @Test(dependsOnMethods = "create_new_item_with_valid_data")
    public void delete_newly_created_item(){
        homePage.deleteLastElementFromList();
        Assert.assertEquals(homePage.getNewNumberOfItems(), homePage.getItemCount());
    }

    @Test(dependsOnMethods = "delete_newly_created_item")
    public void create_item_with_more_than_300_characters_description(){
        homePage.uploadImage("C:/Users/jorge/OneDrive/Documentos/Test.jpg");
        homePage.fillDescription("tellus in metus vulputate eu scelerisque felis imperdiet proin fermentum leo vel orci porta non pulvinar neque laoreet suspendisse interdum consectetur libero id faucibus nisl tincidunt eget nullam non nisi est sit amet facilisis magna etiam tempor orci eu lobortis elementum nibh tellus molestie nunc non blandit massa enim nec dui nunc mattis enim ut tellus elementum sagittis vitae et leo duis ut diam quam nulla porttitor massa id neque aliquam vestibulum morbi blandit cursus risus at ultrices mi tempus imperdiet nulla malesuada pellentesque elit eget gravida cum sociis natoque penatibus et magnis dis parturient montes nascetur ridiculus mus mauris vitae ultricies leo integer malesuada nunc vel risus commodo viverra maecenas accumsan lacus vel facilisis volutpat est velit egestas dui id ornare arcu odio ut sem nulla pharetra diam sit amet nisl suscipit adipiscing bibendum est ultricies integer quis auctor elit sed vulputate mi sit amet mauris commodo quis imperdiet");
        Assert.assertFalse(homePage.isButtonActive());
    }

    @Test(dependsOnMethods = "create_item_with_more_than_300_characters_description")
    public void search_for_existing_item_in_list(){
        Assert.assertNotNull(homePage.searchItemInListByText());
    }

    @Test(dependsOnMethods = "search_for_existing_item_in_list")
    public void edit_an_existing_item(){
        homePage.editElementFromList(4);
        homePage.updateDescription("ANOTHER TEST");
        homePage.clickUpdateDescriptionButton();
        Assert.assertTrue(homePage.getTextFromItem(4).contains("ANOTHER TEST"));
    }
}

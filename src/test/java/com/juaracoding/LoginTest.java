package com.juaracoding;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.testng.Assert;

import com.juaracoding.pages.InventoryPage;
import com.juaracoding.pages.LoginPage;
import com.juaracoding.utils.Constants;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginTest {

    private static WebDriver driver;
    private static LoginPage loginPage;
    private static InventoryPage inventoryPage;

    Logger logger = org.slf4j.LoggerFactory.getLogger(LoginTest.class);

    public LoginTest() {
        logger.info("Initializing LoginTest");
        driver = Hooks.driver;
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
    }

    @Given("the user is on the login page")
    public void the_user_is_on_the_login_page() {
        logger.info("Navigating to the login page");
        driver.get(Constants.URL);
    }

    @When("the user enters valid username and password")
    public void the_user_enters_valid_username_and_password() {
        logger.info("Entering valid username and password");
        loginPage.setCredentials("standard_user", "secret_sauce");
    }

    @And("clicks the login button")
    public void clicks_the_login_button() {
        logger.info("Clicking the login button");
        loginPage.clickLogin();
    }

    @Then("the user should be redirected to the inventory page")
    public void the_user_should_be_redirected_to_the_inventory_page() {
        logger.info("Checking if user is redirected to the inventory page");
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"), "User should be logged in and on the inventory page");
    }

    @And("the page header should display Products")
    public void the_page_header_should_display_Products() {
        logger.info("Checking the page header");
        Assert.assertEquals(inventoryPage.getPageHeader(), "Products", "Page header should display Products");
    }

    @When("the user enters wrong username and password")
    public void the_user_enters_invalid_username_and_password() {
        logger.info("Entering invalid username and password");
        inventoryPage.clickMenu();
        inventoryPage.clickLogout();
        loginPage.setCredentials("wrong_user", "wrong_password");
    }

    @Then("the user should see an error message")
    public void the_user_should_see_an_error_message() {
        logger.info("Checking for error message");
        Assert.assertTrue(loginPage.getErrorText().contains("username and password do not match"), "Error message should indicate invalid credentials");
    }
}

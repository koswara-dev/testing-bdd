package com.juaracoding;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.juaracoding.drivers.DriverSingleton;
import com.juaracoding.pages.InventoryPage;
import com.juaracoding.pages.LoginPage;
import com.juaracoding.utils.Constants;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginTest {

    private static WebDriver driver;
    private static LoginPage loginPage;
    private static InventoryPage inventoryPage;

    @BeforeAll
    public static void setUp() {
        DriverSingleton.getInstance(Constants.FIREFOX);
        driver = DriverSingleton.getDriver();
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
    }

    @AfterAll
    public static void tearDown() {
        DriverSingleton.closeObjectInstance();
    }

    @Given("the user is on the login page")
    public void the_user_is_on_the_login_page() {
        driver.get(Constants.URL);
    }

    @When("the user enters valid username and password")
    public void the_user_enters_valid_username_and_password() {
        loginPage.setCredentials("standard_user", "secret_sauce");
    }

    @And("clicks the login button")
    public void clicks_the_login_button() {
        loginPage.clickLogin();
    }

    @Then("the user should be redirected to the inventory page")
    public void the_user_should_be_redirected_to_the_inventory_page() {
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"), "User should be logged in and on the inventory page");
    }

    @And("the page header should display Products")
    public void the_page_header_should_display_Products() {
        Assert.assertEquals(inventoryPage.getPageHeader(), "Products", "Page header should display Products");
    }

    @When("the user enters wrong username and password")
    public void the_user_enters_invalid_username_and_password() {
        inventoryPage.clickMenu();
        inventoryPage.clickLogout();
        loginPage.setCredentials("wrong_user", "wrong_password");
    }

    @Then("the user should see an error message")
    public void the_user_should_see_an_error_message() {
        Assert.assertTrue(loginPage.getErrorText().contains("Username and password do not match"), "Error message should indicate invalid credentials");
    }
}

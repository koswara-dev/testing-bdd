package com.juaracoding;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import com.juaracoding.pages.InventoryPage;
import com.juaracoding.pages.LoginPage;
import com.juaracoding.utils.Constants;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class InventoryTest {

    private static WebDriver driver;
    private static LoginPage loginPage;
    private static InventoryPage inventoryPage;

    Logger logger = org.slf4j.LoggerFactory.getLogger(InventoryTest.class);

    public InventoryTest() {
        logger.info("Initializing InventoryTest");
        driver = Hooks.driver;
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
    }

    // Add product to cart use collection list
    @Given("the user is on the inventory page")
    public void the_user_is_on_the_inventory_page() {
        logger.info("Navigating to the inventory page");
        driver.get(Constants.URL);
        loginPage.setCredentials("standard_user", "secret_sauce");
        loginPage.clickLogin();
        inventoryPage.getAddToCartButtons().forEach(button -> {
            System.out.println("Found add to cart button: " + button.getAttribute("name"));
        });
        System.out.println(inventoryPage.getAddToCartButtons().size() + " add to cart buttons found on the inventory page");
    }

    @When("the user clicks the {string} button for a product")
    public void the_user_clicks_the_button_for_a_product(String button) {
        logger.info("User clicks the " + button + " button for a product");
        System.out.println("User clicks the " + button + " button for a product");
    }

    @Then("the product should be added to the cart")
    public void the_product_should_be_added_to_the_cart() {
        logger.info("Checking if product is added to the cart");
        System.out.println("Product should be added to the cart");
    }

    @And("the cart icon should display the number of items in the cart")
    public void the_cart_icon_should_display_the_number_of_items_in_the_cart() {
        logger.info("Checking the cart icon for the number of items in the cart");
        System.out.println("Cart icon should display the number of items in the cart");
    }

    @When("the user clicks the {string} button for {string}")
    public void the_user_clicks_the_add_to_cart_button_for_product(String button, String product) {
        logger.info("User clicks the " + button + " button for product: " + product);
        System.out.println("User clicks the " + button + " button for product: " + product);
        inventoryPage.clickAddToCart(product);
    }

    @Then("{string} should be added to the cart")
    public void product_should_be_added_to_the_cart(String product) {
        logger.info(product + " should be added to the cart");
        System.out.println(product + " should be added to the cart");
    }

    @And("the cart icon should display the correct number of items in the cart")
    public void the_cart_icon_should_display_the_correct_number_of_items_in_the_cart() {
        logger.info("Checking the cart icon for the correct number of items in the cart");
        System.out.println("Cart icon should display the correct number of items in the cart");
    }

}

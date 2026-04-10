package com.juaracoding;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginTest {


    @Given("the user is on the login page")
    public void the_user_is_on_the_login_page() {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("User is on the login page");
    }

    @When("the user enters valid username and password")
    public void the_user_enters_valid_username_and_password() {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("User enters valid username and password");
    }

    @And("clicks the login button")
    public void clicks_the_login_button() {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("User clicks the login button");
    }

    @Then("the user should be redirected to the inventory page")
    public void the_user_should_be_redirected_to_the_inventory_page() {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("User is redirected to the inventory page");
    }

    @And("the page header should display Products")
    public void the_page_header_should_display_Products() {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("Page header displays Products");
    }

    @When("the user enters wrong username and password")
    public void the_user_enters_invalid_username_and_password() {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("User enters invalid username and password");
    }

    @Then("the user should see an error message")
    public void the_user_should_see_an_error_message() {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("User sees an error message");
    }
}

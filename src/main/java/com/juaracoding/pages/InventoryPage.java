package com.juaracoding.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class InventoryPage {

    public InventoryPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    // element text Products
    @FindBy(xpath = "//span[@class='title']")
    private WebElement pageHeader;

    // element logout
    @FindBy(id = "react-burger-menu-btn")
    private WebElement menuButton;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    @FindBy(xpath = "//button[text()='Add to cart']")
    private List<WebElement> addToCartBtn;

    public void clickMenu() {
        menuButton.click();
    }

    public void clickLogout() {
        logoutLink.click();
    }

    public void clickAddToCart(String productName) {
        String buttonXpath = String.format("//button[@name='add-to-cart-%s']", productName.toLowerCase().replace(" ", "-"));
        System.out.println("Clicking add to cart button for product: " + buttonXpath);
    }

    public List<WebElement> getAddToCartButtons() {
        return addToCartBtn;
    }

    public String getPageHeader() {
        return pageHeader.getText();
    }



}

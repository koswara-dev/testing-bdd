package com.juaracoding.pages;

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

    public void clickMenu() {
        menuButton.click();
    }

    public void clickLogout() {
        logoutLink.click();
    }

    public String getPageHeader() {
        return pageHeader.getText();
    }



}

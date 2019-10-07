package com.hillel.page.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by alpa on 2019-09-05
 */
public class CustomerLoginPopup extends BasePage {

    private By popupLocator = By.id("login-modal");

    public CustomerLoginPopup(WebDriver driver) {
        super(driver);
    }

    public CustomerLoginPopup inputUserName(String userName) {
        WebElement loginModalWindow = driver.findElement(popupLocator);
        WebElement userNameInput = wait.until(ExpectedConditions.visibilityOf(
                loginModalWindow.findElement(By.id("username-modal"))));
        userNameInput.clear();
        userNameInput.sendKeys(userName);
        return this;
    }

    public CustomerLoginPopup inputPassword(String password) {
        WebElement loginModalWindow = driver.findElement(popupLocator);
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOf(
                loginModalWindow.findElement(By.id("password-modal"))));
        passwordInput.clear();
        passwordInput.sendKeys(password);
        return this;
    }

    public HomePageObject clickSignInBtn() {
        WebElement signInBtn = driver.findElement(popupLocator).findElement(By.cssSelector(".fa-sign-in"));
        signInBtn.click();
        return new HomePageObject(driver);
    }

}

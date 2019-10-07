package com.hillel.page.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by alpa on 2019-09-05
 */
public class HomePageObject extends BasePage {

    public HomePageObject(WebDriver driver) {
        super(driver);
    }

    public HomePageObject open() {
        driver.get("http://localhost/");
        return this;
    }

    public HomePageObject login(String userName, String password) {
        return clickLoginBtn()
                .inputUserName(userName)
                .inputPassword(password)
                .clickSignInBtn();
    }

    public CustomerLoginPopup clickLoginBtn() {
        WebElement loginLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#login>a")));
        loginLink.click();
        return new CustomerLoginPopup(driver);
    }

    public boolean isUserLoggedIn() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout"))).isDisplayed();
    }

    public String getUserInfo() {
        WebElement loggedInInfo = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#howdy>a")));
        return loggedInInfo.getText();
    }

}

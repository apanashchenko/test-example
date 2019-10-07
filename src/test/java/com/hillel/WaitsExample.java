package com.hillel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.NoSuchElementException;

/**
 * Created by alpa on 2019-08-28
 */
public class WaitsExample {

    private static final long DEFAULT_WAIT_TIME = 4;

    public WebDriver driver;

    public WaitsExample(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement visibilityOf(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_WAIT_TIME);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement visibilityOfElementLocated(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_WAIT_TIME);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement elementToBeClickable(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_WAIT_TIME);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement elementToBeClickable(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_WAIT_TIME);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public boolean invisibilityOf(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_WAIT_TIME);
        return wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public boolean stalenessOf(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_WAIT_TIME);
        return wait.until(ExpectedConditions.stalenessOf(element));
    }

    public boolean textToBePresentInElement(WebElement element, String text) {
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_WAIT_TIME);
        return wait.until(ExpectedConditions.textToBePresentInElement(element, text));
    }

    public boolean textToBePresentInElement(By locator, String text) {
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_WAIT_TIME);
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    public boolean attributeContains(By locator, String attribute, String text) {
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_WAIT_TIME);
        return wait.until(ExpectedConditions.attributeContains(locator, attribute, text));
    }

    public WebElement fluentWaitVisibilityOfElementLocated(By locator) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(DEFAULT_WAIT_TIME))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class);
       return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}

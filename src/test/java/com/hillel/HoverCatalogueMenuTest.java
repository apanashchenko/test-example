package com.hillel;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.*;

/**
 * Created by alpa on 2019-08-29
 */
public class HoverCatalogueMenuTest {

    private WebDriver driver;

    @BeforeClass
    public void setUpBeforeClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(4000, TimeUnit.MILLISECONDS);
        driver.manage().timeouts().implicitlyWait(4000, TimeUnit.MILLISECONDS);
        driver.get("http://localhost/");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void hoverCatalogueTest() {
        WebElement catalogueMenuBtn = driver.findElement(By.xpath("//*[@id='tabCatalogue']"));

        Actions actions = new Actions(driver);

        actions.moveToElement(catalogueMenuBtn).build().perform();

        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement catalogueMenu = wait.until(ExpectedConditions.visibilityOf(catalogueMenuBtn.findElement(By.xpath(".//*[@class='dropdown-menu']"))));

        assertTrue(catalogueMenu.isDisplayed());

        WebElement menuItem = wait.until(ExpectedConditions.visibilityOf(catalogueMenu.findElement(By.xpath(".//*[text()='Formal']"))));
        menuItem.click();

        String currentUrl = driver.getCurrentUrl();
        assertEquals(currentUrl, "http://localhost/category.html?tags=formal");

        List<WebElement> productList = driver.findElements(By.cssSelector("#products>div"));
        System.out.println("Products found: " + productList.size());

        Map<String, String> productMap = new HashMap<>();
        productList.forEach(product -> {
            WebElement info = product.findElement(By.cssSelector("h3>a"));
            String name = info.getText();
            String link = info.getAttribute("href");
            System.out.println(String.format("Product name: %s, link: %s", info.getText(), link));
            productMap.put(name, link);
        });

        productMap.keySet().forEach(name -> {
            driver.get(productMap.get(name));
            WebElement productTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#title")));
            assertTrue(productTitle.getText().contains(name));
        });


        assertThat("sdfdsf").satisfiesAnyOf(
                value -> assertThat(value).isEqualTo(""),
                value -> assertThat(value).isEqualTo("")
        );
    }
}

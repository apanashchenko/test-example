package com.hillel;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;

import static org.testng.Assert.*;

/**
 * Created by alpa on 2019-08-22
 */
public class FirstSimpleTest {

    private WebDriver driver;

    @BeforeClass
    public void setUpDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void hillelSimpleTest() {
        String url = "https://dnipro.ithillel.ua/";
        driver.get(url);

        String currentUrl = driver.getCurrentUrl();
        System.out.println("currentUrl: " + currentUrl);
        assertEquals(url, currentUrl, "Wrong opened URL!");

        String title = driver.getTitle();
        System.out.println("title: " + title);
        assertFalse(title.isEmpty(), "Wrong title!");
        assertEquals("Компьютерная школа Hillel в Днепре: курсы IT технологий", title, "Wrong title!");
    }

    @Test
    public void windowHandlesTest() throws InterruptedException {
        String url = "https://auto.ria.com";
        driver.get(url);

        String mainWindow = driver.getWindowHandle();
        System.out.println("mainWindow: " + mainWindow);
        String title = driver.getTitle();
        System.out.println("main window title: " + title);

        WebElement element = driver.findElement(By.xpath("//*[@id='header']//*[contains(@href,'dom.ria.com')]"));
        element.click();

        Thread.sleep(2000);

        Set<String> windowHandles = driver.getWindowHandles();
        System.out.println("windowHandles: " + windowHandles);

        String secondWindow = windowHandles.stream().filter(window -> !window.equalsIgnoreCase(mainWindow)).findFirst().get();
        System.out.println("secondWindow: " + secondWindow);

        driver.switchTo().window(secondWindow);

        String secondTitle = driver.getTitle();
        System.out.println("second window title: " + secondTitle);

        String currentUrl = driver.getCurrentUrl();
        System.out.println("currentUrl: " + currentUrl);

        assertNotEquals(url, currentUrl, "Wrong opened URL!");

        driver.switchTo().window(mainWindow);

        String secondTitle1 = driver.getTitle();
        System.out.println("second window title: " + secondTitle1);

        String currentUrl1 = driver.getCurrentUrl();
        System.out.println("currentUrl: " + currentUrl1);
    }

}

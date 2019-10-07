package com.hillel.remote;

import com.hillel.models.User;
import com.hillel.page.objects.HomePageObject;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.Inet4Address;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

/**
 * Created by alpa on 2019-08-25
 */
public class LoginTest {

    private WebDriver driver;
    private User existingUser;

    @BeforeClass
    public void setUpBeforeClass() {
        WebDriverManager.chromedriver().setup();
//        WebDriverManager.firefoxdriver().setup();
//        WebDriverManager.operadriver().setup();
        existingUser = new User();
        existingUser.setUserName("test");
        existingUser.setFirstName("bob");
        existingUser.setLastName("marley");
        existingUser.setEmail("bob@email.com");
        existingUser.setPassword("123456");
    }

    @BeforeMethod
    public void setUp() throws MalformedURLException {
//        DesiredCapabilities capabilities = new DesiredCapabilities();
//        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
//        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
//        capabilities.setCapability("browserName", "chrome");
//        capabilities.setCapability("browserName", "firefox");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("firefox");
//        capabilities.setBrowserName("opera");
        capabilities.setCapability("enableVNC", true);
        driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
        driver.manage().timeouts().pageLoadTimeout(50000, TimeUnit.MILLISECONDS);
        driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void selenoidSimpleTest() throws UnknownHostException {
//        String url = "http://"+Inet4Address.getLocalHost().getHostAddress()+"/";
        String url = "https://www.google.com/";
        driver.get(url);

        String currentUrl = driver.getCurrentUrl();
        System.out.println("currentUrl: " + currentUrl);
        assertEquals(url, currentUrl, "Wrong opened URL!");

        String title = driver.getTitle();
        System.out.println("title: " + title);
        assertFalse(title.isEmpty(), "Wrong title!");
        assertEquals("Google", title, "Wrong title!");
    }

//    26c0c9ae22504977a19d6293a1d90222 jenkins password

}

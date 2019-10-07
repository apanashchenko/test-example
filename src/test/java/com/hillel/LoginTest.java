package com.hillel;

import com.hillel.models.User;
import com.hillel.page.objects.HomePageObject;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by alpa on 2019-08-25
 */
@Feature("Login feature")
public class LoginTest {

    private WebDriver driver;
    private User existingUser;

    @BeforeClass
    public void setUpBeforeClass() {
        WebDriverManager.chromedriver().setup();
        existingUser = new User();
        existingUser.setUserName("test");
        existingUser.setFirstName("bob");
        existingUser.setLastName("marley");
        existingUser.setEmail("bob@email.com");
        existingUser.setPassword("123456");
    }

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(4000, TimeUnit.MILLISECONDS);
        driver.manage().timeouts().implicitlyWait(4000, TimeUnit.MILLISECONDS);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Story("Success login story")
    @Severity(SeverityLevel.TRIVIAL)
    @TmsLink("1274")
    @Issue("BUG-1756")
    @Test
    public void loginTest() {
        HomePageObject homePage = new HomePageObject(driver);

        homePage.open();
        homePage.login(existingUser.getUserName(), existingUser.getPassword());

        assertThat(homePage.isUserLoggedIn()).as("User is not logged in!").isTrue();

        String infoText = homePage.getUserInfo();

        assertThat(infoText)
                .isNotEmpty()
                .contains(existingUser.getFirstName())
                .contains(existingUser.getLastName());
    }

}

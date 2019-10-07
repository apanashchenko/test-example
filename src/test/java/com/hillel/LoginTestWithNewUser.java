package com.hillel;

import com.hillel.models.Customer;
import com.hillel.models.CustomerResponse;
import com.hillel.models.User;
import com.hillel.page.objects.HomePageObject;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.Cookies;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static io.restassured.mapper.ObjectMapperType.GSON;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by alpa on 9/22/19
 */
public class LoginTestWithNewUser {

    private WebDriver driver;
    private User user;
    private Cookies detailedCookies;

    @BeforeClass
    public void setUpBeforeClass() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(4000, TimeUnit.MILLISECONDS);
        driver.manage().timeouts().implicitlyWait(4000, TimeUnit.MILLISECONDS);
    }

    @BeforeMethod
    public void setUp() {
        driver.get("http://localhost");
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 80;
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(new ObjectMapperConfig(GSON));

        user = getUser();

        Response response = given()
                .contentType("application/json;charset=UTF-8")
                .body(user)
                .post("/register");
        response
                .then()
                .statusCode(200);

        detailedCookies = response.getDetailedCookies();

        io.restassured.http.Cookie traefik_backend = detailedCookies.get("_TRAEFIK_BACKEND");
        Cookie cookie = new Cookie(traefik_backend.getName(), traefik_backend.getValue(), traefik_backend.getPath());

        io.restassured.http.Cookie logged_in = detailedCookies.get("logged_in");
        Cookie cookie1 = new Cookie(logged_in.getName(), logged_in.getValue(), logged_in.getPath(), logged_in.getExpiryDate());

        io.restassured.http.Cookie md_sid = detailedCookies.get("md.sid");
        Cookie cookie2 = new Cookie(md_sid.getName(), md_sid.getValue(), md_sid.getPath());

        driver.manage().addCookie(cookie);
        driver.manage().addCookie(cookie1);
        driver.manage().addCookie(cookie2);
    }


    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
        CustomerResponse response = given()
                .contentType("application/json;charset=UTF-8")
                .cookies(detailedCookies)
                .get("/customers")
                .then()
                .statusCode(200)
                .extract()
                .as(CustomerResponse.class);

        List<Customer> customer = response.getEmbedded().getCustomer();
        Optional<Customer> first = customer.stream().filter(customer1 -> customer1.getUsername().equalsIgnoreCase(user.getUserName())).findFirst();
        first.ifPresent(value -> given()
                .cookies(detailedCookies)
                .contentType("application/json;charset=UTF-8")
                .delete("/customers/" + value.getId()).then().statusCode(200));
    }

    @Test
    public void loginTest() {
        HomePageObject homePage = new HomePageObject(driver);
        homePage.open();
        String infoText = homePage.getUserInfo();
        assertThat(infoText)
                .isNotEmpty()
                .contains(user.getFirstName())
                .contains(user.getLastName());
    }


    private User getUser() {
        User user = new User();
        user.setUserName("test" + new Random().nextInt(100000));
        user.setFirstName("bob");
        user.setLastName("marley");
        user.setEmail("bob@email.com");
        user.setPassword("123456");

        return user;
    }

}

package com.hillel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hillel.models.CartItem;
import com.hillel.models.User;
import com.hillel.page.objects.HomePageObject;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.http.Cookies;
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
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by alpa on 9/22/19
 */
public class LoginTestNew {

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


        String originalInput = String.format("%s:%s", existingUser.getUserName(), existingUser.getPassword());
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
        Response response = given()
                .header("Authorization", "Basic " + encodedString)
                .contentType("application/json;charset=UTF-8")
                .get("/login");

        response
                .then()
                .statusCode(200);

        Response response1 = given()
                .cookies(response.getDetailedCookies())
                .header("Authorization", "Basic " + encodedString)
                .contentType("application/json;charset=UTF-8")
                .get("/cart").andReturn();

        System.out.println(response1.getBody().asString());

        Gson gson = new Gson();
        List<CartItem> cartItemList = gson.fromJson(response1.getBody().asString(), new TypeToken<List<CartItem>>(){}.getType());

        cartItemList.forEach(cartItem -> {
            given()
                    .cookies(response.getDetailedCookies())
                    .header("Authorization", "Basic " + encodedString)
                    .contentType("application/json;charset=UTF-8")
                    .delete("/cart/" + cartItem.getItemId())
                    .then()
                    .statusCode(202);
        });

        Cookies detailedCookies = response.getDetailedCookies();

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
    }

    @Test
    public void loginTest() {
        HomePageObject homePage = new HomePageObject(driver);
        homePage.open();
//        assertThat(homePage.isUserLoggedIn()).isTrue();
        String infoText = homePage.getUserInfo();
        assertThat(infoText)
                .isNotEmpty()
                .contains(existingUser.getFirstName())
                .contains(existingUser.getLastName());

        String numItemsInCart = driver.findElement(By.id("numItemsInCart")).getText();
        assertThat(numItemsInCart).isEqualTo("0 items in cart");
    }

}

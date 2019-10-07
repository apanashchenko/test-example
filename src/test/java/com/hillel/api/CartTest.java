package com.hillel.api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by alpa on 2019-09-17
 */
public class CartTest {

    private Cookies cookies;

    @BeforeMethod
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 80;
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType("application/json;charset=UTF-8")
                .build();

        String originalInput = "test:123456";
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
        Response loginResponse = given()
                .header("Authorization", "Basic " + encodedString)
                .get("/login");

        loginResponse.then()
                .statusCode(200)
                .assertThat()
                .cookie("_TRAEFIK_BACKEND", notNullValue())
                .cookie("logged_in", notNullValue())
                .cookie("md.sid", notNullValue())
                .body(equalTo("Cookie is set"));

        cookies = loginResponse.getDetailedCookies();
    }

    @AfterMethod
    public void tearDown() {
        cookies = null;
    }

    @Test(priority = 1)
    public void addToCartTest() {
        given().cookies(cookies)
                .body("{\"id\":\"03fef6ac-1896-4ce8-bd69-b798f85c6e0b\"}")
                .post("/cart")
                .then()
                .statusCode(201);
    }

    @Test(priority = 2)
    public void getCartTest() {
        List<String> jsonResponse = given().cookies(cookies)
                .get("/cart")
                .jsonPath().getList("$");
//        TODO
        System.out.println(jsonResponse.size());
    }

    @Test(priority = 3)
    public void removeFromCartTest() {
        given().cookies(cookies)
                .delete("/cart/" + "03fef6ac-1896-4ce8-bd69-b798f85c6e0b")
                .then()
                .statusCode(202);
    }
}

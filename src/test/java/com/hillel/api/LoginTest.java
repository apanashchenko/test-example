package com.hillel.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Base64;
import java.util.Map;

import static io.restassured.RestAssured.basic;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

/**
 * Created by alpa on 2019-09-07
 */
public class LoginTest {

    @Test
    public void loginTest() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 80;

        String originalInput = "test:123456";
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
        Response test = given()
                .header("Authorization", "Basic " + encodedString)
                .contentType("application/json;charset=UTF-8")
                .get("/login");

        test.then()
                .statusCode(200)
                .assertThat()
                .cookie("_TRAEFIK_BACKEND", notNullValue())
                .cookie("logged_in", notNullValue())
                .cookie("md.sid", notNullValue())
                .body(equalTo("Cookie is set"));

        Map<String, String> cookies = test.getCookies();

        System.out.println(cookies);
        System.out.println(test.statusLine());
        System.out.println(test.getBody().asString());
    }

    @Test
    public void catalogueTest() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 80;
        RestAssured.authentication = basic("test", "123456");

        Response test = given().get("/catalogue");

        test.prettyPrint();

        System.out.println(test.statusLine());
        System.out.println(test.getBody().asString());
    }

    @Test
    public void customersTest() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 80;

        String originalInput = "test:123456";
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
        System.out.println("dGVzdDoxMjM0NTY=");
        System.out.println(encodedString);
        Response test = given()
                .header("Authorization", "Basic " + encodedString)
                .contentType("application/json;charset=UTF-8")
                .get("/login");

        Map<String, String> cookies = test.getCookies();

        Response test2 = given()
                .cookies(cookies)
                .contentType("application/json;charset=UTF-8")
                .get("/orders");


        System.out.println(test2.statusLine());
        System.out.println(test2.getBody().asString());
    }

    @Test
    public void ordersTest() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 80;

        String originalInput = "test:123456";
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
        Response test = given()
                .header("Authorization", "Basic " + encodedString)
                .get("/login");

        Map<String, String> cookies = test.getCookies();

        Response test2 = given()
                .cookies(cookies)
                .contentType("application/json;charset=UTF-8")
                .get("/orders");


        System.out.println(test2.statusLine());
        System.out.println(test2.getBody().asString());
    }


    @Test
    public void addToCartTest() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 80;

        String originalInput = "test:123456";
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
        Response test = given()
                .header("Authorization", "Basic " + encodedString)
                .get("/login");

        Map<String, String> cookies = test.getCookies();

        Response test2 = given()
                .cookies(cookies)
                .contentType("application/json;charset=UTF-8")
                .body("{\"id\":\"03fef6ac-1896-4ce8-bd69-b798f85c6e0b\"}")
                .post("/cart");

        test2.then().statusCode(201);

        System.out.println(test2.statusLine());
        System.out.println(test2.getBody().asString());
    }

    @Test
    public void removeFromCartTest() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 80;

        String originalInput = "test:123456";
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
        Response test = given()
                .header("Authorization", "Basic " + encodedString)
                .get("/login");

        Map<String, String> cookies = test.getCookies();

        Response test2 = given()
                .cookies(cookies)
                .contentType("application/json;charset=UTF-8")
                .delete("/cart/"+ "03fef6ac-1896-4ce8-bd69-b798f85c6e0b");

        test2.then().statusCode(202);

        System.out.println(test2.statusLine());
        System.out.println(test2.getBody().asString());
    }
}

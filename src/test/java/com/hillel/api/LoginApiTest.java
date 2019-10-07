package com.hillel.api;

import com.hillel.models.Customer;
import com.hillel.models.CustomerResponse;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Base64;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by alpa on 2019-09-19
 */
public class LoginApiTest {

    @Test
    public void loginTest() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 80;

        String originalInput = "test:123456";
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
        Response response = given()
                .header("Authorization", "Basic " + encodedString)
                .contentType("application/json;charset=UTF-8")
                .get("/login");

        response
                .then()
                .statusCode(200);
        System.out.println(response.getDetailedCookies());
    }

    @Test
    public void getCustomersTest() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 80;
        RestAssured.defaultParser = Parser.JSON;

        String originalInput = "test:123456";
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
        CustomerResponse response = given()
                .header("Authorization", "Basic " + encodedString)
                .contentType("application/json;charset=UTF-8")
                .get("/customers")
                .then()
                .statusCode(200)
                .extract()
                .as(CustomerResponse.class);

        assertThat(response).isNotNull();
    }

    @Test
    public void getCustomerByIdTest() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 80;
        RestAssured.defaultParser = Parser.JSON;

        String originalInput = "test:123456";
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());

        Response login = given()
                .header("Authorization", "Basic " + encodedString)
                .contentType("application/json;charset=UTF-8")
                .get("/login");

        Customer response = given()
                .cookies(login.getDetailedCookies())
                .contentType("application/json;charset=UTF-8")
                .get("/customers/" + "5d711bccee11cb0001e2d1be")
                .then()
                .statusCode(200)
                .extract()
                .as(Customer.class);

        assertThat(response).isNotNull();
    }

    @Test
    public void addToCartTest() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 80;

        String originalInput = "test:123456";
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
        Response loginResponse = given()
                .header("Authorization", "Basic " + encodedString)
                .contentType("application/json;charset=UTF-8")
                .get("/login");

        given()
                .cookies(loginResponse.getDetailedCookies())
                .contentType("application/json;charset=UTF-8")
                .body("{\"id\":\"03fef6ac-1896-4ce8-bd69-b798f85c6e0b\"}")
                .post("/cart")
                .then()
                .statusCode(201);

        List<String> jsonResponse = given()
                .cookies(loginResponse.getDetailedCookies())
                .get("/cart")
                .jsonPath().getList("$");
        System.out.println(jsonResponse);
        assertThat(jsonResponse).hasSize(1);
    }
}

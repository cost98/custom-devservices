package com.github.cost98.custom.devservices.it;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class CustomDevservicesResourceTest {

    @Test
    public void testHelloEndpoint() throws InterruptedException {
        //Thread.sleep(10000000);
        given()
                .when().get("/custom-devservices1")
                .then()
                .statusCode(200)
                .body(is("Hello custom-devservices1"));
    }
}

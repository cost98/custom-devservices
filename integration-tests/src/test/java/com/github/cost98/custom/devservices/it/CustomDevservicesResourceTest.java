package com.github.cost98.custom.devservices.it;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class CustomDevservicesResourceTest {

    @Test
    public void testHelloEndpoint() throws InterruptedException {
        //Thread.sleep(10000000);
        given()
                .when().get("/custom-devservices")
                .then()
                .statusCode(200)
                .body(is("Hello custom-devservices"));
    }
}

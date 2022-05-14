package org.acme.quickstart;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class GreetingResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/hello")
          .then()
             .statusCode(200)
             .body(is("Hello Quarkus"));
    }
    
	@Test
	public void testParam() {
		String name = "CpE";
		given()
		.pathParam("param", name)
		.when().get("/hello/{param}")
		.then()
		.statusCode(200)
		.body(containsString(name));
	}
	
	@Test
	public void test() {
		given()
		.when().get("/hello/cm")
		.then()
		.statusCode(200)
		.body(containsString("Hello"));
	}
	
	@Test
	public void resourceNotFound() {
		given()
		.when().get("/station")
		.then()
		.statusCode(404);
	}

}
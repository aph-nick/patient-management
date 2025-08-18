import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.notNullValue;

public class AuthIntegrationTest {
    @BeforeAll
    static void setUp(){
        RestAssured.baseURI = "http://localhost:4004";
    }

    // Arrange -> Act -> Assert
    @Test
    public void shouldReturnOKWithValidToken() {
        String loginPayLoad = """
                    {
                        "email": "testuser@test.com",
                        "password": "password123"
                    }
                """;
        Response response = RestAssured.given()
                .contentType("application/json")
                .body(loginPayLoad)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue())
                .extract().response();

        System.out.println("Generated Token: " +response.jsonPath().getString("token"));
    }

    @Test
    public void shouldReturnUnauthorizedOnInvalidLogin() {
        String loginPayLoad = """
                    {
                        "email": "invalid_user@test.com",
                        "password": "incorrect_password"
                    }
                """;
        RestAssured.given()
                .contentType("application/json")
                .body(loginPayLoad)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(401);
    }

}

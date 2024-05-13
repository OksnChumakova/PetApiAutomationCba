package api.pet.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ConfigReader;

import static io.restassured.RestAssured.given;

public class CreatePet {

    @BeforeClass
    void setUp() {

    }

    @Test
    void createPetWithNoBody() {
        ConfigReader configReader = new ConfigReader("src/test/resources/config.properties");
        String baseURL = configReader.getProperty("api.baseURL");
        RestAssured.given().baseUri(baseURL).header("api-key", System.getenv("apiKey"))
                .contentType(ContentType.JSON).
                when().
                post("/pet").
                then().
                statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
    }
}

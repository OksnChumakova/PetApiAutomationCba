package api.pet.tests;

import api.pet.objectMapping.Pet;
import api.pet.objectMapping.PetTag;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ConfigReader;
import utils.PetStatus;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static utils.DataHelper.MOUSE_PHOTO_URL;
import static utils.UtilityMethods.*;

public class DeletePetTest {
    private static RequestSpecification requestSpec;
    public static final String PET_ENDPOINT = "/pet";
    public static final String INVALID_PET_ID = "abcd";

    @BeforeClass
    void setUp() {
        ConfigReader configReader = new ConfigReader("src/test/resources/config.properties");
        String baseURL = configReader.getProperty("api.baseURL");

        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(baseURL);
        builder.addHeader("api-key", System.getenv("apiKey"));

        requestSpec = builder.build();
    }

    @Test
    void deleteCreatedPetTest() {

        List<PetTag> listOfPetTags = new ArrayList<>();

        Pet rat = createPetAllFieldsTest(0, 4, "Rat", "Missy", createListOfPhotos(MOUSE_PHOTO_URL), addPetTagToTheList(listOfPetTags, 1, "Tag1"), PetStatus.available, requestSpec, PET_ENDPOINT);

        Response response = RestAssured.given()
                .spec(requestSpec)
                .log()
                .all()
                .basePath(PET_ENDPOINT + "/" + rat.getId())
                .when()
                .delete();

        response
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK);

    }

    @Test
    void deleteCreatedPetInvalidPetIdTest() {

        List<PetTag> listOfPetTags = new ArrayList<>();

        createPetAllFieldsTest(0, 4, "Rat", "Missy", createListOfPhotos(MOUSE_PHOTO_URL), addPetTagToTheList(listOfPetTags, 1, "Tag1"), PetStatus.available, requestSpec, PET_ENDPOINT);

        Response response = RestAssured.given()
                .spec(requestSpec)
                .log()
                .all()
                .basePath(PET_ENDPOINT + "/" + INVALID_PET_ID)
                .when()
                .delete();

        response
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    void deleteCreatedPetNotFoundPetIdTest() {
        List<PetTag> listOfPetTags = new ArrayList<>();

        Pet rat = createPetAllFieldsTest(0, 4, "Rat", "Missy", createListOfPhotos(MOUSE_PHOTO_URL), addPetTagToTheList(listOfPetTags, 1, "Tag1"), PetStatus.available, requestSpec, PET_ENDPOINT);

        Response responseOfFirstDeleteRequest = RestAssured.given()
                .spec(requestSpec)
                .log()
                .all()
                .basePath(PET_ENDPOINT + "/" + rat.getId())
                .when()
                .delete();

        responseOfFirstDeleteRequest
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK);

        Response responseOfSecondDeleteRequest = RestAssured.given()
                .spec(requestSpec)
                .log()
                .all()
                .basePath(PET_ENDPOINT + "/" + rat.getId())
                .when()
                .delete();

        responseOfSecondDeleteRequest
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}

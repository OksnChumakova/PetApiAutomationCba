package api.pet.tests;

import api.pet.objectMapping.Pet;
import api.pet.objectMapping.PetTag;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ConfigReader;
import utils.PetStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static utils.DataHelper.*;
import static utils.UtilityMethods.*;

public class GetPetTest {
    private static RequestSpecification requestSpec;
    public static final String PET_ENDPOINT = "/pet";
    public static final String INVALID_STATUS = "abc";
    public static final String INVALID_ID = "zxc";

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
    void getPetsByStatusOneByOneTest() {
        for (PetStatus status : PetStatus.values()) {
            Response response = RestAssured.given()
                    .spec(requestSpec)
                    .log()
                    .all()
                    .basePath(PET_ENDPOINT + "/findByStatus")
                    .queryParam("status", status)
                    .when()
                    .get();

            response
                    .then()
                    .log()
                    .all()
                    .statusCode(HttpStatus.SC_OK);

            // Deserialize the JSON response into a list of Pet objects
            List<Pet> petList = response.getBody().jsonPath().getList("", Pet.class);

            for (Pet pet : petList) {
                // Check the value of the "status" field of each pet object
                Assert.assertEquals(pet.getStatus(), status);
            }
        }
    }

    @Test
    void getPetsByAllStatusesTest() {
        for (PetStatus status : PetStatus.values()) {
            Response response = RestAssured.given()
                    .spec(requestSpec)
                    .log()
                    .all()
                    .basePath(PET_ENDPOINT + "/findByStatus")
                    .queryParam("status", PetStatus.available)
                    .queryParam("status", PetStatus.pending)
                    .queryParam("status", PetStatus.sold)
                    .when()
                    .get();

            response
                    .then()
                    .log()
                    .all()
                    .statusCode(HttpStatus.SC_OK);

            // Deserialize the JSON response into a list of Pet objects
            List<Pet> petList = response.getBody().jsonPath().getList("", Pet.class);

            for (Pet pet : petList) {
                String petStatus = pet.getStatus().toString();
                // Check the value of the "status" field of each pet object
                // Assert that the actual value is equal to one of the expected values
                Assert.assertTrue(petStatus.equals(PetStatus.available.name()) ||
                                petStatus.equals(PetStatus.pending.name()) ||
                                petStatus.equals(PetStatus.sold.name()),
                        "Actual value is not equal to any of the expected values");
            }
        }
    }

    @Test
    void getPetsByInvalidStatusTest() {
        Response response = RestAssured.given()
                .spec(requestSpec)
                .log()
                .all()
                .basePath(PET_ENDPOINT + "/findByStatus")
                .queryParam("status", INVALID_STATUS)
                .when()
                .get();

        response
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    //     get pet by id
    @Test
    void getPetsByIdTest() {
        List<PetTag> listOfPetTags = new ArrayList<>();

        Pet tiger = createPetAllFieldsTest(0, 10, "Tiger", "Joe", createListOfPhotos(TIGER_PHOTO_URL), addPetTagToTheList(listOfPetTags, 1, "Tag1"), PetStatus.available, requestSpec, PET_ENDPOINT);

        Response response = RestAssured.given()
                .spec(requestSpec)
                .log()
                .all()
                .basePath(PET_ENDPOINT + "/" + tiger.getId())
                .when()
                .get();

        response
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK);

        Pet petResponse = response.as(Pet.class);

        Assert.assertEquals(Long.valueOf(petResponse.getId()), tiger.getId());
        assert (petResponse.getName()).equals(tiger.getName());
        assert (petResponse.getCategory().equals(tiger.getCategory()));
        assert (petResponse.getPhotoUrls()).equals(tiger.getPhotoUrls());
        assert (petResponse.getTags()).equals(tiger.getTags());
        assert (petResponse.getStatus().equals(tiger.getStatus()));
    }

    @Test
    void getPetsByInvalidIdTest() {
        List<PetTag> listOfPetTags = new ArrayList<>();

        Pet bear = createPetAllFieldsTest(0, 12, "Bear", "Michael", createListOfPhotos(BEAR_PHOTO_URL), addPetTagToTheList(listOfPetTags, 1, "Tag1"), PetStatus.available, requestSpec, PET_ENDPOINT);

        Response response = RestAssured.given()
                .spec(requestSpec)
                .log()
                .all()
                .basePath(PET_ENDPOINT + "/" + INVALID_ID)
                .when()
                .get();

        response
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    void getPetsByNotFoundIdTest() {
        List<PetTag> listOfPetTags = new ArrayList<>();

        Pet rat = createPetAllFieldsTest(0, 4, "Rat", "Missy", createListOfPhotos(MOUSE_PHOTO_URL), addPetTagToTheList(listOfPetTags, 1, "Tag1"), PetStatus.available, requestSpec, PET_ENDPOINT);

        Response responseOfDeleteRequest = RestAssured.given()
                .spec(requestSpec)
                .log()
                .all()
                .basePath(PET_ENDPOINT + "/" + rat.getId())
                .when()
                .delete();

        responseOfDeleteRequest
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK);

        Response responseOfGetRequest = RestAssured.given()
                .spec(requestSpec)
                .log()
                .all()
                .basePath(PET_ENDPOINT + "/" + rat.getId())
                .when()
                .get();

        responseOfGetRequest
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}

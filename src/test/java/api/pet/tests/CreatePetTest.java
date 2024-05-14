package api.pet.tests;

import api.pet.objectMapping.Pet;
import api.pet.objectMapping.PetTag;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ConfigReader;
import utils.PetStatus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static utils.DataHelper.*;
import static utils.UtilityMethods.*;


public class CreatePetTest {
    private static RequestSpecification requestSpec;
    public static final String PET_ENDPOINT = "/pet";

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
    void createPetWithNoBodyTest() {

        RestAssured.given()
                .spec(requestSpec)
                .log()
                .all()
                .contentType(ContentType.JSON)
                .when()
                .post(PET_ENDPOINT)
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
    }

    @Test
    void createPetTest() {
        List<PetTag> listOfPetTags = new ArrayList<>();

        createPetAllFieldsTest(0, 0, "Dog", "Bobby", createListOfPhotos(DOG_PHOTO_URL), addPetTagToTheList(listOfPetTags, 0, "Tag0"), PetStatus.available, requestSpec, PET_ENDPOINT);


    }

    @Test
    void uploadImageToPetTest() {

        List<PetTag> listOfPetTags = new ArrayList<>();

        Pet cat = createPetAllFieldsTest(0, 1, "Cat", "Lizzy", createListOfPhotos(CAT_PHOTO_URL), addPetTagToTheList(listOfPetTags, 0, "Tag0"), PetStatus.available, requestSpec, PET_ENDPOINT);

        RestAssured.given()
                .spec(requestSpec)
                .log()
                .all()
                .multiPart(new File("src/test/resources/cat.jpg"))
                .basePath(PET_ENDPOINT + "/" + cat.getId() + "/uploadImage")
                .contentType("multipart/form-data")
                .when()
                .post()
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK);
    }
}

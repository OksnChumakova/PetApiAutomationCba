package api.pet.tests;

import api.pet.objectMapping.Pet;
import api.pet.objectMapping.PetTag;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

public class UpdatePetTest {
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
    void updateCreatedPetTest() {

        List<PetTag> listOfPetTags = new ArrayList<>();

        Pet mouse = createPetAllFieldsTest(0, 3, "Mouse", "Fluffy", createListOfPhotos(MOUSE_PHOTO_URL), addPetTagToTheList(listOfPetTags, 1, "Tag1"), PetStatus.available, requestSpec, PET_ENDPOINT);

        mouse.setName("Snow");

        Response response = RestAssured.given()
                .spec(requestSpec)
                .log()
                .all()
                .basePath(PET_ENDPOINT)
                .contentType(ContentType.JSON)
                .body(mouse)
                .when()
                .put();

        response
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK);

        Pet petResponse = response.as(Pet.class);

        assert (petResponse.getName()).equals(mouse.getName());
        assert (petResponse.getPhotoUrls()).equals(mouse.getPhotoUrls());
        assert (petResponse.getTags()).equals(mouse.getTags());
    }
}

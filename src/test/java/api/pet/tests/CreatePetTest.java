package api.pet.tests;

import api.pet.objectMapping.Pet;
import api.pet.objectMapping.PetTag;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ConfigReader;
import utils.PetStatus;

import java.util.ArrayList;
import java.util.List;

import static api.pet.methods.CreatePet.*;
import static utils.DataHelper.*;


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
        createPetWithNoBody(requestSpec, PET_ENDPOINT);
    }

    @Test
    void createPetWithAllFieldsTest() {
        List<PetTag> listOfPetTags = new ArrayList<>();
        createPetAllFields(0, 0, "Dog", "Bobby", createListOfPhotos(DOG_PHOTO_URL), addPetTagToTheList(listOfPetTags, 0, "Tag0"), PetStatus.available, requestSpec, PET_ENDPOINT);
    }

    @Test
    void createPetWithRequiredFieldsTest() {
        createPetRequiredFields("Bobby", createListOfPhotos(DOG_PHOTO_URL), requestSpec, PET_ENDPOINT);
    }

    @Test
    void uploadImageToPetTest() {
        List<PetTag> listOfPetTags = new ArrayList<>();
        Pet cat = createPetAllFields(0, 1, "Cat", "Lizzy", createListOfPhotos(CAT_PHOTO_URL), addPetTagToTheList(listOfPetTags, 0, "Tag0"), PetStatus.available, requestSpec, PET_ENDPOINT);

        uploadImageToPet(requestSpec, PET_ENDPOINT, cat, CAT_PHOTO_FILE_PATH);
    }
}

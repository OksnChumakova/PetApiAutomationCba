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
import static api.pet.methods.DeletePet.*;
import static utils.DataHelper.MOUSE_PHOTO_URL;

public class DeletePetTest {
    private static RequestSpecification requestSpec;
    public static final String PET_ENDPOINT = "/pet";
    public static final String INVALID_PET_ID = null;

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
        Pet rat = createPetAllFields(0, 4, "Rat", "Missy", createListOfPhotos(MOUSE_PHOTO_URL), addPetTagToTheList(listOfPetTags, 1, "Tag1"), PetStatus.available, requestSpec, PET_ENDPOINT);

        deleteCreatedPet(requestSpec, PET_ENDPOINT, rat);
    }

    @Test
    void deleteWithInvalidPetIdTest() {
        deleteWithInvalidPetId(requestSpec, PET_ENDPOINT, INVALID_PET_ID);
    }

    @Test
    void deleteWithNotFoundPetIdTest() {
        List<PetTag> listOfPetTags = new ArrayList<>();
        Pet rat = createPetAllFields(0, 4, "Rat", "Missy", createListOfPhotos(MOUSE_PHOTO_URL), addPetTagToTheList(listOfPetTags, 1, "Tag1"), PetStatus.available, requestSpec, PET_ENDPOINT);

        deleteCreatedPet(requestSpec, PET_ENDPOINT, rat);
        deleteWithNotFoundPetId(requestSpec, PET_ENDPOINT, rat);
    }
}

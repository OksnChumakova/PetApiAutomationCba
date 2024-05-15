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
import static api.pet.methods.UpdatePet.*;
import static utils.DataHelper.MOUSE_PHOTO_URL;

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
    void updatePetTest() {
        List<PetTag> listOfPetTags = new ArrayList<>();
        Pet mouse = createPetAllFields(0, 3, "Mouse", "Fluffy", createListOfPhotos(MOUSE_PHOTO_URL), addPetTagToTheList(listOfPetTags, 1, "Tag1"), PetStatus.available, requestSpec, PET_ENDPOINT);

        // modify name of the pet
        mouse.setName("Snow");

        updateCreatedPet(requestSpec, PET_ENDPOINT, mouse);
    }

    // update with form data
    @Test
    void updateWithFormDataTest() {
        List<PetTag> listOfPetTags = new ArrayList<>();
        Pet mouse = createPetAllFields(0, 3, "Mouse", "Nordie", createListOfPhotos(MOUSE_PHOTO_URL), addPetTagToTheList(listOfPetTags, 1, "Tag1"), PetStatus.available, requestSpec, PET_ENDPOINT);

        updateCreatedPetWithFormData(requestSpec, PET_ENDPOINT, mouse);
    }

    @Test
    void updateWithFormDataInvalidInputTest() {
        List<PetTag> listOfPetTags = new ArrayList<>();
        Pet mouse = createPetAllFields(0, 3, "Mouse", "Nordie", createListOfPhotos(MOUSE_PHOTO_URL), addPetTagToTheList(listOfPetTags, 1, "Tag1"), PetStatus.available, requestSpec, PET_ENDPOINT);

        updateCreatedPetWithFormDataInvalidInput(requestSpec, PET_ENDPOINT, mouse);
    }
}

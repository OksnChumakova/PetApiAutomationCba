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
import static api.pet.methods.DeletePet.deleteCreatedPet;
import static api.pet.methods.GetPet.*;
import static utils.DataHelper.*;

public class GetPetTest {
    private static RequestSpecification requestSpec;
    public static final String PET_ENDPOINT = "/pet";
    public static final String INVALID_STATUS = "invalid_status";
    public static final String INVALID_ID = null;

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
        getPetsByStatusOneByOne(requestSpec, PET_ENDPOINT);
    }

    @Test
    void getPetsByAllStatusesTest() {
        getPetsByAllStatuses(requestSpec, PET_ENDPOINT);
    }

    @Test
    void getPetsByInvalidStatusTest() {
        getPetsByInvalidStatus(requestSpec, PET_ENDPOINT, INVALID_STATUS);
    }

//         get pet by id
    @Test
    void getPetsByIdTest() {
        List<PetTag> listOfPetTags = new ArrayList<>();
        Pet dog = createPetAllFields(0, 0, "Dog", "Joe", createListOfPhotos(DOG_PHOTO_URL), addPetTagToTheList(listOfPetTags, 1, "Tag1"), PetStatus.available, requestSpec, PET_ENDPOINT);

        getPetsById(requestSpec, PET_ENDPOINT, dog);
    }

    @Test
    void getPetsByInvalidPetIdTest() {
        getPetsByInvalidPetId(requestSpec, PET_ENDPOINT, INVALID_ID);
    }

    @Test
    void getPetsByNotFoundPetIdTest() {
        List<PetTag> listOfPetTags = new ArrayList<>();
        Pet rat = createPetAllFields(0, 4, "Rat", "Missy", createListOfPhotos(MOUSE_PHOTO_URL), addPetTagToTheList(listOfPetTags, 1, "Tag1"), PetStatus.available, requestSpec, PET_ENDPOINT);

        deleteCreatedPet(requestSpec, PET_ENDPOINT, rat);

        getPetsByNotFoundPetId(requestSpec, PET_ENDPOINT, rat);
    }
}

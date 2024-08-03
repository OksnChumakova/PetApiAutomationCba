package api.pet.tests;

import api.pet.objectMapping.Pet;
import api.pet.objectMapping.PetTag;
import org.testng.annotations.Test;
import api.pet.endpoints.PetStatus;

import java.util.ArrayList;
import java.util.List;

import static api.pet.endpoints.CreatePet.*;
import static utils.Constants.PET_ENDPOINT;
import static utils.DataHelper.*;


public class CreatePetTest extends BaseTest {

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

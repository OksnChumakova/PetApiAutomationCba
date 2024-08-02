package api.pet.tests;

import api.pet.objectMapping.Pet;
import api.pet.objectMapping.PetTag;
import org.testng.annotations.Test;
import api.pet.endpoints.PetStatus;

import java.util.ArrayList;
import java.util.List;

import static api.pet.endpoints.CreatePet.*;
import static api.pet.endpoints.UpdatePet.*;
import static utils.Constants.PET_ENDPOINT;
import static utils.DataHelper.MOUSE_PHOTO_URL;

public class UpdatePetTest extends BaseTest {

    @Test
    void updatePetTest() {
        List<PetTag> listOfPetTags = new ArrayList<>();
        Pet mouse = createPetAllFields(0, 3, "Mouse", "Fluffy", createListOfPhotos(MOUSE_PHOTO_URL), addPetTagToTheList(listOfPetTags, 1, "Tag1"), PetStatus.available, requestSpec, PET_ENDPOINT);

        // modify name of the pet
        mouse.setName("Snow");

        updateCreatedPet(requestSpec, PET_ENDPOINT, mouse);
    }

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

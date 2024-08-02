package api.pet.tests;

import api.pet.objectMapping.Pet;
import api.pet.objectMapping.PetTag;
import org.testng.annotations.Test;
import api.pet.endpoints.PetStatus;

import java.util.ArrayList;
import java.util.List;

import static api.pet.endpoints.CreatePet.*;
import static api.pet.endpoints.DeletePet.*;
import static utils.Constants.INVALID_PET_ID;
import static utils.Constants.PET_ENDPOINT;
import static utils.DataHelper.MOUSE_PHOTO_URL;

public class DeletePetTest extends BaseTest {


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

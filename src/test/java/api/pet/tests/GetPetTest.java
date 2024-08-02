package api.pet.tests;

import api.pet.objectMapping.Pet;
import api.pet.objectMapping.PetTag;
import org.testng.annotations.Test;
import api.pet.endpoints.PetStatus;

import java.util.ArrayList;
import java.util.List;

import static api.pet.endpoints.CreatePet.*;
import static api.pet.endpoints.DeletePet.deleteCreatedPet;
import static api.pet.endpoints.GetPet.*;
import static utils.Constants.*;
import static utils.DataHelper.*;

public class GetPetTest extends BaseTest{

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

    @Test
    void getPetsByIdTest() {
        List<PetTag> listOfPetTags = new ArrayList<>();
        Pet dog = createPetAllFields(0, 0, "Dog", "Joe", createListOfPhotos(DOG_PHOTO_URL), addPetTagToTheList(listOfPetTags, 1, "Tag1"), PetStatus.available, requestSpec, PET_ENDPOINT);

        getPetsById(requestSpec, PET_ENDPOINT, dog);
    }

    @Test
    void getPetsByInvalidPetIdTest() {
        getPetsByInvalidPetId(requestSpec, PET_ENDPOINT, INVALID_PET_ID);
    }

    @Test
    void getPetsByNotFoundPetIdTest() {
        List<PetTag> listOfPetTags = new ArrayList<>();
        Pet rat = createPetAllFields(0, 4, "Rat", "Missy", createListOfPhotos(MOUSE_PHOTO_URL), addPetTagToTheList(listOfPetTags, 1, "Tag1"), PetStatus.available, requestSpec, PET_ENDPOINT);

        deleteCreatedPet(requestSpec, PET_ENDPOINT, rat);

        getPetsByNotFoundPetId(requestSpec, PET_ENDPOINT, rat);
    }
}

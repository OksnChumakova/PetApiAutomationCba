package utils;

import api.pet.objectMapping.Pet;
import api.pet.objectMapping.PetCategory;
import api.pet.objectMapping.PetTag;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;

public class UtilityMethods {

    public static Pet createPetAllFieldsTest(long petId, int categoryId, String categoryName, String petName, List<String> photoUrls, List<PetTag> tags, PetStatus petStatus, RequestSpecification requestSpec, String endpoint) {
        PetCategory petCategory = new PetCategory(categoryId, categoryName);

        Pet pet = new Pet(petId, petCategory, petName, photoUrls, tags, petStatus);
        Response response = RestAssured.given()
                .spec(requestSpec)
                .log()
                .all()
                .contentType(ContentType.JSON)
                .body(pet)
                .when()
                .post(endpoint);

        response
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK);

        Pet petResponse = response.as(Pet.class);

        assert(petResponse.getName()).equals(pet.getName());
        assert(petResponse.getPhotoUrls()).equals(pet.getPhotoUrls());
        assert(petResponse.getTags()).equals(pet.getTags());
//        assert(petResponse.getId()).eq
        return petResponse;
    }

    public static List<String> createListOfPhotos(String... photoUrls) {
        List<String> listOfPhotos = new ArrayList<>();
        Collections.addAll(listOfPhotos, photoUrls);

        return listOfPhotos;
    }

    public static List<PetTag> addPetTagToTheList(List<PetTag> listOfPetTags, int id, String name) {
        PetTag petTag = new PetTag(id, name);
        listOfPetTags.add(petTag);

        return listOfPetTags;
    }
}

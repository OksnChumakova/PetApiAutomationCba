package api.pet.methods;

import api.pet.objectMapping.Category;
import api.pet.objectMapping.Pet;
import api.pet.objectMapping.PetTag;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import utils.PetStatus;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CreatePet {
    public static Pet createPetAllFields(long petId, int categoryId, String categoryName, String petName, List<String> photoUrls, List<PetTag> tags, PetStatus petStatus, RequestSpecification requestSpec, String endpoint) {
        Category petCategory = new Category(categoryId, categoryName);

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

        assert (petResponse.getName()).equals(pet.getName());
        assert (petResponse.getPhotoUrls()).equals(pet.getPhotoUrls());
        assert (petResponse.getTags()).equals(pet.getTags());
        Assert.assertNotEquals(petResponse.getId(), 0, "ID should not be zero");
        return petResponse;
    }

    public static void createPetAllFieldsWithLogging(long petId, int categoryId, String categoryName, String petName, List<String> photoUrls, List<PetTag> tags, PetStatus petStatus, RequestSpecification requestSpec, String endpoint) {
        Category petCategory = new Category(categoryId, categoryName);

        Pet pet = new Pet(petId, petCategory, petName, photoUrls, tags, petStatus);
        Response response = RestAssured.given()
                .filter(new AllureRestAssured())
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

        assert (petResponse.getName()).equals(pet.getName());
        assert (petResponse.getPhotoUrls()).equals(pet.getPhotoUrls());
        assert (petResponse.getTags()).equals(pet.getTags());
        Assert.assertNotEquals(petResponse.getId(), 0, "ID should not be zero");
    }

    public static void createPetRequiredFields(String petName, List<String> photoUrls, RequestSpecification requestSpec, String endpoint) {
        Pet pet = Pet.builder()
                .name(petName)
                .photoUrls(photoUrls)
                .build();

        Response response = RestAssured.given()
                .filter(new AllureRestAssured())
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

        assert (petResponse.getName()).equals(pet.getName());
        assert (petResponse.getPhotoUrls()).equals(pet.getPhotoUrls());
        Assert.assertNotEquals(petResponse.getId(), 0, "ID should not be zero");
    }

    public static void createPetWithNoBody(RequestSpecification requestSpec, String endpoint) {
        RestAssured.given()
                .filter(new AllureRestAssured())
                .spec(requestSpec)
                .log()
                .all()
                .contentType(ContentType.JSON)
                .when()
                .post(endpoint)
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
    }

    public static void uploadImageToPet(RequestSpecification requestSpec, String endpoint, Pet pet, String filePath) {
        RestAssured.given()
                .filter(new AllureRestAssured())
                .spec(requestSpec)
                .log()
                .all()
                .multiPart(new File(filePath))
                .basePath(endpoint + "/" + pet.getId() + "/uploadImage")
                .contentType("multipart/form-data")
                .when()
                .post()
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK);
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

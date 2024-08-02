package api.pet.endpoints;

import api.pet.objectMapping.Pet;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.testng.Assert;

import java.util.List;

import static utils.Constants.INVALID_STATUS;

public class GetPet {
    public static void getPetsByStatusOneByOne(RequestSpecification requestSpec, String endpoint) {
        for (PetStatus status : PetStatus.values()) {
            Response response = RestAssured.given()
                    .filter(new AllureRestAssured())
                    .spec(requestSpec)
                    .log()
                    .all()
                    .basePath(endpoint + "/findByStatus")
                    .queryParam("status", status)
                    .when()
                    .get();

            response
                    .then()
                    .log()
                    .all()
                    .statusCode(HttpStatus.SC_OK);

            // Deserialize the JSON response into a list of Pet objects
            List<Pet> petList = response.getBody().jsonPath().getList("", Pet.class);

            for (Pet pet : petList) {
                // Check the value of the "status" field of each pet object
                Assert.assertEquals(pet.getStatus(), status);
            }
        }
    }

    public static void getPetsByAllStatuses(RequestSpecification requestSpec, String endpoint) {
        Response response = RestAssured.given()
                .filter(new AllureRestAssured())
                .spec(requestSpec)
                .log()
                .all()
                .basePath(endpoint + "/findByStatus")
                .queryParam("status", PetStatus.available)
                .queryParam("status", PetStatus.pending)
                .queryParam("status", PetStatus.sold)
                .when()
                .get();

        response
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK);

        // Deserialize the JSON response into a list of Pet objects
        List<Pet> petList = response.getBody().jsonPath().getList("", Pet.class);

        for (Pet pet : petList) {
            String petStatus = pet.getStatus().toString();
            // Check the value of the "status" field of each pet object
            // Assert that the actual value is equal to one of the expected values
            Assert.assertTrue(petStatus.equals(PetStatus.available.name()) ||
                            petStatus.equals(PetStatus.pending.name()) ||
                            petStatus.equals(PetStatus.sold.name()),
                    "Actual value is not equal to any of the expected values");
        }
    }

    public static void getPetsByInvalidStatus(RequestSpecification requestSpec, String endpoint) {
        Response response = RestAssured.given()
                .filter(new AllureRestAssured())
                .spec(requestSpec)
                .log()
                .all()
                .basePath(endpoint + "/findByStatus")
                .queryParam("status", INVALID_STATUS)
                .when()
                .get();

        response
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    public static void getPetsById(RequestSpecification requestSpec, String endpoint, Pet pet) {
        Response response = RestAssured.given()
                .filter(new AllureRestAssured())
                .spec(requestSpec)
                .log()
                .all()
                .basePath(endpoint + "/" + pet.getId())
                .when()
                .get();

        response
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK);

        Pet petResponse = response.as(Pet.class);

        Assert.assertEquals(Long.valueOf(petResponse.getId()), pet.getId());
        assert (petResponse.getName()).equals(pet.getName());
        assert (petResponse.getCategory().equals(pet.getCategory()));
        assert (petResponse.getPhotoUrls()).equals(pet.getPhotoUrls());
        assert (petResponse.getTags()).equals(pet.getTags());
        assert (petResponse.getStatus().equals(pet.getStatus()));
    }

    public static void getPetsByInvalidPetId(RequestSpecification requestSpec, String endpoint, String invalidId) {
        Response response = RestAssured.given()
                .filter(new AllureRestAssured())
                .spec(requestSpec)
                .log()
                .all()
                .basePath(endpoint + "/" + invalidId)
                .when()
                .get();

        response
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    public static void getPetsByNotFoundPetId(RequestSpecification requestSpec, String endpoint, Pet pet) {
        Response response = RestAssured.given()
                .filter(new AllureRestAssured())
                .spec(requestSpec)
                .log()
                .all()
                .basePath(endpoint + "/" + pet.getId())
                .when()
                .get();

        response
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}


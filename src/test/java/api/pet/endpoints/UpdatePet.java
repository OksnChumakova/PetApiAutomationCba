package api.pet.endpoints;

import api.pet.objectMapping.Pet;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class UpdatePet {
    public static void updateCreatedPet(RequestSpecification requestSpec, String endpoint, Pet pet) {
        Response response = RestAssured.given()
                .filter(new AllureRestAssured())
                .spec(requestSpec)
                .log()
                .all()
                .basePath(endpoint)
                .contentType(ContentType.JSON)
                .body(pet)
                .when()
                .put();

        response
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK);

        Pet petResponse = response.as(Pet.class);

        assert (petResponse.getName()).equals(pet.getName());
        assert (petResponse.getPhotoUrls()).equals(pet.getPhotoUrls());
        assert (petResponse.getTags()).equals(pet.getTags());
    }

    public static void updateCreatedPetWithFormData(RequestSpecification requestSpec, String endpoint, Pet pet) {
        Response response = RestAssured.given()
                .filter(new AllureRestAssured())
                .spec(requestSpec)
                .log()
                .all()
                .basePath(endpoint + "/" + pet.getId())
                .contentType("application/x-www-form-urlencoded")
                .formParam("name", "Betty")
                .formParam("status", PetStatus.sold)
                .when()
                .post();

        response
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK);
    }

    public static void updateCreatedPetWithFormDataInvalidInput(RequestSpecification requestSpec, String endpoint, Pet pet) {
        Response response = RestAssured.given()
                .filter(new AllureRestAssured())
                .spec(requestSpec)
                .log()
                .all()
                .basePath(endpoint + "/" + pet.getId())
                .contentType("application/x-www-form-urlencoded")
                .formParam("name", "Betty")
                .formParam("status", "invalid_status")
                .when()
                .post();

        response
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
    }
}

package api.pet.methods;

import api.pet.objectMapping.Pet;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class DeletePet {
    public static void deleteCreatedPetWithLogging(RequestSpecification requestSpec, String endpoint, Pet pet) {
        Response response = RestAssured.given()
                .filter(new AllureRestAssured())
                .spec(requestSpec)
                .log()
                .all()
                .basePath(endpoint + "/" + pet.getId())
                .when()
                .delete();

        response
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK);
    }

    public static void deleteCreatedPet(RequestSpecification requestSpec, String endpoint, Pet pet) {
        Response response = RestAssured.given()
                .spec(requestSpec)
                .log()
                .all()
                .basePath(endpoint + "/" + pet.getId())
                .when()
                .delete();

        response
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK);
    }

    public static void deleteWithInvalidPetId(RequestSpecification requestSpec, String endpoint, String invalidPetId) {
        Response response = RestAssured.given()
                .filter(new AllureRestAssured())
                .spec(requestSpec)
                .log()
                .all()
                .basePath(endpoint + "/" + invalidPetId)
                .when()
                .delete();

        response
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    public static void deleteWithNotFoundPetId(RequestSpecification requestSpec, String endpoint, Pet pet) {
        Response response = RestAssured.given()
                .filter(new AllureRestAssured())
                .spec(requestSpec)
                .log()
                .all()
                .basePath(endpoint + "/" + pet.getId())
                .when()
                .delete();

        response
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}

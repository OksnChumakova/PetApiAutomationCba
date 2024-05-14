package api.pet.tests;

import api.pet.objectMapping.Pet;
import api.pet.objectMapping.PetTag;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import utils.ConfigReader;
import utils.ExtentReportFilter;
import utils.PetStatus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static utils.DataHelper.*;
import static utils.UtilityMethods.*;


public class CreatePetTest {
    private static RequestSpecification requestSpec;
    public static final String PET_ENDPOINT = "/pet";

    private ExtentReports extent;

    @BeforeSuite
    public void reporterSetUp() {

        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("test-output/ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @AfterSuite
    public void tearDown() {
        extent.flush();
    }

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
    void createPetWithNoBodyTest() {

        ExtentTest extentTest = extent.createTest("Create pet with no body - 405 is expected");

        RestAssured.given()
                .filter(new ExtentReportFilter(extentTest))
                .spec(requestSpec)
                .log()
                .all()
                .contentType(ContentType.JSON)
                .when()
                .post(PET_ENDPOINT)
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
    }

    @Test
    void createPetWithAllFieldsTest() {
        ExtentTest extentTest = extent.createTest("Create pet with All fields - 200 is expected");

        List<PetTag> listOfPetTags = new ArrayList<>();
        createPetAllFieldsTest(0, 0, "Dog", "Bobby", createListOfPhotos(DOG_PHOTO_URL), addPetTagToTheList(listOfPetTags, 0, "Tag0"), PetStatus.available, requestSpec, PET_ENDPOINT);
    }

    @Test
    void createPetWithRequiredFieldsTest() {

        createPetRequiredFieldsTest("Bobby", createListOfPhotos(DOG_PHOTO_URL), requestSpec, PET_ENDPOINT);
    }

    @Test
    void uploadImageToPetTest() {

        List<PetTag> listOfPetTags = new ArrayList<>();

        Pet cat = createPetAllFieldsTest(0, 1, "Cat", "Lizzy", createListOfPhotos(CAT_PHOTO_URL), addPetTagToTheList(listOfPetTags, 0, "Tag0"), PetStatus.available, requestSpec, PET_ENDPOINT);

        RestAssured.given()
                .spec(requestSpec)
                .log()
                .all()
                .multiPart(new File("src/test/resources/cat.jpg"))
                .basePath(PET_ENDPOINT + "/" + cat.getId() + "/uploadImage")
                .contentType("multipart/form-data")
                .when()
                .post()
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK);
    }
}

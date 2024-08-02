package api.pet.tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeSuite;
import utils.ConfigReader;

import static utils.Constants.PROPERTIES_LOCATION;

public class BaseTest {
    public static RequestSpecification requestSpec;

    @BeforeSuite
    void setUp() {
        ConfigReader configReader = new ConfigReader(PROPERTIES_LOCATION);
        String baseURL = configReader.getProperty("api.baseURL");

        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(baseURL);
        builder.addHeader("api-key", System.getenv("apiKey"));

        requestSpec = builder.build();
    }
}

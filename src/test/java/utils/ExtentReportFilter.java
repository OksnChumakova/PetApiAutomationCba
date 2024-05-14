package utils;
import com.aventstack.extentreports.ExtentTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class ExtentReportFilter implements Filter {
    private ExtentTest extentTest;

    public ExtentReportFilter(ExtentTest extentTest) {
        this.extentTest = extentTest;
    }

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext filterContext) {
        // Log request details
        extentTest.info("Request method: " + requestSpec.getMethod());
        extentTest.info("Request URI: " + requestSpec.getURI());
        extentTest.info("Request headers: " + requestSpec.getHeaders());

        // Proceed with the request
        Response response = filterContext.next(requestSpec, responseSpec);

        // Log response details
        extentTest.info("Response status code: " + response.getStatusCode());
        extentTest.info("Response headers: " + response.getHeaders());

        // Format response body as nicely indented JSON
        formatResponseBodyAsPrettyJSON(response.getBody().asString());

        return response;
    }

    private void formatResponseBodyAsPrettyJSON(String responseBody) {
        try {
            // Create ObjectMapper instance
            ObjectMapper mapper = new ObjectMapper();

            // Configure ObjectMapper to enable pretty printing
            mapper.enable(SerializationFeature.INDENT_OUTPUT);

            // Convert JSON string to JsonNode
            JsonNode jsonNode = mapper.readTree(responseBody);

            // Convert JsonNode back to formatted JSON string
            String prettyJSON = mapper.writeValueAsString(jsonNode);

            // Log formatted JSON response body
            extentTest.info("Response body: \n" + prettyJSON);
        } catch (Exception e) {
            // Log response body as is if unable to format
            extentTest.info("Response body (unable to format): \n" + responseBody);
        }
    }
}
package utils;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.internal.ResponseSpecificationImpl;
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
        extentTest.info("Response body: " + response.getBody().asString());

        return response;
    }
}

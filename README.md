# test-automation-framework

This is a framework of API tests for PetStore based on the following documentation - https://petstore.swagger.io/.
Covers PET endpoint only


## Requirements

In order to utilise this project you need to have the following installed locally:

* Maven 3
* Java 1.21
* Allure

## Usage

To run all tests, navigate to `PetApiAutomationCba` directory and run:

`mvn clean test`

## Reporting

To generate Allure report after each run, navigate to `PetApiAutomationCba` directory and run:

`allure serve`

## Failed tests

This is the list of the failed test cases with the explanation:

* [ERROR]   DeletePetTest.deleteWithInvalidPetIdTest:46 1 expectation failed.
Expected status code <400> but was <404> - When providing invalid pet id (in documentation it is stated that expected type is `integer($int64`) as `null` - the response code is 404 while I expect 400.

* [ERROR]   GetPetTest.getPetsByInvalidPetIdTest:65 1 expectation failed.
Expected status code <400> but was <404> - When providing invalid pet id (in documentation it is stated that expected type is `integer($int64`) as `null` - the response code is 404 while I expect 400.

* [ERROR]   GetPetTest.getPetsByInvalidStatusTest:51 1 expectation failed.
Expected status code <400> but was <200> - When providing invalid pet `status` (in documentation it is stated that only 3 values are possible - `available`, `pending`, `sold`) as `invalid_status` - the response code is 200 while I expect 400 (according to swagger schema).

* [ERROR]   UpdatePetTest.updateCreatedPetWithFormDataInvalidInputTest:60 1 expectation failed.
Expected status code <405> but was <200> - When providing invalid value for `status` field in the form data (in documentation it is stated that expected type of name parameter is `string`) as `invalid_status` - the response code is 200 while I expect 405 (according to swagger schema).
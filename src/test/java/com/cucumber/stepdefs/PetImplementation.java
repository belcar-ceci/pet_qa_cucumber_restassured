package com.cucumber.stepdefs;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.io.File;

import static io.restassured.RestAssured.given;

public class PetImplementation {
    private Response postPet = null;
    private Response getListPet = null;
    private Response putPet = null;
    private Response deletePet = null;

    private Response jsonPathPets = null;

    @Before
    public void before() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2/";
    }

    @Given("the following post that add pet")
    public void thePostAddPet() {
        File jsonFile = new File("src/test/resources/data/bodyRequestPetListPost.json");
        postPet = given().contentType(ContentType.JSON).body(jsonFile).post("/pet");
    }

    @Then("the response is {int} for the post pet")
    public void theResponseIsForPostPet(int status) {
        assertEquals("The response is not 200", +status, 200, postPet.statusCode());
    }

    @Given("the get request that displays the list of pets")
    public void theResponseGetListPets() {
        getListPet = given().log().all().get("/pet/findByStatus?status=available&status=available");
    }

    @Then("the response is {int} for the get list pet")
    public void validateResponse(int status) {
        assertEquals("The response is not 200", +status, 200, getListPet.statusCode());
    }

    @Given("the following put request that update a pet")
    public void theFollowingPutRequestThatUpdateAPet() {
        File bodyRequestPet = new File("src/test/resources/data/bodyRequestUpdatePet.json");
        given().contentType(ContentType.JSON).body(bodyRequestPet).put("pet");
    }

    @And("the response is {int} for the put pet")
    public void theResponseIsForThePutPet(int status) {
        assertEquals("The response is not 200", +status, 200, putPet.statusCode());
    }

    @Then("the body response contains update {string}")
    public void theBodyResponseContainsUpdate(String updatedStatus) {
        JsonPath jsonPathPets = new JsonPath(putPet.body().asString());
        String jsonPetValidate = jsonPathPets.getString("name");
        assertEquals("The value of the status field is not what is expected", updatedStatus, jsonPetValidate);
    }

    @Given("the following request that delete a pet")
    public void validatingDeletePets() {
        deletePet = given().log().all().delete("/11115555");
    }

    @And("the response is {int} for delete")
    public void validateResponseDelete() {
        assertTrue("The response is not 200", putPet.statusCode() == 200);
    }

    @Then("the body response contains the {string} of the pet delete")
    public void theBodyResponseContainsTheOfThePetDelete(String message) {
        JsonPath jsonPathPets = new JsonPath(postPet.body().asString());
        String jsonUser = jsonPathPets.getString("message");
        assertEquals("The value of the id field is not what is expected", message, jsonUser);
    }

}


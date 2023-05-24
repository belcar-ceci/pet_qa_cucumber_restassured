package com.cucumber.stepdefs;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.internal.ResponseSpecificationImpl;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.io.File;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class PetImplementation {
    private Response postPet = null;
    private Response getListPet = null;
    private Response putPet = null;
    private Response deletePet = null;

    @Before
    public void before(){
        RestAssured.baseURI = "https://petstore.swagger.io/v2/";
    }

    @Given("the following post that add pet")
    public void thePostAddPet(){
        File jsonFile = new File("src/test/resources/data/bodyRequestUsersListPost.json");
        postPet = given().contentType(ContentType.JSON).body(jsonFile).post("/pet");
    }

    @And("The response is 201 for post pet")
    public void the_response_is_for_the_post_pet(int status){
        assertEquals("The response is not 200", 201, postPet.statusCode());
    }

    @Then("the body response contains the {string} of the pet created")
    public void theBodyResponseNameCreatedPet(String key){
        JsonPath jsonPathPet = new JsonPath(postPet.body().asString());
        String jsonPet = jsonPathPet.getString("name");
        assertEquals("The value of the name field is not what is expected", key, jsonPet );
    }

    @Given("the get request that brings us the pets list")
    public void theResponseGetListPets() {
        getListPet = given().log().all().get("/pet/findByStatus?status=available&status=pending&status=sold");
    }

    @Then("the response is {int} for the get list pet")
    public void validateResponse(){
        assertTrue("The response is not 200", getListPet.statusCode()==200);
    }


    @Given("the following put request that update a pet")
    public void theFollowingPutRequestThatUpdateAPet() {
        thePostAddPet();
        JsonPath jsonPathUsers = new JsonPath(postPet.body().asString());
        String jsonIdCreate = jsonPathUsers.getString("id");
        HashMap<String,String> bodyRequestMapPut = new HashMap<>();
        bodyRequestMapPut.put("name", "blisa");
        putPet = given().contentType(ContentType.JSON).body(bodyRequestMapPut).put("/pet"+jsonIdCreate);


    }

    @And("the response is {int} for the put pet")
    public void theResponseIsForThePutPet() {
        assertTrue("The response is not 200",putPet.statusCode()==200);
    }

    @Then("the body response contains update {string}")
    public void theBodyResponseContainsUpdate(String updatedStatus) {
        JsonPath jsonPathPets = new JsonPath(putPet.body().asString());
        String jsonPetValidate = jsonPathPets.getString("name");
        assertEquals("The value of the status field is not what is expected",updatedStatus,jsonPetValidate);

    }
}

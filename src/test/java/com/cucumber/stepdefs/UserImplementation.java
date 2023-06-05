package com.cucumber.stepdefs;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.io.File;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserImplementation {

    private Response postUser = null;
    private Response postUsersList = null;
    private Response putUser = null;
    private Response getUser = null;
    private Response getLoginUser = null;
    private Response getLogoutUser = null;
    private Response deleteUser = null;

    @Before
    public void before() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2/";
    }

    @Given("the following post request that add one user")
    public void the_following_post_request_that_add_one_user(){
        HashMap<String, String> bodyRequestMap = new HashMap<>();
        bodyRequestMap.put("id", "111222");
        bodyRequestMap.put("username", "fepoLopez");
        bodyRequestMap.put("firstName", "Felipe");
        bodyRequestMap.put("lastName", "Lopez");
        bodyRequestMap.put("email", "fepo@petshop.com");
        bodyRequestMap.put("password", "catwhite");
        bodyRequestMap.put("phone", "909090909");
        bodyRequestMap.put("userStatus", "2");

        postUser = given().contentType(ContentType.JSON).body(bodyRequestMap).post("/user");
    }

    @And("the response is 200 for the post")
    public void validateResponsePostUser(){
        assertTrue("The response is not 200", postUser.statusCode()==200);
    }

    @Then("the body response contains the {string} of the user created")
    public void validateResponsePostIdBody(String message){
       JsonPath jsonPathUser = new JsonPath(postUser.body().asString());
        String jsonUser = jsonPathUser.getString("message");
        assertEquals("The value of the id field is not what is expected", message, jsonUser);

    }

    @Given("the following post request that create with a list")
    public void theFollowingPostRequestThatCreateWithAList() {
        File jsonFile = new File("src/test/resources/data/bodyRequestUsersListPost.json");
        postUsersList = given().contentType(ContentType.JSON).body(jsonFile).post("/user/createWithList");
    }

    @Then("the response is {int} for the post users list")
    public void theResponseForPostUsersList(int status) {
        assertEquals("The response is not 200", + status,201, postUsersList.statusCode());
    }

    @Given("the following get request which brings us {string}")
    public void theGetRequestUser(String username) {
        getUser = given().log().all().get("/user/" + username);
    }

    @Then("the response is {int} for the get user")
    public void theResponseIsForTheGetUser(int status){
        assertEquals("The response is not 200", + status,200, postUsersList.statusCode());
    }

    @Given("the user login with {string} and {string}")
    public void theUserLoginWithAnd(String username, String password) {
        getLoginUser = given().log().all().param("username", username).param("password", password).get("/user/login");
    }

    @Then("the response is {int} for login")
    public void theResponseIsForLogin(int status) {
        assertEquals("The response is not 200", + status,200, getLoginUser.statusCode());
    }

    @Given("the user logout the current session")
    public void theUserLogoutSession() {
        getLogoutUser = given().get("/user/logout");
    }

    @Then("the response is {int} for logout")
    public void theResponseIsAndMessageIsOk(Integer status) {
        assertEquals("The response is not " + status, 200, getLogoutUser.statusCode());
    }

    @Given("the following put request that update users")
    public void theUserPutSession(){
        the_following_post_request_that_add_one_user();
        JsonPath jsonPathUsers = new JsonPath(postUser.body().asString());
        String jsonCreate = jsonPathUsers.getString("username");
        HashMap<String, Object> bodyRequestMapPut = new HashMap<>();
        bodyRequestMapPut.put("id", 111223);
        bodyRequestMapPut.put("username", "ladyBug");
        bodyRequestMapPut.put("firstName", "Jane");
        bodyRequestMapPut.put("lastName", "Hills");
        bodyRequestMapPut.put("email", "ladybug@gmail.com");
        bodyRequestMapPut.put("password", "catblack");
        bodyRequestMapPut.put("phone", "666666666");
        bodyRequestMapPut.put("userStatus", 1);

        putUser = given().contentType(ContentType.JSON).body(bodyRequestMapPut).put("/user/"+ jsonCreate);
    }


    @Then("the response is {int} for the update")
    public void statusCodePutUser(int status) {
        assertEquals("The response is not " + status, 200, putUser.statusCode());
    }


    @Then("the following delete request that delete user") //2
    public void theFollowingDeleteRequestThatDeleteUser() {
        JsonPath jsonPathUsers = new JsonPath(postUser.body().asString());
        String jsonIdCreate = jsonPathUsers.getString("id");
        deleteUser = given().accept(ContentType.JSON).delete("/users/" +jsonIdCreate);

    }





}

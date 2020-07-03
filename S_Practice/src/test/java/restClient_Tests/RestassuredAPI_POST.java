package restClient_Tests;

import org.json.JSONObject;
import org.junit.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestassuredAPI_POST {

	@Test
	void Registration() {

		// specify base URI
		RestAssured.baseURI = "http://restapi.demoqa.com/customer";

		// Request object
		RequestSpecification httpRestAssuredRequest = RestAssured.given();

		// Request payload sending aling with post request
		JSONObject requestParams = new JSONObject();
		requestParams.put("FirstName", "Jp0010");
		requestParams.put("LastName", "Reddy");
		requestParams.put("UserName", "Jp0010");
		requestParams.put("Password", "Jp123");
		requestParams.put("Email", "Jp@gmail.com");

		httpRestAssuredRequest.header("Content-Type", "application/json");
		httpRestAssuredRequest.body((requestParams).toString());

		// response object
		Response response = httpRestAssuredRequest.request(Method.POST, "/register");

		// response body
		String responseBody = response.getBody().asString(); // attache above data to the request
		System.out.println("responseBody: " + responseBody);

		// status code
		int statusCode = response.statusCode();
		System.out.println("statusCode: " + statusCode);
		Assert.assertEquals(statusCode, 200);

		// success code validation:
		String successCode = response.jsonPath().get("FaultId");
		Assert.assertEquals(successCode, "User already exists");
	}
}

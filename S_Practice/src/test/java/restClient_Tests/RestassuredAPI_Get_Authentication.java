package restClient_Tests;

import org.junit.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestassuredAPI_Get_Authentication {

	@Test
	void AuthenticationTest() {

		RestAssured.baseURI = "http://restapi.demoqa.com/authentication/CheckForAuthentication";

		// Basic Authentication:
		PreemptiveBasicAuthScheme authSchme = new PreemptiveBasicAuthScheme();
		authSchme.setUserName("ToolsQA");
		authSchme.setPassword("TestPassword");
		RestAssured.authentication = authSchme;

		// Request object
		RequestSpecification httpRequest = RestAssured.given();

		Response response = httpRequest.request(Method.GET, "/");
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200);

		String responseBody = response.getBody().asString();
		System.out.println("Response Body is : " + responseBody);

	}
}

package restClient_Tests;

import org.junit.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class restAssured_RestClient {

	public Response RestAssured_GET() {

		// specify base URI
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";

		// Request object
		RequestSpecification httpRestAssuredRequest = RestAssured.given();
		Response response = httpRestAssuredRequest.request(Method.GET, "/Hyderabad");

		return response;

	}
}

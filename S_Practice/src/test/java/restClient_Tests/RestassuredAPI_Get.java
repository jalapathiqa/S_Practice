package restClient_Tests;

import org.junit.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestassuredAPI_Get {

	@Test
	void getweatherDetails() {

		// specify base URI
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";

		// Request object
		RequestSpecification httpRestAssuredRequest = RestAssured.given();
		Response response = httpRestAssuredRequest.request(Method.GET, "/Hyderabad");

		// response body
		String responseBody = response.getBody().asString();
		System.out.println("responseBody: " + responseBody);

		// status code
		int statusCode = response.statusCode();
		System.out.println("statusCode: " + statusCode);
		Assert.assertEquals(statusCode, 200);

		// status line
		String statusLine = response.statusLine();
		System.out.println("statusLine: " + statusLine);
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");

		// Validating single Header from response
		String headerConnection = response.header("Connection");
		System.out.println("Header details Connection is: " + headerConnection);
		Assert.assertEquals(headerConnection, "keep-alive");

		// validating all the Headers from response
		Headers allHeaders = response.headers();
		for (Header header : allHeaders) {
			System.out.println(header.getName() + "--->" + header.getValue());

		}

	}
}

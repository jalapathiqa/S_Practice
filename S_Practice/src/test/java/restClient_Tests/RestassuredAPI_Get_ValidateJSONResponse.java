package restClient_Tests;

import org.junit.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestassuredAPI_Get_ValidateJSONResponse {

	@Test
	void getDelhi_weatherDetails() {

		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
		RequestSpecification httpRequest = RestAssured.given();

		Response response = httpRequest.request(Method.GET, "/Delhi");
		String responseBody = response.getBody().asString();
		System.out.println("response: " + responseBody);

		// validating single json body filed
		Assert.assertEquals(responseBody.contains("Delhi"), true);

		JsonPath jsonPath = response.jsonPath();
		System.out.println(" " + jsonPath.get("City"));
		System.out.println(" " + jsonPath.get("Temperature"));
		System.out.println(" " + jsonPath.get("Humidity"));
		System.out.println(" " + jsonPath.get("WeatherDescription"));
		System.out.println(" " + jsonPath.get("WindSpeed"));
		System.out.println(" " + jsonPath.get("WindDirectionDegree"));

		Assert.assertEquals(jsonPath.get("Temperature"), "33.43 Degree celsius");

	}
}

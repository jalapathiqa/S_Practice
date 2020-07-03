package restClient_Tests;

import org.json.JSONObject;
import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestassuredAPI_Post_DataDriven {

	@DataProvider(name = "empData")
	String[][] empNewData() {
		String empdata[][] = { { "a101", "100", "30" }, { "a102", "101", "31" }, { "a103", "102", "32" } };
		return empdata;
	}

	@Test(dataProvider = "empData")
	void Post_AddNewEmployees(String ename, String esal, String eage) {

		RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1";
		RequestSpecification httpsRequest = RestAssured.given();

		// Create data to send with POST Request
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", ename);
		requestParams.put("salary", esal);
		requestParams.put("age", eage);

		// adding Header
		httpsRequest.header("Content-Type", "application/json;charset=utf-8");

		// adding the json to the body of the request
		httpsRequest.body(requestParams.toString());

		// Post request
		Response response = httpsRequest.request(Method.POST, "/create");
		int statusCode = response.statusCode();
		System.out.println("statusCode: " + statusCode);
		Assert.assertEquals(statusCode, 200);

		String responseBody = response.getBody().asString();
		System.out.println("responseBody: " + responseBody);
		Assert.assertEquals(responseBody.contains(ename), true);
		Assert.assertEquals(responseBody.contains(esal), true);
		Assert.assertEquals(responseBody.contains(eage), true);

		String successCode = response.jsonPath().get("status");
		Assert.assertEquals(successCode, "success");

	}
}

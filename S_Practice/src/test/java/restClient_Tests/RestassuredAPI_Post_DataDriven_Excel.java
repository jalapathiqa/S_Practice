package restClient_Tests;

import org.json.JSONObject;
import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Excel_Utils.Read_Excel;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestassuredAPI_Post_DataDriven_Excel extends Read_Excel {

	public RestassuredAPI_Post_DataDriven_Excel(String filelocation) {
		super(filelocation);
		// TODO Auto-generated constructor stub
	}

	String fileLocation = "C:\\Users\\Rishi\\Desktop\\Jp_Practice_Final\\TestData\\DataDrivenExcel.xlsx";
	String sheetName = "TestSheet";
	Read_Excel readExcel = new Read_Excel(fileLocation);
	String testCaseName = "RestAssured";

	@DataProvider(name = "empData")
	Object[][] empNewData() {
		return readExcel.retrieveTestData(sheetName, testCaseName);
	}

	@Test(dataProvider = "empData")
	void Post_AddNewEmployees(String execute, String ename, String esal, String eage) {

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

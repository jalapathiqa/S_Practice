package restClient_Tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class APIPostTest {

	Http_RestClient restClient;
	CloseableHttpResponse httpResponse;

	@Test
	public void postAPI() throws JsonGenerationException, JsonMappingException, IOException {

		String url = "https://reqres.in/api/users";
		restClient = new Http_RestClient();

		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");

		// jackson API - to convert data_users (post body) to json:
		ObjectMapper mapper = new ObjectMapper();
		Users users = new Users("marpheus", "leader");

		// object to json file:
		mapper.writeValue(new File("C:\\Users\\Rishi\\Desktop\\Jp_Practice_Final\\src\\restAPIWebServices\\data.json"),
				users);

		// object to json in String:
		String usersJsonString = mapper.writeValueAsString(users);
		System.out.println("usersJsonString: " + usersJsonString);

		CloseableHttpResponse postResponse = restClient.post(url, usersJsonString, headerMap);
		int statusCode = postResponse.getStatusLine().getStatusCode();

		System.out.println("statusCode: " + statusCode);
		Assert.assertEquals(statusCode, 201);

		// JsonString:
		String responseString = EntityUtils.toString(postResponse.getEntity(), "UTF-8");

		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("response from API is: " + responseJson);

		// json to java object
		Users usersResObj = mapper.readValue(responseString, Users.class);
		System.out.println("usersResObj: " + usersResObj);

		Assert.assertTrue((users.getName().endsWith(usersResObj.getName())));
		Assert.assertTrue((users.getJob().endsWith(usersResObj.getJob())));
		System.out.println(usersResObj.getId());
		System.out.println(usersResObj.getCreatedAt());

	}
}

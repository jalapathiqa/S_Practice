package restClient_Tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class APIGetTest_withHeaders {

	CloseableHttpResponse httpResponse;
	Http_RestClient restClient;

	@Test
	public void getAPI() throws ClientProtocolException, IOException {

		String httpURL = "https://reqres.in/api/users";
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
		// headerMap.put("UserName", "Jalapathi");
		// headerMap.put("PassWord", "Asdf@1234");
		// headerMap.put("Auth", "Allow/json");

		restClient = new Http_RestClient();
		httpResponse = restClient.get(httpURL, headerMap);

		// Status code
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		System.out.println("status code: " + statusCode);
		Assert.assertEquals(statusCode, 200, "Status code is not 200");
		String responseString = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");

		// Jason String
		JSONObject responsejson = new JSONObject(responseString);
		System.out.println("jsonObj: " + responsejson);

		String per_PageValue = JSONUtil.getValueByJPath(responsejson, "/per_page");
		System.out.println("per page value is: --" + per_PageValue);
		// Assert.assertEquals(per_PageValue, 6);

		String totalValue = JSONUtil.getValueByJPath(responsejson, "/total");
		System.out.println("Total values: --" + totalValue);
		// Assert.assertEquals(totalValue, 12);

		// get the value from JSON Array
		String lastName = JSONUtil.getValueByJPath(responsejson, "/data[0]/last_name");
		String id = JSONUtil.getValueByJPath(responsejson, "/data[0]/id");
		String avatar = JSONUtil.getValueByJPath(responsejson, "/data[0]/avatar");
		String firstName = JSONUtil.getValueByJPath(responsejson, "/data[0]/first_name");

		System.out.println(lastName);
		System.out.println(id);
		System.out.println(avatar);
		System.out.println(firstName);

		// Headers
		Header[] headerArray = httpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		for (Header header : headerArray) {
			allHeaders.put(header.getName(), header.getValue());

		}

		System.out.print("Headers Array: " + allHeaders);

	}

}

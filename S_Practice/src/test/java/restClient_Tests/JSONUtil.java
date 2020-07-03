package restClient_Tests;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.JsonObject;

public class JSONUtil {

	public static JSONObject responsejson;

	public static String getValueByJPath(JSONObject responsejson, String jpath) {

		// responsejson = new JSONObject();

		Object obj = responsejson;

		for (String s : jpath.split("/")) {
			if (!s.isEmpty())
				if (!(s.contains("[") || s.contains("]")))
					obj = ((JSONObject) obj).get(s);
				else if (s.contains("[") || s.contains("]"))
					obj = ((JSONArray) ((JSONObject) obj).get(s.split("\\[")[0]))
							.get(Integer.parseInt(s.split("\\[")[1].replace("]", "")));

		}
		return obj.toString();

	}

}

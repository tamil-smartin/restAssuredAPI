package Utils;
import org.json.simple.*;
import com.google.gson.JsonObject;

public class CustomerAccountPayloads {
	
	@SuppressWarnings("unchecked")
	public static String signInPayload(){
		JSONObject loginRequest = new JSONObject();
		JSONObject identity = new JSONObject();
		loginRequest.put("logonId", "mcm@qa74.com");
		loginRequest.put("password", "passw0rd");
		loginRequest.put("originId", "dotcomauth");
		identity.put("identity", loginRequest);
        System.out.println("DD request " + identity.toJSONString());

		return identity.toString();
	}
	
}

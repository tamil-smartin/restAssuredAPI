package Utils;

import com.jayway.restassured.path.json.JsonPath;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
 
public class HelperMethods {
    /*
    Verify the http response status returned. Check Status Code is 200?
    We can use Rest Assured library's response's getStatusCode method
    */
	public static long timestamp;
	public static String clientAuthToken;
    public static void checkStatusIs200 (Response res) {
        assertEquals("Status Check Failed!", 200, res.getStatusCode());
    }
 public static void GenerateHmacToken()   {
		timestamp = System.currentTimeMillis();
		System.out.println("timeStamp" + timestamp);
    	RestAssured.baseURI = RestUtils.getBaseUri();
		Response hmacresponse = given().log().all()
				.header("clientId", "4567")
				.header("Content-Type", "application/json")
				.header("Accept", "application/json")
				.header("timestamp", timestamp)
				.header("Cust-Acct-Client-Delay-Token-Validation", "3000")
			.when().get(RestUtils.hmacPathUrl()).then().assertThat()
				.statusCode(200).and().extract().response();
		System.out.println("Hmac Token = "+hmacresponse.asString());
		clientAuthToken = hmacresponse.jsonPath().get("clientAuthToken");
	}
		
    
}

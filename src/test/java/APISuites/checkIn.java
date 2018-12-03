package customerAccountAPISuites;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

import com.jayway.restassured.path.json.JsonPath;

import Utils.CustomerAccountPayloads;
import Utils.HelperMethods;
import Utils.RestUtils;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import java.io.FileReader;
import java.io.IOException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) // For Ascending order test
												// execution
public class UserSignIn {

	// First, I declared Response and JsonPath objects.
	private Response res = null; // Response object
	private JsonPath jp = null; // JsonPath object
   
	@BeforeMethod
	public void setup() throws IOException, ParseException {
	
		HelperMethods.GenerateHmacToken();
		
	}

	@Test (priority = 1)
	public void signInAPI() {
	
		RestAssured.baseURI = RestUtils.getBaseUri();
		Response signInresponse = given().log().all().header("channelId", "1")
				.header("Content-Type", "application/json")
				.header("Accept", "application/json")
				.header("Cust-Acct-Client-ID", "4960")
				.header("Cust-Acct-Client-Timestamp", HelperMethods.timestamp)
				.header("Cust-Acct-Client-Token", HelperMethods.clientAuthToken)
				.header("Cust-Acct-Client-Delay-Token-Validation", "3000")
				.body(CustomerAccountPayloads.signInPayload()).when()
				.post(RestUtils.getLoginUrl()).then().assertThat().statusCode(200).and()
				.extract().response();
		String stringResponse = signInresponse.asString();
		System.out.println("Status code" +signInresponse.getStatusCode());
		System.out.println("Cookie code" +signInresponse.getCookie("THD_USER").toString());
		RestUtils.authorization=signInresponse.getCookie("THD_USER").toString();
		System.out.println(stringResponse);


	}

	@Test (priority = 2)
	public void logOutAPI() {

		RestAssured.baseURI = RestUtils.getBaseUri();
		Response logOutRes = given().log().all()
				.header("Authorization", RestUtils.authorization)
				.header("channelid", "1")
				.header("Content-Type", "application/json").when()
				.get(RestUtils.getLogOutURI()).then().assertThat().statusCode(200).and().extract().response();
		String stringResponse = logOutRes.asString();
		System.out.println("Response Body = " + stringResponse);

	}
	@Test (priority = 3)
	public void checkEmailExists() {
		RestAssured.baseURI = RestUtils.getBaseUri();
		Response logOutRes = given().log().all()
				.queryParam("emailId", "mcm@qa74.com")
				.header("Authorization", RestUtils.authorization)
				.header("channelid", "1")
				.header("Content-Type", "application/json").when()
				.get(RestUtils.getCheckEmailExistsURI()).then().extract().response();
		String stringResponse = logOutRes.asString();
		JSONObject jsonObject = null;  	 
       	JSONParser parser = new JSONParser();
            Object obj = null;
        try {
				obj = parser.parse(stringResponse);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            jsonObject = (JSONObject) obj;
		System.out.println("Response Body = " + logOutRes.getStatusCode());
		System.out.println("Response Body = " + jsonObject.get("errors"));
	
	}
	
	@AfterMethod
	public void clearURI() {
			RestUtils.resetBaseURI();
		}
	
	
}
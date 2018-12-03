package Utils;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

import static com.jayway.restassured.RestAssured.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RestUtils {
    //Global Setup Variables
   
    public static String loginUrl;
    public static String hostName;
    public static String hmacPathUrl;
	private static String checkEmailExists;
	 public static String authorization;
	private static String logOut;
	static File jsonfile;
    
    public static JSONObject readJSONFile() {
      	JSONObject jsonObject = null;
  		try {
			jsonfile = new File (System.getProperty("user.dir")+"\\src\\test\\resources\\environment.json");
	        Object obj;
	       	JSONParser parser = new JSONParser();
			obj = parser.parse(new FileReader(jsonfile.toString()));
			jsonObject = (JSONObject) obj;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          return jsonObject;  
    }

  
    public static String getBaseUri() {
    	JSONObject jsonObject = readJSONFile();
        hostName = (String) jsonObject.get("Hostname");
        System.out.println("host name url : "+ hostName);
        System.out.println("Host name from get BaseURI  "+ hostName);
    	return hostName;
    }
    
 	
  	//login url 
  	
  	public static String getLoginUrl () {
  		
  		JSONObject jsonObject = readJSONFile();
        String path = (String) jsonObject.get("login");
     	loginUrl = path;
  		System.out.println("login url : "+ loginUrl);
  		return loginUrl;
		
  	}
  	 
    
  //HMAC Token
  	public static String hmacPathUrl()  {
  		
  		JSONObject jsonObject = readJSONFile();
        String hmacPath = (String) jsonObject.get("hmac");
        hmacPathUrl = hmacPath;
     	
  		System.out.println("hmacPath url : "+ hmacPathUrl);
  		return hmacPathUrl;
  		
  		}

  	 //check Existing email uri
  	public static String getCheckEmailExistsURI()  {
  		
  		JSONObject jsonObject = readJSONFile();
        String emailExistingPath = (String) jsonObject.get("existingEmail");
        checkEmailExists = emailExistingPath;
     	
  		System.out.println("checkEmailExists url : "+ checkEmailExists);
  		return checkEmailExists;
  		
  		}
  
  	
  //log out uri
  	public static String getLogOutURI()  {
  		
  		JSONObject jsonObject = readJSONFile();
        String logOutPath = (String) jsonObject.get("logout");
        logOut = logOutPath;

  		System.out.println("LogOut url : "+ logOut);
  		return logOut;
  		
  		}
    
    public static void resetBaseURI (){
        RestAssured.baseURI = null;
    }
  
}


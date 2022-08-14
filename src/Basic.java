import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Basic 
{

	public static void main(String[] args) {
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
//		POST API
		String postResponse = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body(BodyFile.postBody())
		.when().post("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = new JsonPath(postResponse);
		String placeID = js.getString("place_id");
		
		
//		PUT API
		String newAdd = "70 Summer walk, USA";
		String putResponse = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeID+"\",\r\n"
				+ "\"address\":\""+newAdd+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}").when().put("maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js1 = new JsonPath(putResponse);
		String updAdd = js.getString("address");
		
		System.out.println(newAdd + " __ "+updAdd);
		
		
//		GET API
		given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeID)
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat().body("address", equalTo(newAdd));
		
		
	}

}

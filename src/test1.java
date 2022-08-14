
import static io.restassured.RestAssured.given;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import pojo.GetCourse;
import pojo.WebAutomation;
import pojo.Api;
import pojo.GetCourse;

public class test1 {

	public static void main(String[] args) throws InterruptedException {

// TODO Auto-generated method stub
		
		String[] courseTit = {"Selenium Webdriver Java","Cypress11","Protractor"};

		String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AdQt8qhjylnDsWLwGs6tzaf2tp_jIshb9E84ogwyWFz3Mavv1R3me6ZF2pPDSmWqrw1moA&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=1&prompt=none";
		String partialcode = url.split("code=")[1];
		String code = partialcode.split("&scope")[0];
		System.out.println(code);
		String response =
				given()
						.urlEncodingEnabled(false)
						.queryParams("code", code)
						.queryParams("client_id",
								"692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
						.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
						.queryParams("grant_type", "authorization_code")
						.queryParams("state", "verifyfjdss")
						.queryParams("session_state", "ff4a89d1f7011eb34eef8cf02ce4353316d9744b..7eb8")

						// .queryParam("scope",
						// "email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email")

						.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
						.when().log().all()
						.post("https://www.googleapis.com/oauth2/v4/token").asString();

// System.out.println(response);

		JsonPath jsonPath = new JsonPath(response);
		String accessToken = jsonPath.getString("access_token");
		System.out.println(accessToken);
		
		
		
		
		GetCourse gc = given().contentType("application/json").
				queryParams("access_token", accessToken).expect().defaultParser(Parser.JSON)
				.when()
				.get("https://rahulshettyacademy.com/getCourse.php")
				.as(GetCourse.class);
		
		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getInstructor());
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
		
		List<Api> apiCources = gc.getCourses().getApi();
		
		for (int i = 0; i < apiCources.size(); i++) 
		{
			if(apiCources.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
			{
				System.out.println(apiCources.get(i).getPrice());
			}
		}
		
		System.out.println(gc.getCourses().getWebAutomation().get(1).getCourseTitle());
		
		
		
		ArrayList<String> a = new ArrayList<String>();
		List<WebAutomation> wa = gc.getCourses().getWebAutomation();
		
		for (int i = 0; i < wa.size(); i++) 
		{
			a.add(wa.get(i).getCourseTitle());
		}
		
		List<String> expectedList = Arrays.asList(courseTit);
		Assert.assertTrue(a.equals(expectedList));
		
		
		
//		System.out.println(gc);
		
		
	}

}

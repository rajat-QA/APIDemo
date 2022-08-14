import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.DeleteOrd;
import pojo.DeletePrd;
import pojo.GetOrder;
import pojo.Login;
import pojo.LoginResponsePayload;
import pojo.Order;
import pojo.PlaceOrder;
import pojo.PlaceOrderResp;
import pojo.ProductId;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import com.fasterxml.jackson.databind.type.PlaceholderForType;

public class EcommerceAPITest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		// Login
		
		System.out.println("--------------------------------------------------");
		System.out.println("----------------LOGIN----------------");
		System.out.println("--------------------------------------------------");
		
		RequestSpecification req =  new RequestSpecBuilder()
		.setBaseUri("https://rahulshettyacademy.com")
		.setContentType(ContentType.JSON).build();
		
		Login l = new Login();
		l.setUserEmail("rajat2@gmail.com");
		l.setUserPassword("Raigad@202020");
		

		RequestSpecification reqLogin = given().relaxedHTTPSValidation().log().all()
		.spec(req).body(l);
		
		LoginResponsePayload loginResponse =  reqLogin.when()
		.post("/api/ecom/auth/login").then()
		.log().all().extract().response()
		.as(LoginResponsePayload.class);
		
		System.out.println(loginResponse.getToken());
		String token = loginResponse.getToken();
		System.out.println(loginResponse.getUserId());
		String userID = loginResponse.getUserId();
		
		
		
		// Add Product
		
		System.out.println("--------------------------------------------------");
		System.out.println("----------------ADD PRODUCT----------------");
		System.out.println("--------------------------------------------------");
		
		
		String productName = "Realme Buds 2";
		
		RequestSpecification addProductBaseReq =  new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token)
				.build();
		
		RequestSpecification reqAddProd = given().relaxedHTTPSValidation().log().all().spec(addProductBaseReq)
		.param("productName", productName)
		.param("productAddedBy", userID)
		.param("productCategory", "electronics")
		.param("productSubCategory", "earphones")
		.param("productPrice", "11500")
		.param("productDescription", "Realme Original")
		.param("productFor", "men/women")
		.multiPart("productImage", new File("C:\\Users\\rajat.mahadik\\eclipse-workspace\\APIAuto\\Ecom_test_iamge.png"));
		
		ProductId createResponse =  reqAddProd.when()
				.post("/api/ecom/product/add-product").then()
				.log().all().extract().response()
				.as(ProductId.class);
		
		System.out.println(createResponse.getProductId());
		String productId = createResponse.getProductId();
		
		
		// Place Order
		
		System.out.println("--------------------------------------------------");
		System.out.println("----------------PLACE ORDER----------------");
		System.out.println("--------------------------------------------------");
		
		
		RequestSpecification placeOrderReq =  new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token)
				.setContentType(ContentType.JSON)
				.build();
		
		
		Order o = new Order();
		o.setProductOrderedId(productId);
		o.setCountry("India");
		
		List<Order> odl = new ArrayList<Order>();
		odl.add(o);
		
		PlaceOrder po = new PlaceOrder();
		po.setOrders(odl);
		
		
		
		PlaceOrderResp placeOrdResponse = given().log().all().spec(placeOrderReq)
		.body(po).when().post("api/ecom/order/create-order")
		.then().log().all().extract().response().as(PlaceOrderResp.class);
		
		System.out.println(placeOrdResponse.getOrders());
		List<String> order1 =  placeOrdResponse.getOrders();
		String order = order1.get(0);
		
		// Get Order Details
		
		System.out.println("--------------------------------------------------");
		System.out.println("----------------GET ORDER DETAILS----------------");
		System.out.println("--------------------------------------------------");
		
		
		RequestSpecification getOrderReq =  new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token)
				.addQueryParam("id", order)
				.build();
		
		GetOrder getOrdResponse = given().log().all().spec(getOrderReq)
				.when().get("/api/ecom/order/get-orders-details")
				.then().log().all().extract().response().as(GetOrder.class);
				
		String respProductName = getOrdResponse.getData().getProductName();
		Assert.assertTrue(respProductName.equalsIgnoreCase(productName));

		
		// Delete Product 
		
		System.out.println("--------------------------------------------------");
		System.out.println("----------------DELETE PRODUCT----------------");
		System.out.println("--------------------------------------------------");
		
		
		RequestSpecification delPrdReq =  new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token)
				.build();
		
		DeletePrd delPrdResp =  given().log().all().spec(delPrdReq)
		.when().delete("/api/ecom/product/delete-product/"+productId+"")
		.then().log().all().extract().response().as(DeletePrd.class);
		
		System.out.println(delPrdResp.getMessage());
		
		
		// Delete Order
		
		System.out.println("--------------------------------------------------");
		System.out.println("----------------DELETE ORDER----------------");
		System.out.println("--------------------------------------------------");
		
		RequestSpecification delOrdReq =  new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token)
				.build();
		
		DeleteOrd delOrdResp =  given().log().all().spec(delPrdReq)
		.when().delete("https://rahulshettyacademy.com/api/ecom/order/delete-order/"+order+"")
		.then().log().all().extract().response().as(DeleteOrd.class);
		
		System.out.println(delOrdResp.getMessage());

	}
}

package RestClient_APITesting;

import org.junit.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssured_GET_2 {
	@Test
	void getEmpDetails() {

		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";

		RequestSpecification httpRestAssuredRequest = RestAssured.given();
		Response response = httpRestAssuredRequest.request(Method.GET, "/Delhi");
		
		int statusCode = response.statusCode();
		System.out.println("statusCode: "+statusCode);
		
		String responseBody = response.getBody().asString();
		System.out.println("responseBody: "+responseBody);
		
		
	}
}

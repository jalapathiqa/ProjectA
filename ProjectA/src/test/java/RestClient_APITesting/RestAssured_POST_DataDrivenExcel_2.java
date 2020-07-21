package RestClient_APITesting;

import org.codehaus.groovy.syntax.ReadException;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ExcelUtils.Read_Excel;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssured_POST_DataDrivenExcel_2 {
	
	String filelocation = "C:\\Users\\Rishi\\Desktop\\Jp_Practice_Final\\TestData\\DataDrivenExcel.xlsx";
	Read_Excel readExcel = new Read_Excel(filelocation);
	String sheetName = "TestSheet";
	String testCaseName = "RestAssured";
	
	
	
	@DataProvider(name = "emp")
	Object [][] getEmpdata(){
		Object [][] data = readExcel.retrieveTestData(sheetName, testCaseName);
		
		return data;
	}
	
	
	@Test(dataProvider = "emp")
	public void Post_Employee(String execute, String ename, String sal, String age) {
			
		RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1";
		RequestSpecification request = RestAssured.given();
		
		JSONObject requestParams = new JSONObject();
		requestParams.put("ename", ename);
		requestParams.put("sal", sal);
		requestParams.put("age", age);
		
		System.out.println("Test Data: -> "+execute + ename + sal + age );
		
		request.header("Content-Type", "application/json;charset=utf-8");
		request.body(requestParams.toString());
		
		Response response = request.request(Method.POST, "/create");
		int statusCode = response.getStatusCode();
		System.out.println("statusCode "+statusCode);
		
		
		
		
	}

	
	
	
}


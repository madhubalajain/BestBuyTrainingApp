package com.bestbuy.response.extraction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;


import static io.restassured.RestAssured.given;

public class JsonPathJsonSlurperExamples {

	static ValidatableResponse validatableResponse;

	/**
	 * Print to console
	 * 
	 * @param val
	 */
	static void print(String val) {
		System.out.println(val);
	}

	@BeforeAll
	public static void init() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3030;
		
		validatableResponse = given().when().get("/stores").then();

	}

	@BeforeEach
	void printToConsole() {
		System.out.println("-----Starting the test method--------");
		System.out.println("   ");
	}

	@AfterEach
	void printToConsoleAgain() {
		System.out.println("-----Ending the test method--------");
		System.out.println("   ");
	}

	
	
	
	@DisplayName("Print the 'total' value from the response")
	@Test
	public void getTotal() {
		
		int total = validatableResponse.extract().path("total");
		print(total + "");

	}

	@DisplayName("Print 'storeName' from first data element")
	@Test
	public void getFristStoreName() {
		
		String storeName = validatableResponse.extract().path("data[0].name");
		print(storeName);

	}

	@DisplayName("Print first 'service name' from the first data element")
	@Test
	public void getFirstServoceNameFromFirstDataElement() {
		
		String serviceName = validatableResponse.extract().path("data[0].services[0].name");
		print(serviceName);
	}

	@DisplayName("Get all the info of store with zip 55901")
	@Test
	public void findStoreWithZip() {
		
		Map<String, ?> info = validatableResponse.extract().path("data.find{it.zip=='55901'}");
		print(info.toString());
	}
	

	@DisplayName("Get 'address' of store with zip 55901")
	@Test
	public void findAddressOfStoreWithZip() {
		
		String info = validatableResponse.extract().path("data.find{it.zip=='55901'}.address");
		print(info);
	}

	
	@DisplayName("Get all information of store with max & min id")
	@Test
	public void getInforOfStoreWithMaxId() {
		
		HashMap<String,?> maxid = validatableResponse.extract().path("data.max{it.id}");
        print(maxid.toString());

        HashMap<String,?> minId = validatableResponse.extract().path("data.min{it.id}");
        print(minId.toString());

	}


	@DisplayName("Get the all the store zipcodes with ids less than 10")
	@Test
	public void getAllStoresWithIdsLessThan10() {
		
		    List<String> zipCode = validatableResponse.extract().path("data.findAll{it.id<10}.zip");	       
		    zipCode.stream().forEach(zip -> System.out.println(zip));
		    
	}

	@DisplayName("Get all the service name for all the stores")
	@Test
	public void getAllServiceNamesInAllStores0() {
		
		  List< List<String> > serviceNames = validatableResponse.extract().path("data.services.findAll{it.name}.name");
	       print(serviceNames.toString());
	}


}

package com.booker.core;

import org.testng.annotations.BeforeSuite;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseTest {

	/*
	 * A RequestSpecification for setting up our requests
	 */

	protected static RequestSpecification requestSpec;

	/*
	 * Variable to store the Authentication token
	 */

	protected static String token;

	/*
	 * Creating a logger instance
	 */

	private static final Logger logger = LogManager.getLogger(BaseTest.class);

	/*
	 * Setup method that will run before any and all tests in the suite
	 */

	@BeforeSuite
	public void setup() {

		/*
		 * Creating Properties object
		 */

		Properties props = new Properties();

		try {

			/*
			 * Loading the config file
			 */

			FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
			props.load(fis);

		} catch (IOException e) {

			e.printStackTrace();
		}

		/*
		 * Build the base request spec
		 */

		requestSpec = new RequestSpecBuilder().setBaseUri(props.getProperty("baseUri")).build();

		/*
		 * Obtain and store the Authentication and Using config file to read user
		 * details
		 */

		String requestBody = String.format("{\"username\" : \"%s\", \"password\" : \"%s\"}",
				props.getProperty("username"), props.getProperty("password"));

		token = given().spec(requestSpec).header("Content-Type", "application/json").body(requestBody).when()
				.post("/auth").then().statusCode(200).log().all().extract().path("token");

		logger.info("Setup complete. Using Base URI: {} ", props.getProperty("baseUri"));
		logger.info("Authentication Token Retrived");

	}

}

package com.booker.tests;

// Third-party library imports
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

// Local project imports
import com.booker.api.BookingClient;
import com.booker.core.BaseTest;
import com.booker.pojos.Booking;
import com.booker.pojos.BookingDates;

/**
 * Test class for the Booking endpoints. Inherits from BaseTest to get the base
 * URI and authentication token.
 */
public class BookingTests extends BaseTest {

	// Logger instance for this class
	private static final Logger logger = LogManager.getLogger(BookingTests.class);

	// Class-level variables to be used across tests
	private BookingClient bookingClient;
	private int bookingId;

	/**
	 * Setup method to initialize the API client before any tests run.
	 */
	@BeforeClass
	public void setupTests() {
		
		this.bookingClient = new BookingClient();
	}

	/**
	 * Test to verify that we can retrieve a list of all booking IDs.
	 */
	@Test
	public void getAllBookingIds() {
		
		logger.info("Test: Get all booking IDs.");
		Response response = bookingClient.getAllBookings();
		response.then().statusCode(200).body("$", not(empty())).extract().response();
	}

	/**
	 * Test to create a new booking and verify its creation. This test runs first in
	 * the end-to-end flow.
	 */
	@Test
	public void createBooking() {
		
		logger.info("Test: Create a new booking.");

		// --- 1. Prepare Test Data ---
		BookingDates bookingDates = new BookingDates();
		bookingDates.setCheckin("2025-10-01");
		bookingDates.setCheckout("2025-10-15");

		Booking bookingPayload = new Booking();
		bookingPayload.setFirstname("Jim");
		bookingPayload.setLastname("Brown");
		bookingPayload.setTotalprice(111);
		bookingPayload.setDepositpaid(true);
		bookingPayload.setBookingdates(bookingDates);
		bookingPayload.setAdditionalneeds("Breakfast");

		// --- 2. Make API Call ---
		Response response = bookingClient.createBooking(bookingPayload);

		// --- 3. Perform Assertions and Extract Data ---
		response.then().statusCode(200);
		bookingId = response.then().extract().path("bookingid");
		logger.info("Successfully created booking with ID: {}", bookingId);
	}

	/**
	 * Test to update the booking created in the createBooking test. Depends on the
	 * successful execution of createBooking.
	 */
	@Test(dependsOnMethods = "createBooking")
	public void updateBooking() {
		
		logger.info("Test: Update booking with ID: {}", bookingId);

		// --- 1. Prepare Test Data for the update ---
		BookingDates bookingDates = new BookingDates();
		bookingDates.setCheckin("2025-11-01");
		bookingDates.setCheckout("2025-11-15");

		Booking updatedBookingPayload = new Booking();
		updatedBookingPayload.setFirstname("James");
		updatedBookingPayload.setLastname("Bond");
		updatedBookingPayload.setTotalprice(999);
		updatedBookingPayload.setDepositpaid(false);
		updatedBookingPayload.setBookingdates(bookingDates);
		updatedBookingPayload.setAdditionalneeds("Shaken, not stirred");

		// --- 2. Make API Call ---
		Response response = bookingClient.updateBooking(bookingId, updatedBookingPayload);

		// --- 3. Perform Assertions ---
		response.then().statusCode(200).body("firstname", equalTo("James")).body("totalprice", equalTo(999));
	}

	/**
	 * Test to delete the booking that was just created and updated. Depends on the
	 * successful execution of updateBooking.
	 */
	@Test(dependsOnMethods = "updateBooking")
	public void deleteBooking() {
		
		logger.info("Test: Delete booking with ID: {}", bookingId);

		// --- 1. Make API Call ---
		Response response = bookingClient.deleteBooking(bookingId);

		// --- 2. Perform Assertions ---
		response.then().statusCode(201);
		logger.info("Successfully deleted booking with ID: {}", bookingId);
	}
}

package com.booker.api;

import static io.restassured.RestAssured.given;
import com.booker.core.BaseTest;
import com.booker.pojos.Booking;
import io.restassured.response.Response;

// This class is our "waiter". It handles all communication with the API.
//It extends BaseTest to get access to the shared setup like the base URI and token.

public class BookingClient extends BaseTest {

	// Method for creating booking

	/**
	 * This method takes a booking "order" (the POJO) from the customer, handles the
	 * complex REST Assured communication with the "kitchen" (the API), and returns
	 * the "food" (the Response) back to the customer.
	 * 
	 * @param bookingPayload The booking object created in our test.
	 * @return The full Response object from the server.
	 */
	public Response createBooking(Booking bookingPayLoad) {

		// This is the "kitchen communication" part. It's now hidden from the test.

		return given().spec(requestSpec).header("Content-Type", "application/json").header("Accept", "application/json")
				.header("Cookie", "token=+" + token).body(bookingPayLoad).log().ifValidationFails().when()
				.post("/booking");

	}

	/**
	 * Updates an existing booking.
	 * 
	 * @param bookingId      The ID of the booking to update.
	 * @param bookingPayload The POJO containing the updated booking data.
	 * @return The full Response object from the server.
	 */

	public Response updateBooking(int bookingId, Booking bookingPayLoad) {

		return given().spec(requestSpec).header("Content-Type", "application/json").header("Accept", "application/json")
				.header("Cookie", "token=" + token).body(bookingPayLoad).log().ifValidationFails().when()
				.put("/booking/" + bookingId);

	}

	/**
	 * Deletes a booking.
	 * 
	 * @param bookingId The ID of the booking to delete.
	 * @return The full Response object from the server.
	 */

	public Response deleteBooking(int bookingId) {

		return given().spec(requestSpec).header("Content-Type", "application/json").header("Cookie", "token=" + token)
				.log().ifValidationFails().when().delete("/booking/" + bookingId);
	}

	/*
	 * Gets all bookings.
	 */

	public Response getAllBookings() {
		return given().spec(requestSpec).log().ifValidationFails().when().get("/booking");
	}

}

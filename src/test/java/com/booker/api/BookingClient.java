package com.booker.api;

import static io.restassured.RestAssured.given;
import com.booker.core.BaseTest;
import com.booker.pojos.Booking;
import io.restassured.response.Response;

public class BookingClient extends BaseTest {

	

	/**
	 * Method for creating booking
	 * @param bookingPayload The booking object created in our test.
	 * @return The full Response object from the server.
	 */
	public Response createBooking(Booking bookingPayLoad) {


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

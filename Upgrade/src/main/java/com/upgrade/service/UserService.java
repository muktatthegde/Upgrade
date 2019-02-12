package main.java.com.upgrade.service;

import java.sql.Date;
import java.util.List;


import org.springframework.stereotype.Repository;

import main.java.com.upgrade.model.User;

public interface UserService {
	
	String reserve(User u);
	public String generateBookingReference();
	String cancelBooking(String bookingReference);
	String updateBooking(String bookingReference, java.sql.Date arrivalDate, java.sql.Date departureDate);
	String validateUser(User u);
	List<java.util.Date> getAvailableDates();

}

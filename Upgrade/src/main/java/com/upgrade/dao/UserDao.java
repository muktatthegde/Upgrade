package main.java.com.upgrade.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import main.java.com.upgrade.model.User;
@Repository
public interface UserDao {
	
	public void save(User u);
	public List<User> list();
	public String getBookingReference(Date arrivalDate, Date departureDate);
	public void deleteEntry(String bookingReference);
	public User getUser(String bookingReference); 
	public void updateDates(Date arrivalDate, Date departureDate, String bookingReference);
	public List<Date> bookedDates();

}

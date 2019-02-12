package main.java.com.upgrade.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import main.java.com.upgrade.dao.UserDao;
import main.java.com.upgrade.dao.UserDaoImpl;
import main.java.com.upgrade.model.User;
@Service("UserService")
public class UserServiceImpl implements UserService {
	
@Autowired
UserDao userDao;

@Override
	public String reserve(User u) {
		String reserveMessage ="";
		//validate the user before booking
		String validateMessage = validateUser(u);
		//if validation is satisfied, check for available dates
		if(validateMessage == "") {
		reserveMessage = userDao.getBookingReference(u.getArrival_Date(), u.getDeparture_Date());
		// if dates are available, reserve booking
		if(reserveMessage.equals("You can book")) {
			String bookingReference = generateBookingReference();
			u.setBooking_Reference(bookingReference);
			userDao.save(u);
			return bookingReference;
		}
		}else {
			reserveMessage = validateMessage;
		}
		return reserveMessage;
	}
@Override
	public String generateBookingReference() {
		
		String reference = "";
		
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		reference= format.format(date);
		return reference;
		
	}
@Override
	public String validateUser(User u) {
		String message = "";
		Date date = new Date();
		java.sql.Date todaysDate = new java.sql.Date((date.getYear()), date.getMonth(), date.getDay()+1);
		java.sql.Date monthDate = new java.sql.Date((date.getYear()), date.getMonth()+1, date.getDay()+1);
		long difference = u.getDeparture_Date().getTime()-u.getArrival_Date().getTime();
		int days = (int)(difference/(1000*60*24*60));
		System.out.println("Days : "+days);
		if(days > 3) {
			message = "You cannot book for more than 3 days";
		}else if(u.getArrival_Date().compareTo(u.getDeparture_Date()) >=0) {
			message = "Departure date must be greater than arrival date";
		}else if(u.getArrival_Date().compareTo(todaysDate) <= 0 ) {
			
			message = "Your arrival date must be greater than today's date";
		}else if(u.getDeparture_Date().compareTo(monthDate) <= 0 ) {
			
			message = "Your arrival date must be lesser than one month";
		}
		
		return message;
	}
@Override
	public String cancelBooking(String bookingReference) {
		userDao.deleteEntry(bookingReference);
		return "Booking is cancelled";
	}
@Override
	public String updateBooking(String bookingReference, java.sql.Date arrivalDate, java.sql.Date departureDate) {
		User user = userDao.getUser(bookingReference);
		user.setArrival_Date(arrivalDate);
		user.setDeparture_Date(departureDate);
		String validateMessage = validateUser(user);
		String reserveMessage = "";
		if(validateMessage == "") {
			reserveMessage = userDao.getBookingReference(user.getArrival_Date(), user.getDeparture_Date());
			// if dates are available, reserve booking
			if(reserveMessage.equals("You can book")) {
				userDao.updateDates(arrivalDate, departureDate, bookingReference);
				return "Booking is updated";
			}
			}else {
				reserveMessage = validateMessage;
			}
		
		return validateMessage;
	}
@Override
public List<Date> getAvailableDates() {
	List<Date> dates = new ArrayList<Date>();
	Date fromDate = new Date();
	Date toDate = new Date(fromDate.getYear(),fromDate.getMonth(),fromDate.getDate()+30);
	List<java.sql.Date> bookedDates = userDao.bookedDates();
	
	
	Calendar cal = Calendar.getInstance();
	cal.setTime(fromDate);
	
	while (cal.getTime().before(toDate)) {
		cal.add(Calendar.DATE, 1);
		dates.add(cal.getTime());
		  
	    
	}
	
	
	for (Date bookDate : bookedDates) {
		Date checkDate = new Date(bookDate.getYear(),bookDate.getMonth(),bookDate.getDate());

		for (int i = 0; i < dates.size(); i++) {
			Date innerDate = new Date(dates.get(i).getYear(),dates.get(i).getMonth(),dates.get(i).getDate());
			if (innerDate.equals(checkDate)) {
				dates.remove(i);
				dates.remove(i);
				dates.remove(i);
				dates.remove(i);
		
			}
		}
			
		
	}
	
	return dates;
}

}

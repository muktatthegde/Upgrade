package com.upgrade.config;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import main.java.com.upgrade.dto.AvailableDatesDto;
import main.java.com.upgrade.dto.BookingMessageDto;
import main.java.com.upgrade.dto.IsAliveDto;
import main.java.com.upgrade.dto.UpdateBookingDto;
import main.java.com.upgrade.dto.UserDto;
import main.java.com.upgrade.model.User;
import main.java.com.upgrade.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@Component
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Upgrade project Doc")
public class ResourceTest {
	
	@Autowired
	UserService userService;
	
	@GET
	@RequestMapping("/isalive")
	@Path("/isalive")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "isALive", notes = "Checking Upgrade API is Up and Running")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Upgrade API Services is Running!!!!"),
			@ApiResponse(code = 500, message = "Internal servererror") })

	public IsAliveDto isAlive() {
		System.out.println("User Service "+userService);
		IsAliveDto isAliveDto = new IsAliveDto();
		isAliveDto.setResponseCode("200");
		isAliveDto.setResponseDesc("Application is Running!!!!");
		return isAliveDto;

	}
	
	@POST
	@RequestMapping("/reserve")
	@Path("/reservebooking")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "reserve", notes = "Reserve booking")
	@ApiResponses(value= {@ApiResponse(code = 200, message= "OK",response = BookingMessageDto.class)})
	
	public BookingMessageDto reserveBooking(UserDto user) {
		BookingMessageDto  bookingMessage =  new BookingMessageDto();
		User u = new User(user.getFullName(), user.getArrivalDate(), user.getDepartureDate(), user.getEmail());
		String message = userService.reserve(u);
		bookingMessage.setMessage(message);
		return bookingMessage;
		
	}
	
	@POST
	@RequestMapping("/cancelbooking")
	@Path("/cancelbooking")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "cancel", notes = "Cancel booking")
	@ApiResponses(value= {@ApiResponse(code = 200, message= "OK",response = BookingMessageDto.class)})
	
	public BookingMessageDto cancelBooking(String bookingReference) {
		BookingMessageDto  bookingMessage =  new BookingMessageDto();
		System.out.println("Booking Reference "+ bookingReference);
		String message = userService.cancelBooking(bookingReference);
		bookingMessage.setMessage(message);
		return bookingMessage;
		
	}
	
	@POST
	@RequestMapping("/updatebooking")
	@Path("/updatebooking")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "update", notes = "Update booking")
	@ApiResponses(value= {@ApiResponse(code = 200, message= "OK",response = BookingMessageDto.class)})
	
	public BookingMessageDto updateBooking(UpdateBookingDto updateBooking) {
		BookingMessageDto  bookingMessage =  new BookingMessageDto();
		String message = userService.updateBooking(updateBooking.getBookingReference(),updateBooking.getArrivalDate(),updateBooking.getDepartureDate());
		bookingMessage.setMessage(message);
		return bookingMessage;
		
	}
	
	@GET
	@RequestMapping("/availabledates")
	@Path("/availabledates")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "availableDates", notes = "Get Available Dates")
	@ApiResponses(value = { @ApiResponse(code = 200,response = BookingMessageDto.class, message = "Available Dates")})
			

	public AvailableDatesDto getAVailableDates() {
		List<Date> dates = userService.getAvailableDates();
		AvailableDatesDto datesDto = new AvailableDatesDto();
		datesDto.setAvailableDates(dates);
		return datesDto;
		
		

	}

}

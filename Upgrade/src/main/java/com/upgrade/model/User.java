package main.java.com.upgrade.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;

@Entity
@Table(name="user")
public class User {
	
	public int getIdUser_Details() {
		return idUser_Details;
	}
	public void setIdUser_Details(int idUser_Details) {
		this.idUser_Details = idUser_Details;
	}
	public String getUser_Name() {
		return user_Name;
	}
	public void setUser_Name(String user_Name) {
		this.user_Name = user_Name;
	}
	public String getUser_Email() {
		return user_Email;
	}
	public void setUser_Email(String user_Email) {
		this.user_Email = user_Email;
	}
	public String getBooking_Reference() {
		return booking_Reference;
	}
	public void setBooking_Reference(String booking_Reference) {
		this.booking_Reference = booking_Reference;
	}
	public Date getArrival_Date() {
		return arrival_Date;
	}
	public void setArrival_Date(Date arrival_Date) {
		this.arrival_Date = arrival_Date;
	}
	public Date getDeparture_Date() {
		return departure_Date;
	}
	public void setDeparture_Date(Date departure_Date) {
		this.departure_Date = departure_Date;
	}
	@Id
	@Column(name= "idUser_Details")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idUser_Details;
	private String user_Name;
	private String user_Email;
	private String booking_Reference;
	private Date arrival_Date;
	private Date departure_Date;
	
	public String toString() {
		
	return "id: "+idUser_Details+" user name: "+user_Name;
	}
	public User(String user_Name, Date arrival_Date, Date departure_Date, String user_Email) {
		super();
		this.user_Name = user_Name;
		this.arrival_Date = arrival_Date;
		this.departure_Date = departure_Date;
		this.user_Email = user_Email;
	}
	
	public User() {
		
	}

}

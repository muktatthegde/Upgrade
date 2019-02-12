package main.java.com.upgrade.dao;

import java.sql.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.object.SqlQuery;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import main.java.com.upgrade.model.User;
@Service("UserDao")
public class UserDaoImpl implements UserDao {
	@Autowired
 SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	public void save(User u) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(u);
		tx.commit();
		session.close();

	}
@SuppressWarnings("unchecked")

	public List<User> list() {
		Session session = this.sessionFactory.openSession();
		List<User> userList = session.createQuery("from User").list();
		session.close();
		return userList;
	}


	public String getBookingReference(Date arrivalDate, Date departureDate) {
		
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		SQLQuery query =session.createSQLQuery("SELECT  `user`.`booking_Reference` FROM UPGArDE.USER  where  `user`.`arrival_Date`\r\n" + 
				"between :arrivalDate and :departureDate");
		
		query.setDate("arrivalDate", arrivalDate);
		query.setDate("departureDate", departureDate);
		
		
		List<String> referenceIds = query.list();
		session.close();
		if(referenceIds.size() >0 && referenceIds != null) {
			return "Dates not available for booking";
		}
		
		return "You can book";
		
	}
	public void deleteEntry(String bookingReference) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		SQLQuery query = session.createSQLQuery("delete from upgarde.user where user.booking_Reference =:bookingReference");
		query.setString("bookingReference", bookingReference);
		
		System.out.println("Delete query is "+query.getQueryString());
		int count = query.executeUpdate();
		System.out.println("Delete query count is "+count);
		tx.commit();
		session.close();
	}
	public User getUser(String bookingReference) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		User user = new User();
		ModelMap mapper = new ModelMap();
		SQLQuery query =session.createSQLQuery("SELECT * FROM UPGArDE.USER  where  `user`.`booking_Reference` =:bookingReference");
		query.addEntity(User.class);
		query.setString("bookingReference", bookingReference);
		
		System.out.println("query is "+ query.getQueryString());
		List users = query.list();
		for (Iterator iterator = users.iterator(); iterator.hasNext();) {
			user = (User) iterator.next();
			
		}
		session.close();
		return user ;
	}
	public void updateDates(Date arrivalDate, Date departureDate,String bookingReference) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		SQLQuery query =session.createSQLQuery("update `upgarde`.`user`\r\n" + 
				"set `user`.`arrival_Date` =:arrivalDate,\r\n" + 
				"`user`.`departure_Date` = :departureDate where `user`.`booking_Reference`=:bookingReference");
		query.setDate("arrivalDate", arrivalDate);
		query.setDate("departureDate", departureDate);
		query.setString("bookingReference", bookingReference);
		query.executeUpdate();
		tx.commit();
		session.close();
		
	}
	@Override
	public List<Date> bookedDates() {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		SQLQuery query =session.createSQLQuery("SELECT   `user`.`arrival_Date` FROM UPGArDE.USER  where  `user`.`arrival_Date`> curdate()");
		
		List<Date> bookedDates = query.list();
		
		session.close();
		return bookedDates;
	}


}

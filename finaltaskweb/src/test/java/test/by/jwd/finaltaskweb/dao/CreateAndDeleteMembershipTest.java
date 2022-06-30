package test.by.jwd.finaltaskweb.dao;

import static org.testng.Assert.assertEquals;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import by.jwd.finaltaskweb.dao.DaoException;
import by.jwd.finaltaskweb.dao.DaoFactory;
import by.jwd.finaltaskweb.dao.pool.ConnectionPool;
import by.jwd.finaltaskweb.entity.Client;
import by.jwd.finaltaskweb.entity.Membership;
import by.jwd.finaltaskweb.entity.MembershipType;

public class CreateAndDeleteMembershipTest {
	
	static Logger logger = LogManager.getLogger(CreateAndDeleteMembershipTest.class);

	@BeforeTest
	public void beforeTest() {

		ResourceBundle resourceBundle = ResourceBundle.getBundle("testdatabase");

		String driverClass = resourceBundle.getString("db.driver");
		String url = resourceBundle.getString("db.url");
		String user = resourceBundle.getString("user");
		String password = resourceBundle.getString("password");
		int startSize = Integer.parseInt(resourceBundle.getString("startsize"));
		int maxSize = Integer.parseInt(resourceBundle.getString("maxsize"));
		int checkConnectionTimeout = Integer.parseInt(resourceBundle.getString("checkConnectionTimeout"));

		try {
			ConnectionPool.getInstance().init(driverClass, url, user, password, startSize, maxSize,
					checkConnectionTimeout);
		} catch (DaoException e) {
			logger.debug("connection error");
		}
	}

	@Test(description = "createMembership")

	public void testCreateMembership() throws DaoException {

		List <Membership> expected = new ArrayList<>();
		
		Membership membership1 = new Membership (1);
		membership1.setClient(new Client(5));
		membership1.setType(new MembershipType(1));
		membership1.setStartDate(LocalDate.of(2012, Month.MARCH, 20));
		membership1.setEndDate(LocalDate.of(2013, Month.MARCH, 20));
		membership1.setBalanceClassQuantity(0);
		
		Membership membership2 = new Membership (2);
		membership2.setClient(new Client(5));
		membership2.setType(new MembershipType(1));
		membership2.setStartDate(LocalDate.of(2022, Month.MARCH, 12));
		membership2.setEndDate(LocalDate.of(2022, Month.MARCH, 13));
		membership2.setBalanceClassQuantity(0);
		
		
		Membership membership3 = new Membership (3);
		membership3.setClient(new Client(6));
		membership3.setType(new MembershipType(2));
		membership3.setStartDate(LocalDate.of(2022, Month.APRIL, 01));
		membership3.setEndDate(LocalDate.of(2022, Month.MAY, 01));
		membership3.setBalanceClassQuantity(4);
		
		Membership membership4 = new Membership (4);
		membership4.setClient(new Client(6));
		membership4.setType(new MembershipType(1));
		membership4.setStartDate(LocalDate.of(2022, Month.MARCH, 28));
		membership4.setEndDate(LocalDate.of(2022, Month.MARCH, 29));
		membership4.setBalanceClassQuantity(1);
		
		Membership membership5 = new Membership (5);
		membership5.setClient(new Client(9));
		membership5.setType(new MembershipType(4));
		membership5.setStartDate(LocalDate.of(2022, Month.APRIL, 10));
		membership5.setEndDate(LocalDate.of(2022, Month.MAY, 10));
		membership5.setBalanceClassQuantity(16);
		
		Membership membership6 = new Membership (6);
		membership6.setClient(new Client(8));
		membership6.setType(new MembershipType(5));
		membership6.setStartDate(LocalDate.of(2022, Month.APRIL, 10));
		membership6.setEndDate(LocalDate.of(2022, Month.MAY, 10));
		
		
		Membership membership7 = new Membership (7);
		membership7.setClient(new Client(7));
		membership7.setType(new MembershipType(3));
		membership7.setStartDate(LocalDate.of(2022, Month.APRIL, 07));
		membership7.setEndDate(LocalDate.of(2022, Month.MAY, 07));
		membership7.setBalanceClassQuantity(8);
		
		Membership membership8 = new Membership (12);//need to be updated
		membership8.setClient(new Client(9));
		membership8.setType(new MembershipType(4));
		membership8.setStartDate(LocalDate.of(2022, Month.APRIL, 11));
		membership8.setEndDate(LocalDate.of(2022, Month.MAY, 12));
		membership8.setBalanceClassQuantity(8);
	
		expected.add(membership1);
		expected.add(membership2);
		expected.add(membership3);
		expected.add(membership4);
		expected.add(membership5);
		expected.add(membership6);
		expected.add(membership7);
		expected.add(membership8);
	

		Connection connection = ConnectionPool.getInstance().getConnection();
		DaoFactory factory = new DaoFactory (connection);
		
		factory.getMembershipDao().create(membership8);
		List<Membership> actual = factory.getMembershipDao().readAll();
		
		logger.debug("actual {}", actual);
		assertEquals(actual, expected);
	}

				
	@Test(dependsOnMethods = "testCreateMembership")

	public void testDeleteMembership() throws DaoException {
		
		List <Membership> expected = new ArrayList<>();
		
		Membership membership1 = new Membership (1);
		membership1.setClient(new Client(5));
		membership1.setType(new MembershipType(1));
		membership1.setStartDate(LocalDate.of(2012, Month.MARCH, 20));
		membership1.setEndDate(LocalDate.of(2013, Month.MARCH, 20));
		membership1.setBalanceClassQuantity(0);
		
		Membership membership2 = new Membership (2);
		membership2.setClient(new Client(5));
		membership2.setType(new MembershipType(1));
		membership2.setStartDate(LocalDate.of(2022, Month.MARCH, 12));
		membership2.setEndDate(LocalDate.of(2022, Month.MARCH, 13));
		membership2.setBalanceClassQuantity(0);
		
		
		Membership membership3 = new Membership (3);
		membership3.setClient(new Client(6));
		membership3.setType(new MembershipType(2));
		membership3.setStartDate(LocalDate.of(2022, Month.APRIL, 01));
		membership3.setEndDate(LocalDate.of(2022, Month.MAY, 01));
		membership3.setBalanceClassQuantity(4);
		
		Membership membership4 = new Membership (4);
		membership4.setClient(new Client(6));
		membership4.setType(new MembershipType(1));
		membership4.setStartDate(LocalDate.of(2022, Month.MARCH, 28));
		membership4.setEndDate(LocalDate.of(2022, Month.MARCH, 29));
		membership4.setBalanceClassQuantity(1);
		
		Membership membership5 = new Membership (5);
		membership5.setClient(new Client(9));
		membership5.setType(new MembershipType(4));
		membership5.setStartDate(LocalDate.of(2022, Month.APRIL, 10));
		membership5.setEndDate(LocalDate.of(2022, Month.MAY, 10));
		membership5.setBalanceClassQuantity(16);
		
		Membership membership6 = new Membership (6);
		membership6.setClient(new Client(8));
		membership6.setType(new MembershipType(5));
		membership6.setStartDate(LocalDate.of(2022, Month.APRIL, 10));
		membership6.setEndDate(LocalDate.of(2022, Month.MAY, 10));
		
		
		Membership membership7 = new Membership (7);
		membership7.setClient(new Client(7));
		membership7.setType(new MembershipType(3));
		membership7.setStartDate(LocalDate.of(2022, Month.APRIL, 07));
		membership7.setEndDate(LocalDate.of(2022, Month.MAY, 07));
		membership7.setBalanceClassQuantity(8);
							
		expected.add(membership1);
		expected.add(membership2);
		expected.add(membership3);
		expected.add(membership4);
		expected.add(membership5);
		expected.add(membership6);
		expected.add(membership7);
		
		Connection connection = ConnectionPool.getInstance().getConnection();
		DaoFactory factory = new DaoFactory (connection);
		
		List<Membership> all = factory.getMembershipDao().readAll();
		Membership lastAdded = all.get(all.size()-1);
		factory.getMembershipDao().delete(lastAdded.getId());
		List<Membership> actual = factory.getMembershipDao().readAll();
		assertEquals(actual, expected);
	}
}
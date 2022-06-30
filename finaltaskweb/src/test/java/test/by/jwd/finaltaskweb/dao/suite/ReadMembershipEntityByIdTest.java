package test.by.jwd.finaltaskweb.dao.suite;

import static org.testng.Assert.assertEquals;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import by.jwd.finaltaskweb.dao.DaoException;
import by.jwd.finaltaskweb.dao.DaoFactory;
import by.jwd.finaltaskweb.dao.pool.ConnectionPool;
import by.jwd.finaltaskweb.entity.Client;
import by.jwd.finaltaskweb.entity.Membership;
import by.jwd.finaltaskweb.entity.MembershipType;



public class ReadMembershipEntityByIdTest {
	
	static Logger logger = LogManager.getLogger(ReadMembershipEntityByIdTest.class);

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

	@DataProvider(name = "readById")
	public Object[][] createDataForReadById() {

		Membership membership3 = new Membership (3);
		membership3.setClient(new Client(7));
		membership3.setType(new MembershipType(2));
		membership3.setStartDate(LocalDate.of(2022, Month.APRIL, 01));
		membership3.setEndDate(LocalDate.of(2022, Month.MAY, 01));
		membership3.setBalanceClassQuantity(4);
		
	
		Membership membership5 = new Membership (5);
		membership5.setClient(new Client(10));
		membership5.setType(new MembershipType(4));
		membership5.setStartDate(LocalDate.of(2022, Month.APRIL, 10));
		membership5.setEndDate(LocalDate.of(2022, Month.MAY, 10));
		membership5.setBalanceClassQuantity(16);
		
		
		Membership membership7 = new Membership (7);
		membership7.setClient(new Client(9));
		membership7.setType(new MembershipType(3));
		membership7.setStartDate(LocalDate.of(2022, Month.APRIL, 07));
		membership7.setEndDate(LocalDate.of(2022, Month.MAY, 07));
		membership7.setBalanceClassQuantity(8);
	
		
		return new Object[][] {
				{ 3, membership3 },
				{ 5, membership5 },
				{ 7, membership7 } };

	}

	@Test(groups = { "dao" }, dataProvider = "readById")

	public void testReadById(int id, Membership expected) throws DaoException {

		Connection connection = ConnectionPool.getInstance().getConnection();
		DaoFactory factory = new DaoFactory (connection);
		Membership actual = factory.getMembershipDao().readEntityById(id);
		assertEquals(actual, expected);
	}
}
package test.by.jwd.finaltaskweb.dao.suite;

import static org.testng.Assert.assertEquals;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import by.jwd.finaltaskweb.dao.DaoException;
import by.jwd.finaltaskweb.dao.DaoFactory;
import by.jwd.finaltaskweb.dao.TransactionImpl;
import by.jwd.finaltaskweb.dao.pool.ConnectionPool;
import by.jwd.finaltaskweb.entity.DanceClass;
import by.jwd.finaltaskweb.entity.Membership;
import by.jwd.finaltaskweb.entity.Status;
import by.jwd.finaltaskweb.entity.Visit;

public class ReadAllVisitTest {
	
	static Logger logger = LogManager.getLogger(ReadAllVisitTest.class);

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

	@Test(description = "readAllVisit", groups = { "dao" })
	
	public void testReadAllVisit() throws DaoException {
		
		List <Visit> expected = new ArrayList<>();
		
		Visit visit1 = new Visit(1);
		visit1.setDanceClass(new DanceClass(1));
		visit1.setMembership(new Membership(1));
		visit1.setStatus(Status.ATTENDED);
		
		Visit visit2 = new Visit(2);
		visit2.setDanceClass(new DanceClass(2));
		visit2.setMembership(new Membership(2));
		visit2.setStatus(Status.ABSENT);
		
		Visit visit3 = new Visit(3);
		visit3.setDanceClass(new DanceClass(14));
		visit3.setMembership(new Membership(5));
		visit3.setStatus(Status.PLANNED);
		
		Visit visit4 = new Visit(4);
		visit4.setDanceClass(new DanceClass(12));
		visit4.setMembership(new Membership(3));
		visit4.setStatus(Status.PLANNED);
		
		Visit visit5 = new Visit(6);
		visit5.setDanceClass(new DanceClass(14));
		visit5.setMembership(new Membership(6));
		visit5.setStatus(Status.PLANNED);
		
	
		expected.add(visit1);
		expected.add(visit2);
		expected.add(visit3);
		expected.add(visit4);
		expected.add(visit5);
					
		Connection connection = ConnectionPool.getInstance().getConnection();
		TransactionImpl transaction = new TransactionImpl(connection);
		DaoFactory factory = DaoFactory.getInstance();
		List <Visit> actual = factory.getVisitDao(transaction).readAll();
		assertEquals(actual, expected);
	}
}
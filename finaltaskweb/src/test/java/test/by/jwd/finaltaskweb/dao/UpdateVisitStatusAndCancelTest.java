package test.by.jwd.finaltaskweb.dao;

import static org.testng.Assert.assertEquals;

import java.sql.Connection;
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

public class UpdateVisitStatusAndCancelTest {

	static Logger logger = LogManager.getLogger(UpdateVisitStatusAndCancelTest.class);

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

	@Test(description = "updateStatus")

	public void testUpdateStatusToAttended() throws DaoException {

		Visit visitOld = new Visit(4);
		visitOld.setDanceClass(new DanceClass(12));
		visitOld.setMembership(new Membership(3));
		visitOld.setStatus(Status.PLANNED);
		
		Visit visitNew = new Visit(4);
		visitNew.setDanceClass(new DanceClass(12));
		visitNew.setMembership(new Membership(3));
		visitNew.setStatus(Status.ATTENDED);

		Connection connection = ConnectionPool.getInstance().getConnection();
		TransactionImpl transaction = new TransactionImpl(connection);
		DaoFactory factory = DaoFactory.getInstance();
		
		factory.getVisitDao(transaction).updateStatus(visitOld, Status.ATTENDED);
		Visit  actual = factory.getVisitDao(transaction).readEntityById(4);
		logger.debug(actual);
		assertEquals(actual, visitNew);
	}
	@Test(dependsOnMethods = "testUpdateStatusToAttended")

	public void testCancelUpdateStatus() throws DaoException {

		Visit visitOld = new Visit(4);
		visitOld.setDanceClass(new DanceClass(12));
		visitOld.setMembership(new Membership(3));
		visitOld.setStatus(Status.ATTENDED);
		
		Visit visitNew = new Visit(4);
		visitNew.setDanceClass(new DanceClass(12));
		visitNew.setMembership(new Membership(3));
		visitNew.setStatus(Status.PLANNED);

		Connection connection = ConnectionPool.getInstance().getConnection();
		TransactionImpl transaction = new TransactionImpl(connection);
		DaoFactory factory = DaoFactory.getInstance();
		
		factory.getVisitDao(transaction).cancelUpdateStatus(visitOld);
		Visit  actual = factory.getVisitDao(transaction).readEntityById(4);
		logger.debug(actual);
		assertEquals(actual, visitNew);
	}
	
	@Test(description = "updateStatusToAbsent")

	public void testUpdateStatusToAbsent() throws DaoException {

		Visit visitOld = new Visit(3);
		visitOld.setDanceClass(new DanceClass(14));
		visitOld.setMembership(new Membership(5));
		visitOld.setStatus(Status.PLANNED);
		
		Visit visitNew = new Visit(3);
		visitNew.setDanceClass(new DanceClass(14));
		visitNew.setMembership(new Membership(5));
		visitNew.setStatus(Status.ABSENT);

		Connection connection = ConnectionPool.getInstance().getConnection();
		TransactionImpl transaction = new TransactionImpl(connection);
		DaoFactory factory = DaoFactory.getInstance();
		
		factory.getVisitDao(transaction).updateStatus(visitOld, Status.ABSENT);
		Visit  actual = factory.getVisitDao(transaction).readEntityById(3);
		logger.debug(actual);
		assertEquals(actual, visitNew);
	}
	@Test(dependsOnMethods = "testUpdateStatusToAbsent")

	public void testCancelUpdateStatus2() throws DaoException {

		Visit visitOld = new Visit(3);
		visitOld.setDanceClass(new DanceClass(14));
		visitOld.setMembership(new Membership(5));
		visitOld.setStatus(Status.ABSENT);
		
		Visit visitNew = new Visit(3);
		visitNew.setDanceClass(new DanceClass(14));
		visitNew.setMembership(new Membership(5));
		visitNew.setStatus(Status.PLANNED);

		Connection connection = ConnectionPool.getInstance().getConnection();
		TransactionImpl transaction = new TransactionImpl(connection);
		DaoFactory factory = DaoFactory.getInstance();
		factory.getVisitDao(transaction).cancelUpdateStatus(visitOld);
		Visit  actual = factory.getVisitDao(transaction).readEntityById(3);
		logger.debug(actual);
		assertEquals(actual, visitNew);
	}	
}
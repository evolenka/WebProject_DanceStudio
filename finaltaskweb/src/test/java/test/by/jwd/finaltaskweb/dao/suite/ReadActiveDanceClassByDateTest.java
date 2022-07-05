package test.by.jwd.finaltaskweb.dao.suite;

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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import by.jwd.finaltaskweb.dao.DaoException;
import by.jwd.finaltaskweb.dao.DaoFactory;
import by.jwd.finaltaskweb.dao.TransactionImpl;
import by.jwd.finaltaskweb.dao.pool.ConnectionPool;
import by.jwd.finaltaskweb.entity.DanceClass;
import by.jwd.finaltaskweb.entity.Schedule;

public class ReadActiveDanceClassByDateTest {
	
	static Logger logger = LogManager.getLogger(ReadActiveDanceClassByDateTest.class);

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

	@DataProvider(name = "ReadActiveByDate")
	public Object[][] createDataForReadActiveByDate() {
		
	List <DanceClass> expected1 = new ArrayList<>();
	List <DanceClass> expected2 = new ArrayList<>();
	List <DanceClass> expected3 = new ArrayList<>();
		
				
	DanceClass danceClass11 = new DanceClass (12);
	danceClass11.setSchedule(new Schedule(1));
	danceClass11.setDate(LocalDate.of(2022, Month.APRIL, 11));
	danceClass11.setActive(true);
	
	DanceClass danceClass12 = new DanceClass (14);
	danceClass12.setSchedule(new Schedule(3));
	danceClass12.setDate(LocalDate.of(2022, Month.APRIL, 11));
	danceClass12.setActive(true);
	
	DanceClass danceClass13 = new DanceClass (15);
	danceClass13.setSchedule(new Schedule(4));
	danceClass13.setDate(LocalDate.of(2022, Month.APRIL, 12));
	danceClass13.setActive(true);	
	
	DanceClass danceClass14 = new DanceClass (16);
	danceClass14.setSchedule(new Schedule(5));
	danceClass14.setDate(LocalDate.of(2022, Month.APRIL, 12));
	danceClass14.setActive(true);	
	
	DanceClass danceClass15 = new DanceClass (17);
	danceClass15.setSchedule(new Schedule(6));
	danceClass15.setDate(LocalDate.of(2022, Month.APRIL, 12));
	danceClass15.setActive(true);	
			
		expected1.add(danceClass11);
		expected1.add(danceClass12);
		expected2.add(danceClass13);
		expected2.add(danceClass14);
		expected2.add(danceClass15);
		
		LocalDate date1 = LocalDate.of(2022, 04, 11);
		LocalDate date2 = LocalDate.of(2022, 04, 12);
		LocalDate date3 = LocalDate.of(2022, 03, 15);
		
			
		return new Object[][] { { date1, expected1 }, { date2, expected2 },  { date3, expected3 }};
	}

	@Test(groups = { "dao" }, dataProvider = "ReadActiveByDate")

	public void testReadActiveByDate(LocalDate date, List <DanceClass> expected) throws DaoException {

		Connection connection = ConnectionPool.getInstance().getConnection();
		TransactionImpl transaction = new TransactionImpl(connection);
		DaoFactory factory = DaoFactory.getInstance();
		
		List <DanceClass>  actual = factory.getDanceClassDao(transaction).readActiveByDate (date);
		assertEquals(actual, expected);
	}
}
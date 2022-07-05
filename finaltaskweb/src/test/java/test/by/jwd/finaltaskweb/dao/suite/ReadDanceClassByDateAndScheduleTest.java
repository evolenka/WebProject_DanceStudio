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
import by.jwd.finaltaskweb.dao.TransactionImpl;
import by.jwd.finaltaskweb.dao.pool.ConnectionPool;
import by.jwd.finaltaskweb.entity.DanceClass;
import by.jwd.finaltaskweb.entity.Schedule;

public class ReadDanceClassByDateAndScheduleTest {
	
	static Logger logger = LogManager.getLogger(ReadDanceClassByDateAndScheduleTest.class);

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

	@DataProvider(name = "ReadByDateAndSchedule")
	public Object[][] createDataForReadDateAndSchedule() {
		
			
	DanceClass danceClass6 = new DanceClass (6);
	danceClass6.setSchedule(new Schedule(7));
	danceClass6.setDate(LocalDate.of(2022, Month.MARCH, 9));
	danceClass6.setActive(false);
	
	DanceClass danceClass0 = new DanceClass();
	
	DanceClass danceClass9 = new DanceClass (10);
	danceClass9.setSchedule(new Schedule(4));
	danceClass9.setDate(LocalDate.of(2022, Month.MARCH, 15));
	danceClass9.setActive(false);
		
		LocalDate date1 = LocalDate.of(2022, 03, 9);
		LocalDate date2 = LocalDate.of(2022, 03, 15);
		LocalDate date3 = LocalDate.of(2022, 03, 16);
		
			
		return new Object[][] { { new Schedule(7), date1, danceClass6 }, { new Schedule(10), date3, danceClass0 }, { new Schedule(4), date2, danceClass9}};
	}

	@Test(groups = { "dao" }, dataProvider = "ReadByDateAndSchedule")

	public void testReadByDateAndSchedule(Schedule schedule, LocalDate date, DanceClass expected) throws DaoException {

		Connection connection = ConnectionPool.getInstance().getConnection();
		TransactionImpl transaction = new TransactionImpl(connection);
		DaoFactory factory = DaoFactory.getInstance();
		DanceClass  actual = factory.getDanceClassDao(transaction).readByDateAndSchedule (date, schedule);
		assertEquals(actual, expected);
	}
}
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
import by.jwd.finaltaskweb.entity.DanceClass;
import by.jwd.finaltaskweb.entity.Schedule;

public class ReadDanceClassByIdTest {

	private static Logger logger = LogManager.getLogger(ReadDanceClassByIdTest.class);

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

	@DataProvider(name = "ReadDanceClassById")
	public Object[][] createDataForReadById() {

		DanceClass danceClass6 = new DanceClass(6);
		danceClass6.setSchedule(new Schedule(7));
		danceClass6.setDate(LocalDate.of(2022, Month.MARCH, 9));
		danceClass6.setActive(false);

		DanceClass danceClass9 = new DanceClass(10);
		danceClass9.setSchedule(new Schedule(4));
		danceClass9.setDate(LocalDate.of(2022, Month.MARCH, 15));
		danceClass9.setActive(false);

		DanceClass danceClass3 = new DanceClass(3);
		danceClass3.setSchedule(new Schedule(6));
		danceClass3.setDate(LocalDate.of(2022, Month.MARCH, 01));
		danceClass3.setActive(false);

		 DanceClass danceClass5 = new DanceClass(5);
		danceClass5.setSchedule(new Schedule(8));
		danceClass5.setDate(LocalDate.of(2022, Month.MARCH, 02));
		danceClass5.setActive(false);

		return new Object[][] { { 6, danceClass6 }, { 10, danceClass9 }, { 5, danceClass5 }, { 3, danceClass3 } };
	}

	@Test(groups = { "dao" }, dataProvider = "ReadDanceClassById")

	public void testReadById(Integer id, DanceClass expected) throws DaoException {

		Connection connection = ConnectionPool.getInstance().getConnection();
		DaoFactory factory = new DaoFactory(connection);
		DanceClass actual = factory.getDanceClassDao().readEntityById(id);
		assertEquals(actual, expected);
	}
}
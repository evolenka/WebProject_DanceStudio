package test.by.jwd.finaltaskweb.dao.suite;

import static org.testng.Assert.assertEquals;

import java.sql.Connection;
import java.time.LocalTime;
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
import by.jwd.finaltaskweb.entity.Group;
import by.jwd.finaltaskweb.entity.Schedule;
import by.jwd.finaltaskweb.entity.WeekDay;

public class ReadScheduleEntityByIdTest {

	static Logger logger = LogManager.getLogger(ReadScheduleEntityByIdTest.class);

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

	@DataProvider(name = "ReadScheduleById")
	public Object[][] createDataForReadEntityById() {

		Schedule schedule3 = new Schedule(4);
		schedule3.setWeekDay(WeekDay.TUESDAY);
		schedule3.setTime(LocalTime.of(18, 00));
		schedule3.setDuration(60);
		schedule3.setGroup(new Group(3));

		Schedule schedule4 = new Schedule(5);
		schedule4.setWeekDay(WeekDay.WEDNESDAY);
		schedule4.setTime(LocalTime.of(18, 00));
		schedule4.setDuration(60);
		schedule4.setGroup(new Group(1));

		return new Object[][] { { 4, schedule3 }, { 5, schedule4 } };

	}

	@Test(groups = { "dao" }, dataProvider = "ReadScheduleById")

	public void testReadScheduleById(Integer id, Schedule schedule) throws DaoException {

		Schedule expected = schedule;
		Connection connection = ConnectionPool.getInstance().getConnection();
		TransactionImpl transaction = new TransactionImpl(connection);
		DaoFactory factory = DaoFactory.getInstance();
		Schedule actual = factory.getScheduleDao(transaction).readEntityById(id);
		assertEquals(actual, expected);
	}
}
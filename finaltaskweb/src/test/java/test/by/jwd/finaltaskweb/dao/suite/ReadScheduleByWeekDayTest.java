package test.by.jwd.finaltaskweb.dao.suite;

import static org.testng.Assert.assertEquals;

import java.sql.Connection;
import java.time.LocalTime;
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
import by.jwd.finaltaskweb.entity.Group;
import by.jwd.finaltaskweb.entity.Schedule;
import by.jwd.finaltaskweb.entity.WeekDay;

public class ReadScheduleByWeekDayTest {

	static Logger logger = LogManager.getLogger(ReadScheduleByWeekDayTest.class);

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

	@DataProvider(name = "ReadScheduleByWeekDay")
	public Object[][] createDataForReadScheduleByWeekDay() {

		List<Schedule> list1 = new ArrayList<>();
		List<Schedule> list2 = new ArrayList<>();

		Schedule schedule7 = new Schedule(8);
		schedule7.setWeekDay(WeekDay.FRIDAY);
		schedule7.setTime(LocalTime.of(18, 00));
		schedule7.setDuration(90);
		schedule7.setGroup(new Group(7));

		Schedule schedule4 = new Schedule(5);
		schedule4.setWeekDay(WeekDay.WEDNESDAY);
		schedule4.setTime(LocalTime.of(18, 00));
		schedule4.setDuration(60);
		schedule4.setGroup(new Group(1));

		Schedule schedule5 = new Schedule(6);
		schedule5.setWeekDay(WeekDay.WEDNESDAY);
		schedule5.setTime(LocalTime.of(19, 00));
		schedule5.setDuration(60);
		schedule5.setGroup(new Group(2));

		list1.add(schedule7);

		list2.add(schedule4);
		list2.add(schedule5);

		return new Object[][] { { WeekDay.WEDNESDAY, list2 }, { WeekDay.FRIDAY, list1 } };

	}

	@Test(groups = { "dao" }, dataProvider = "ReadScheduleByWeekDay")

	public void testReadScheduleById(WeekDay weekday, List<Schedule> schedule) throws DaoException {

		List<Schedule> expected = schedule;
		Connection connection = ConnectionPool.getInstance().getConnection();
		TransactionImpl transaction = new TransactionImpl(connection);
		DaoFactory factory = DaoFactory.getInstance();
		List<Schedule> actual = factory.getScheduleDao(transaction).readByWeekDay(weekday);
		assertEquals(actual, expected);
	}
}
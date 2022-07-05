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

public class ReadDanceClassByPeriodTest {

	private static Logger logger = LogManager.getLogger(ReadDanceClassByPeriodTest.class);

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

	@DataProvider(name = "ReadDanceClassByPeriod")
	public Object[][] createDataForReadByPeriod() {

		List<DanceClass> expected1 = new ArrayList<>();
		List<DanceClass> expected2 = new ArrayList<>();

		DanceClass danceClass1 = new DanceClass(1);
		danceClass1.setSchedule(new Schedule(4));
		danceClass1.setDate(LocalDate.of(2022, Month.MARCH, 01));
		danceClass1.setActive(false);

		DanceClass danceClass2 = new DanceClass(2);
		danceClass2.setSchedule(new Schedule(5));
		danceClass2.setDate(LocalDate.of(2022, Month.MARCH, 01));
		danceClass2.setActive(false);

		DanceClass danceClass3 = new DanceClass(3);
		danceClass3.setSchedule(new Schedule(6));
		danceClass3.setDate(LocalDate.of(2022, Month.MARCH, 01));
		danceClass3.setActive(false);

		DanceClass danceClass4 = new DanceClass(4);
		danceClass4.setSchedule(new Schedule(7));
		danceClass4.setDate(LocalDate.of(2022, Month.MARCH, 02));
		danceClass4.setActive(false);

		DanceClass danceClass5 = new DanceClass(5);
		danceClass5.setSchedule(new Schedule(8));
		danceClass5.setDate(LocalDate.of(2022, Month.MARCH, 02));
		danceClass5.setActive(false);

		DanceClass danceClass6 = new DanceClass(6);
		danceClass6.setSchedule(new Schedule(7));
		danceClass6.setDate(LocalDate.of(2022, Month.MARCH, 9));
		danceClass6.setActive(false);

		DanceClass danceClass7 = new DanceClass(8);
		danceClass7.setSchedule(new Schedule(1));
		danceClass7.setDate(LocalDate.of(2022, Month.MARCH, 14));
		danceClass7.setActive(false);

		DanceClass danceClass8 = new DanceClass(9);
		danceClass8.setSchedule(new Schedule(3));
		danceClass8.setDate(LocalDate.of(2022, Month.MARCH, 14));
		danceClass8.setActive(false);

		DanceClass danceClass9 = new DanceClass(10);
		danceClass9.setSchedule(new Schedule(4));
		danceClass9.setDate(LocalDate.of(2022, Month.MARCH, 15));
		danceClass9.setActive(false);

		DanceClass danceClass10 = new DanceClass(11);
		danceClass10.setSchedule(new Schedule(5));
		danceClass10.setDate(LocalDate.of(2022, Month.MARCH, 15));
		danceClass10.setActive(false);

		DanceClass danceClass11 = new DanceClass(12);
		danceClass11.setSchedule(new Schedule(1));
		danceClass11.setDate(LocalDate.of(2022, Month.APRIL, 11));
		danceClass11.setActive(true);

		DanceClass danceClass12 = new DanceClass(14);
		danceClass12.setSchedule(new Schedule(3));
		danceClass12.setDate(LocalDate.of(2022, Month.APRIL, 11));
		danceClass12.setActive(true);

		DanceClass danceClass13 = new DanceClass(15);
		danceClass13.setSchedule(new Schedule(4));
		danceClass13.setDate(LocalDate.of(2022, Month.APRIL, 12));
		danceClass13.setActive(true);

		DanceClass danceClass14 = new DanceClass(16);
		danceClass14.setSchedule(new Schedule(5));
		danceClass14.setDate(LocalDate.of(2022, Month.APRIL, 12));
		danceClass14.setActive(true);

		DanceClass danceClass15 = new DanceClass(17);
		danceClass15.setSchedule(new Schedule(6));
		danceClass15.setDate(LocalDate.of(2022, Month.APRIL, 12));
		danceClass15.setActive(true);

		expected1.add(danceClass1);
		expected1.add(danceClass2);
		expected1.add(danceClass3);
		expected1.add(danceClass4);
		expected1.add(danceClass5);
		expected1.add(danceClass6);
		expected1.add(danceClass7);
		expected1.add(danceClass8);
		expected1.add(danceClass9);
		expected1.add(danceClass10);
		expected2.add(danceClass11);
		expected2.add(danceClass12);
		expected2.add(danceClass13);
		expected2.add(danceClass14);
		expected2.add(danceClass15);

		LocalDate startdate1 = LocalDate.of(2022, 03, 01);
		LocalDate enddate1 = LocalDate.of(2022, 03, 31);

		LocalDate startdate2 = LocalDate.of(2022, 04, 01);
		LocalDate enddate2 = LocalDate.of(2022, 04, 30);

		return new Object[][] { { startdate1, enddate1, expected1 }, { startdate2, enddate2, expected2 } };
	}

	@Test(groups = { "dao" }, dataProvider = "ReadDanceClassByPeriod")

	public void testReadDanceClassByPeriod(LocalDate startdate, LocalDate enddate, List<DanceClass> expected)
			throws DaoException {

		Connection connection = ConnectionPool.getInstance().getConnection();
		TransactionImpl transaction = new TransactionImpl(connection);
		DaoFactory factory = DaoFactory.getInstance();
		List <DanceClass> actual = factory.getDanceClassDao(transaction).readByPeriod(startdate, enddate);
		assertEquals(actual, expected);
	}
}
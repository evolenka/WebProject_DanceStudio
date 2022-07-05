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

public class ReadDanceClassByScheduleTest {
	
	static Logger logger = LogManager.getLogger(ReadDanceClassByScheduleTest.class);

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

	@DataProvider(name = "ReadBySchedule")
	public Object[][] createDataForReadBychedule() {
		
	List <DanceClass> expected1 = new ArrayList<>();
	List <DanceClass> expected2 = new ArrayList<>();
		
				
	DanceClass danceClass1 = new DanceClass (1);
	danceClass1.setSchedule(new Schedule(4));
	danceClass1.setDate(LocalDate.of(2022, Month.MARCH, 01));
	danceClass1.setActive(false);
	
		
	DanceClass danceClass5 = new DanceClass (5);
	danceClass5.setSchedule(new Schedule(8));
	danceClass5.setDate(LocalDate.of(2022, Month.MARCH, 02));
	danceClass5.setActive(false);
	
	
	DanceClass danceClass9 = new DanceClass (10);
	danceClass9.setSchedule(new Schedule(4));
	danceClass9.setDate(LocalDate.of(2022, Month.MARCH, 15));
	danceClass9.setActive(false);
	
	DanceClass danceClass10 = new DanceClass (11);
	danceClass10.setSchedule(new Schedule(5));
	danceClass10.setDate(LocalDate.of(2022, Month.MARCH, 15));
	danceClass10.setActive(false);
	
		DanceClass danceClass13 = new DanceClass (15);
	danceClass13.setSchedule(new Schedule(4));
	danceClass13.setDate(LocalDate.of(2022, Month.APRIL, 12));
	danceClass13.setActive(true);	
	
		
		expected1.add(danceClass1);
		expected1.add(danceClass9);
		expected1.add(danceClass13);
		
		expected2.add(danceClass5);
			
		return new Object[][] { { new Schedule(4), expected1 }, { new Schedule(8), expected2 }};
	}

	@Test(groups = { "dao" }, dataProvider = "ReadBySchedule")

	public void testReadBySchedule(Schedule schedule, List <DanceClass> expected) throws DaoException {

		Connection connection = ConnectionPool.getInstance().getConnection();
		TransactionImpl transaction = new TransactionImpl(connection);
		DaoFactory factory = DaoFactory.getInstance();
		
		List <DanceClass>  actual = factory.getDanceClassDao(transaction).readBySchedule (schedule);
		assertEquals(actual, expected);
	}
}
package test.by.jwd.finaltaskweb.dao;

import static org.testng.Assert.assertEquals;

import java.sql.Connection;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import by.jwd.finaltaskweb.dao.DaoException;
import by.jwd.finaltaskweb.dao.DaoFactory;
import by.jwd.finaltaskweb.dao.pool.ConnectionPool;
import by.jwd.finaltaskweb.entity.Group;
import by.jwd.finaltaskweb.entity.Schedule;
import by.jwd.finaltaskweb.entity.WeekDay;

public class CreateAndDeleteScheduleTest {
	
	static Logger logger = LogManager.getLogger(CreateAndDeleteScheduleTest.class);

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

	@Test(description = "createSchedule")

	public void testCreateSchedule() throws DaoException {

List <Schedule> expected = new ArrayList<>();
		
		Schedule schedule1 = new Schedule (1);
		schedule1.setWeekDay(WeekDay.MONDAY);
		schedule1.setTime(LocalTime.of(18,00));
		schedule1.setDuration(60);
		schedule1.setGroup (new Group (1));
		
		Schedule schedule2 = new Schedule (3);
		schedule2.setWeekDay(WeekDay.MONDAY);
		schedule2.setTime(LocalTime.of(19,00));
		schedule2.setDuration(60);
		schedule2.setGroup (new Group (2));
	
		Schedule schedule3 = new Schedule (4);
		schedule3.setWeekDay(WeekDay.TUESDAY);
		schedule3.setTime(LocalTime.of(18,00));
		schedule3.setDuration(60);
		schedule3.setGroup (new Group (3));
		
		Schedule schedule4 = new Schedule (5);
		schedule4.setWeekDay(WeekDay.WEDNESDAY);
		schedule4.setTime(LocalTime.of(18,00));
		schedule4.setDuration(60);
		schedule4.setGroup (new Group (1));
		
		Schedule schedule5 = new Schedule (6);
		schedule5.setWeekDay(WeekDay.WEDNESDAY);
		schedule5.setTime(LocalTime.of(19,00));
		schedule5.setDuration(60);
		schedule5.setGroup (new Group (2));
		
		Schedule schedule6 = new Schedule (7);
		schedule6.setWeekDay(WeekDay.THURSDAY);
		schedule6.setTime(LocalTime.of(18,00));
		schedule6.setDuration(60);
		schedule6.setGroup (new Group (3));
		
		Schedule schedule7 = new Schedule (8);
		schedule7.setWeekDay(WeekDay.FRIDAY);
		schedule7.setTime(LocalTime.of(18,00));
		schedule7.setDuration(90);
		schedule7.setGroup (new Group (7));
		
		Schedule schedule8 = new Schedule (11);//need to be updated
		schedule8.setWeekDay(WeekDay.SUNDAY);
		schedule8.setTime(LocalTime.of(18,00));
		schedule8.setDuration(90);
		schedule8.setGroup (new Group (5));
			
		expected.add(schedule1);
		expected.add(schedule2);
		expected.add(schedule3);
		expected.add(schedule4);
		expected.add(schedule5);
		expected.add(schedule6);
		expected.add(schedule7);
		expected.add(schedule8);
		
		Connection connection = ConnectionPool.getInstance().getConnection();
		DaoFactory factory = new DaoFactory (connection);
		factory.getScheduleDao().create(schedule8);
		List<Schedule> actual = factory.getScheduleDao().readAll();
		logger.debug("actual {}", actual);
		logger.debug("expected {}", expected);
		assertEquals(actual, expected);
	}

				
	@Test(dependsOnMethods = "testCreateSchedule")

	public void testDeleteSchedule() throws DaoException {
List <Schedule> expected = new ArrayList<>();
		
		Schedule schedule1 = new Schedule (1);
		schedule1.setWeekDay(WeekDay.MONDAY);
		schedule1.setTime(LocalTime.of(18,00));
		schedule1.setDuration(60);
		schedule1.setGroup (new Group (1));
		
		Schedule schedule2 = new Schedule (3);
		schedule2.setWeekDay(WeekDay.MONDAY);
		schedule2.setTime(LocalTime.of(19,00));
		schedule2.setDuration(60);
		schedule2.setGroup (new Group (2));
	
		Schedule schedule3 = new Schedule (4);
		schedule3.setWeekDay(WeekDay.TUESDAY);
		schedule3.setTime(LocalTime.of(18,00));
		schedule3.setDuration(60);
		schedule3.setGroup (new Group (3));
		
		Schedule schedule4 = new Schedule (5);
		schedule4.setWeekDay(WeekDay.WEDNESDAY);
		schedule4.setTime(LocalTime.of(18,00));
		schedule4.setDuration(60);
		schedule4.setGroup (new Group (1));
		
		Schedule schedule5 = new Schedule (6);
		schedule5.setWeekDay(WeekDay.WEDNESDAY);
		schedule5.setTime(LocalTime.of(19,00));
		schedule5.setDuration(60);
		schedule5.setGroup (new Group (2));
		
		Schedule schedule6 = new Schedule (7);
		schedule6.setWeekDay(WeekDay.THURSDAY);
		schedule6.setTime(LocalTime.of(18,00));
		schedule6.setDuration(60);
		schedule6.setGroup (new Group (3));
		
		Schedule schedule7 = new Schedule (8);
		schedule7.setWeekDay(WeekDay.FRIDAY);
		schedule7.setTime(LocalTime.of(18,00));
		schedule7.setDuration(90);
		schedule7.setGroup (new Group (7));
			
		expected.add(schedule1);
		expected.add(schedule2);
		expected.add(schedule3);
		expected.add(schedule4);
		expected.add(schedule5);
		expected.add(schedule6);
		expected.add(schedule7);
		
		Connection connection = ConnectionPool.getInstance().getConnection();
		DaoFactory factory = new DaoFactory (connection);
		List<Schedule> all = factory.getScheduleDao().readAll();
		Schedule lastAdded = all.get(all.size()-1);
		factory.getScheduleDao().delete(lastAdded.getId());
		List<Schedule> actual = factory.getScheduleDao().readAll();
		assertEquals(actual, expected);
	}
}
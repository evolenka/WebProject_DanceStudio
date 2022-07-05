package test.by.jwd.finaltaskweb.dao.suite;

import static org.testng.Assert.assertEquals;

import java.sql.Connection;
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
import by.jwd.finaltaskweb.dao.pool.ConnectionPool;
import by.jwd.finaltaskweb.entity.Group;
import by.jwd.finaltaskweb.entity.Level;
import by.jwd.finaltaskweb.entity.Teacher;

public class ReadGroupByLevelTest {
	static Logger logger = LogManager.getLogger(ReadGroupByLevelTest.class);

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

	@DataProvider(name = "ReadByLevel")
	public Object[][] createDataForReadEntityById() {
		
		Group group1 = new Group(1);
		group1.setTeacher(new Teacher(2));
		group1.setTitle("group1");
		group1.setLevel(Level.BEG);

		Group group2 = new Group(2);
		group2.setTeacher(new Teacher(2));
		group2.setTitle("group2");
		group2.setLevel(Level.PRO);

		Group group3 = new Group(3);
		group3.setTeacher(new Teacher(3));
		group3.setTitle("group3");
		group3.setLevel(Level.BEG);
		
		Group group4 = new Group (4);
		group4.setTeacher(new Teacher(3));
		group4.setTitle("group4");
		group4.setLevel(Level.PRO);
				
		Group group5 = new Group (5);
		group5.setTeacher(new Teacher(4));
		group5.setTitle("group5");
		group5.setLevel(Level.BEG);
				
		Group group6 = new Group (6);
		group6.setTeacher(new Teacher(4));
		group6.setTitle("group6");
		group6.setLevel(Level.PRO);
				
		Group group7 = new Group (7);
		group7.setTeacher(new Teacher(5));
		group7.setTitle("group7");
		group7.setLevel(Level.BEG);
		
		List <Group> groups1 = new ArrayList<>();
		groups1.add(group1);
		groups1.add(group3);
		groups1.add(group5);
		groups1.add(group7);
		
		List <Group> groups2 = new ArrayList<>();
		groups2.add(group2);
		groups2.add(group4);
		groups2.add(group6);

		Object level1 = Level.BEG;
		Object level2 = Level.PRO;
		
		return new Object[][] { { level1, groups1 },  { level2, groups2 }};

	}

	@Test(groups = { "dao" }, dataProvider = "ReadByLevel")

	public void testReadByLevel(Level level, List <Group> group) throws DaoException {

		List <Group> expected = group;
		Connection connection = ConnectionPool.getInstance().getConnection();
		DaoFactory factory = new DaoFactory (connection);
		List <Group>  actual = factory.getGroupDao().readByLevel(level);
		assertEquals(actual, expected);
	}
}
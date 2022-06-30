package test.by.jwd.finaltaskweb.dao.suite;

import static org.testng.Assert.assertEquals;

import java.sql.Connection;
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

public class ReadGroupEntityByIdTest {

	static Logger logger = LogManager.getLogger(ReadGroupEntityByIdTest.class);

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

	@DataProvider(name = "ReadById")
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

		Object id1 = 1;
		Object id2 = 2;
		Object id3 = 3;

		return new Object[][] { { id1, group1 }, { id2, group2 }, { id3, group3 } };

	}

	@Test(groups = { "dao" }, dataProvider = "ReadById")

	public void testReadById(Integer id, Group group) throws DaoException {

		Group expected = group;
		Connection connection = ConnectionPool.getInstance().getConnection();
		DaoFactory factory = new DaoFactory (connection);
		Group actual = factory.getGroupDao().readEntityById(id);
		assertEquals(actual, expected);
	}
}

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

public class ReadAllDanceStyleTest {
	
	static Logger logger = LogManager.getLogger(ReadAllDanceStyleTest.class);

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

	@DataProvider(name = "ReadByDanceStyle")
	public Object[][] createDataForReadByDanceStyle() {
		
		List<String> dancestyles = new ArrayList<>();
		dancestyles.add("contemporary");
		dancestyles.add("hip-hop");
		dancestyles.add("jazz-funk");
		dancestyles.add("vogue");
		
		return new Object[][] { { dancestyles }};
	}

	@Test(groups = { "dao" }, dataProvider = "ReadByDanceStyle")

	public void testReadAllDanceStyle(List<String>expected) throws DaoException {

	
		Connection connection = ConnectionPool.getInstance().getConnection();
		DaoFactory factory = new DaoFactory (connection);
		List <String>  actual = factory.getUserDao().readAllDanceStyle();
		assertEquals(actual, expected);
	}
}
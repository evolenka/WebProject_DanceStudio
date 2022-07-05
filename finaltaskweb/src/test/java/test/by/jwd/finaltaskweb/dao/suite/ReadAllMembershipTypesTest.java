package test.by.jwd.finaltaskweb.dao.suite;

import static org.testng.Assert.assertEquals;

import java.sql.Connection;
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
import by.jwd.finaltaskweb.entity.MembershipType;


public class ReadAllMembershipTypesTest {
	
	static Logger logger = LogManager.getLogger(ReadAllMembershipTypesTest.class);

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

	@Test(description = "readAllTypes", groups = { "dao" })
	
	public void testReadAllTypes() throws DaoException {
		
		List <MembershipType> expected = new ArrayList<>();
		
		MembershipType type1 = new MembershipType (1);
		type1.setTitle("GUEST");
		type1.setMaxClassQuantity(1);
		type1.setPrice(15.0);
		
		
		MembershipType type2 = new MembershipType (2);
		type2.setTitle("LIGHT");
		type2.setMaxClassQuantity(4);
		type2.setPrice(48.0);
				
		MembershipType type3 = new MembershipType (3);
		type3.setTitle("BASIC");
		type3.setMaxClassQuantity(8);
		type3.setPrice(80.0);
		
		MembershipType type4 = new MembershipType (4);
		type4.setTitle("STRONG");
		type4.setMaxClassQuantity(16);
		type4.setPrice(150.0);
				
		MembershipType type5 = new MembershipType (5);
		type5.setTitle("UNLIM");
		type5.setPrice(190.0);
				
		expected.add(type1);
		expected.add(type2);
		expected.add(type3);
		expected.add(type4);
		expected.add(type5);
		
		Connection connection = ConnectionPool.getInstance().getConnection();
		DaoFactory factory = new DaoFactory (connection);
		List <MembershipType> actual = factory.getMembershipDao().readAllTypes();
		assertEquals(actual, expected);
	}
}
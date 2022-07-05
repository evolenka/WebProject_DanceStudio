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
import by.jwd.finaltaskweb.entity.Client;
import by.jwd.finaltaskweb.entity.Membership;
import by.jwd.finaltaskweb.entity.MembershipType;

public class ReadValidMembershipByClientTest {
	
	static Logger logger = LogManager.getLogger(ReadValidMembershipByClientTest.class);

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

	@DataProvider(name = "readValidByClient")
	public Object[][] createDataForReadValidByClient() {

		List<Membership> membershipList1 = new ArrayList<>();
		List<Membership> membershipList2 = new ArrayList<>();
		
		Membership membership3 = new Membership (3);
		membership3.setClient(new Client(7));
		membership3.setType(new MembershipType(2));
		membership3.setStartDate(LocalDate.of(2022, Month.APRIL, 01));
		membership3.setEndDate(LocalDate.of(2022, Month.MAY, 01));
		membership3.setBalanceClassQuantity(4);
		
		membershipList1.add(membership3);
		
		return new Object[][] {
				{ 7, membershipList1 },{6, membershipList2 }
		};
	}

	@Test(groups = { "dao" }, dataProvider = "readValidByClient")

	public void testReadByClientAndPeriod(int clientId, List<Membership> expected) throws DaoException {

		Connection connection = ConnectionPool.getInstance().getConnection();
		TransactionImpl transaction = new TransactionImpl(connection);
		DaoFactory factory = DaoFactory.getInstance();
		List<Membership> actual = factory.getMembershipDao(transaction).readValidByClient(clientId);
		assertEquals(actual, expected);
	}
}
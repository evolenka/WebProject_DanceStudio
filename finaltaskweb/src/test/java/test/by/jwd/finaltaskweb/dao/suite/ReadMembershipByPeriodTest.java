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
import by.jwd.finaltaskweb.dao.pool.ConnectionPool;
import by.jwd.finaltaskweb.entity.Client;
import by.jwd.finaltaskweb.entity.Membership;
import by.jwd.finaltaskweb.entity.MembershipType;

public class ReadMembershipByPeriodTest {
	

	static Logger logger = LogManager.getLogger(ReadMembershipByPeriodTest.class);

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

	@DataProvider(name = "readByPeriod")
	public Object[][] createDataForReadByPeriod() {

		List<Membership> membershipList1 = new ArrayList<>();
		List<Membership> membershipList2 = new ArrayList<>();
		
		Membership membership2 = new Membership (2);
		membership2.setClient(new Client(6));
		membership2.setType(new MembershipType(1));
		membership2.setStartDate(LocalDate.of(2022, Month.MARCH, 12));
		membership2.setEndDate(LocalDate.of(2022, Month.MARCH, 13));
		membership2.setBalanceClassQuantity(0);
		
		
		Membership membership3 = new Membership (3);
		membership3.setClient(new Client(7));
		membership3.setType(new MembershipType(2));
		membership3.setStartDate(LocalDate.of(2022, Month.APRIL, 01));
		membership3.setEndDate(LocalDate.of(2022, Month.MAY, 01));
		membership3.setBalanceClassQuantity(4);
		
		Membership membership4 = new Membership (4);
		membership4.setClient(new Client(7));
		membership4.setType(new MembershipType(1));
		membership4.setStartDate(LocalDate.of(2022, Month.MARCH, 28));
		membership4.setEndDate(LocalDate.of(2022, Month.MARCH, 29));
		membership4.setBalanceClassQuantity(1);
		

		
		membershipList1.add(membership2);
		membershipList1.add(membership4);
		
		
		return new Object[][] {
				{LocalDate.of(2022, Month.MARCH, 01), LocalDate.of(2022, Month.MARCH, 31), membershipList1 },
				{ LocalDate.of(2010, Month.APRIL, 01), LocalDate.of(2010, Month.APRIL, 11), membershipList2 } };

	}

	@Test(groups = { "dao" }, dataProvider = "readByPeriod")

	public void testReadByPeriod(LocalDate startDate, LocalDate endDate,
			List<Membership> expected) throws DaoException {
		
		Connection connection = ConnectionPool.getInstance().getConnection();
		DaoFactory factory = new DaoFactory (connection);
		List<Membership> actual = factory.getMembershipDao().readByPeriod(startDate, endDate);
		logger.debug("actual {}", actual);
		logger.debug("expected {}", expected);

		assertEquals(actual, expected);
	}
}
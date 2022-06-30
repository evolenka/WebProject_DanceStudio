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
import by.jwd.finaltaskweb.entity.Role;
import by.jwd.finaltaskweb.entity.Teacher;

public class ReadTeacherByDanceStyleTest {
	
	static Logger logger = LogManager.getLogger(ReadTeacherByDanceStyleTest.class);

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
		
		List<Teacher> teachers1 = new ArrayList<>();
		
		Teacher teacher2 = new Teacher();
		teacher2.setId(3);
		teacher2.setLogin("divanov");
		teacher2.setPassword("rZDdprnLVXNuvafESK0DUA==");
		teacher2.setName("Дмитрий");
		teacher2.setSurname("Иванов");
		teacher2.setRole(Role.TEACHER);
		teacher2.setDanceStyle("hip-hop");
		teacher2.setPortfolio ("В танцах уже 18 лет. Опыт преподавания 6 лет. Постановщик и участник конкурсных программ, ТВ-Шоу, музыкальных клипов, рекламных видеороликов и подтанцовок");
		teachers1.add(teacher2);		
		
		List<Teacher> teachers2 = new ArrayList<>();
		Teacher teacher4 = new Teacher();
		teacher4.setId(5);
		teacher4.setLogin("atalai");
		teacher4.setPassword("qas64XWX8NI4PXz6h+b/vw==");
		teacher4.setName("Александра");
		teacher4.setSurname("Талай");
		teacher4.setRole(Role.TEACHER);
		teacher4.setDanceStyle("vogue");
		teacher4.setPortfolio ("Танцевальный опыт - более 15 лет! Стажировка в театре в Берлине. Участие в международных конкурсах и мастер-классах.");
		teachers2.add(teacher4);
		
		return new Object[][] { { "vogue", teachers2 }, {"hip-hop", teachers1}};
	}

	@Test(groups = { "dao" }, dataProvider = "ReadByDanceStyle")

	public void testReadByDanceStyle(String dancestyle, List<Teacher> expected) throws DaoException {

	
		Connection connection = ConnectionPool.getInstance().getConnection();
		DaoFactory factory = new DaoFactory (connection);
		List <Teacher>  actual = factory.getUserDao().readByDanceStyle (dancestyle);
		assertEquals(actual, expected);
	}
}
package test.by.jwd.finaltaskweb.dao;

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
import by.jwd.finaltaskweb.dao.TransactionImpl;
import by.jwd.finaltaskweb.dao.pool.ConnectionPool;
import by.jwd.finaltaskweb.entity.Role;
import by.jwd.finaltaskweb.entity.Teacher;

public class UpdateUserTest {
	
	static Logger logger = LogManager.getLogger(CreateAndDeleteGroupTest.class);

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

	@Test(description = "updateUser")

	public void testUpdateUser() throws DaoException {

		List<Teacher> expected = new ArrayList<>();

		Teacher teacher1 = new Teacher();
		teacher1.setId(2);
		teacher1.setLogin("tpavlova");
		teacher1.setPassword("VLD1B6ane2DpKxt9PkIC9g==");
		teacher1.setName("Татьяна");
		teacher1.setSurname("Павлова");
		teacher1.setRole(Role.TEACHER);
		teacher1.setDanceStyle("contemporary");
		teacher1.setPortfolio ("Танцевальный опыт 6 лет. Опыт преподавания 3 года. Участница международных и республиканских соревнований. Многократная чемпионка в составе команды на Белорусских Чемпионатах и Конкурсах.");
		
		Teacher teacher2 = new Teacher();
		teacher2.setId(3);
		teacher2.setLogin("divanov");
		teacher2.setPassword("rZDdprnLVXNuvafESK0DUA==");
		teacher2.setName("Дмитрий");
		teacher2.setSurname("Иванов");
		teacher2.setRole(Role.TEACHER);
		teacher2.setDanceStyle("hip-hop");
		teacher2.setPortfolio ("В танцах уже 18 лет. Опыт преподавания 6 лет. Постановщик и участник конкурсных программ, ТВ-Шоу, музыкальных клипов, рекламных видеороликов и подтанцовок");
		
		Teacher teacher3 = new Teacher();
		teacher3.setId(4);
		teacher3.setLogin("olesnaya");
		teacher3.setPassword("ufuE2rE3X5GOegBcxcsedA==");
		teacher3.setName("Ольга");
		teacher3.setSurname("Лесная");
		teacher3.setRole(Role.TEACHER);
		teacher3.setDanceStyle("jazz-funk");
		teacher3.setPortfolio ("Окончила БГУКИ с отличием, эстрадное отделение. Опыт преподавания более 10 лет. Призер множества международных и республиканских соревнований.");
		
		
		Teacher teacher4 = new Teacher();
		teacher4.setId(5);
		teacher4.setLogin("new");
		teacher4.setPassword("qas64XWX8NI4PXz6h+b/vw==");
		teacher4.setName("testnamee");
		teacher4.setSurname("testsurname");
		teacher4.setRole(Role.TEACHER);
		teacher4.setDanceStyle("stylenew");
		teacher4.setPortfolio ("Танцевальный опыт - более 15 лет! Стажировка в театре в Берлине. Участие в международных конкурсах и мастер-классах.");
		
		expected.add(teacher1);
		expected.add(teacher2);
		expected.add(teacher3);
		expected.add(teacher4);				

		Connection connection = ConnectionPool.getInstance().getConnection();
		TransactionImpl transaction = new TransactionImpl(connection);
		DaoFactory factory = DaoFactory.getInstance();
		
		factory.getUserDao(transaction).update(teacher4);
		List<Teacher> actual = factory.getUserDao(transaction).readByRole (Role.TEACHER);
		logger.debug(actual);
		assertEquals(actual, expected);
	}

				
	@Test(dependsOnMethods = "testUpdateUser")

	public void testUpdateUser2() throws DaoException {

		Teacher teacher1 = new Teacher();
		teacher1.setId(2);
		teacher1.setLogin("tpavlova");
		teacher1.setPassword("VLD1B6ane2DpKxt9PkIC9g==");
		teacher1.setName("Татьяна");
		teacher1.setSurname("Павлова");
		teacher1.setRole(Role.TEACHER);
		teacher1.setDanceStyle("contemporary");
		teacher1.setPortfolio ("Танцевальный опыт 6 лет. Опыт преподавания 3 года. Участница международных и республиканских соревнований. Многократная чемпионка в составе команды на Белорусских Чемпионатах и Конкурсах.");
		
		Teacher teacher2 = new Teacher();
		teacher2.setId(3);
		teacher2.setLogin("divanov");
		teacher2.setPassword("rZDdprnLVXNuvafESK0DUA==");
		teacher2.setName("Дмитрий");
		teacher2.setSurname("Иванов");
		teacher2.setRole(Role.TEACHER);
		teacher2.setDanceStyle("hip-hop");
		teacher2.setPortfolio ("В танцах уже 18 лет. Опыт преподавания 6 лет. Постановщик и участник конкурсных программ, ТВ-Шоу, музыкальных клипов, рекламных видеороликов и подтанцовок");
		
		Teacher teacher3 = new Teacher();
		teacher3.setId(4);
		teacher3.setLogin("olesnaya");
		teacher3.setPassword("ufuE2rE3X5GOegBcxcsedA==");
		teacher3.setName("Ольга");
		teacher3.setSurname("Лесная");
		teacher3.setRole(Role.TEACHER);
		teacher3.setDanceStyle("jazz-funk");
		teacher3.setPortfolio ("Окончила БГУКИ с отличием, эстрадное отделение. Опыт преподавания более 10 лет. Призер множества международных и республиканских соревнований.");
		
		
		Teacher teacher4 = new Teacher();
		teacher4.setId(5);
		teacher4.setLogin("atalai");
		teacher4.setPassword("qas64XWX8NI4PXz6h+b/vw==");
		teacher4.setName("Александра");
		teacher4.setSurname("Талай");
		teacher4.setRole(Role.TEACHER);
		teacher4.setDanceStyle("vogue");
		teacher4.setPortfolio ("Танцевальный опыт - более 15 лет! Стажировка в театре в Берлине. Участие в международных конкурсах и мастер-классах.");
		
		List<Teacher> expected = new ArrayList<>();
		
		expected.add(teacher1);
		expected.add(teacher2);
		expected.add(teacher3);
		expected.add(teacher4);
		
		Connection connection = ConnectionPool.getInstance().getConnection();
		TransactionImpl transaction = new TransactionImpl(connection);
		DaoFactory factory = DaoFactory.getInstance();
		
		factory.getUserDao(transaction).update(teacher4);
		List<Teacher> actual = factory.getUserDao(transaction).readByRole (Role.TEACHER);
		logger.debug(actual);
		assertEquals(actual, expected);
	}
}
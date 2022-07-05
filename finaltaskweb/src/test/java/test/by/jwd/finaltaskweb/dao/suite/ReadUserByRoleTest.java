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
import by.jwd.finaltaskweb.dao.TransactionImpl;
import by.jwd.finaltaskweb.dao.pool.ConnectionPool;
import by.jwd.finaltaskweb.entity.Client;
import by.jwd.finaltaskweb.entity.Role;
import by.jwd.finaltaskweb.entity.Teacher;
import by.jwd.finaltaskweb.entity.User;

public class ReadUserByRoleTest {
	
	static Logger logger = LogManager.getLogger(ReadUserByRoleTest.class);

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

	@DataProvider(name = "ReadByRole")
	public Object[][] createDataForReadByRole() {
		
		List <Client> clients = new ArrayList <>();
		
		Client client1 = new Client();
		client1.setId(6);
		client1.setLogin("MARKOVA");
		client1.setPassword("Uas78XWK8NI4PXz6h+b/vw==");
		client1.setName("Мария");
		client1.setSurname("Маркова");
		client1.setRole(Role.CLIENT);
		client1.setPatronymic("Алексеевна");
		client1.setEmail("markovamarialex@gmail.com");
		client1.setPhone("+375(29)768-45-36");

		
		Client client2 = new Client();
		client2.setId(7);
		client2.setLogin("nataliana");
		client2.setPassword("U0Tcja4XuTrc4I9sWAIr6w==");
		client2.setName("Наталья");
		client2.setSurname("Андреева");
		client2.setRole(Role.CLIENT);
		client2.setPatronymic("Николаевна");
		client2.setEmail("nnandreeva85@mail.ru");
		client2.setPhone("+375(44)741-58-96");
		
		Client client3 = new Client();
		client3.setId(8);
		client3.setLogin("vikkra");
		client3.setPassword("pfg78g4XWX8NI4PXz6h+c/vw==");
		client3.setName("Виктория");
		client3.setSurname("Кратова");
		client3.setRole(Role.CLIENT);
		client3.setPatronymic("Петровна");
		client3.setEmail("kratkovavikki@gmail.com");
		client3.setPhone("+375(44)789-41-78");
		
				
		Client client4 = new Client();
		client4.setId(9);
		client4.setLogin("anastaissakis");
		client4.setPassword("qas64XWX8NI4PXz6h+b+yu==");
		client4.setName("Анастасия");
		client4.setSurname("Киселева");
		client4.setRole(Role.CLIENT);
		client4.setPatronymic("Алексеевна");
		client4.setEmail("akiseleva89@mail.ru");
		client4.setPhone("+375(29)698-25-63");
		
		Client client5 = new Client();
		client5.setId(10);
		client5.setLogin("kirstepnov");
		client5.setPassword("YFGjdu7d56ggy/wePXzb+py==");
		client5.setName("Кирилл");
		client5.setSurname("Степнов");
		client5.setRole(Role.CLIENT);
		client5.setPatronymic("Александрович");
		client5.setEmail("stepnovka@mail.ru");
		client5.setPhone("+375(29)259-47-15");

		clients.add(client1);
		clients.add(client2);
		clients.add(client3);
		clients.add(client4);
		clients.add(client5);
		
		List <Teacher> teachers = new ArrayList <>();
		
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
		
		teachers.add(teacher1);
		teachers.add(teacher2);
		teachers.add(teacher3);
		teachers.add(teacher4);
		
		List <User> users = new ArrayList<>();
		
		User user = new User();
		user.setId(1);
		user.setLogin("admin");
		user.setPassword("ACNLVsim8ADv4Su/8w4loeSwTw2yH2R3++Pvrfx5xlDGlx0cDRdQ9t0TLJd1a5pvZw==");
		user.setRole(Role.ADMIN);
		users.add(user);
		
		
		return new Object[][] { { Role.CLIENT, clients }, {Role.TEACHER, teachers}, {Role.ADMIN, users}};
	}

	@Test(groups = { "dao" }, dataProvider = "ReadByRole")

	public void testReadByRole(Role role, List <Client> expected) throws DaoException {
		Connection connection = ConnectionPool.getInstance().getConnection();
		TransactionImpl transaction = new TransactionImpl(connection);
		DaoFactory factory = DaoFactory.getInstance();
		List <Client> actual = factory.getUserDao(transaction).readByRole (role);
		assertEquals(actual, expected);
	}
}
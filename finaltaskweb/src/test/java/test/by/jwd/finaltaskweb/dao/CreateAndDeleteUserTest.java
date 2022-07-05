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
import by.jwd.finaltaskweb.entity.Client;
import by.jwd.finaltaskweb.entity.Role;


public class CreateAndDeleteUserTest {

	static Logger logger = LogManager.getLogger(CreateAndDeleteUserTest.class);

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

	@Test(description = "createUser")

	public void testCreateUser() throws DaoException {
	
		List<Client> clients = new ArrayList<>();

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

		Client client6 = new Client();
		client6.setId(11);
		client6.setLogin("testnick");
		client6.setPassword("testpassword");
		client6.setName("Имя");
		client6.setSurname("Фамилия");
		client6.setRole(Role.CLIENT);
		client6.setPatronymic("Отчество");
		client6.setEmail("email@gmail.com");
		client6.setPhone("+375(29)111-11-11");

		clients.add(client1);
		clients.add(client2);
		clients.add(client3);
		clients.add(client4);
		clients.add(client5);
		clients.add(client6);

		Connection connection = ConnectionPool.getInstance().getConnection();
		TransactionImpl transaction = new TransactionImpl(connection);
		DaoFactory factory = DaoFactory.getInstance();
		
		factory.getUserDao(transaction).create(client6);
		List<Client> actual = factory.getUserDao(transaction).readByRole(Role.CLIENT);
		List<Client> expected = clients;
		logger.debug("actual {}", actual);
		logger.debug("expected {}", expected);
		assertEquals(actual, expected);
	}

	@Test(dependsOnMethods = "testCreateUser")

	public void testDeleteUser() throws DaoException {
		
		List<Client> clients = new ArrayList<>();

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
		
		Connection connection = ConnectionPool.getInstance().getConnection();
		TransactionImpl transaction = new TransactionImpl(connection);
		DaoFactory factory = DaoFactory.getInstance();
		
		List<Client> allClients = factory.getUserDao(transaction).readByRole (Role.CLIENT);
		Client lastAdded = allClients.get(allClients.size() - 1);
		factory.getUserDao(transaction).delete(lastAdded.getId());
		List<Client> actual = factory.getUserDao(transaction).readByRole (Role.CLIENT);
		assertEquals(actual, clients);
	}
}
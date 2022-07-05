package by.jwd.finaltaskweb.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.dao.DaoException;
import by.jwd.finaltaskweb.dao.DaoFactory;
import by.jwd.finaltaskweb.entity.Client;
import by.jwd.finaltaskweb.entity.Role;
import by.jwd.finaltaskweb.entity.Teacher;
import by.jwd.finaltaskweb.entity.User;
import by.jwd.finaltaskweb.service.PasswordHashGenerator;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.StudioServiceImpl;
import by.jwd.finaltaskweb.service.UserService;

public class UserServiceImpl extends StudioServiceImpl implements UserService {

	private static Logger logger = LogManager.getLogger(UserServiceImpl.class);

	private DaoFactory factory = DaoFactory.getInstance();

	@Override
	public List<User> readAll() throws ServiceException {
		List<User> users = null;
		try {
			users = factory.getUserDao(transaction).readAll();
			transaction.close();

		} catch (DaoException e) {
			throw new ServiceException();
		}
		return users;
	}

	@Override
	public List<Client> readAllClient() throws ServiceException {
		List<Client> clients = null;
		try {
			clients = factory.getUserDao(transaction).readByRole(Role.CLIENT);
			transaction.close();

		} catch (DaoException e) {
			throw new ServiceException();
		}
		return clients;
	}

	@Override
	public List<Teacher> readAllTeacher() throws ServiceException {
		List<Teacher> teachers = null;
		try {
			teachers = factory.getUserDao(transaction).readByRole(Role.TEACHER);
			transaction.close();

		} catch (DaoException e) {
			throw new ServiceException();
		}

		return teachers;
	}

	@Override
	public User readEntityById(Integer id) throws ServiceException {
		User user = null;
		try {
			user = factory.getUserDao(transaction).readEntityById(id);
			transaction.close();

		} catch (DaoException e) {
			throw new ServiceException();
		}
		return user;
	}

	@Override
	public User readByLogin(String login) throws ServiceException {
		User user = null;
		try {
			user = factory.getUserDao(transaction).readByLogin(login);
			transaction.close();
		} catch (DaoException e) {
			throw new ServiceException();
		}
		return user;
	}

	public User readByLoginAndPassword(String login, String password) throws ServiceException {
		User user = null;
		try {
			String hashPassword = PasswordHashGenerator.generate(password, login);
			logger.debug("hashPassword {}", hashPassword);
			user = factory.getUserDao(transaction).readByLoginAndPassword(login, hashPassword);
			transaction.close();
		} catch (DaoException e) {
			throw new ServiceException();
		}
		return user;
	}

	@Override
	public List<Teacher> readByDanceStyle(String danceStyle) throws ServiceException {
		List<Teacher> teachers = new ArrayList<>();
		try {
			teachers = factory.getUserDao(transaction).readByDanceStyle(danceStyle);
			transaction.close();
		} catch (DaoException e) {
			throw new ServiceException();
		}
		logger.debug("teachers{}", teachers);
		return teachers;
	}

	@Override
	public List<String> readAllDanceStyle() throws ServiceException {
		List<String> danceStyles = new ArrayList<>();
		try {
			danceStyles = factory.getUserDao(transaction).readAllDanceStyle();
			transaction.close();
		} catch (DaoException e) {
			throw new ServiceException();
		}
		return danceStyles;
	}

	@Override
	public boolean delete(Integer id) throws ServiceException {
		try {
			factory.getUserDao(transaction).delete(id);
			transaction.close();
		} catch (DaoException e) {
			throw new ServiceException();
		}
		return true;
	}

	@Override
	public boolean create(User user) throws ServiceException {
		try {
			String hashPassword = PasswordHashGenerator.generate(user.getPassword(), user.getLogin());
			logger.debug("hashPassword {}", hashPassword);
			user.setPassword(hashPassword);

			factory.getUserDao(transaction).create(user);
			transaction.close();
		} catch (DaoException e) {
			throw new ServiceException();
		}
		return true;
	}

	@Override
	public boolean update(User user) throws ServiceException {
		try {

			factory.getUserDao(transaction).update(user);
			transaction.close();
		} catch (DaoException e) {
			throw new ServiceException();
		}
		return true;
	}

	@Override
	public boolean updatePassword(Integer userId, String password) throws ServiceException {
		try {
			User user = factory.getUserDao(transaction).readEntityById(userId);
			logger.debug("user {}", user);
			String hashPassword = PasswordHashGenerator.generate(password, user.getLogin());
			logger.debug("hashPassword {}", hashPassword);
			user.setPassword(hashPassword);
			factory.getUserDao(transaction).update(user);
			transaction.close();
		} catch (DaoException e) {
			throw new ServiceException();
		}
		return true;
	}

	@Override
	public List<Client> readAllClient(int startIndex, int endIndex) throws ServiceException {
		List<Client> clients = null;
		try {
			clients = factory.getUserDao(transaction).readAllClient(startIndex, endIndex);
			transaction.close();

		} catch (DaoException e) {
			throw new ServiceException();
		}
		return clients;
	}

	@Override
	public int countClient() throws ServiceException {
		int count = 0;
		try {
			count = factory.getUserDao(transaction).countClient();
			transaction.close();

		} catch (DaoException e) {
			throw new ServiceException();
		}
		return count;
	}

	@Override
	public Teacher readBySurname(String surname) throws ServiceException {
		Teacher teacher = null;
		try {
			teacher = factory.getUserDao(transaction).readBySurname(surname);
			transaction.close();
		} catch (DaoException e) {
			throw new ServiceException();
		}
		logger.debug("teacher{}", teacher);
		return teacher;
	}
}
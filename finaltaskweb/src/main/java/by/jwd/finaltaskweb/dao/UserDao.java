package by.jwd.finaltaskweb.dao;

import java.util.List;

import by.jwd.finaltaskweb.entity.Client;
import by.jwd.finaltaskweb.entity.Role;
import by.jwd.finaltaskweb.entity.Teacher;
import by.jwd.finaltaskweb.entity.User;

public interface UserDao extends StudioDao<Integer, User> {

	<T extends User> List<T> readByRole(Role role) throws DaoException;

	public User readByLogin(String login) throws DaoException;

	public User readByLoginAndPassword(String login, String password) throws DaoException;

	public List<Teacher> readByDanceStyle(String danceStyle) throws DaoException;

	public List<String> readAllDanceStyle() throws DaoException;

	public List<Client> readAllClient(int startIndex, int endIndex) throws DaoException;

	public int countClient() throws DaoException;

	public Teacher readBySurname(String surname) throws DaoException;
}
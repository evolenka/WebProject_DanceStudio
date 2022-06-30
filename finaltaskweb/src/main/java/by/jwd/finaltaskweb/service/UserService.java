package by.jwd.finaltaskweb.service;

import java.util.List;

import by.jwd.finaltaskweb.entity.Client;
import by.jwd.finaltaskweb.entity.Teacher;
import by.jwd.finaltaskweb.entity.User;

public interface UserService extends StudioService<Integer, User> {

	public User readByLogin(String login) throws ServiceException;

	public User readByLoginAndPassword(String login, String password) throws ServiceException;

	public List<Client> readAllClient() throws ServiceException;

	public List<Client> readAllClient(int startIndex, int endIndex) throws ServiceException;

	public List<Teacher> readAllTeacher() throws ServiceException;

	public List<Teacher> readByDanceStyle(String danceStyle) throws ServiceException;

	public List<String> readAllDanceStyle() throws ServiceException;

	public boolean updatePassword(Integer clientId, String password) throws ServiceException;

	public int countClient() throws ServiceException;

	public Teacher readBySurname(String surname) throws ServiceException;

}
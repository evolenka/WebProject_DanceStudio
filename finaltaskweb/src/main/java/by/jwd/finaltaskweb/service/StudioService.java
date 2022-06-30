package by.jwd.finaltaskweb.service;

import java.util.List;

import by.jwd.finaltaskweb.entity.Entity;

public interface StudioService <K, T extends Entity> {

	public List<T> readAll() throws ServiceException;

	public T readEntityById(K id) throws  ServiceException;

	public 	boolean delete(K id) throws  ServiceException;

	public boolean create(T t) throws  ServiceException;

	public boolean update(T t) throws  ServiceException;
}

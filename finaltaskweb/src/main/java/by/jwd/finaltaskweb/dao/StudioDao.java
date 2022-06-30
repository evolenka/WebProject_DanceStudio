package by.jwd.finaltaskweb.dao;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import by.jwd.finaltaskweb.entity.Entity;

public interface StudioDao<K, T extends Entity> {

	public List<T> readAll() throws DaoException;

	public T readEntityById(K id) throws DaoException;

	public 	boolean delete(K id) throws DaoException;

	public boolean create(T t) throws DaoException;

	public boolean update(T t) throws DaoException;

	public default void close(Statement statement) throws DaoException {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			throw new DaoException();
		}
	}
}
package by.jwd.finaltaskweb.dao;

import java.sql.Connection;

public interface Transaction {
	
	public Connection getConnection () throws DaoException;

	public void commit() throws DaoException;
	
	public void setAutoCommit(Boolean autoCommit) throws DaoException;

	public void rollback() throws DaoException;
	
	public void close() throws DaoException;
}
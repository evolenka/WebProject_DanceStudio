package by.jwd.finaltaskweb.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionImpl implements Transaction {

	private Connection connection;

	public TransactionImpl(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Connection getConnection() {
		return connection;
	}

	@Override
	public void commit() throws DaoException {
		try {
			connection.commit();
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	@Override
	public void rollback() throws DaoException {
		try {
			connection.rollback();
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	@Override
	public void close() throws DaoException {

		try {
			connection.close();
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	@Override
	public void setAutoCommit(Boolean autoCommit) throws DaoException {
		try {
			connection.setAutoCommit(autoCommit);
		} catch (SQLException e) {
			throw new DaoException();
		}
	}
}
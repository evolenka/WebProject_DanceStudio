package by.jwd.finaltaskweb.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.dao.pool.ConnectionPool;

public class TransactionFactoryImpl implements TransactionFactory {

	static Logger logger = LogManager.getLogger(TransactionFactoryImpl.class);

	private static final TransactionFactoryImpl instance = new TransactionFactoryImpl();

	private TransactionFactoryImpl() {
	}

	public static TransactionFactoryImpl getInstance() {
		return instance;
	}

	@Override
	public Transaction createTransaction() throws DaoException {
		Connection connection;
		try {
			connection = ConnectionPool.getInstance().getConnection();
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			throw new DaoException();
		}
		return new TransactionImpl(connection);
	}
}

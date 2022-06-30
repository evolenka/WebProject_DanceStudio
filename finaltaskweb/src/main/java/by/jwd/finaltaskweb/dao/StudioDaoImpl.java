package by.jwd.finaltaskweb.dao;

import java.sql.Connection;

public abstract class StudioDaoImpl {
	
	protected Connection connection;
	
	public void setConnection (Connection connection) {
		this.connection = connection;
	}
}

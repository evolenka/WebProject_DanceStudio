package by.jwd.finaltaskweb.service;

import by.jwd.finaltaskweb.dao.Transaction;

public abstract class StudioServiceImpl {
	
	protected Transaction transaction = null;
	
	public void setTransaction (Transaction transaction) {
		this.transaction = transaction;
	}
}
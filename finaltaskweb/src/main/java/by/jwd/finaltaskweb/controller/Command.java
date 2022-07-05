package by.jwd.finaltaskweb.controller;

import javax.servlet.http.HttpServletRequest;

/**
 * @author User
 *
 */
public interface Command {

	public String execute(HttpServletRequest request);
}
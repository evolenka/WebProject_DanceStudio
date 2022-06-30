package by.jwd.finaltaskweb.controller.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;

/**
 * LogoutCommandImpl implements command for logging out the private account
 * 
 * @author Evlashkina
 *
 */
public class LogoutCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(LogoutCommandImpl.class);

	@Override
	public String execute(HttpServletRequest request) {

		HttpSession session = request.getSession(false);

		if (session != null) {
			session.invalidate();
		}
		logger.debug("user has been logged out");

		return ConfigurationManager.getProperty("path.page.index");

	}
}
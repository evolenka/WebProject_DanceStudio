package by.jwd.finaltaskweb.controller.impl;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.PageResult;
import by.jwd.finaltaskweb.controller.SessionRequestContent;

/**
 * LogoutCommandImpl implements command for logging out the private account
 * 
 * @author Evlashkina
 *
 */
public class LogoutCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(LogoutCommandImpl.class);

	@Override
	public PageResult execute(SessionRequestContent content)  {
		
		
		HttpSession session = content.getRequest().getSession(false);

		if (session != null) {
			session.invalidate();
		}
		logger.debug("user has been logged out");
		logger.debug("session {}", content.getRequest().getSession(false));

		return new PageResult (ConfigurationManager.getProperty("path.page.index"), false);

	}
}
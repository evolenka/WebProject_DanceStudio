package by.jwd.finaltaskweb.controller.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.controller.PageResult;
import by.jwd.finaltaskweb.controller.SessionRequestContent;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * ReadAllStylesCommandImpl implements command for viewing by client all dance
 * styles to choose groups by style
 * 
 * @author Evlashkina
 *
 */
public class ReadAllStyleCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(ReadAllStyleCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;
		
		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);

		Integer clientId = (Integer) content.getSessionAttribute("clientId");
		logger.debug("clientId {}", clientId);

		try {
			if (clientId != null) {
				List<String> styles = factory.getUserService().readAllDanceStyle();
				content.setSessionAttribute("styles", styles);
				logger.debug("styles {}", styles);

				result = new PageResult(ConfigurationManager.getProperty("path.page.chooseGroupByStyle"), false);
			}
		} catch (ServiceException e) {
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
		}

		return result;
	}
}
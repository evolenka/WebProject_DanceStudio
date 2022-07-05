package by.jwd.finaltaskweb.controller.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.controller.PageResult;
import by.jwd.finaltaskweb.controller.SessionRequestContent;
import by.jwd.finaltaskweb.entity.Client;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * EditClientCommandImpl implements command for selecting client to edit info
 * about him by admin
 * 
 * @author Evlashkina
 *
 */
public class ChooseClientToEditCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(ChooseClientToEditCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;

		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);

		Integer adminId = (Integer)(content.getSessionAttribute("adminId"));
		logger.debug("adminId {}", adminId);

		Integer clientId = Integer.parseInt(content.getRequestParameter("clientId"));
		logger.debug("clientId {}", clientId);
		
		content.setSessionAttribute("clientId", clientId);

		try {
			if (adminId != null && clientId != null) {

				Client client = (Client) factory.getUserService().readEntityById(clientId);
				content.setSessionAttribute("client", client);

				result = new PageResult(ConfigurationManager.getProperty("path.page.updateClient"), false);
			}
		} catch (ServiceException e) {
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
		}

		return result;
	}
}
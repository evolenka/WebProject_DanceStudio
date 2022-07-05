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
 * UpdateClientCommandImpl implements command for changing personal details of
 * the client by himself in his private account
 * 
 * @author Evlashkina
 *
 */
public class UpdateClientCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(UpdateClientCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;

		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);

		Integer clientId = (Integer)(content.getSessionAttribute("clientId"));
		logger.debug("clientId {}", clientId);

		String surname = content.getRequestParameter("surname");
		logger.debug("surname {}", surname);
		String name = content.getRequestParameter("name");
		logger.debug("name {}", name);
		String patronymic = content.getRequestParameter("patronymic");
		logger.debug("patronymic {}", patronymic);
		String email = content.getRequestParameter("email");
		logger.debug("email {}", email);
		String phone = content.getRequestParameter("phone");
		logger.debug("phone {}", phone);

		try {
			if (clientId != null && surname != null && name != null && email != null) {

				Client client = (Client) factory.getUserService().readEntityById(clientId);

				client = factory.getClientBuilder().buildClient(client.getLogin(), client.getPassword(), surname, name,
						patronymic, email, phone);
				client.setId(clientId);
				if (factory.getUserService().update(client)) {
					content.setSessionAttribute("successUpdateUserMessage",
							MessageManager.getProperty("successUpdateUserMessage", language));

				} else {
					content.setSessionAttribute("errorMessage", MessageManager.getProperty("errorMessage", language));
				}
				content.setSessionAttribute("client", client);

				result = new PageResult(ConfigurationManager.getProperty("path.page.updateClient"), true);
			}

		} catch (ServiceException e) {
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
		}
		return result;
	}
}
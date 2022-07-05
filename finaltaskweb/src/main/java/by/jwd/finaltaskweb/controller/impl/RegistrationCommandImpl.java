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
 * RegistrationCommandImpl implements command for registration of new client
 * 
 * @author Evlashkina
 *
 */
public class RegistrationCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(RegistrationCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;

		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);

		String login = content.getRequestParameter("login");
		logger.debug("login {}", login);
		String password = content.getRequestParameter("password");
		logger.debug("password {}", password);
		String password2 = content.getRequestParameter("confirmPassword");
		logger.debug("password2 {}", password2);
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
			if (login != null && password != null && password2 != null && surname != null && name != null
					&& email != null) {
				if (!(password.equals(password2))) {
					content.setSessionAttribute("errorPassMatchMessage",
							MessageManager.getProperty("errorPassMatchMessage", language));

				} else if (factory.getUserService().readByLogin(login) != null) {
					content.setSessionAttribute("errorLoginMessage",
							MessageManager.getProperty("errorLoginMessage", language));
					logger.debug(factory.getUserService().readByLogin(login));

				} else {

					Client client = factory.getClientBuilder().buildClient(login, password, surname, name, patronymic,
							email, phone);

					if (factory.getUserService().create(client)) {
						content.setSessionAttribute("successRegMessage",
								MessageManager.getProperty("successRegMessage", language));

					} else {
						content.setSessionAttribute("errorRegMessage",
								MessageManager.getProperty("errorRegMessage", language));

					}

					result = new PageResult(ConfigurationManager.getProperty("path.page.registration"), true);
				}
			}
		} catch (ServiceException e) {
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
		}
		return result;
	}
}
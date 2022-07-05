package by.jwd.finaltaskweb.controller.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.controller.PageResult;
import by.jwd.finaltaskweb.controller.SessionRequestContent;
import by.jwd.finaltaskweb.entity.User;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * UpdatePasswordCommandImpl implements command for changing the password by
 * user
 * 
 * @author Evlashkina
 *
 */

public class UpdatePasswordCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(UpdatePasswordCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;

		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);

		String login = content.getRequestParameter("login");
		logger.debug("login {}", login);

		String currentPassword = content.getRequestParameter("currentPassword");
		logger.debug("currentPassword {}", currentPassword);

		String newPassword = content.getRequestParameter("newPassword");
		logger.debug("newPassword  {}", newPassword);

		String confirmPassword = content.getRequestParameter("confirmPassword");
		logger.debug("confirmPassword {}", confirmPassword);

		User user = null;
		try {
			if (login != null && currentPassword != null && newPassword != null && confirmPassword != null) {
				user = factory.getUserService().readByLoginAndPassword(login, currentPassword);
				logger.debug("user {}", user);

				if (user == null) {
					content.setSessionAttribute("errorLoginOrPassMessage",
							MessageManager.getProperty("incorrectLoginOrPasswordMessage", language));
					logger.debug(factory.getUserService().readByLogin(login));
				}

				if (!(newPassword.equals(confirmPassword))) {
					content.setSessionAttribute("errorPassMatchMessage",
							MessageManager.getProperty("errorPassMatchMessage", language));

				} else {
					Integer userId = user.getId();

					if (factory.getUserService().updatePassword(userId, newPassword)) {
						content.setSessionAttribute("successUpdatePassMessage",
								MessageManager.getProperty("successUpdatePassMessage", language));

					} else {
						content.setSessionAttribute("errorMessage",
								MessageManager.getProperty("errorMessage", language));
					}

					result = new PageResult(ConfigurationManager.getProperty("path.page.changePassword"), true);
				}
			}
		} catch (ServiceException e) {
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
		}
		return result;
	}
}
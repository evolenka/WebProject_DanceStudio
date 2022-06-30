package by.jwd.finaltaskweb.controller.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.entity.User;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * UpdatePasswordCommandImpl implements command for changing the password by
 * client in his private account
 * 
 * @author Evlashkina
 *
 */

public class UpdatePasswordCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(UpdatePasswordCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public String execute(HttpServletRequest request) {

		String page = null;

		HttpSession session = request.getSession(true);
		String language = (String) session.getAttribute("language");

		logger.debug("language {}", language);

		MessageManager manager;

		switch (language) {
		case "en", "en_US":
			manager = MessageManager.EN;
			break;
		case "ru", "ru_RU":
			manager = MessageManager.RU;
			break;
		case "be", "be_BY":
			manager = MessageManager.BY;
			break;
		default:
			manager = MessageManager.EN;
		}

		String login = request.getParameter("login");
		String currentPassword = request.getParameter("currentPassword");
		String newPassword = request.getParameter("newPassword");
		String confirmPassword = request.getParameter("confirmPassword");

		User user = null;
		try {
			user = factory.getUserService().readByLoginAndPassword(login, currentPassword);
			logger.debug("user {}", user);

			if (user == null) {
				request.setAttribute("errorLoginOrPassMessage", manager.getProperty("incorrectLoginOrPasswordMessage"));
				logger.debug(factory.getUserService().readByLogin(login));
			} else {

				if (!(newPassword.equals(confirmPassword))) {
					request.setAttribute("errorPassMatchMessage", manager.getProperty("errorPassMatchMessage"));

				} else {
					Integer userId = user.getId();

					if (factory.getUserService().updatePassword(userId, newPassword)) {
						request.setAttribute("successUpdatePassMessage",
								manager.getProperty("successUpdatePassMessage"));

					} else {
						request.setAttribute("errorMessage", manager.getProperty("errorMessage"));
					}
				}
			}
			page = ConfigurationManager.getProperty("path.page.changePassword");
		} catch (ServiceException e) {
			request.setAttribute("errorMessage", manager.getProperty("errorMessage"));
			page = ConfigurationManager.getProperty("path.page.error");
		}
		return page;
	}
}
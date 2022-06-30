package by.jwd.finaltaskweb.controller.impl;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
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
		logger.debug("login {}", login);
		String password = request.getParameter("password");
		logger.debug("password {}", password);
		String password2 = request.getParameter("confirmPassword");
		logger.debug("password2 {}", password2);
		String surname = request.getParameter("surname");
		logger.debug("surname {}", surname);
		String name = request.getParameter("name");
		logger.debug("name {}", name);
		String patronymic = request.getParameter("patronymic");
		logger.debug("patronymic {}", patronymic);
		String email = request.getParameter("email");
		logger.debug("email {}", email);
		String phone = request.getParameter("phone");
		logger.debug("phone {}", phone);

		try {
			if (!(password.equals(password2))) {
				request.setAttribute("errorPassMatchMessage", manager.getProperty("errorPassMatchMessage"));

			} else if (factory.getUserService().readByLogin(login) != null) {
				request.setAttribute("errorLoginMessage", manager.getProperty("errorLoginMessage"));
				logger.debug(factory.getUserService().readByLogin(login));

			} else {

				Client client = factory.getClientBuilder().buildClient(login, password, surname, name, patronymic,
						email, phone);

				if (factory.getUserService().create(client)) {
					request.setAttribute("successRegMessage", manager.getProperty("successRegMessage"));

				} else {
					request.setAttribute("errorRegMessage", manager.getProperty("errorRegMessage"));

				}
			}
			page = ConfigurationManager.getProperty("path.page.registration");
		} catch (ServiceException e) {
			request.setAttribute("errorMessage", manager.getProperty("errorMessage"));
			page = ConfigurationManager.getProperty("path.page.error");
		}
		return page;
	}
}
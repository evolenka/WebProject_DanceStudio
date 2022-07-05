package by.jwd.finaltaskweb.controller.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.entity.Teacher;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
* TeacherRegistrationCommandImpl implements command for registration of new client
* 
* @author Evlashkina
*
*/
public class TeacherRegistrationCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(TeacherRegistrationCommandImpl.class);

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
		String style = request.getParameter("style");
		logger.debug("style {}", style);
		String portfolio = request.getParameter("portfolio");
		logger.debug("portfolio {}", portfolio);
		

		try {
			if (!(password.equals(password2))) {
				request.setAttribute("errorPassMatchMessage", manager.getProperty("errorPassMatchMessage"));

			} else if (factory.getUserService().readByLogin(login) != null) {
				request.setAttribute("errorLoginMessage", manager.getProperty("errorLoginMessage"));
				logger.debug(factory.getUserService().readByLogin(login));

			} else {

				Teacher teacher = factory.getTeacherBuilder().buildTeacher(login, password, surname, name, style,
						portfolio);

				if (factory.getUserService().create(teacher)) {
					request.setAttribute("successRegMessage", manager.getProperty("successRegMessage"));

				} else {
					request.setAttribute("errorRegMessage", manager.getProperty("errorRegMessage"));

				}
			}
			page = ConfigurationManager.getProperty("path.page.teacherRegistration");
		} catch (ServiceException e) {
			request.setAttribute("errorMessage", manager.getProperty("errorMessage"));
			page = ConfigurationManager.getProperty("path.page.error");
		}
		return page;
	}
}
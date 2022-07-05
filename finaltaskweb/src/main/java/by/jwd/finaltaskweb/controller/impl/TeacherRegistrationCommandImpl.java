package by.jwd.finaltaskweb.controller.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.controller.PageResult;
import by.jwd.finaltaskweb.controller.SessionRequestContent;
import by.jwd.finaltaskweb.entity.Teacher;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * TeacherRegistrationCommandImpl implements command for registration of new
 * teacher by admin
 * 
 * @author Evlashkina
 *
 */
public class TeacherRegistrationCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(TeacherRegistrationCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;

		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);
		
		Integer adminId = (Integer)(content.getSessionAttribute("adminId"));
		logger.debug("adminId {}", adminId);

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
		String style = content.getRequestParameter("style");
		logger.debug("style {}", style);
		String portfolio = content.getRequestParameter("portfolio");
		logger.debug("portfolio {}", portfolio);

		try {
			if (adminId !=null && login != null && password != null && password2 != null && surname != null && name != null
					&& style != null) {
			
			if (!(password.equals(password2))) {
				content.setSessionAttribute("errorPassMatchMessage", MessageManager.getProperty("errorPassMatchMessage", language));

			} else if (factory.getUserService().readByLogin(login) != null) {
				content.setSessionAttribute("errorLoginMessage", MessageManager.getProperty("errorLoginMessage", language));
				logger.debug(factory.getUserService().readByLogin(login));

			} else {

				Teacher teacher = factory.getTeacherBuilder().buildTeacher(login, password, surname, name, style,
						portfolio);

				if (factory.getUserService().create(teacher)) {
					content.setSessionAttribute("successRegMessage", MessageManager.getProperty("successRegMessage", language));

				} else {
					content.setSessionAttribute("errorRegMessage", MessageManager.getProperty("errorRegMessage", language));

				}
			
			result = new PageResult(ConfigurationManager.getProperty("path.page.teacherRegistration"), true);
			}
		}
	} catch (ServiceException e) {
		content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
		result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
	}
	return result;
}
}
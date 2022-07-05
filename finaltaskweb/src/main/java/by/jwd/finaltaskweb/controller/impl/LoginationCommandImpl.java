package by.jwd.finaltaskweb.controller.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.controller.PageResult;
import by.jwd.finaltaskweb.controller.SessionRequestContent;
import by.jwd.finaltaskweb.entity.Client;
import by.jwd.finaltaskweb.entity.Role;
import by.jwd.finaltaskweb.entity.User;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * LoginationCommandImpl implements command for logging in the private account
 * 
 * @author Evlashkina
 *
 */
public class LoginationCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(LoginationCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public PageResult execute(SessionRequestContent content)  {
		
		PageResult result = null;
		
		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);
		
		String login = content.getRequestParameter ("login");
		logger.debug("login {}", login);
		String password = content.getRequestParameter ("password");
		logger.debug("password {}", password);
		

		User user = null;
		try {
			if (login !=null && password !=null) {
			user = factory.getUserService().readByLoginAndPassword(login, password);
			logger.debug("user {}", user);
			}

		if (user != null) {

			content.setSessionAttribute("role", user.getRole());
			content.setSessionAttribute("userId", user.getId());
								
			StringBuilder userName = new StringBuilder("");
			
			if (Role.CLIENT == user.getRole()) {
				result = new PageResult (ConfigurationManager.getProperty("path.page.clientMain"), true);
				userName.append(user.getName());
				userName.append(" ").append(((Client) user).getPatronymic());
				userName.append(" ").append(user.getSurname());
				content.setSessionAttribute("clientId", user.getId());
				content.setSessionAttribute("client", user);
				
				logger.debug("current user is a client");
				
			} else if (Role.TEACHER == user.getRole()) {
				result = new PageResult (ConfigurationManager.getProperty("path.page.teacherMain"), true);
				userName.append(user.getName());
				userName.append(" ").append(user.getSurname());
				content.setSessionAttribute("teacherId", user.getId());
				content.setSessionAttribute("teacher", user);
				
				logger.debug("current user is a teacher");
				
			} else if (Role.ADMIN == user.getRole()) {
				result = new PageResult (ConfigurationManager.getProperty("path.page.adminMain"), true);
				userName.append(user.getName());
				content.setSessionAttribute("adminId", user.getId());
				logger.debug("current user is admin, id {}", user.getId());
			}
			
			content.setSessionAttribute("userName", userName);
			logger.debug("name {}", userName);
			
		} else {
			content.setRequestParameter("errorLoginOrPassword", MessageManager.getProperty("errorLoginOrPassword", language));
			logger.debug("error Login Or Password incorrect");
			logger.debug(MessageManager.getProperty("errorLoginOrPassword", language));
			result = new PageResult (ConfigurationManager.getProperty("path.page.index"), false);
		}
		} catch (ServiceException e) {
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult (ConfigurationManager.getProperty("path.page.visitsByTeacher2"), false);
		}
        
		return result;
	}
}
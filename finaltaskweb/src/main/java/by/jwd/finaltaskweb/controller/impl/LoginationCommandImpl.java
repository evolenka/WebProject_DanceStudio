package by.jwd.finaltaskweb.controller.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
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
	public String execute(HttpServletRequest request) {

		String page = null;
		
		HttpSession session = request.getSession(true);
		String language = session.getAttribute("language").toString();
		
		logger.debug("language {}", language);
		
		MessageManager manager;

		switch (language) {
		case "en", "en_US":
			manager = MessageManager.EN;
			break;
		case "ru", "ru_RU":
			manager = MessageManager.RU;
			break;
		case "be","be_BY":
			manager = MessageManager.BY;
			break;
		default:
			manager = MessageManager.EN;
		}
		
		String login = null;
		String password = null;

		if (request.getParameter("login")!=null && request.getParameter("password")!=null) {
		session.setAttribute("login", request.getParameter("login"));
		login = request.getParameter("login");
		logger.debug("reqLogin {}", login);
		session.setAttribute("password", request.getParameter("password"));		
		password = request.getParameter("password");
		
		
		} else if (session.getAttribute("login") !=null && session.getAttribute("password") !=null) {
			login = (String) session.getAttribute("login");
			logger.debug("sessionLogin {}", login);
			password = (String) session.getAttribute("password");
			logger.debug("sessionPassword {}", password);
		}

		User user = null;
		
		try {
			user = factory.getUserService().readByLoginAndPassword(login, password);
			logger.debug(user);

		if (user != null) {

			session.setAttribute("role", user.getRole());
			session.setAttribute("userId", user.getId());
								
			StringBuilder userName = new StringBuilder("");
			
			if (Role.CLIENT == user.getRole()) {
				page = ConfigurationManager.getProperty("path.page.clientMain");
				userName.append(user.getName());
				userName.append(" ").append(((Client) user).getPatronymic());
				userName.append(" ").append(user.getSurname());
				session.setAttribute("clientId", user.getId());
				session.setAttribute("client", user);
				
				logger.debug("current user is a client");
				
			} else if (Role.TEACHER == user.getRole()) {
				page = ConfigurationManager.getProperty("path.page.teacherMain");
				userName.append(user.getName());
				userName.append(" ").append(user.getSurname());
				session.setAttribute("teacherId", user.getId());
				
				logger.debug("current user is a teacher");
				
			} else if (Role.ADMIN == user.getRole()) {
				page = ConfigurationManager.getProperty("path.page.adminMain");
				userName.append(user.getName());
				session.setAttribute("adminId", user.getId());
				logger.debug("current user is admin, id {}", user.getId());
			}
			
			session.setAttribute("userName", userName);
			logger.debug("name {}", userName);
			
		} else {
			request.setAttribute("errorLoginOrPassword", manager.getProperty("errorLoginOrPassword"));
			logger.debug("errorLoginOrPasswordMessage{}", manager.getProperty("errorLoginOrPassword"));
			page = ConfigurationManager.getProperty("path.page.index");
		}
		} catch (ServiceException e) {
			request.setAttribute("errorMessage", manager.getProperty("errorMessage"));
			page = ConfigurationManager.getProperty("path.page.error");
		}
		return page;
	}
}
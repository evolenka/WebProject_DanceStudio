package by.jwd.finaltaskweb.controller.impl;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.entity.Group;
import by.jwd.finaltaskweb.entity.Level;
import by.jwd.finaltaskweb.entity.Teacher;
import by.jwd.finaltaskweb.entity.WeekDay;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * ReadAllGroupCommandImpl implements command to view all groups by admin
 * 
 * 
 * @author Evlashkina
 *
 */

public class ReadAllGroupCommandImpl implements Command {

	static Logger logger = LogManager.getLogger(ReadAllGroupCommandImpl.class);

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
		case "be", "be_BY":
			manager = MessageManager.BY;
			break;
		default:
			manager = MessageManager.EN;
		}

		Integer adminId = (Integer) session.getAttribute("adminId");

		try {
			if (adminId == null) {
				request.setAttribute("errorNoSession", manager.getProperty("errorNoSession"));
				logger.debug("session timed out");
			} else {

				List<Group> groups = ServiceFactory.getInstance().getGroupService().readAll();
				session.setAttribute("groups", groups);
				
				List<Teacher> teachers = ServiceFactory.getInstance().getUserService().readAllTeacher();
				session.setAttribute("teachers", teachers);
				
				List<Level> levels = Arrays.asList(Level.values());
				session.setAttribute("levels", levels);
				logger.debug("levels {}", levels);
								
				List<WeekDay> weekdays = Arrays.asList(WeekDay.values());
				session.setAttribute("weekdays", weekdays);
				logger.debug("weekdays {}", weekdays);
							
				page = ConfigurationManager.getProperty("path.page.groups");
				logger.debug("page {}", page);
			}
		} catch (ServiceException e) {
			request.setAttribute("errorMessage", manager.getProperty("errorMessage"));
			page = ConfigurationManager.getProperty("path.page.error");
		}
		return page;
	}
}
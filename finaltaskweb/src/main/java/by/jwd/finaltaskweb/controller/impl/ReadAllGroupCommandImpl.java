package by.jwd.finaltaskweb.controller.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.controller.PageResult;
import by.jwd.finaltaskweb.controller.SessionRequestContent;
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
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;

		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);

		Integer adminId = (Integer)(content.getSessionAttribute("adminId"));
		logger.debug("adminId {}", adminId);
		try {
			if (adminId != null) {

				List<Group> groups = ServiceFactory.getInstance().getGroupService().readAll();
				content.setSessionAttribute("groups", groups);

				List<Teacher> teachers = ServiceFactory.getInstance().getUserService().readAllTeacher();
				content.setSessionAttribute("teachers", teachers);

				List<Level> levels = Arrays.asList(Level.values());
				content.setSessionAttribute("levels", levels);
				logger.debug("levels {}", levels);

				List<WeekDay> weekdays = Arrays.asList(WeekDay.values());
				content.setSessionAttribute("weekdays", weekdays);
				logger.debug("weekdays {}", weekdays);

				result = new PageResult(ConfigurationManager.getProperty("path.page.groups"), false);
			}
		} catch (ServiceException e) {
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
		}

		return result;
	}
}
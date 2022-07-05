package by.jwd.finaltaskweb.controller.impl;

import java.util.List;

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
 * ReadAllTeacherCommandImpl implements command for viewing info about all
 * teachers
 * 
 * 
 * @author Evlashkina
 *
 */

public class ReadAllTeacherCommandImpl implements Command {

	static Logger logger = LogManager.getLogger(ReadAllTeacherCommandImpl.class);

	@Override
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;

		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);

		try {
			List<Teacher> teachers = ServiceFactory.getInstance().getUserService().readAllTeacher();

			content.setSessionAttribute("teachers", teachers);
			result = new PageResult(ConfigurationManager.getProperty("path.page.teacherReview"), false);

		} catch (ServiceException e) {
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
		}

		return result;
	}
}
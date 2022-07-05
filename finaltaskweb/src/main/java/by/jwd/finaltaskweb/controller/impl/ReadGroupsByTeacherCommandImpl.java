package by.jwd.finaltaskweb.controller.impl;

import java.util.List;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.controller.PageResult;
import by.jwd.finaltaskweb.controller.SessionRequestContent;
import by.jwd.finaltaskweb.entity.Group;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * ReadGroupsByTeacherCommandImpl implements command for viewing all groups of
 * the teacher in his private account
 * 
 * @author Evlashkina
 *
 */
public class ReadGroupsByTeacherCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(ReadGroupsByTeacherCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;

		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);

		Integer teacherId = (Integer)(content.getSessionAttribute("teacherId"));
		logger.debug("teacher id {}", teacherId);

		try {
			if (teacherId != null) {

				List<Group> groups = factory.getGroupService().readByTeacher(teacherId);
				content.setSessionAttribute("groups", groups);

				result = new PageResult(ConfigurationManager.getProperty("path.page.visitsByTeacherFirstStep"), false);
			}
		} catch (ServiceException e) {
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
		}
		return result;
	}
}
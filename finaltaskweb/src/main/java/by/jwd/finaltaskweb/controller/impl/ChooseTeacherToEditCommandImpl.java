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
 * EditTeacherCommandImpl implements command for selecting teacher to edit info
 * about him by admin
 * 
 * @author Evlashkina
 *
 */
public class ChooseTeacherToEditCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(ChooseTeacherToEditCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;

		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);

		Integer adminId = (Integer)(content.getSessionAttribute("adminId"));
		logger.debug("adminId {}", adminId);
		
		Integer teacherId = Integer.parseInt(content.getRequestParameter("teacherId"));
		logger.debug("teacherId {}", teacherId);
		content.setSessionAttribute("teacherId", teacherId);


		try {
			if (adminId != null && teacherId != null) {

				Teacher teacher = (Teacher) factory.getUserService().readEntityById(teacherId);
				content.setSessionAttribute("teacher", teacher);
				result = new PageResult(ConfigurationManager.getProperty("path.page.updateTeacher"), false);

			}
		} catch (ServiceException e) {
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
		}

		return result;
	}
}
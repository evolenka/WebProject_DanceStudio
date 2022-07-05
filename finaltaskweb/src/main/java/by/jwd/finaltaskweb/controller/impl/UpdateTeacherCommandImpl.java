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
 * UpdateTeacherCommandImpl implements command for changing personal details of
 * the teacher by admin
 * 
 * @author Evlashkina
 *
 */
public class UpdateTeacherCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(UpdateTeacherCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;

		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);

		Integer adminId = (Integer)(content.getSessionAttribute("adminId"));
		logger.debug("adminId {}", adminId);

		Integer teacherId = (Integer)(content.getSessionAttribute("teacherId"));
		logger.debug("teacherId {}", teacherId);

		String surname = content.getRequestParameter("surname");
		logger.debug("surname {}", surname);
		String name = content.getRequestParameter("name");
		logger.debug("name {}", name);
		String style = content.getRequestParameter("style");
		logger.debug("style {}", style);
		String portfolio = content.getRequestParameter("portfolio");
		logger.debug("portfolio {}", portfolio);

		try {
			if (adminId != null && teacherId != null && surname != null && name != null && style != null) {

				Teacher teacher = (Teacher) factory.getUserService().readEntityById(teacherId);

				teacher = factory.getTeacherBuilder().buildTeacher(teacher.getLogin(), teacher.getPassword(), surname,
						name, style, portfolio);
				teacher.setId(teacherId);

				if (factory.getUserService().update(teacher)) {
					content.setSessionAttribute("successUpdateUserMessage",
							MessageManager.getProperty("successUpdateUserMessage", language));

				} else {
					content.setSessionAttribute("errorMessage", MessageManager.getProperty("errorMessage", language));
				}
				content.setSessionAttribute("teacher", teacher);

				result = new PageResult(ConfigurationManager.getProperty("path.page.updateTeacher"), true);
			}

		} catch (ServiceException e) {
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
		}
		return result;
	}
}
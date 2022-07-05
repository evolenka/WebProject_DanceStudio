package by.jwd.finaltaskweb.controller.impl;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.controller.PageResult;
import by.jwd.finaltaskweb.controller.SessionRequestContent;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * DeleteTeacherCommandImpl implements command to delete teacher by admin
 * 
 * @author Evlashkina
 *
 */
public class DeleteTeacherCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(DeleteTeacherCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;

		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);

		Integer teacherId = Integer.parseInt(content.getRequestParameter("teacherId"));
		logger.debug("teacherId {}", teacherId);
		
		Integer adminId = (Integer)(content.getSessionAttribute("adminId"));
		logger.debug("adminId {}", adminId);

		try {
			if (adminId != null && teacherId != null) {

				factory.getUserService().delete(teacherId);
				new ReadAllTeacherCommandImpl().execute(content);

				result = new PageResult(ConfigurationManager.getProperty("path.page.teachers"), true);
			}
		} catch (ServiceException e) {
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
		}

		return result;
	}
}
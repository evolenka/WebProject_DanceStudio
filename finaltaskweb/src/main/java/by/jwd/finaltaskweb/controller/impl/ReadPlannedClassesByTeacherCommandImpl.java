package by.jwd.finaltaskweb.controller.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.controller.PageResult;
import by.jwd.finaltaskweb.controller.SessionRequestContent;
import by.jwd.finaltaskweb.entity.Visit;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * ReadPlannedClassesByTeacherCommandImpl implements command for viewing all
 * visits of classes of the given teacher with unmarked presence of clients
 * (status = planned)
 * 
 * @author Evlashkina
 *
 */
public class ReadPlannedClassesByTeacherCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(ReadPlannedClassesByTeacherCommandImpl.class);

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

				List<Visit> plannedVisits = factory.getVisitService().readPlannedByTeacher(teacherId);
				content.setSessionAttribute("plannedVisits", plannedVisits);

				result = new PageResult(ConfigurationManager.getProperty("path.page.markPresence"), false);
			}
		} catch (ServiceException e) {
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
		}
		return result;
	}
}
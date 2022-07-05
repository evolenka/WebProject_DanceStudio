package by.jwd.finaltaskweb.controller.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.controller.PageResult;
import by.jwd.finaltaskweb.controller.SessionRequestContent;
import by.jwd.finaltaskweb.entity.Status;
import by.jwd.finaltaskweb.entity.Visit;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * UpdateVisitStatusCommandImpl implements command for marking the presence of
 * clients on the classes by teacher
 * 
 * @author Evlashkina
 *
 */

public class UpdateVisitStatusCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(UpdateVisitStatusCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;

		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);

		Integer teacherId = (Integer)(content.getSessionAttribute("teacherId"));
		logger.debug("teacherId {}", teacherId);

		Integer visitId = Integer.parseInt(content.getRequestParameter("visitId"));
		logger.debug("visitId {}", visitId);

		String status = content.getRequestParameter("status");
		logger.debug("status {}", status);

		try {
			if (teacherId != null && visitId != null && status != null) {

				factory.getVisitService().markPresence(visitId, Status.valueOf(status));

				List<Visit> plannedVisits = factory.getVisitService().readPlannedByTeacher(teacherId);
				content.setSessionAttribute("plannedVisits", plannedVisits);

				result = new PageResult(ConfigurationManager.getProperty("path.page.markPresence"), true);
			}

		} catch (ServiceException e) {
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
		}
		return result;
	}
}

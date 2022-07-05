package by.jwd.finaltaskweb.controller.impl;

import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.controller.PageResult;
import by.jwd.finaltaskweb.controller.SessionRequestContent;
import by.jwd.finaltaskweb.entity.DanceClass;
import by.jwd.finaltaskweb.entity.Group;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * ReadVisitsByGroupAndDateCommandImpl implements command for viewing by teacher all visits
 * of the clients of the selected group per the selected date
 * 
 * @author Evlashkina
 *
 */

public class ReadDanceClassByGroupAndDateCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(ReadDanceClassByGroupAndDateCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;

		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);

		Integer teacherId = (Integer)(content.getSessionAttribute("teacherId"));
		logger.debug("teacherId {}", teacherId);

		Integer groupId = Integer.parseInt(content.getRequestParameter("groupId"));
		logger.debug("group id {}", groupId);

		LocalDate date = LocalDate.parse(content.getRequestParameter("visitDate"));
		logger.debug("visit date {}", date);

		try {

			if (teacherId != null && groupId != null && date != null) {

				Group group = factory.getGroupService().readEntityById(groupId);
				content.setSessionAttribute("group", group);

				DanceClass danceClass = factory.getDanceClassService().readByDateAndGroup(date, groupId);
				content.setSessionAttribute("danceClass", danceClass);
								
				content.setSessionAttribute("visitDate", date);

				result = new PageResult(ConfigurationManager.getProperty("path.page.visitsByTeacherSecondStep"), false);
			}
		} catch (ServiceException e) {
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
		}

		return result;
	}
}
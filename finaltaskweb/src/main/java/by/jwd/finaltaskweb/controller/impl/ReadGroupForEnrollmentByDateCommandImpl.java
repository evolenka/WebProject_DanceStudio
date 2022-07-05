package by.jwd.finaltaskweb.controller.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
 * ReadGroupForEnrollmentByDateCommandImpl implements command for viewing by
 * client all groups filtered by chosen date on the enrollment page
 * 
 * @author Evlashkina
 *
 */

public class ReadGroupForEnrollmentByDateCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(ReadGroupForEnrollmentByDateCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;

		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);

		Integer clientId = (Integer)(content.getSessionAttribute("clientId"));
		logger.debug("clientId {}", clientId);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(content.getRequestParameter("enrollmentDate"), formatter);
		logger.debug("date {}", date);

		try {
			if (clientId != null && date != null) {

				List<Group> groups = factory.getGroupService().readByDate(date);
				content.setSessionAttribute("groups", groups);
				content.setSessionAttribute("enrollmentDate", date);

				result = new PageResult(ConfigurationManager.getProperty("path.page.enrollmentSecondStep"), false);
			}
		} catch (ServiceException e) {
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
		}
		return result;
	}
}
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
import by.jwd.finaltaskweb.entity.DanceClass;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * ReadActiveClassesByDateComandImpl implements command for selecting by admin
 * all active danceClasses by date
 * 
 * @author Evlashkina
 *
 */

public class ReadActiveClassesByDateComandImpl implements Command {

	private static Logger logger = LogManager.getLogger(ReadActiveClassesByDateComandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;

		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);

		Integer adminId = (Integer)(content.getSessionAttribute("adminId"));
		logger.debug("adminId {}", adminId);

		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(content.getRequestParameter("classDate"), formatter);
		logger.debug("date {}", date);
		
		content.setSessionAttribute("classDate", date);

		try {
			if (adminId != null && date != null) {

				List<DanceClass> danceClasses = factory.getDanceClassService().readActiveByDate(date);

				if (!danceClasses.isEmpty()) {
					content.setRequestAttribute("danceClasses", danceClasses);
				} else {
					content.setRequestAttribute("noClasses", MessageManager.getProperty("noClasses", language));
				}

				result = new PageResult(ConfigurationManager.getProperty("path.page.danceClasses"), false);
			}
		} catch (ServiceException e) {
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
		}

		return result;
	}
}
package by.jwd.finaltaskweb.controller.impl;

import java.time.LocalDate;

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
 * CloseClassForEnrollmentCommandImpl implements command for closing enrollment
 * for class by admin
 * 
 * @author Evlashkina
 *
 */

public class CloseClassForEnrollmentCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(CloseClassForEnrollmentCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;

		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);

		Integer adminId = (Integer)(content.getSessionAttribute("adminId"));
		logger.debug("adminId {}", adminId);

		Integer danceClassId = (Integer)(content.getSessionAttribute("danceClassId"));
		logger.debug(danceClassId);

		try {
			if (adminId != null) {

				factory.getDanceClassService().changeForNoActive(danceClassId);

				LocalDate date = (LocalDate) content.getSessionAttribute("classDate");
				logger.debug(date);

				if (date != null) {

					List<DanceClass> danceClasses = factory.getDanceClassService().readActiveByDate(date);
					content.setSessionAttribute("danceClasses", danceClasses);
					if (danceClasses.isEmpty()) {
						
						content.setRequestAttribute("noClasses", MessageManager.getProperty("noClasses", language));
					}

					result = new PageResult(ConfigurationManager.getProperty("path.page.danceClasses"), false);
				}
			}
		} catch (ServiceException e) {
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
		}

		return result;
	}
}
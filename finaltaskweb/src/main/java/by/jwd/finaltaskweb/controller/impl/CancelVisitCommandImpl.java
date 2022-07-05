
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
 * CancelVisitCommandImpl implements command to cancel planned visit by client
 * in his private account
 * 
 * @author Evlashkina
 *
 */
public class CancelVisitCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(CancelVisitCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;

		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);

		Integer clientId = (Integer)(content.getSessionAttribute("clientId"));
		logger.debug("clientId {}", clientId);

		Integer plannedvisitId = Integer.parseInt(content.getRequestParameter("plannedvisitId"));
		logger.debug("plannedvisitId {}", plannedvisitId);

		try {
			if (clientId != null && plannedvisitId != null) {
				
			factory.getVisitService().delete(plannedvisitId);
			List<Visit> plannedVisits = factory.getVisitService().readPlannedByClient(clientId);
			content.setSessionAttribute("plannedVisits", plannedVisits);

			result = new PageResult(ConfigurationManager.getProperty("path.page.myPlannedVisits"), true);
			}

		} catch (ServiceException e) {
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
		}

		return result;
	}
}
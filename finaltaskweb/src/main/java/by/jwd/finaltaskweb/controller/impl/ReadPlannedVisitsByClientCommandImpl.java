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
 * ReadPlannedVisitsByClientCommandImpl implements command for viewing by client
 * all his planned visits
 * 
 * @author Evlashkina
 *
 */
public class ReadPlannedVisitsByClientCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(ReadPlannedVisitsByClientCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;

		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);

		Integer clientId = (Integer)(content.getSessionAttribute("clientId"));
		logger.debug("clientId {}", clientId);
		
		try {
			if (clientId != null) {

				List<Visit> plannedVisits = factory.getVisitService().readPlannedByClient(clientId);
				content.setSessionAttribute("plannedVisits", plannedVisits);

				result = new PageResult(ConfigurationManager.getProperty("path.page.myPlannedVisits"), false);
			}
		} catch (

		ServiceException e) {
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
		}
		return result;
	}
}
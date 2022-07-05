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
import by.jwd.finaltaskweb.entity.Visit;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * ReadVisitsByClientAndPeriodCommandImpl implements command for viewing all
 * visits of client for the selected period
 * 
 * @author Evlashkina
 *
 */
public class ReadVisitsByClientAndPeriodCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(ReadVisitsByClientAndPeriodCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;

		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);

		Integer clientId = (Integer)(content.getSessionAttribute("clientId"));
		logger.debug("clientId {}", clientId);

		LocalDate startDate = LocalDate.parse(content.getRequestParameter("startDateVisit"));
		logger.debug("startdate {}", startDate);

		LocalDate endDate = LocalDate.parse(content.getRequestParameter("endDateVisit"));
		logger.debug("enddate {}", endDate);

		try {
			if (clientId != null && startDate != null && endDate != null) {

				List<Visit> visits = factory.getVisitService().readByClientAndPeriod(clientId, startDate, endDate);
				content.setSessionAttribute("visits", visits);
				content.setSessionAttribute("startDateVisit", String.valueOf(startDate));
				content.setSessionAttribute("endDateVisit", String.valueOf(endDate));

				result = new PageResult(ConfigurationManager.getProperty("path.page.myVisits"), false);
			}
		} catch (ServiceException e) {
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
		}
		return result;
	}
}
package by.jwd.finaltaskweb.controller.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

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
 * ReadVisitsCountByGroupsAndPeriodCommandImpl implements command for viewing by
 * admin the visits statistics in all groups within the selected period
 * 
 * @author Evlashkina
 *
 */
public class ReadVisitsCountByGroupsAndPeriodCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(ReadVisitsCountByGroupsAndPeriodCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;

		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);

		Integer adminId = (Integer)(content.getSessionAttribute("adminId"));
		logger.debug("adminId {}", adminId);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate startDate = LocalDate.parse(content.getRequestParameter("startDate"), formatter);
		logger.debug("startdate {}", startDate);

		LocalDate endDate = LocalDate.parse(content.getRequestParameter("endDate"), formatter);
		logger.debug("enddate {}", endDate);

		try {
			if (adminId != null && startDate != null && endDate != null) {

				Map<String, Integer> countVisitsByGroups = factory.getVisitService()
						.countVisitsForPeriodByAllGroups(startDate, endDate);

				logger.debug("group and count {}", countVisitsByGroups.entrySet().toString());

				content.setSessionAttribute("countVisitsByGroups", countVisitsByGroups);

				result = new PageResult(ConfigurationManager.getProperty("path.page.visitStatisticsForAdmin"), false);
			}
		} catch (ServiceException e) {
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
		}
		return result;
	}
}
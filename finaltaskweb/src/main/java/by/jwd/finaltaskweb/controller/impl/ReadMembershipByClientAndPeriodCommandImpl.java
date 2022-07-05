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
import by.jwd.finaltaskweb.entity.Membership;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * ReadMembershipByClientAndPeriodCommandImpl implements command for viewing by client all
 * his memberships purchased for the selected period
 * 
 * @author Evlashkina
 *
 */

public class ReadMembershipByClientAndPeriodCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(ReadMembershipByClientAndPeriodCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;

		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);

		Integer clientId = (Integer)(content.getSessionAttribute("clientId"));
		logger.debug("client id {}", clientId);

		LocalDate startDate = LocalDate.parse(content.getRequestParameter("startdate"));
		logger.debug("startdate {}", startDate);

		LocalDate endDate = LocalDate.parse(content.getRequestParameter("enddate"));
		logger.debug("enddate {}", endDate);

		try {
			if (clientId != null && startDate != null && endDate != null) {

				content.setSessionAttribute("isCheckedPeriod", true);

				List<Membership> memberships = factory.getMembershipService().readByClientAndPeriod(clientId, startDate,
						endDate);
				content.setSessionAttribute("memberships", memberships);
				content.setSessionAttribute("startdate", startDate);
				content.setSessionAttribute("enddate", endDate);

				result = new PageResult(ConfigurationManager.getProperty("path.page.myMemberships"), false);
			}
		} catch (ServiceException e) {
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
		}
		return result;
	}
}
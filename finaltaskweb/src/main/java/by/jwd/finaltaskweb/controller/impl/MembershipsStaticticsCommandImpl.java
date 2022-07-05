package by.jwd.finaltaskweb.controller.impl;

import java.time.LocalDate;

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
 * MembershipsStaticticsCommandImpl implements command for viewing quantity and
 * sum of the sold memberships for period by admin
 * 
 * @author Evlashkina
 *
 */
public class MembershipsStaticticsCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(MembershipsStaticticsCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;

		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);

		Integer adminId = (Integer)(content.getSessionAttribute("adminId"));
		logger.debug("adminId {}", adminId);

		LocalDate startDate = LocalDate.parse(content.getRequestParameter("startDate"));
		logger.debug("startdate {}", startDate);

		LocalDate endDate = LocalDate.parse(content.getRequestParameter("endDate"));
		logger.debug("enddate {}", endDate);

		try {
			if (adminId != null && startDate != null && endDate != null) {

				Integer quantity = factory.getMembershipService().countQuantityForPeriod(startDate, endDate);
				content.setSessionAttribute("quantity", quantity);

				Double sum = factory.getMembershipService().countSumForPeriod(startDate, endDate);
				content.setSessionAttribute("sum", sum);

				result = new PageResult(ConfigurationManager.getProperty("path.page.membershipStatistics"), false);
			}
		} catch (ServiceException e) {
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
		}

		return result;
	}
}
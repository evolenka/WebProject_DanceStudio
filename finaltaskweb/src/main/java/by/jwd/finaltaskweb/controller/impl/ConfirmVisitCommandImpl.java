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

import by.jwd.finaltaskweb.entity.Membership;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * ConfirmVisitCommandImpl implements command for viewing details of enrollment
 * by client
 * 
 * @author Evlashkina
 *
 */
public class ConfirmVisitCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(ConfirmVisitCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;

		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);

		Integer clientId = (Integer) (content.getSessionAttribute("clientId"));
		logger.debug("clientId {}", clientId);

		Integer membershipId = Integer.parseInt(content.getRequestParameter("membershipId"));
		logger.debug("membershipId {}", membershipId);

		Integer groupId = Integer.parseInt((String) content.getSessionAttribute("groupId"));
		logger.debug("groupId {}", groupId);
		
	
		LocalDate enrollmentDate = (LocalDate)content.getSessionAttribute("enrollmentDate");
		logger.debug("enrollmentDate {}", enrollmentDate);

		try {
			if (clientId != null && membershipId != null && groupId != null && enrollmentDate != null) {

				Membership membership = factory.getMembershipService().readEntityById(membershipId);
				content.setSessionAttribute("membership", membership);

				DanceClass danceClass = factory.getDanceClassService().readByDateAndGroup(enrollmentDate, groupId);
				content.setSessionAttribute("danceClass", danceClass);

				result = new PageResult(ConfigurationManager.getProperty("path.page.enrollmentForthStep"), false);

			}
		} catch (ServiceException e) {
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
		}

		return result;
	}
}
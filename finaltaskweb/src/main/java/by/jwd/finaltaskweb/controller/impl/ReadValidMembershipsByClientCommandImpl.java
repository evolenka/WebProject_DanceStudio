package by.jwd.finaltaskweb.controller.impl;

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
 * ReadValidMembershipsByClientCommandImpl implements command for viewing by
 * client all his valid memberships
 * 
 * @author Evlashkina
 *
 */
public class ReadValidMembershipsByClientCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(ReadValidMembershipsByClientCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;

		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);

		Integer clientId = (Integer)(content.getSessionAttribute("clientId"));
		logger.debug("clientId {}", clientId);

		String page = content.getRequestParameter("page");
		logger.debug("page {}", page);

		try {
			if (clientId != null) {

				content.setSessionAttribute("isCheckedValid", true);

				List<Membership> validMemberships = factory.getMembershipService().readValidByClient(clientId);
				logger.debug("valid memberships {}", validMemberships);

				if (!validMemberships.isEmpty()) {
					content.setSessionAttribute("memberships", validMemberships);
				} else {
					logger.debug("no valid memberships");
					content.setRequestParameter("noMemberships",
							MessageManager.getProperty("myMemberships.noMembership", language));
				}
			}

			if (content.getRequestParameter("groupId") !=null) {
				content.setSessionAttribute("groupId", content.getRequestParameter("groupId"));
				logger.debug("groupId {}", content.getRequestParameter("groupId")); 
			}
			if (page != null && "myMemberships".equals(page)) {
				result = new PageResult(ConfigurationManager.getProperty("path.page.myMemberships"), false);

			} else {
				result = new PageResult(ConfigurationManager.getProperty("path.page.enrollmentThirdStep"), false);
			}
		} catch (ServiceException e) {
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
		}
		return result;
	}
}
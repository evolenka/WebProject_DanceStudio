package by.jwd.finaltaskweb.controller.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.controller.PageResult;
import by.jwd.finaltaskweb.controller.SessionRequestContent;
import by.jwd.finaltaskweb.entity.MembershipType;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * ChooseMembershipCommandImpl implements command to choose membership type by
 * client for purchase
 * 
 * @author Evlashkina
 *
 */
public class ChooseMembershipCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(CreateMembershipCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;

		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);

		Integer clientId = (Integer)(content.getSessionAttribute("clientId"));
		logger.debug("clientId {}", clientId);
		
		Integer membershipTypeId = Integer.parseInt(content.getRequestParameter("membershipTypeId"));
		logger.debug("membershipTypeId {}", membershipTypeId);
		

		try {
			if (clientId != null && membershipTypeId !=null) {
				
				MembershipType type = factory.getMembershipService().readTypeById(membershipTypeId);
				logger.debug("membershipType {}", type);
				content.setSessionAttribute("membershipType", type);

				result = new PageResult(ConfigurationManager.getProperty("path.page.purchaseMembership"), false);
			}
			else {
				result = new PageResult(ConfigurationManager.getProperty("path.page.login"), false);
				
			}
		} catch (ServiceException e) {
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
		}

		return result;
	}
}
package by.jwd.finaltaskweb.controller.impl;

import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.controller.PageResult;
import by.jwd.finaltaskweb.controller.SessionRequestContent;
import by.jwd.finaltaskweb.entity.Client;
import by.jwd.finaltaskweb.entity.Membership;
import by.jwd.finaltaskweb.entity.MembershipType;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * CreateMembershipCommandImpl implements command for purchase a membership by client
 * 
 * @author Evlashkina
 *
 */
public class CreateMembershipCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(CreateMembershipCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;

		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);

		Integer clientId = (Integer)(content.getSessionAttribute("clientId"));
		logger.debug("clientId  {}", clientId );
		
		Integer membershipTypeId = Integer.parseInt(content.getRequestParameter("membershipTypeId"));
		logger.debug("membershipTypeId {}", membershipTypeId);
		
		LocalDate startDate = LocalDate.parse(content.getRequestParameter("membershipStartDate"));
		logger.debug("startDate {}", startDate);
		
		try {
			if (clientId != null && membershipTypeId != null && startDate != null ) {
				
				Client client = (Client) factory.getUserService().readEntityById(clientId);
				MembershipType type = factory.getMembershipService().readTypeById(membershipTypeId);
				
				Membership membership = factory.getMembershipBuilder().buildMembership(client, type, startDate);

				if (factory.getMembershipService().create(membership)) {
				 content.setSessionAttribute("successPurchaseMessage", MessageManager.getProperty("successPurchaseMessage", language));
				} else {
					content.setSessionAttribute("errorPurchaseMessage", MessageManager.getProperty("errorPurchaseMessage", language));
				}
			result = new PageResult(ConfigurationManager.getProperty("path.page.purchaseMembership"), true);
			}
		} catch (ServiceException e) {
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
		}

		return result;
	}
}
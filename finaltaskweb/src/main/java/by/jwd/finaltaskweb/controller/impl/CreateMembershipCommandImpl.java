package by.jwd.finaltaskweb.controller.impl;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.entity.Client;
import by.jwd.finaltaskweb.entity.Membership;
import by.jwd.finaltaskweb.entity.MembershipType;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * CreateMembershipCommandImpl implements command to buy membership by client
 * 
 * @author Evlashkina
 *
 */
public class CreateMembershipCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(CreateMembershipCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public String execute(HttpServletRequest request) {

		String page;

		HttpSession session = request.getSession(true);
		String language = session.getAttribute("language").toString();

		logger.debug("language {}", language);

		MessageManager manager;

		switch (language) {
		case "en", "en_US":
			manager = MessageManager.EN;
			break;
		case "ru", "ru_RU":
			manager = MessageManager.RU;
			break;
		case "be", "be_BY":
			manager = MessageManager.BY;
			break;
		default:
			manager = MessageManager.EN;
		}

		
		Integer clientId = (Integer) session.getAttribute("clientId");

		try {
			if (clientId == null) {
				request.setAttribute("errorNoSession", manager.getProperty("errorNoSession"));
				logger.debug("session timed out");

			} else {
				Client client = (Client) factory.getUserService().readEntityById(clientId);
				
				Integer membershipTypeId = Integer.parseInt((String) session.getAttribute("membershipTypeId"));
				logger.debug("membershipTypeId {}", membershipTypeId);

				MembershipType type = factory.getMembershipService().readTypeById(membershipTypeId);
				
				//checking in case of updating page
				if (request.getParameter("membershipStartDate") != null) {
					session.setAttribute("membershipStartDate", request.getParameter("membershipStartDate"));
				}

				LocalDate startDate = LocalDate.parse((String) session.getAttribute("membershipStartDate"));
				Membership membership = factory.getMembershipBuilder().buildMembership(client, type, startDate);

				if (factory.getMembershipService().create(membership)) {
					request.setAttribute("membership", membership);
					request.setAttribute("successPurchaseMessage", manager.getProperty("successPurchaseMessage"));
				} else {
					request.setAttribute("errorPurchaseMessage", manager.getProperty("errorPurchaseMessage"));
				}
			}
			page = ConfigurationManager.getProperty("path.page.purchaseMembership");

		} catch (ServiceException e) {
			page = ConfigurationManager.getProperty("path.page.error");
			request.setAttribute("errorMessage", manager.getProperty("errorMessage"));
			
		}

		return page;
	}
}
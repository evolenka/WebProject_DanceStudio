package by.jwd.finaltaskweb.controller.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
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
				page = ConfigurationManager.getProperty("path.page.login");
				logger.debug("no client authorization");
			} else {

				if (request.getParameter("membershipTypeId") != null) {
					session.setAttribute("membershipTypeId", request.getParameter("membershipTypeId"));
				}

				Integer membershipTypeId = Integer.parseInt((String) session.getAttribute("membershipTypeId"));
				MembershipType type = factory.getMembershipService().readTypeById(membershipTypeId);
				logger.debug("membershipType {}", type);
				session.setAttribute("membershipType", type);

				page = ConfigurationManager.getProperty("path.page.purchaseMembership");

			}
		} catch (ServiceException e) {
			page = ConfigurationManager.getProperty("path.page.error");
			request.setAttribute("errorMessage", manager.getProperty("errorMessage"));
		}
		return page;
	}
}
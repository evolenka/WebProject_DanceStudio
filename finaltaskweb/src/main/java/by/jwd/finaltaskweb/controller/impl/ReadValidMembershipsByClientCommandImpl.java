package by.jwd.finaltaskweb.controller.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.entity.Membership;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

public class ReadValidMembershipsByClientCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(ReadValidMembershipsByClientCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public String execute(HttpServletRequest request) {

		String page = null;
		List<Membership> validMemberships;

		HttpSession session = request.getSession(true);
		String language = (String) session.getAttribute("language");
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

				session.setAttribute("isCheckedValid", true);
				logger.debug("isCheckedValid {}", session.getAttribute("isCheckedValid"));

			} else {
				
				if (request.getParameter("groupId") != null) {
					session.setAttribute(("groupId"), request.getParameter("groupId"));
				}

				validMemberships = factory.getMembershipService().readValidByClient(clientId);
				logger.debug("valid memberships {}", validMemberships);

				if (validMemberships.isEmpty()) {
					logger.debug("no valid memberships");
					request.setAttribute("myMemberships.noMembership",
							manager.getProperty("myMemberships.noMembership"));
				} else {
					session.setAttribute("memberships", validMemberships);

				}
			}
			if (request.getParameter("page") != null) {
				session.setAttribute("page", request.getParameter("page"));
			}

			if (session.getAttribute("page").equals("myMemberships")) {

				page = ConfigurationManager.getProperty("path.page.myMemberships");
				logger.debug("page - myMemberships");
			} else {
				page = ConfigurationManager.getProperty("path.page.enrollment3");
				logger.debug("page - enrollment3");

			}
		} catch (ServiceException e) {
			page = ConfigurationManager.getProperty("path.page.error");
			request.setAttribute("errorMessage", manager.getProperty("errorMessage"));
		}
		return page;
	}
}
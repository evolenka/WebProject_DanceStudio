package by.jwd.finaltaskweb.controller.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
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
	public String execute(HttpServletRequest request) {

		String page = null;

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

				if (request.getParameter("membershipId") != null) {
					session.setAttribute("membershipId", request.getParameter("membershipId"));
				}

				Integer membershipId = Integer.parseInt((String) session.getAttribute("membershipId"));

				Membership membership = factory.getMembershipService().readEntityById(membershipId);
				session.setAttribute("membership", membership);
				
				Integer groupId = Integer.parseInt((String) session.getAttribute("groupId"));
				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate enrollmentDate = LocalDate.parse((String) session.getAttribute("enrollmentDate"), formatter);
				
				DanceClass danceClass =  factory.getDanceClassService().readByDateAndGroup(enrollmentDate, groupId);
				session.setAttribute("danceClass", danceClass);

			}
			page = ConfigurationManager.getProperty("path.page.enrollment4");
		} catch (ServiceException e) {
			page = ConfigurationManager.getProperty("path.page.error");
			request.setAttribute("errorMessage", manager.getProperty("errorMessage"));
		}
		return page;
	}
}
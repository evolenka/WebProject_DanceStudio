package by.jwd.finaltaskweb.controller.impl;

import java.time.LocalDate;
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

public class ReadMembershipByClientAndPeriodCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(ReadValidMembershipsByClientCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public String execute(HttpServletRequest request) {

		String page = null;

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

		Integer id = (Integer) session.getAttribute("clientId");
		logger.debug("clientId {}", id);

		session.setAttribute("isCheckedPeriod", true);
		logger.debug("isCheckedPeriod {}", session.getAttribute("isCheckedPeriod"));

		try {
			if (id == null) {
				request.setAttribute("errorNoSession", manager.getProperty("errorNoSession"));
			} else {

				if (request.getParameter("startdate") != null && request.getParameter("enddate") != null) {
					session.setAttribute("startdate", request.getParameter("startdate"));
					session.setAttribute("enddate", request.getParameter("enddate"));
				}

				if (session.getAttribute("startdate") != null && session.getAttribute("enddate") != null) {

					LocalDate startDate = LocalDate.parse((String) session.getAttribute("startdate"));
					logger.debug("startdate {}", startDate);

					LocalDate endDate = LocalDate.parse((String) session.getAttribute("enddate"));
					logger.debug("enddate {}", endDate);

					List<Membership> memberships = factory.getMembershipService().readByClientAndPeriod(id, startDate,
							endDate);
					request.setAttribute("memberships", memberships);

				} else {
					request.setAttribute("errorNoSession", manager.getProperty("errorNoSession"));
				}
			}
		} catch (ServiceException e) {
			request.setAttribute("errorMessage", manager.getProperty("errorMessage"));
			page = ConfigurationManager.getProperty("path.page.error");

		}
		page = ConfigurationManager.getProperty("path.page.myMemberships");
		return page;
	}
}
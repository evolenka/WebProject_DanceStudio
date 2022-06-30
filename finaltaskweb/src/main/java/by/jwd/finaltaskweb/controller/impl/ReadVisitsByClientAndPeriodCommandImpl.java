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
import by.jwd.finaltaskweb.entity.Visit;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * ReadVisitsByClientAndPeriodCommandImpl implements command for viewing all
 * visits of client for the given period
 * 
 * @author Evlashkina
 *
 */
public class ReadVisitsByClientAndPeriodCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(ReadVisitsByClientAndPeriodCommandImpl.class);

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

		try {

			if (id == null) {
				request.setAttribute("errorNoSession", manager.getProperty("errorNoSession"));
			} else {

				if (request.getParameter("startDateVisit") != null && request.getParameter("endDateVisit") != null) {
					session.setAttribute("startDateVisit", request.getParameter("startDateVisit"));
					session.setAttribute("endDateVisit", request.getParameter("endDateVisit"));
				}

				if (session.getAttribute("startDateVisit") != null && session.getAttribute("endDateVisit") != null) {

					LocalDate startDate = LocalDate.parse((String) session.getAttribute("startDateVisit"));
					logger.debug("startdate {}", startDate);
					LocalDate endDate = LocalDate.parse((String) session.getAttribute("endDateVisit"));
					logger.debug("enddate {}", endDate);

					List<Visit> visits = factory.getVisitService().readByClientAndPeriod(id, startDate, endDate);
					request.setAttribute("visits", visits);

				} else {
					request.setAttribute("errorNoSession", manager.getProperty("errorNoSession"));

				}
			}
		} catch (ServiceException e) {
			request.setAttribute("errorMessage", manager.getProperty("errorMessage"));
			page = ConfigurationManager.getProperty("path.page.error");

		}
		page = ConfigurationManager.getProperty("path.page.myVisits");
		return page;
	}
}
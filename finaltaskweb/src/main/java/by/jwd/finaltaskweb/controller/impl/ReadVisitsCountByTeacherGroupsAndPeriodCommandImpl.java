package by.jwd.finaltaskweb.controller.impl;

import java.time.LocalDate;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * ReadVisitsCountByTeacherGroupsAndPeriodCommandImpl implements command for
 * viewing by teacher the visits statistics in his groups within the selected
 * period
 * 
 * @author Evlashkina
 *
 */
public class ReadVisitsCountByTeacherGroupsAndPeriodCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(ReadVisitsCountByTeacherGroupsAndPeriodCommandImpl.class);

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

		Integer teacherId = (Integer) session.getAttribute("teacherId");
		logger.debug("teacher id {}", teacherId);

		try {
			if (teacherId == null) {
				request.setAttribute("errorNoSession", manager.getProperty("errorNoSession"));
				logger.debug("session timed out");
			} else {
				if (request.getParameter("startDate") != null && request.getParameter("endDate") != null) {
					session.setAttribute("startDate", request.getParameter("startDate"));
					session.setAttribute("endDate", request.getParameter("endDate"));
				}

				if (session.getAttribute("startDate") != null && session.getAttribute("endDate") != null) {

					LocalDate startDate = LocalDate.parse((String) session.getAttribute("startDate"));
					logger.debug("startdate {}", startDate);

					LocalDate endDate = LocalDate.parse((String) session.getAttribute("endDate"));
					logger.debug("enddate {}", endDate);

					Map<String, Integer> countVisitsByTeacherGroups = factory.getVisitService()
							.countVisitsForPeriodByTeacherGroups(teacherId, startDate, endDate);

					logger.debug("group and count {}", countVisitsByTeacherGroups.entrySet().toString());

					session.setAttribute("countVisitsByTeacherGroups", countVisitsByTeacherGroups);
				}
			}
			page = ConfigurationManager.getProperty("path.page.visitStatisticsForTeacher");

		} catch (ServiceException e) {
			session.setAttribute("errorMessage", manager.getProperty("errorMessage"));
			page = ConfigurationManager.getProperty("path.page.error");

		}
		return page;
	}
}
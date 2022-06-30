package by.jwd.finaltaskweb.controller.impl;



import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.entity.Status;
import by.jwd.finaltaskweb.entity.Visit;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * UpdateVisitStatusCommandImpl implements command for marking the presence of
 * clients on the classes by teacher
 * 
 * @author Evlashkina
 *
 */

public class UpdateVisitStatusCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(UpdateVisitStatusCommandImpl.class);

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
				if (request.getParameter("visitId") != null && request.getParameter("status") != null) {
					session.setAttribute("visitId", request.getParameter("visitId"));
					logger.debug("id from req {}",request.getParameter("visitId"));
					session.setAttribute("status", request.getParameter("status"));
				}
				Integer visitId = Integer.parseInt((String) session.getAttribute("visitId"));
				logger.debug("visit id {}", visitId);

				String status = (String) session.getAttribute("status");
				logger.debug("status {}", status);

				factory.getVisitService().markPresence(visitId, Status.valueOf(status));
				
				List<Visit> plannedVisits = factory.getVisitService().readPlannedByTeacher(teacherId);
				session.setAttribute("plannedVisits", plannedVisits);
			}
			page = ConfigurationManager.getProperty("path.page.markPresence");
		} catch (ServiceException e) {
			request.setAttribute("errorMessage", manager.getProperty("errorMessage"));
			page = ConfigurationManager.getProperty("path.page.error");
		}
		return page;
	}

}

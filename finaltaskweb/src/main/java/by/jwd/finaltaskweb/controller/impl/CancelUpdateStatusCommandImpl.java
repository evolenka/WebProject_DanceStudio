package by.jwd.finaltaskweb.controller.impl;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.entity.DanceClass;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * CancelUpdateStatusCommandImpl implements command to cancel marked by mistake
 * presence or absence of the client on the dance class
 * 
 * @author Evlashkina
 *
 */
public class CancelUpdateStatusCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(CancelUpdateStatusCommandImpl.class);

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
		Integer teacherId = (Integer) session.getAttribute("teacherId");
		logger.debug("teacher id {}", teacherId);

		try {
			if (teacherId == null) {
				request.setAttribute("errorNoSession", manager.getProperty("errorNoSession"));
				logger.debug("session timed out");
			} else {
				if (request.getParameter("visitId") != null) {
					session.setAttribute("visitId", request.getParameter("visitId"));
					
				}
				Integer visitId = Integer.parseInt((String) session.getAttribute("visitId"));
				logger.debug("visit id {}", visitId);

				factory.getVisitService().cancelMarkPresence(visitId);
				
				Integer groupId = Integer.parseInt((String)session.getAttribute("groupId"));
				LocalDate date = LocalDate.parse((String)session.getAttribute("visitDate"));
				DanceClass danceClass = factory.getDanceClassService().readByDateAndGroup(date, groupId);
				session.setAttribute("danceClass", danceClass);
				
			}
			page = ConfigurationManager.getProperty("path.page.visitsByTeacher2");
		} catch (ServiceException e) {
			request.setAttribute("errorMessage", manager.getProperty("errorMessage"));
			page = ConfigurationManager.getProperty("path.page.error");
		}

		return page;
	}
}
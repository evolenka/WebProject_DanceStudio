package by.jwd.finaltaskweb.controller.impl;

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
 * ReadPlannedClassesByTeacherCommandImpl implements command for viewing all
 * visits of classes of the given teacher with unmarked presence of clients (status = planned)
 * 
 * @author Evlashkina
 *
 */
public class ReadPlannedClassesByTeacherCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(ReadPlannedClassesByTeacherCommandImpl.class);

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
				List<Visit> plannedVisits = factory.getVisitService().readPlannedByTeacher(teacherId);
				session.setAttribute("plannedVisits", plannedVisits);
			}
			page = ConfigurationManager.getProperty("path.page.markPresence");

		} catch (ServiceException e) {
			session.setAttribute("errorMessage", manager.getProperty("errorMessage"));
			page = ConfigurationManager.getProperty("path.page.error");
			logger.error(" request has been failed");
		}
		return page;
	}
}
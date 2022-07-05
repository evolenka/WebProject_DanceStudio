package by.jwd.finaltaskweb.controller.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.entity.Group;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * ReadGroupByDateCommandImpl implements command for viewing all groups filtered
 * by chosen date on the enrollment page
 * 
 * @author Evlashkina
 *
 */

public class ReadGroupForEnrollmentByDateCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(ReadGroupForEnrollmentByDateCommandImpl.class);

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

				if (request.getParameter("enrollmentDate") != null) {

					session.setAttribute("enrollmentDate", request.getParameter("enrollmentDate"));
					logger.debug("enrollment date {}", request.getParameter("enrollmentDate"));
				}

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate date = LocalDate.parse((String) session.getAttribute("enrollmentDate"), formatter);

				List<Group> groups = factory.getGroupService().readByDate(date);
				session.setAttribute("groups", groups);
				
				page = ConfigurationManager.getProperty("path.page.enrollment2");
			}
		} catch (ServiceException e) {
			request.setAttribute("errorMessage", manager.getProperty("errorMessage"));
			page = ConfigurationManager.getProperty("path.page.error");
		}
		return page;
	}
}
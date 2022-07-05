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
import by.jwd.finaltaskweb.entity.Group;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;


	/**
	 * ReadVisitsByGroupAndDateCommandImpl implements command for viewing all
	 * visits of the clients of the selected group per the selected date
	 * 
	 * @author Evlashkina
	 *
	 */

	public class ReadDanceClassByGroupAndDateCommandImpl implements Command {

		private static Logger logger = LogManager.getLogger(ReadDanceClassByGroupAndDateCommandImpl.class);

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
										
					if (request.getParameter("groupId") != null && request.getParameter("visitDate") != null) {
					
						session.setAttribute("groupId", request.getParameter("groupId"));
						session.setAttribute("visitDate", request.getParameter("visitDate"));
					}
					
					Integer groupId = Integer.parseInt((String) session.getAttribute("groupId"));
					logger.debug("group id {}", groupId);
					
					Group group = factory.getGroupService().readEntityById(groupId);
					session.setAttribute("group", group);

					LocalDate date = LocalDate.parse((String)session.getAttribute("visitDate"));
					logger.debug("visit date {}", date);
					
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
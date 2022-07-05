package by.jwd.finaltaskweb.controller.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.entity.DanceClass;
import by.jwd.finaltaskweb.entity.Membership;
import by.jwd.finaltaskweb.entity.Visit;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * CreateVisitCommandImpl implements command to make enrollment for dance
 * classes by client
 * 
 * @author Evlashkina
 *
 */
public class CreateVisitCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(CreateVisitCommandImpl.class);

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

				// Integer danceClassId = Integer.parseInt((String)
				// session.getAttribute("danceClassId"));
				// logger.debug("danceClassId {}", danceClassId);

				// Integer membershipId =
				// Integer.parseInt((String)session.getAttribute("membershipId"));
				// logger.debug("membershipId {}", membershipId);

				Membership membership = (Membership) session.getAttribute("membership");
				DanceClass danceClass = (DanceClass) session.getAttribute("danceClass");

				Visit visit = factory.getVisitbuilder().buildVisit(membership, danceClass);

				if (factory.getVisitService().create(visit)) {
					request.setAttribute("successVisitMessage", manager.getProperty("successVisitMessage"));
					logger.debug("visit has been created");
				} else {
					request.setAttribute("errorVisitMessage", manager.getProperty("errorVisitMessage"));
				}
			}
			page = ConfigurationManager.getProperty("path.page.enrollment4");
		} catch (ServiceException e) {
			page = ConfigurationManager.getProperty("path.page.error");
			request.setAttribute("errorMessage", manager.getProperty("errorMessage"));
		}

		return page;
	}
}
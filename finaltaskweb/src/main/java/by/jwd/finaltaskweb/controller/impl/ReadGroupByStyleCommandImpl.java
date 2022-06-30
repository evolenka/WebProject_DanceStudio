
package by.jwd.finaltaskweb.controller.impl;

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
 * ReadGroupByStyleCommandImpl implements command for viewing all groups
 * filtered by chosen dance style by client while picking up the dance class
 * 
 * @author Evlashkina
 *
 */
public class ReadGroupByStyleCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(ReadGroupByStyleCommandImpl.class);

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

				if (request.getParameter("style") != null) {
					session.setAttribute(("style"), request.getParameter("style"));
				}

				String style = (String) session.getAttribute("style");
				logger.debug("style {}", style);

				List<Group> groups = factory.getGroupService().readByDanceStyle(style);
				request.setAttribute("groups", groups);
			}
			page = ConfigurationManager.getProperty("path.page.chooseGroupByStyle");
		} catch (ServiceException e) {
			page = ConfigurationManager.getProperty("path.page.error");
			request.setAttribute("errorMessage", manager.getProperty("errorMessage"));
		}
		return page;
	}
}
package by.jwd.finaltaskweb.controller.impl;

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
	 * ChooseGroupToEditCommandImpl implements command for selecting group to edit info
	 * about him by admin
	 * 
	 * @author Evlashkina
	 *
	 */
	public class ChooseGroupToEditCommandImpl implements Command {

		private static Logger logger = LogManager.getLogger(ChooseGroupToEditCommandImpl.class);

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

			Integer adminId = (Integer) session.getAttribute("adminId");
			logger.debug("adminId {}", adminId);
			try {
				if (adminId == null) {
					request.setAttribute("errorNoSession", manager.getProperty("errorNoSession"));
					logger.debug("session timed out");
				} else {
					if (request.getParameter("groupId") != null) {
						session.setAttribute("groupId", request.getParameter("groupId"));
					}
					Integer groupId = Integer.parseInt((String) session.getAttribute("groupId"));
					logger.debug("groupId {}", groupId);

					Group group = factory.getGroupService().readEntityById(groupId);
					session.setAttribute("group", group);

				}
				page = ConfigurationManager.getProperty("path.page.updateGroup");
			} catch (ServiceException e) {
				request.setAttribute("errorMessage", manager.getProperty("errorMessage"));
				page = ConfigurationManager.getProperty("path.page.error");
			}
			return page;
		}
	}
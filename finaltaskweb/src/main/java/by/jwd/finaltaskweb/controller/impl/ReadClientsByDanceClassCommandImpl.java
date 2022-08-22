package by.jwd.finaltaskweb.controller.impl;

import java.time.LocalTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.controller.PageResult;
import by.jwd.finaltaskweb.controller.SessionRequestContent;
import by.jwd.finaltaskweb.entity.Client;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * ReadClientsByDanceClassCommandImpl implements command for viewing all clients
 * who enrolled for the dance class
 * 
 * @author Evlashkina
 *
 */

public class ReadClientsByDanceClassCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(ReadClientsByDanceClassCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;

		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);

		Integer adminId = (Integer) (content.getSessionAttribute("adminId"));
		logger.debug("adminId {}", adminId);

		Integer danceClassId = Integer.parseInt(content.getRequestParameter("danceClassId"));
		logger.debug("danceClassId  {}", danceClassId);
		
		LocalTime time = LocalTime.parse (content.getRequestParameter("time"));
		logger.debug("time {}", time);

		String group = content.getRequestParameter("group");
		logger.debug("group {}", group);

		try {
			if (adminId != null) {

				List<Client> enrolledClients = factory.getDanceClassService().readAllEnrolledClients(danceClassId);
				if (!enrolledClients.isEmpty()) {
					content.setRequestAttribute("clients", enrolledClients);
				}
					else {
						content.setRequestAttribute("noClients", MessageManager.getProperty("noClients", language));
				}

					result = new PageResult(ConfigurationManager.getProperty("path.page.enrolledClients"), false);
					content.setRequestAttribute("danceClassId", danceClassId);
					content.setRequestAttribute("time", time);
					content.setRequestAttribute("group", group);
				}
			
		} catch (ServiceException e) {
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
		}

		return result;
	}
}

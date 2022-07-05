package by.jwd.finaltaskweb.controller.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.controller.PageResult;
import by.jwd.finaltaskweb.controller.SessionRequestContent;
import by.jwd.finaltaskweb.entity.DanceClass;
import by.jwd.finaltaskweb.entity.Membership;
import by.jwd.finaltaskweb.entity.Visit;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * CreateVisitCommandImpl implements command to make enrollment for dance class
 * by client
 * 
 * @author Evlashkina
 *
 */
public class CreateVisitCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(CreateVisitCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;

		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);

		Integer clientId = (Integer)(content.getSessionAttribute("clientId"));
		logger.debug("clientId  {}", clientId);

		Membership membership = (Membership) content.getSessionAttribute("membership");
		logger.debug("membership  {}", membership);
		DanceClass danceClass = (DanceClass) content.getSessionAttribute("danceClass");
		logger.debug("danceClass  {}", danceClass);

		try {
			if (clientId != null && membership != null && danceClass != null) {

				Visit visit = factory.getVisitbuilder().buildVisit(membership, danceClass);

				if (factory.getVisitService().create(visit)) {
					content.setSessionAttribute("successVisitMessage",
							MessageManager.getProperty("successVisitMessage", language));

				} else {
					content.setSessionAttribute("errorVisitMessage",
							MessageManager.getProperty("errorVisitMessage", language));
				}

				result = new PageResult(ConfigurationManager.getProperty("path.page.enrollmentForthStep"), true);
			}
		} catch (ServiceException e) {
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
		}

		return result;
	}
}
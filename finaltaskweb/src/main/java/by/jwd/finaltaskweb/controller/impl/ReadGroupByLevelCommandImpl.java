package by.jwd.finaltaskweb.controller.impl;


import java.util.List;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.controller.PageResult;
import by.jwd.finaltaskweb.controller.SessionRequestContent;
import by.jwd.finaltaskweb.entity.Group;
import by.jwd.finaltaskweb.entity.Level;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * ReadGroupByLevelCommandImpl implements command for viewing all groups
 * filtered by chosen level by client while picking up the group
 * 
 * @author Evlashkina
 *
 */

public class ReadGroupByLevelCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(ReadGroupByLevelCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;

		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);

		Integer clientId = (Integer)(content.getSessionAttribute("clientId"));
		logger.debug("clientId {}", clientId);

		Level level = Level.valueOf(content.getRequestParameter("level"));
		logger.debug("level {}", level);

		try {
			if (clientId != null && level != null) {

				List<Group> groups = factory.getGroupService().readByLevel(level);
				content.setSessionAttribute("groups", groups);

				result = new PageResult(ConfigurationManager.getProperty("path.page.chooseGroupByLevel"), false);
			}
		} catch (ServiceException e) {
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
		}
		return result;
	}
}
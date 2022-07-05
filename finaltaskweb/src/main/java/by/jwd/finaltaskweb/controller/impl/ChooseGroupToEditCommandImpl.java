package by.jwd.finaltaskweb.controller.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.controller.PageResult;
import by.jwd.finaltaskweb.controller.SessionRequestContent;
import by.jwd.finaltaskweb.entity.Group;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
	 * ChooseGroupToEditCommandImpl implements command for selecting group to edit info
	 * about it by admin
	 * 
	 * @author Evlashkina
	 *
	 */
	public class ChooseGroupToEditCommandImpl implements Command {

		private static Logger logger = LogManager.getLogger(ChooseGroupToEditCommandImpl.class);

		private ServiceFactory factory = ServiceFactory.getInstance();

		@Override
		public PageResult execute(SessionRequestContent content) {

			PageResult result = null;

			String language =  (String) content.getSessionAttribute("language");
			logger.debug("language {}", language);

			Integer adminId = (Integer)(content.getSessionAttribute("adminId"));
			logger.debug("adminId {}", adminId);
			
			Integer groupId = Integer.parseInt(content.getRequestParameter("groupId"));
			logger.debug("groupId {}", groupId);
			content.setSessionAttribute("groupId", groupId);
						
			try {
				if (adminId != null && groupId !=null) {
					Group group = factory.getGroupService().readEntityById(groupId);
					content.setSessionAttribute("group", group);

				result = new PageResult(ConfigurationManager.getProperty("path.page.updateGroup"), false);
				}
				} catch (ServiceException e) {
					content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
					result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
				}

				return result;
			}
		}
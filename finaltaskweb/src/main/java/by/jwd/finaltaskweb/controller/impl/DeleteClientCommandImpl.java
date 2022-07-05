	package by.jwd.finaltaskweb.controller.impl;


	import org.apache.logging.log4j.LogManager;
	import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.controller.PageResult;
import by.jwd.finaltaskweb.controller.SessionRequestContent;
import by.jwd.finaltaskweb.service.ServiceException;
	import by.jwd.finaltaskweb.service.ServiceFactory;

	/**
	 * DeleteClientCommandImpl implements command to delete client by admin
	 * 
	 * @author Evlashkina
	 *
	 */
	public class DeleteClientCommandImpl implements Command {

		private static Logger logger = LogManager.getLogger(DeleteClientCommandImpl.class);

		private ServiceFactory factory = ServiceFactory.getInstance();

		@Override
		public PageResult execute(SessionRequestContent content) {

			PageResult result = null;

			String language = (String) content.getSessionAttribute("language");
			logger.debug("language {}", language);

			Integer clientId = Integer.parseInt(content.getRequestParameter("clientId"));
			logger.debug("clientId {}", clientId);
			
			Integer adminId = (Integer)(content.getSessionAttribute("adminId"));
			logger.debug("adminId {}", adminId);
			
			try {
				if (adminId != null && clientId !=null) {
					
					factory.getUserService().delete(clientId);
					new ReadAllClientCommandImpl().execute(content);
				
					result = new PageResult(ConfigurationManager.getProperty("path.page.clients"), true);
				}
			} catch (ServiceException e) {
				content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
				result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
			}

			return result;
		}
	}
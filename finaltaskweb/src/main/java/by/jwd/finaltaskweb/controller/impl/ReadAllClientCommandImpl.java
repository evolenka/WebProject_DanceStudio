package by.jwd.finaltaskweb.controller.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.controller.PageResult;
import by.jwd.finaltaskweb.controller.SessionRequestContent;
import by.jwd.finaltaskweb.entity.Client;
import by.jwd.finaltaskweb.service.PaginationPageCount;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * ReadAllClientCommandImpl implements command to view all clients by admin
 * 
 * 
 * @author Evlashkina
 *
 */
public class ReadAllClientCommandImpl implements Command {

	static Logger logger = LogManager.getLogger(ReadAllClientCommandImpl.class);
	private ServiceFactory factory = ServiceFactory.getInstance();
	private static final int STEP = 5;
	private static final int START = 1;

	@Override
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;

		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);

		Integer adminId = (Integer)(content.getSessionAttribute("adminId"));
		logger.debug("adminId {}", adminId);

		try {
			if (adminId != null) {

				int countClient = factory.getUserService().countClient();
				int pageQuantity = new PaginationPageCount().count(countClient, STEP);
				logger.debug("pageQuantity {}", pageQuantity);
				content.setSessionAttribute("pageQuantity", pageQuantity);

				Integer currentPage = START;
				if (content.getRequestParameter("currentPage")!=null) {
				currentPage = Integer.parseInt(content.getRequestParameter("currentPage"));
				logger.debug("currentPage from request {}", currentPage);
				}

				int startIndex = START;
				if (currentPage != null) {
					startIndex = (currentPage - 1) * STEP + 1;
				}
				logger.debug("startIndex {}", startIndex);

				int endIndex = startIndex + STEP - 1;
				logger.debug("endIndex {}", endIndex);

				List<Client> clients = factory.getUserService().readAllClient(startIndex, endIndex);
				content.setSessionAttribute("clients", clients);
				logger.debug("clients {}", clients);

				content.setRequestAttribute("index", startIndex);
				content.setRequestAttribute("currentPage", currentPage);

				result = new PageResult(ConfigurationManager.getProperty("path.page.clients"), false);
			}
		} catch (ServiceException e) {
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
		}

		return result;
	}
}
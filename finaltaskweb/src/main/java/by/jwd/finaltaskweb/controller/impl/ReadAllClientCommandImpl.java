package by.jwd.finaltaskweb.controller.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
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

	@Override
	public String execute(HttpServletRequest request) {

		String page = null;

		final int step = 5;

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
				int countClient = factory.getUserService().countClient();
				int pageQuantity = new PaginationPageCount().count(countClient, step);
				session.setAttribute("pageQuantity", pageQuantity);
				logger.debug("pageQuantity {}", pageQuantity);
				
				int startIndex = 1;
				int currentPage = 1;
				
				if (request.getParameter("currentPage") != null) {
					currentPage = Integer.parseInt((String) request.getParameter("currentPage"));
					logger.debug("currentPage {}", currentPage);
					
					startIndex = (currentPage - 1) * step+1;	
				} 
				logger.debug("startIndex {}", startIndex);
				
				int endIndex = startIndex + step-1;
				logger.debug("endIndex {}", endIndex);
				
				List<Client> clients = factory.getUserService().readAllClient(startIndex, endIndex);
				request.setAttribute("clients", clients);
				logger.debug("clients {}", clients);
				
				request.setAttribute("index", startIndex);
				request.setAttribute("currentPage", currentPage);
			}
			page = ConfigurationManager.getProperty("path.page.clients");

		} catch (ServiceException e) {
			request.setAttribute("errorMessage", manager.getProperty("errorMessage"));
			page = ConfigurationManager.getProperty("path.page.error");
		}
		return page;
	}
}
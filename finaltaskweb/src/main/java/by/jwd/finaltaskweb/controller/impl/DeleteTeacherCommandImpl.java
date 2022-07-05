package by.jwd.finaltaskweb.controller.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;


/**
 * DeleteTeacherCommandImpl implements command to delete teacher by admin
 * 
 * @author Evlashkina
 *
 */
public class DeleteTeacherCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(DeleteTeacherCommandImpl.class);

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
	
				Integer teacherId = Integer.parseInt((String) request.getParameter("teacherId"));
				logger.debug("teacherId {}", teacherId);

				factory.getUserService().delete(teacherId);
				new ReadAllTeacherCommandImpl().execute(request);
			}
				page = ConfigurationManager.getProperty("path.page.teachers");
			
		} catch (ServiceException e) {
			request.setAttribute("errorMessage", manager.getProperty("errorMessage"));
			page = ConfigurationManager.getProperty("path.page.error");
		}
		return page;
	}
}
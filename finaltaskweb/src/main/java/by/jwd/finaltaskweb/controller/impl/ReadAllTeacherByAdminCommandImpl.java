package by.jwd.finaltaskweb.controller.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.entity.Teacher;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * ReadAllTeacherCommandImpl implements command to view all teachers by admin
 * 
 * 
 * @author Evlashkina
 *
 */

public class ReadAllTeacherByAdminCommandImpl implements Command {

	static Logger logger = LogManager.getLogger(ReadAllTeacherByAdminCommandImpl.class);

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

		try {
			if (adminId == null) {
				request.setAttribute("errorNoSession", manager.getProperty("errorNoSession"));
				logger.debug("session timed out");
			} else {

				List<Teacher> teachers = ServiceFactory.getInstance().getUserService().readAllTeacher();

				request.setAttribute("teachers", teachers);
				page = ConfigurationManager.getProperty("path.page.teachers");
				logger.debug("page {}", page);
			}
		} catch (ServiceException e) {
			request.setAttribute("errorMessage", manager.getProperty("errorMessage"));
			page = ConfigurationManager.getProperty("path.page.error");
		}
		return page;
	}
}
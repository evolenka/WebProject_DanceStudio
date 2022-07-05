package by.jwd.finaltaskweb.controller.impl;

import java.util.List;

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
 * ReadGroupsByTeacherCommandImpl implements command for viewing all groups of
 * the teacher in his private account
 * 
 * @author Evlashkina
 *
 */
public class ReadGroupsByTeacherCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(ReadGroupsByTeacherCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public String execute(HttpServletRequest request) {

		String page = null;

		HttpSession session = request.getSession(true);
		String language = (String) session.getAttribute("language");

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
		
		Integer teacherId = (Integer) session.getAttribute("teacherId");
		logger.debug("teacher id {}", teacherId);

		try {
			if (teacherId == null) {
				request.setAttribute("errorNoSession", manager.getProperty("errorNoSession"));
				logger.debug("session timed out");
			} else {
				List<Group> groups = factory.getGroupService().readByTeacher(teacherId);
				session.setAttribute("groups", groups);
			}
			page = ConfigurationManager.getProperty("path.page.visitsByTeacher1");

		} catch (ServiceException e) {
			session.setAttribute("errorMessage", manager.getProperty("errorMessage"));
			page = ConfigurationManager.getProperty("path.page.error");
			logger.error("request has been failed");
		}
		return page;
	}
}
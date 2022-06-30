package by.jwd.finaltaskweb.controller.impl;

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
 * UpdateTeacherCommandImpl implements command for changing personal details of
 * the teacher by admin
 * 
 * @author Evlashkina
 *
 */
public class UpdateTeacherCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(UpdateTeacherCommandImpl.class);

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

		Integer adminId = (Integer) session.getAttribute("adminId");
		logger.debug("adminId {}", adminId);
		try {
			if (adminId == null) {
				request.setAttribute("errorNoSession", manager.getProperty("errorNoSession"));
				logger.debug("session timed out");
			} else {
				if (request.getParameter("teacherId") != null) {
					session.setAttribute("teacherId", request.getParameter("teacherId"));
				}
				Integer teacherId = Integer.parseInt((String) session.getAttribute("teacherId"));
				Teacher teacher = (Teacher) factory.getUserService().readEntityById(teacherId);

				String surname = request.getParameter("surname");
				logger.debug("surname {}", surname);
				String name = request.getParameter("name");
				logger.debug("name {}", name);
				String style = request.getParameter("style");
				logger.debug("style {}", style);
				String portfolio = request.getParameter("portfolio");
				logger.debug("portfolio {}", portfolio);

				teacher = factory.getTeacherBuilder().buildTeacher(teacher.getLogin(), teacher.getPassword(), surname,
						name, style, portfolio);
				teacher.setId(teacherId);
				if (factory.getUserService().update(teacher)) {
					request.setAttribute("successUpdateUserMessage", manager.getProperty("successUpdateUserMessage"));

				} else {
					request.setAttribute("errorMessage", manager.getProperty("errorMessage"));
				}
				session.setAttribute("teacher", teacher);
			}
			page = ConfigurationManager.getProperty("path.page.updateTeacher");
		} catch (ServiceException e) {
			request.setAttribute("errorMessage", manager.getProperty("errorMessage"));
			page = ConfigurationManager.getProperty("path.page.error");
		}
		return page;
	}
}
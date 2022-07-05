package by.jwd.finaltaskweb.controller.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
 * CreateClassesCommandImpl implements command for creating new danceclass by
 * admin
 * 
 * @author Evlashkina
 *
 */

public class CreateClassesCommandImpl implements Command{

	private static Logger logger = LogManager.getLogger(CreateGroupCommandImpl.class);

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

		try {
			if (adminId == null) {
				request.setAttribute("errorNoSession", manager.getProperty("errorNoSession"));
				logger.debug("session timed out");
			} else {

			}

			if (request.getParameterValues("groupId") != null) {
				session.setAttribute("groupId", request.getParameterValues("groupId"));
			}

			LocalDate date = LocalDate.parse((String) session.getAttribute("classDate"));

			List <Integer> groupsId = new ArrayList<>();
						for (String id: (String [])session.getAttribute("groupId")){
				groupsId.add(Integer.parseInt(id));
			}
			
						if (factory.getDanceClassService().createClassesByDateAndGroups(date, groupsId)) {
							request.setAttribute("successRegMessage", manager.getProperty("successRegMessage"));
							} else {
							request.setAttribute("errorRegMessage", manager.getProperty("errorRegMessage"));
							}
		
			page = ConfigurationManager.getProperty("path.page.createClass");
		} catch (ServiceException e) {
			request.setAttribute("errorMessage", manager.getProperty("errorMessage"));
			logger.debug("error");
			page = ConfigurationManager.getProperty("path.page.error");
		}
		return page;
	}
}

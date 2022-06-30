package by.jwd.finaltaskweb.controller.impl;

import java.time.LocalDate;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * ReadAllLevelsCommandImpl implements command for viewing all levels to choose
 * on the enrollment page
 * 
 * @author Evlashkina
 *
 */
public class ReadAllAvailiableDatesCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(ReadAllAvailiableDatesCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public String execute(HttpServletRequest request) {

		String page = null;
		HttpSession session = request.getSession(true);
		String language = session.getAttribute("language").toString();
		logger.debug("language {}", language);

		List<LocalDate> dates;
		try {
			dates = factory.getDanceClassService().readAllAvailiableDates();
			request.setAttribute("dates", dates);
			logger.debug("dates {}", dates);

			page = ConfigurationManager.getProperty("path.page.enrollment1");
		} catch (ServiceException e) {
			logger.error(e);
		}
		return page;
	}
}

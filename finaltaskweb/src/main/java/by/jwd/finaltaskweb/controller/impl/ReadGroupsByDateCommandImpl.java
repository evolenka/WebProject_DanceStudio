package by.jwd.finaltaskweb.controller.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.entity.Group;
import by.jwd.finaltaskweb.entity.WeekDay;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * ReadGroupsByDateCommandImpl implements command for viewing all groups
 * filtered by chosen date by admin according to schedule
 * 
 * @author Evlashkina
 *
 */
public class ReadGroupsByDateCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(ReadGroupsByDateCommandImpl.class);

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

				if (request.getParameter("classDate") != null) {

					session.setAttribute("classDate", request.getParameter("classDate"));
					logger.debug("classDate {}", request.getParameter("classDate"));
				}

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate date = LocalDate.parse((String) session.getAttribute("classDate"), formatter);

				WeekDay weekday = WeekDay
						.valueOf((date.getDayOfWeek().getDisplayName(TextStyle.FULL_STANDALONE, Locale.US)).toUpperCase());

				List<WeekDay> weekdays = new ArrayList<>();
				weekdays.add(weekday);

				List<Group> groupsBySchedule = factory.getGroupService().readByWeekDay(weekdays);
				request.setAttribute("groupsBySchedule", groupsBySchedule);

			}
			page = ConfigurationManager.getProperty("path.page.createClass");
		} catch (ServiceException e) {
			page = ConfigurationManager.getProperty("path.page.error");
			request.setAttribute("errorMessage", manager.getProperty("errorMessage"));
		}
		return page;
	}
}
package by.jwd.finaltaskweb.controller.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.controller.PageResult;
import by.jwd.finaltaskweb.controller.SessionRequestContent;
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
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;

		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);

		Integer adminId = (Integer)(content.getSessionAttribute("adminId"));
		logger.debug("adminId {}", adminId);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(content.getRequestParameter("classDate"), formatter);
		logger.debug("classDate {}", date);
		content.setSessionAttribute("classDate", date);

		try {
			if (adminId != null && date != null) {

				WeekDay weekday = WeekDay.valueOf(
						(date.getDayOfWeek().getDisplayName(TextStyle.FULL_STANDALONE, Locale.US)).toUpperCase());

				List<WeekDay> weekdays = new ArrayList<>();
				weekdays.add(weekday);

				List<Group> groupsBySchedule = factory.getGroupService().readByWeekDay(weekdays);
				content.setSessionAttribute("groupsBySchedule", groupsBySchedule);

				result = new PageResult(ConfigurationManager.getProperty("path.page.createClass"), false);
			}
		} catch (ServiceException e) {
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
		}
		return result;
	}
}
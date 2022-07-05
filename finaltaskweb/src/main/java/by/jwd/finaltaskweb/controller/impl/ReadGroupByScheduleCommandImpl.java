package by.jwd.finaltaskweb.controller.impl;

import java.util.ArrayList;

import java.util.List;

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
 * ReadGroupByScheduleCommandImpl implements command for viewing all groups
 * filtered by chosen week day(s) by client while picking up the group
 * 
 * @author Evlashkina
 *
 */
public class ReadGroupByScheduleCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(ReadGroupByScheduleCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;

		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);

		Integer clientId = (Integer)(content.getSessionAttribute("clientId"));
		logger.debug("clientId {}", clientId);

		String[] days = (String [])content.getRequestAttribute("weekday");
		logger.debug("weekday {}", days.toString());

		try {
			if (clientId != null && days != null) {

				List<WeekDay> weekdays = new ArrayList<>();

				for (String day : days) {
					logger.debug("days {}", day);
					weekdays.add(WeekDay.valueOf(day));
				}
				if (!weekdays.isEmpty()) {
					List<Group> groups = factory.getGroupService().readByWeekDay(weekdays);
					content.setSessionAttribute("groups", groups);

					result = new PageResult(ConfigurationManager.getProperty("path.page.chooseGroupByWeekDay"),
							false);
				}
			}
		} catch (ServiceException e) {
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
		}
		return result;
	}
}
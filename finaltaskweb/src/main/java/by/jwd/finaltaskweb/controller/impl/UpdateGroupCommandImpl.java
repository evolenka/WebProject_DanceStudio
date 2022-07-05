package by.jwd.finaltaskweb.controller.impl;

import java.time.LocalTime;
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
import by.jwd.finaltaskweb.entity.Level;
import by.jwd.finaltaskweb.entity.Schedule;
import by.jwd.finaltaskweb.entity.Teacher;
import by.jwd.finaltaskweb.entity.WeekDay;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * UpdateGroupCommandImpl implements command for editing the group by admin
 * 
 * @author Evlashkina
 *
 */
public class UpdateGroupCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(UpdateGroupCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;

		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);

		Integer adminId = (Integer)(content.getSessionAttribute("adminId"));
		logger.debug("adminId {}", adminId);

		Integer groupId = (Integer)(content.getSessionAttribute("groupId"));
		logger.debug("groupId {}", groupId);

		String title = content.getRequestParameter("group");
		logger.debug("title {}", title);

		String surname = content.getRequestParameter("teacher");
		logger.debug("surname {}", surname);

		String level = content.getRequestParameter("level");
		logger.debug("level {}", level);

		String[] weekdays = (String []) content.getRequestAttribute("weekday");
		logger.debug("weekdays {}", (Object[]) weekdays);

		String time = content.getRequestParameter("time");
		logger.debug("name {}", time);

		String duration = content.getRequestParameter("duration");
		logger.debug("duration {}", duration);

		try {
			if (adminId != null && groupId != null && title != null && surname != null && level != null
					&& weekdays != null && time != null && duration != null) {

				Teacher teacher = factory.getUserService().readBySurname(surname);

				Group group = factory.getGroupbuilder().buildGroup(title, teacher, Level.valueOf(level));
				group.setId(groupId);

				List<Schedule> schedules = new ArrayList<>();

				for (String weekday : weekdays) {
					Schedule schedule = factory.getSchedulebuilder().buildSchedule(Integer.parseInt(duration), group,
							LocalTime.parse(time), WeekDay.valueOf(weekday));
					schedules.add(schedule);
				}

				group.setSchedule(schedules);
				logger.debug("newSchedule {}", group.getSchedule());

				if (factory.getGroupService().update(group)) {
					content.setSessionAttribute("successUpdateUserMessage",
							MessageManager.getProperty("successUpdateUserMessage", language));
					logger.debug("group has been updated");

				} else {
					content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
				}

				result = new PageResult(ConfigurationManager.getProperty("path.page.updateGroup"), true);
			}

		} catch (ServiceException e) {
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
		}
		return result;
	}
}
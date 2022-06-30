package by.jwd.finaltaskweb.controller.impl;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.entity.Group;
import by.jwd.finaltaskweb.entity.Level;
import by.jwd.finaltaskweb.entity.Schedule;
import by.jwd.finaltaskweb.entity.Teacher;
import by.jwd.finaltaskweb.entity.WeekDay;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * GroupRegistrationCommandImpl implements command for adding new group by admin
 * 
 * @author Evlashkina
 *
 */
public class CreateGroupCommandImpl implements Command {

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

		String title = request.getParameter("group");
		logger.debug("title {}", title);
		String surname = request.getParameter("teacher");
		logger.debug("surname {}", surname);
		String level = request.getParameter("level");
		logger.debug("level {}", level);
		String[] weekdays = request.getParameterValues("weekday");
		logger.debug("weekdays {}", weekdays.toString());
		String time = request.getParameter("time");
		logger.debug("name {}", time);
		String duration = request.getParameter("duration");
		logger.debug("duration {}", duration);

		try {
			if (factory.getGroupService().readByTitle(title) != null) {
				request.setAttribute("errorGroupTitleMessage", manager.getProperty("errorGroupTitleMessage"));
			} else {
				Teacher teacher = factory.getUserService().readBySurname(surname);

				if (title != null && level != null) {
					
					Group group = factory.getGroupbuilder().buildGroup(title, teacher, Level.valueOf(level));

					List<Schedule> schedules = new ArrayList<>();

					for (String weekday : weekdays) {
						if (duration != null && time != null && weekday != null) {
							Schedule schedule = factory.getSchedulebuilder().buildSchedule(Integer.parseInt(duration),
									group, LocalTime.parse(time), WeekDay.valueOf(weekday));
							schedules.add(schedule);
						}
					}
					group.setSchedule(schedules);

					if (factory.getGroupService().create(group)) {
						request.setAttribute("successRegMessage", manager.getProperty("successRegMessage"));
						logger.debug("group has been created");

					} else {
						request.setAttribute("errorRegMessage", manager.getProperty("errorRegMessage"));
					}
				}
			}
			page = ConfigurationManager.getProperty("path.page.groupRegistration");
		} catch (ServiceException e) {
			request.setAttribute("errorMessage", manager.getProperty("errorMessage"));
			logger.debug("error");
			page = ConfigurationManager.getProperty("path.page.error");
		}
		return page;
	}
}

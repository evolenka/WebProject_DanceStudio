package by.jwd.finaltaskweb.controller.impl;

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
import by.jwd.finaltaskweb.entity.WeekDay;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * ReadGroupByScheduleCommandImpl implements command for viewing all groups
 * filtered by chosen week day(s) by client while picking up the dance class
 * 
 * @author Evlashkina
 *
 */
public class ReadGroupByScheduleCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(ReadGroupByScheduleCommandImpl.class);

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

		Integer clientId = (Integer) session.getAttribute("clientId");

		try {
			if (clientId == null) {
				request.setAttribute("errorNoSession", manager.getProperty("errorNoSession"));
				logger.debug("session timed out");

			} else {

				if (request.getParameterValues("weekday") != null) {
					session.setAttribute(("weekday"), request.getParameterValues("weekday"));
				}

				String[] days = (String[]) session.getAttribute(("weekday"));

				List<WeekDay> weekdays = new ArrayList<>();
				if (days != null) {
					for (String day : days) {
						logger.debug("days {}", day);
						weekdays.add(WeekDay.valueOf(day));
					}

					List<Group> groups = factory.getGroupService().readByWeekDay(weekdays);
					request.setAttribute("groups", groups);
				}
			}
			page = ConfigurationManager.getProperty("path.page.chooseGroupByWeekDay");
		} catch (ServiceException e) {
			page = ConfigurationManager.getProperty("path.page.error");
			request.setAttribute("errorMessage", manager.getProperty("errorMessage"));
		}
		return page;
	}
}
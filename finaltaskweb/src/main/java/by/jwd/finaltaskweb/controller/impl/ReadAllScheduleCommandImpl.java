package by.jwd.finaltaskweb.controller.impl;

import java.util.EnumMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.controller.PageResult;
import by.jwd.finaltaskweb.controller.SessionRequestContent;
import by.jwd.finaltaskweb.entity.Schedule;
import by.jwd.finaltaskweb.entity.WeekDay;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
* ReadAllScheduleCommandImpl implements command for viewing dance classes schedule
* 
* @author Evlashkina
*
*/

public class ReadAllScheduleCommandImpl implements Command{
	
	static Logger logger = LogManager.getLogger(ReadAllScheduleCommandImpl.class);

	@Override
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;

		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);

		
		try {
			EnumMap<WeekDay, List <Schedule>> schedule; schedule = ServiceFactory.getInstance().getScheduleService().allScheduleByWeekDay();
			content.setSessionAttribute("scheduleMonday", schedule.get(WeekDay.MONDAY));
			content.setSessionAttribute("scheduleTuesday", schedule.get(WeekDay.TUESDAY));
			content.setSessionAttribute("scheduleWednesday", schedule.get(WeekDay.WEDNESDAY));
			content.setSessionAttribute("scheduleThursday", schedule.get(WeekDay.THURSDAY));
			content.setSessionAttribute("scheduleFriday", schedule.get(WeekDay.FRIDAY));
			
			result = new PageResult(ConfigurationManager.getProperty("path.page.schedule"), false);

		} catch (ServiceException e) {
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
		}

		return result;
	}
}
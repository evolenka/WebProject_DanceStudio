package by.jwd.finaltaskweb.controller.impl;

import java.util.EnumMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
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
	public String execute(HttpServletRequest request) {

		String page = null;

		EnumMap<WeekDay, List <Schedule>> schedule;
		
		try {
			schedule = ServiceFactory.getInstance().getScheduleService().allScheduleByWeekDay();
			request.setAttribute("scheduleMonday", schedule.get(WeekDay.MONDAY));
			logger.debug("schedule for Monday {}", schedule.get(WeekDay.MONDAY));
			request.setAttribute("scheduleTuesday", schedule.get(WeekDay.TUESDAY));
			request.setAttribute("scheduleWednesday", schedule.get(WeekDay.WEDNESDAY));
			request.setAttribute("scheduleThursday", schedule.get(WeekDay.THURSDAY));
			request.setAttribute("scheduleFriday", schedule.get(WeekDay.FRIDAY));
			
			page = ConfigurationManager.getProperty("path.page.schedule");
			logger.debug("page {}", page);
			
		} catch (ServiceException e) {
			logger.error(e);
		}
		return page;

	}
}
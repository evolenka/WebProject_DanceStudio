package by.jwd.finaltaskweb.service.builder;


import java.time.LocalTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.entity.Group;
import by.jwd.finaltaskweb.entity.Schedule;
import by.jwd.finaltaskweb.entity.WeekDay;

public class BuilderSchedule {
	private static Logger logger = LogManager.getLogger(BuilderSchedule.class);
	
	public Schedule buildSchedule(int duration, Group group, LocalTime time, WeekDay weekday) {

		Schedule schedule = new Schedule();
		schedule.setDuration(duration);
		schedule.setGroup(group);
		schedule.setTime(time);
		schedule.setWeekDay(weekday);

		logger.debug("schedule has been created {}", schedule);
		return schedule;
	}
}
package by.jwd.finaltaskweb.service;

import java.util.List;
import java.util.Map;

import by.jwd.finaltaskweb.entity.Schedule;
import by.jwd.finaltaskweb.entity.WeekDay;

public interface ScheduleService extends StudioService <Integer, Schedule>{
	
	public Map<WeekDay, List <Schedule>> allScheduleByWeekDay() throws ServiceException;
}
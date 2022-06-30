package by.jwd.finaltaskweb.service;


import java.util.EnumMap;
import java.util.List;

import by.jwd.finaltaskweb.entity.Schedule;
import by.jwd.finaltaskweb.entity.WeekDay;

public interface ScheduleService extends StudioService <Integer, Schedule>{
	
	public EnumMap<WeekDay, List <Schedule>> allScheduleByWeekDay() throws ServiceException;
}
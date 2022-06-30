package by.jwd.finaltaskweb.dao;

import java.util.List;

import by.jwd.finaltaskweb.entity.Schedule;
import by.jwd.finaltaskweb.entity.WeekDay;

public interface ScheduleDao extends StudioDao<Integer, Schedule> {
	
	public List <Schedule> readByGroup(Integer groupId) throws DaoException;//used for searching available dance classes dates by group id (see danceClass Service)
	
	public List <Schedule> readByWeekDay(WeekDay weekDay) throws DaoException; //used for searching groups by weekdays (see GroupService)
	
}

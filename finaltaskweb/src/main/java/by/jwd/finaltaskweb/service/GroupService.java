package by.jwd.finaltaskweb.service;

import java.time.LocalDate;
import java.util.List;

import by.jwd.finaltaskweb.entity.Group;
import by.jwd.finaltaskweb.entity.Level;
import by.jwd.finaltaskweb.entity.WeekDay;

public interface GroupService extends StudioService<Integer, Group> {

	public List<Group> readByLevel(Level level) throws ServiceException;

	public List<Group> readByWeekDay(List <WeekDay> weekdays) throws ServiceException;

	public List<Group> readByDanceStyle(String dancestyle) throws ServiceException;
	
	public List<Group> readByDate (LocalDate date) throws ServiceException;
	
	public List<Group> readByTeacher (Integer teacherId) throws ServiceException;

	public Group readByTitle(String title) throws ServiceException;
}
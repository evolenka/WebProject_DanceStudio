package by.jwd.finaltaskweb.dao;


import java.time.LocalDate;
import java.util.List;

import by.jwd.finaltaskweb.entity.DanceClass;
import by.jwd.finaltaskweb.entity.Schedule;

public interface DanceClassDao extends StudioDao <Integer, DanceClass>{

	public List <DanceClass> readActiveBySchedule(Schedule schedule) throws DaoException;//select dance classes dates opened for registration (see DanceClassService)
	
	public List <DanceClass> readActiveByDate(LocalDate date) throws DaoException;//select dance classes by available dates chosen by client (see DanceClassService)
	
	public List <DanceClass> readBySchedule(Schedule schedule) throws DaoException;//select all dance classes by schedule id to select all visits by teacher (see DanceClassService and VisitService)
	
	public DanceClass readByDateAndSchedule(LocalDate date, Schedule schedule) throws DaoException;//select dance class by date and group (see DanceClassService)

	public List <DanceClass> readByPeriod (LocalDate startDate, LocalDate endDate) throws DaoException;//select dance classes for the period (see VisitService) 
	
	public boolean changeForNoActive (Integer danceClassId) throws DaoException;
}
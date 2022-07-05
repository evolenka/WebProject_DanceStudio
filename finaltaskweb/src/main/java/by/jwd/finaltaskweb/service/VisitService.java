package by.jwd.finaltaskweb.service;

import java.time.LocalDate;

import java.util.List;
import java.util.Map;

import by.jwd.finaltaskweb.entity.Status;
import by.jwd.finaltaskweb.entity.Visit;

public interface VisitService extends StudioService<Integer, Visit> {

	public List<Visit> readPlannedByClient(Integer clientId) throws ServiceException;

	public List<Visit> readPlannedByTeacher(Integer teacherId) throws ServiceException;

	public List<Visit> readByClientAndPeriod(Integer clientId, LocalDate startDate, LocalDate endDate)
			throws ServiceException;

	public List<Visit> readByGroupAndPeriod(Integer groupId, LocalDate startDate, LocalDate endDate)
			throws ServiceException;

	public Map<String, Integer> countVisitsForPeriodByAllGroups(LocalDate startDate, LocalDate endDate)
			throws ServiceException;

	public Map<String, Integer> countVisitsForPeriodByTeacherGroups(Integer Teacher, LocalDate startDate,
			LocalDate endDate) throws ServiceException;
	
	public boolean markPresence(Integer visitId, Status status) throws ServiceException;

	public boolean cancelMarkPresence(Integer visitId) throws ServiceException;

}

package by.jwd.finaltaskweb.service.impl;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.dao.DaoException;
import by.jwd.finaltaskweb.dao.DaoFactory;
import by.jwd.finaltaskweb.entity.Client;
import by.jwd.finaltaskweb.entity.DanceClass;
import by.jwd.finaltaskweb.entity.Group;
import by.jwd.finaltaskweb.entity.Membership;
import by.jwd.finaltaskweb.entity.Schedule;
import by.jwd.finaltaskweb.entity.Teacher;
import by.jwd.finaltaskweb.entity.Visit;
import by.jwd.finaltaskweb.entity.WeekDay;
import by.jwd.finaltaskweb.service.DanceClassService;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.StudioServiceImpl;

public class DanceClassServiceImpl extends StudioServiceImpl implements DanceClassService {

	private static Logger logger = LogManager.getLogger(DanceClassServiceImpl.class);

	private DaoFactory factory = DaoFactory.getInstance();

	@Override
	public List<DanceClass> readAll() throws ServiceException {

		List<DanceClass> danceClasses = null;

		try {
			danceClasses = factory.getDanceClassDao(transaction).readAll();
			transaction.close();

		} catch (DaoException e) {
			throw new ServiceException();
		}
		return danceClasses;
	}

	@Override
	public DanceClass readEntityById(Integer id) throws ServiceException {
		DanceClass danceClass = null;

		try {
			danceClass = factory.getDanceClassDao(transaction).readEntityById(id);
			transaction.close();

		} catch (DaoException e) {
			throw new ServiceException();
		}
		return danceClass;
	}

	@Override
	public boolean delete(Integer id) throws ServiceException {
		try {
			factory.getDanceClassDao(transaction).delete(id);
			transaction.close();
		} catch (DaoException e) {
			throw new ServiceException();
		}
		return true;
	}

	@Override
	public boolean create(DanceClass danceClass) throws ServiceException {
		try {
			factory.getDanceClassDao(transaction).create(danceClass);
			transaction.close();
		} catch (DaoException e) {
			throw new ServiceException();
		}
		return true;
	}

	@Override
	public boolean update(DanceClass danceClass) throws ServiceException {
		try {
			factory.getDanceClassDao(transaction).update(danceClass);
			transaction.close();
		} catch (DaoException e) {
			throw new ServiceException();
		}
		return true;
	}

	@Override
	public DanceClass readByDateAndGroup(LocalDate date, Integer groupId) throws ServiceException {

		DanceClass danceClass = null;
		DanceClass danceClassTemp = null;

		try {
			List<Schedule> schedules = factory.getScheduleDao(transaction).readByGroup(groupId);

			for (Schedule schedule : schedules) {
				danceClassTemp = factory.getDanceClassDao(transaction).readByDateAndSchedule(date, schedule);
				if (danceClassTemp != null) {
					danceClass = danceClassTemp;
					logger.debug("danceClass {}", danceClass);
					break;
				}
			}
			if (danceClass != null) {
				Schedule schedule = factory.getScheduleDao(transaction)
						.readEntityById(danceClass.getSchedule().getId());

				Group group = factory.getGroupDao(transaction).readEntityById(schedule.getGroup().getId());

				Teacher teacher = (Teacher) factory.getUserDao(transaction).readEntityById(group.getTeacher().getId());

				group.setTeacher(teacher);

				schedule.setGroup(group);

				danceClass.setSchedule(schedule);

				List<Visit> visits = factory.getVisitDao(transaction).readByDanceClass(danceClass);

				for (Visit visit : visits) {

					Membership membership = factory.getMembershipDao(transaction)
							.readEntityById(visit.getMembership().getId());
					visit.setMembership(membership);
					Client client = (Client) factory.getUserDao(transaction)
							.readEntityById(membership.getClient().getId());
					membership.setClient(client);
					visit.setMembership(membership);
					visit.setDanceClass(danceClass);
				}

				danceClass.setVisits(visits);

				logger.debug("danceClassFinal {}", danceClass);
			}

			transaction.close();
		} catch (DaoException e) {
			throw new ServiceException();
		}
		return danceClass;
	}

	@Override
	public List<LocalDate> readAvailiableDatesByGroup(Integer groupId) throws ServiceException {

		List<LocalDate> availiableDates = new ArrayList<>();

		try {
			List<Schedule> schedules = factory.getScheduleDao(transaction).readByGroup(groupId);

			for (Schedule schedule : schedules) {
				List<DanceClass> danceClasses = factory.getDanceClassDao(transaction).readActiveBySchedule(schedule);

				for (DanceClass danceClass : danceClasses) {
					availiableDates.add(danceClass.getDate());
				}
			}
			transaction.close();
		} catch (DaoException e) {
			throw new ServiceException();
		}
		logger.debug("availiableDates {}", availiableDates);
		return availiableDates;
	}

	@Override
	public List<LocalDate> readAllAvailiableDates() throws ServiceException {

		List<LocalDate> availiableDates = new ArrayList<>();

		try {
			List<DanceClass> danceClasses = factory.getDanceClassDao(transaction).readAll();

			for (DanceClass danceClass : danceClasses) {
				if (!danceClass.getDate().isBefore(LocalDate.now())) {
					availiableDates.add(danceClass.getDate());
					logger.debug(danceClass.getDate());
				}
			}

			transaction.close();
		} catch (

		DaoException e) {
			throw new ServiceException();
		}

		return availiableDates;
	}

	@Override
	public List<DanceClass> readByGroup(Integer groupId) throws ServiceException {

		List<DanceClass> danceClasses = new ArrayList<>();

		try {
			List<Schedule> schedules = factory.getScheduleDao(transaction).readByGroup(groupId);
			for (Schedule schedule : schedules) {
				List<DanceClass> danceClassesBySchedule = factory.getDanceClassDao(transaction)
						.readBySchedule(schedule);

				for (DanceClass danceClass : danceClassesBySchedule) {
					danceClasses.add(danceClass);
				}
			}
			transaction.close();
		} catch (DaoException e) {
			throw new ServiceException();
		}

		return danceClasses;
	}

	@Override
	public boolean createClassesByDateAndGroups(LocalDate date, List<Integer> groupsId) throws ServiceException {
		try {
			for (Integer groupId : groupsId) {

				List<Schedule> schedules = factory.getScheduleDao(transaction).readByGroup(groupId);

				for (Schedule schedule : schedules) {
					if (schedule.getWeekDay().equals(
							WeekDay.valueOf((date.getDayOfWeek().getDisplayName(TextStyle.FULL_STANDALONE, Locale.US))
									.toUpperCase()))) {
						logger.debug("schedule for date {}", schedule);

						DanceClass danceClass = new DanceClass();
						danceClass.setDate(date);
						danceClass.setSchedule(schedule);
						logger.debug("danceClassToCreate {}", danceClass);
						factory.getDanceClassDao(transaction).create(danceClass);
					}
				}
			}
			transaction.close();
		} catch (DaoException e) {
			throw new ServiceException();
		}
		return true;
	}

	@Override
	public List<DanceClass> readActiveByDate(LocalDate date) throws ServiceException {
		List<DanceClass> danceClasses = null;

		try {
			danceClasses = factory.getDanceClassDao(transaction).readActiveByDate(date);

			for (DanceClass danceClass : danceClasses) {

				Schedule schedule = factory.getScheduleDao(transaction)
						.readEntityById(danceClass.getSchedule().getId());

				Group group = factory.getGroupDao(transaction).readEntityById(schedule.getGroup().getId());

				Teacher teacher = (Teacher) factory.getUserDao(transaction).readEntityById(group.getTeacher().getId());

				group.setTeacher(teacher);

				schedule.setGroup(group);

				danceClass.setSchedule(schedule);
			}

			transaction.close();
		} catch (DaoException e) {
			throw new ServiceException();
		}
		logger.debug("danceClasses {}", danceClasses);
		return danceClasses;
	}

	@Override
	public boolean changeForNoActive(Integer danceClassId) throws ServiceException {
		boolean res = false;
		try {
			if (factory.getDanceClassDao(transaction).changeForNoActive(danceClassId)) {
				res = true;
			}
			transaction.close();
		} catch (DaoException e) {
			throw new ServiceException();
		}
		return res;
	}
}

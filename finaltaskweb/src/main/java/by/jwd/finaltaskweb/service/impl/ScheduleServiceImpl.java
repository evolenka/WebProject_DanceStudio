package by.jwd.finaltaskweb.service.impl;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.dao.DaoException;
import by.jwd.finaltaskweb.dao.DaoFactory;
import by.jwd.finaltaskweb.entity.Group;
import by.jwd.finaltaskweb.entity.Schedule;
import by.jwd.finaltaskweb.entity.Teacher;
import by.jwd.finaltaskweb.entity.WeekDay;
import by.jwd.finaltaskweb.service.ScheduleService;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.StudioServiceImpl;

public class ScheduleServiceImpl extends StudioServiceImpl implements ScheduleService {

	private static Logger logger = LogManager.getLogger(ScheduleServiceImpl.class);

	private DaoFactory factory = DaoFactory.getInstance();

	@Override
	public List<Schedule> readAll() throws ServiceException {

		List<Schedule> schedules = new ArrayList<>();
		try {
			schedules = factory.getScheduleDao(transaction).readAll();
			for (Schedule schedule : schedules) {
				Group group = factory.getGroupDao(transaction).readEntityById(schedule.getGroup().getId());
				schedule.setGroup(group);
			}
			transaction.close();
		} catch (DaoException e) {
			throw new ServiceException();
		}
		return schedules;
	}

	@Override
	public Schedule readEntityById(Integer id) throws ServiceException {

		Schedule schedule = new Schedule();
		try {
			schedule = factory.getScheduleDao(transaction).readEntityById(id);
			transaction.close();

		} catch (DaoException e) {
			throw new ServiceException();
		}
		return schedule;
	}

	@Override
	public boolean create(Schedule schedule) throws ServiceException {
		try {
			factory.getScheduleDao(transaction).create(schedule);
			transaction.close();
		} catch (DaoException e) {
			throw new ServiceException();
		}
		return false;
	}

	@Override
	public boolean update(Schedule schedule) throws ServiceException {
		try {
			factory.getScheduleDao(transaction).update(schedule);
			transaction.close();

		} catch (DaoException e) {
			throw new ServiceException();
		}
		return false;
	}

	@Override
	public boolean delete(Integer id) throws ServiceException {
		try {
			factory.getScheduleDao(transaction).delete(id);
			transaction.close();

		} catch (DaoException e) {
			throw new ServiceException();
		}
		return false;
	}

	@Override
	public EnumMap<WeekDay, List<Schedule>> allScheduleByWeekDay() throws ServiceException {

		EnumMap<WeekDay, List<Schedule>> scheduleGroupByDay = new EnumMap<>(WeekDay.class);

		try {

			List<Schedule> scheduleMonday = factory.getScheduleDao(transaction).readByWeekDay(WeekDay.MONDAY);
			for (Schedule schedule : scheduleMonday) {
				Group group = factory.getGroupDao(transaction).readEntityById(schedule.getGroup().getId());
			logger.debug("group {}", group);
				if (group != null) {
				group.setTeacher((Teacher) factory.getUserDao(transaction).readEntityById(group.getTeacher().getId()));
				schedule.setGroup(group);
				}
			}
			scheduleGroupByDay.put(WeekDay.MONDAY, scheduleMonday);
			logger.debug("sch {}", scheduleMonday);

			List<Schedule> scheduleTuesday = factory.getScheduleDao(transaction).readByWeekDay(WeekDay.TUESDAY);
			for (Schedule schedule : scheduleTuesday) {
				Group group = factory.getGroupDao(transaction).readEntityById(schedule.getGroup().getId());
				if (group !=null) {
				group.setTeacher((Teacher) factory.getUserDao(transaction).readEntityById(group.getTeacher().getId()));
				schedule.setGroup(group);
				}
			}
			scheduleGroupByDay.put(WeekDay.TUESDAY, scheduleTuesday);

			List<Schedule> scheduleWednesday = factory.getScheduleDao(transaction).readByWeekDay(WeekDay.WEDNESDAY);
			for (Schedule schedule : scheduleWednesday) {
				Group group = factory.getGroupDao(transaction).readEntityById(schedule.getGroup().getId());
				if (group !=null) {
				group.setTeacher((Teacher) factory.getUserDao(transaction).readEntityById(group.getTeacher().getId()));
				schedule.setGroup(group);
				}
			}
			scheduleGroupByDay.put(WeekDay.WEDNESDAY, scheduleWednesday);

			List<Schedule> scheduleThursday = factory.getScheduleDao(transaction).readByWeekDay(WeekDay.THURSDAY);
			for (Schedule schedule : scheduleThursday) {
				Group group = factory.getGroupDao(transaction).readEntityById(schedule.getGroup().getId());
				if (group !=null) {
				group.setTeacher((Teacher) factory.getUserDao(transaction).readEntityById(group.getTeacher().getId()));
				schedule.setGroup(group);
				}
			}
			scheduleGroupByDay.put(WeekDay.THURSDAY, scheduleThursday);

			List<Schedule> scheduleFriday = factory.getScheduleDao(transaction).readByWeekDay(WeekDay.FRIDAY);
			for (Schedule schedule : scheduleFriday) {
				Group group = factory.getGroupDao(transaction).readEntityById(schedule.getGroup().getId());
				if (group !=null) {
				group.setTeacher((Teacher) factory.getUserDao(transaction).readEntityById(group.getTeacher().getId()));
				schedule.setGroup(group);
				}
			}
			scheduleGroupByDay.put(WeekDay.FRIDAY, scheduleFriday);

			transaction.close();
		} catch (DaoException e) {
			throw new ServiceException();
		}
		return scheduleGroupByDay;
	}
}
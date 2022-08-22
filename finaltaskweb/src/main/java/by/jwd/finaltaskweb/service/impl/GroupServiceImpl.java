package by.jwd.finaltaskweb.service.impl;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.dao.DaoException;
import by.jwd.finaltaskweb.dao.DaoFactory;
import by.jwd.finaltaskweb.entity.DanceClass;
import by.jwd.finaltaskweb.entity.Group;
import by.jwd.finaltaskweb.entity.Level;
import by.jwd.finaltaskweb.entity.Schedule;
import by.jwd.finaltaskweb.entity.Teacher;
import by.jwd.finaltaskweb.entity.WeekDay;
import by.jwd.finaltaskweb.service.GroupService;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.StudioServiceImpl;

public class GroupServiceImpl extends StudioServiceImpl implements GroupService {

	private static Logger logger = LogManager.getLogger(GroupServiceImpl.class);

	private DaoFactory factory = DaoFactory.getInstance();

	@Override
	public List<Group> readAll() throws ServiceException {

		List<Group> groupsTemp = null;
		List<Group> groups = new ArrayList<>();

		try {
			groupsTemp = factory.getGroupDao(transaction).readAll();

			for (Group group : groupsTemp) {
				Teacher teacher = (Teacher) factory.getUserDao(transaction).readEntityById(group.getTeacher().getId());
				group.setTeacher(teacher);
				List<Schedule> schedules = factory.getScheduleDao(transaction).readByGroup(group.getId());
				group.setSchedule(schedules);
				groups.add(group);
			}
			transaction.close();

		} catch (DaoException e) {
			throw new ServiceException();
		}
		logger.debug("groups{}", groups);
		return groups;
	}

	@Override
	public Group readEntityById(Integer id) throws ServiceException {

		Group group = null;

		try {
			group = factory.getGroupDao(transaction).readEntityById(id);

			Teacher teacher = (Teacher) factory.getUserDao(transaction).readEntityById(group.getTeacher().getId());
			group.setTeacher(teacher);
			List<Schedule> schedules = factory.getScheduleDao(transaction).readByGroup(group.getId());
			logger.debug("schedules {}", schedules);
			group.setSchedule(schedules);

			transaction.close();

		} catch (DaoException e) {
			throw new ServiceException();
		}
		logger.debug("group {}", group);
		return group;
	}

	@Override
	public boolean delete(Integer id) throws ServiceException {
		try {
					
			List <Schedule> schedules = factory.getScheduleDao(transaction).readByGroup(id);
			
			for (Schedule schedule : schedules) {
						factory.getScheduleDao(transaction).delete(schedule.getId());
						logger.debug("schedule for group has been deleted");
			}
			factory.getGroupDao(transaction).delete(id);
			
			transaction.close();

		} catch (DaoException e) {

			try {
				transaction.rollback();
			} catch (DaoException e1) {
				logger.debug("rollback error");
				throw new ServiceException();
			}
		} finally {
			try {
				transaction.setAutoCommit(true);
			} catch (DaoException e1) {
				logger.debug("setAutoCommit error");
				throw new ServiceException();

			}
		}
		return true;
	}
		

	@Override
	public boolean create(Group group) throws ServiceException {

		try {
			Integer groupId = factory.getGroupDao(transaction).createGroup(group);
			logger.debug("groupId {}", groupId);

			Group createdGroup = factory.getGroupDao(transaction).readEntityById(groupId);
			logger.debug("group {}", createdGroup);

			for (Schedule schedule : group.getSchedule()) {
				schedule.setGroup(createdGroup);
				factory.getScheduleDao(transaction).create(schedule);
			}
			transaction.close();

		} catch (DaoException e) {

			try {
				transaction.rollback();
			} catch (DaoException e1) {
				logger.debug("rollback error");
				throw new ServiceException();
			}
		} finally {
			try {
				transaction.setAutoCommit(true);
			} catch (DaoException e1) {
				logger.debug("setAutoCommit error");
				throw new ServiceException();

			}
		}
		return true;
	}

	@Override
	public boolean update(Group group) throws ServiceException {

		try {

			factory.getGroupDao(transaction).update(group);
			
			Group groupBeforeUpdate = this.readEntityById(group.getId());
			logger.debug("groupBeforeUpdate {}", groupBeforeUpdate);

			List<Schedule> schedulesBefore = groupBeforeUpdate.getSchedule();
			logger.debug("schedulesBefore {}", schedulesBefore);

			List<Schedule> schedulesAfter = group.getSchedule();
			logger.debug("schedulesAfter {}", schedulesAfter);

			for (int i = 0; i < schedulesBefore.size(); i++) {

				schedulesAfter.get(i).setId(schedulesBefore.get(i).getId());
				logger.debug("scheduleAfterId {}", schedulesAfter.get(i).getId());

				factory.getScheduleDao(transaction).update(schedulesAfter.get(i));
				schedulesBefore.remove(schedulesBefore.get(i));
				schedulesAfter.remove(schedulesAfter.get(i));
			}
			logger.debug("schedulesBefore {}", schedulesBefore);
			logger.debug("schedulesAfter {}", schedulesAfter);

			if (!schedulesBefore.isEmpty()) {
				for (Schedule scheduleBefore : schedulesBefore) {
					Group groupNull = new Group();
					scheduleBefore.setGroup(groupNull);
					logger.debug("scheduleToUpdate {}", scheduleBefore);
					factory.getScheduleDao(transaction).update(scheduleBefore);
				}
			}

			if (!schedulesAfter.isEmpty()) {
				for (Schedule scheduleAfter : schedulesAfter) {
					scheduleAfter.setGroup(group);
					logger.debug("scheduleToCreate {}", scheduleAfter.toString());
					factory.getScheduleDao(transaction).create(scheduleAfter);
				}
			}

			transaction.close();

		} catch (DaoException e) {

			try {
				transaction.rollback();
			} catch (DaoException e1) {
				logger.debug("rollback error");
				throw new ServiceException();
			}
		} finally {
			try {
				transaction.setAutoCommit(true);
			} catch (DaoException e1) {
				logger.debug("setAutoCommit error");
				throw new ServiceException();

			}
		}
		return true;
	}

	@Override
	public List<Group> readByLevel(Level level) throws ServiceException {

		List<Group> groups = new ArrayList<>();

		try {
			List<Group> tempGroups = factory.getGroupDao(transaction).readByLevel(level);

			for (Group group : tempGroups) {
				Teacher teacher = (Teacher) factory.getUserDao(transaction).readEntityById(group.getTeacher().getId());
				group.setTeacher(teacher);
				List<Schedule> schedules = factory.getScheduleDao(transaction).readByGroup(group.getId());
				group.setSchedule(schedules);
				groups.add(group);
			}

			transaction.close();

		} catch (DaoException e) {
			throw new ServiceException();
		}
		logger.debug("groups {}", groups);
		return groups;
	}

	@Override
	public List<Group> readByWeekDay(List<WeekDay> weekDays) throws ServiceException {
		List<Group> groups = new ArrayList<>();

		try {
			for (WeekDay day : weekDays) {
				List<Schedule> schedules = factory.getScheduleDao(transaction).readByWeekDay(day);

				for (Schedule schedule : schedules) {

					Group group = factory.getGroupDao(transaction).readEntityById(schedule.getGroup().getId());

					Teacher teacher = (Teacher) factory.getUserDao(transaction)
							.readEntityById(group.getTeacher().getId());

					group.setTeacher(teacher);

					List<Schedule> groupSchedules = factory.getScheduleDao(transaction).readByGroup(group.getId());
					group.setSchedule(groupSchedules);

					if (!groups.contains(group)) {
						groups.add(group);
						logger.debug(group);
					}
				}
			}
			transaction.close();
		} catch (DaoException e) {
			throw new ServiceException();
		}

		logger.debug("groups {}", groups);
		return groups;

	}

	@Override
	public List<Group> readByDanceStyle(String dancestyle) throws ServiceException {

		List<Group> teachersGroup = new ArrayList<>();
		List<Group> groups = new ArrayList<>();
		try {
			List<Teacher> teachers = factory.getUserDao(transaction).readByDanceStyle(dancestyle);
			logger.debug("teachers {}", teachers);

			for (Teacher teacher : teachers) {
				teachersGroup = readByTeacher(teacher.getId());

				for (Group group : teachersGroup) {
					if (!groups.contains(group)) {
						group.setTeacher(teacher);

						List<Schedule> schedules = factory.getScheduleDao(transaction).readByGroup(group.getId());
						group.setSchedule(schedules);
						groups.add(group);
					}
				}
			}
			transaction.close();

		} catch (DaoException e) {
			throw new ServiceException();
		}
		logger.debug("groups {}", groups);
		return groups;
	}

	@Override
	public List<Group> readByDate(LocalDate date) throws ServiceException {

		List<Group> groups = new ArrayList<>();
		try {
			if (date.isAfter(LocalDate.now()) || date.isEqual(LocalDate.now())) {
				List<DanceClass> classes = factory.getDanceClassDao(transaction).readActiveByDate(date);

				for (DanceClass danceClass : classes) {
					Schedule schedule = factory.getScheduleDao(transaction)
							.readEntityById(danceClass.getSchedule().getId());
					Group group = factory.getGroupDao(transaction).readEntityById(schedule.getGroup().getId());
					if (group != null) {
						Teacher teacher = (Teacher) factory.getUserDao(transaction)
								.readEntityById(group.getTeacher().getId());
						group.setTeacher(teacher);
						List<Schedule> schedules = factory.getScheduleDao(transaction).readByGroup(group.getId());
						group.setSchedule(schedules);
						groups.add(group);
					}
				}
			}
			transaction.close();

		} catch (DaoException e) {
			throw new ServiceException();
		}
		logger.debug("groups {}", groups);
		return groups;
	}

	@Override
	public List<Group> readByTeacher(Integer teacherId) throws ServiceException {

		List<Group> groups = new ArrayList<>();
		try {
			groups = factory.getGroupDao(transaction).readByTeacherId(teacherId);
			Teacher teacher = (Teacher) factory.getUserDao(transaction).readEntityById(teacherId);
			for (Group group : groups) {
				group.setTeacher(teacher);
				List<Schedule> schedules = factory.getScheduleDao(transaction).readByGroup(group.getId());
				group.setSchedule(schedules);
			}

			transaction.close();

		} catch (DaoException e) {
			throw new ServiceException();
		}
		logger.debug("groups {}", groups);
		return groups;
	}

	@Override
	public Group readByTitle(String title) throws ServiceException {
		Group group = null;

		try {
			group = factory.getGroupDao(transaction).readByTitle(title);

			transaction.close();

		} catch (DaoException e) {
			throw new ServiceException();
		}
		logger.debug("group {}", group);
		return group;
	}
}
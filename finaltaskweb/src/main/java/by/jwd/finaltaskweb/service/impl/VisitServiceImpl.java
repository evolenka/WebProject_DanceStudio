package by.jwd.finaltaskweb.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.dao.DaoException;
import by.jwd.finaltaskweb.dao.DaoFactory;
import by.jwd.finaltaskweb.entity.Client;
import by.jwd.finaltaskweb.entity.DanceClass;
import by.jwd.finaltaskweb.entity.Group;
import by.jwd.finaltaskweb.entity.Membership;
import by.jwd.finaltaskweb.entity.MembershipType;
import by.jwd.finaltaskweb.entity.Schedule;
import by.jwd.finaltaskweb.entity.Status;
import by.jwd.finaltaskweb.entity.Teacher;
import by.jwd.finaltaskweb.entity.Type;
import by.jwd.finaltaskweb.entity.Visit;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.StudioServiceImpl;
import by.jwd.finaltaskweb.service.VisitService;

public class VisitServiceImpl extends StudioServiceImpl implements VisitService {

	private static Logger logger = LogManager.getLogger(VisitServiceImpl.class);

	private DaoFactory factory = DaoFactory.getInstance();

	@Override
	public List<Visit> readAll() throws ServiceException {

		List<Visit> visits = new ArrayList<>();

		try {
			visits = factory.getVisitDao(transaction).readAll();
			transaction.close();

		} catch (DaoException e) {
			throw new ServiceException();
		}
		return visits;
	}

	@Override
	public Visit readEntityById(Integer id) throws ServiceException {
		Visit visit = null;
		try {
			visit = factory.getVisitDao(transaction).readEntityById(id);
			transaction.close();

		} catch (DaoException e) {
			throw new ServiceException();
		}
		return visit;
	}

	@Override
	public boolean delete(Integer id) throws ServiceException {

		try {
			factory.getVisitDao(transaction).delete(id);
			transaction.close();

		} catch (DaoException e) {
			throw new ServiceException();
		}
		return true;
	}

	@Override
	public boolean create(Visit visit) throws ServiceException {
		try {
			factory.getVisitDao(transaction).create(visit);
			transaction.close();

		} catch (DaoException e) {
			throw new ServiceException();
		}
		return true;
	}

	@Override
	public boolean update(Visit visit) throws ServiceException {
		try {
			factory.getVisitDao(transaction).update(visit);
			transaction.close();

		} catch (DaoException e) {
			throw new ServiceException();
		}
		return true;
	}

	@Override
	public List<Visit> readPlannedByClient(Integer clientId) throws ServiceException {

		List<Visit> visits = new ArrayList<>();

		try {

			List<Membership> memberships = factory.getMembershipDao(transaction).readValidByClient(clientId);

			for (Membership membership : memberships) {

				List<Visit> visitByMembership = factory.getVisitDao(transaction).readPlannedByMembership(membership);

				membership = factory.getMembershipDao(transaction).readEntityById(membership.getId());

				for (Visit visit : visitByMembership) {
					visit.setMembership(membership);

					DanceClass danceClass = factory.getDanceClassDao(transaction)
							.readEntityById(visit.getDanceClass().getId());

					Schedule schedule = factory.getScheduleDao(transaction)
							.readEntityById(danceClass.getSchedule().getId());

					Group group = factory.getGroupDao(transaction).readEntityById(schedule.getGroup().getId());
					if (group != null) {
						Teacher teacher = (Teacher) factory.getUserDao(transaction)
								.readEntityById(group.getTeacher().getId());

						group.setTeacher(teacher);
					}
					schedule.setGroup(group);
					danceClass.setSchedule(schedule);
					visit.setDanceClass(danceClass);

					visits.add(visit);

				}
			}
			transaction.close();
		} catch (DaoException e) {
			throw new ServiceException();
		}
		logger.debug("visits {}", visits);
		return visits;
	}

	@Override
	public List<Visit> readPlannedByTeacher(Integer teacherId) throws ServiceException {

		List<Visit> visits = null;
		List<Visit> plannedVisits = new ArrayList<>();

		try {

			// find all planned visits
			visits = factory.getVisitDao(transaction).readAllPlanned();
			logger.debug("all planned visits {}", visits);

			// find all planned dance classes of the given teacher
			List<Group> groups = factory.getGroupDao(transaction).readByTeacherId(teacherId);
			List<DanceClass> danceClassesByTeacher = new ArrayList<>();

			for (Group groupByTeacher : groups) {
				List<Schedule> schedules = factory.getScheduleDao(transaction).readByGroup(groupByTeacher.getId());
				for (Schedule scheduleByTeacher : schedules) {

					List<DanceClass> danceClasses = factory.getDanceClassDao(transaction)
							.readBySchedule(scheduleByTeacher);

					for (DanceClass danceClass : danceClasses) {
						danceClassesByTeacher.add(danceClass);
					}
				}
			}

			logger.debug("all dance classes of the teacher {}", danceClassesByTeacher);

			// find all planned visits of the given teacher`s classes
			for (Visit visit : visits) {
				for (DanceClass danceClass : danceClassesByTeacher) {
					if (visit.getDanceClass().getId() == danceClass.getId()) {
						plannedVisits.add(visit);
					}
				}
			}

			// fill stub visit objects by data
			for (Visit visit : plannedVisits) {

				Membership membership = factory.getMembershipDao(transaction)
						.readEntityById(visit.getMembership().getId());
				visit.setMembership(membership);

				DanceClass danceClass = factory.getDanceClassDao(transaction)
						.readEntityById(visit.getDanceClass().getId());

				Client client = (Client) factory.getUserDao(transaction).readEntityById(membership.getClient().getId());
				membership.setClient(client);
				visit.setMembership(membership);

				if (danceClass.getVisits() == null) {
					danceClass.setVisits(new ArrayList<>());
				}

				if (!(danceClass.getVisits().contains(visit))) {
					danceClass.getVisits().add(visit);
				}

				Schedule schedule = factory.getScheduleDao(transaction)
						.readEntityById(danceClass.getSchedule().getId());

				Group group = factory.getGroupDao(transaction).readEntityById(schedule.getGroup().getId());
				schedule.setGroup(group);
				danceClass.setSchedule(schedule);

				visit.setDanceClass(danceClass);
			}

			transaction.close();
		} catch (DaoException e) {
			throw new ServiceException();
		}
		logger.debug("planned visits by teacher {}", plannedVisits);
		return plannedVisits;
	}

	@Override
	public List<Visit> readByClientAndPeriod(Integer clientId, LocalDate startDate, LocalDate endDate)
			throws ServiceException {

		List<Visit> visits = new ArrayList<>();

		try {

			List<Membership> memberships = factory.getMembershipDao(transaction).readValidByClient(clientId);

			List<DanceClass> danceClasses = factory.getDanceClassDao(transaction).readByPeriod(startDate, endDate);

			for (Membership membership : memberships) {

				for (DanceClass danceClass : danceClasses) {

					Visit visit = factory.getVisitDao(transaction).readByMembershipAndDanceClass(membership,
							danceClass);

					if (visit != null) {

						membership = factory.getMembershipDao(transaction).readEntityById(membership.getId());
						visit.setMembership(membership);

						danceClass = factory.getDanceClassDao(transaction).readEntityById(danceClass.getId());

						Schedule schedule = factory.getScheduleDao(transaction)
								.readEntityById(danceClass.getSchedule().getId());

						Group group = factory.getGroupDao(transaction).readEntityById(schedule.getGroup().getId());
						if (group != null) {
							Teacher teacher = (Teacher) factory.getUserDao(transaction)
									.readEntityById(group.getTeacher().getId());

							group.setTeacher(teacher);
						}
						schedule.setGroup(group);
						danceClass.setSchedule(schedule);
						visit.setDanceClass(danceClass);

						visits.add(visit);
					}
				}
			}

			transaction.close();
		} catch (

		DaoException e) {
			throw new ServiceException();
		}
		logger.debug("visits {}", visits);
		return visits;
	}

	@Override
	public List<Visit> readByGroupAndPeriod(Integer groupId, LocalDate startDate, LocalDate endDate)
			throws ServiceException {

		List<Visit> result = new ArrayList<>();
		List<Visit> visits = null;
		try {
			List<DanceClass> danceClasses = factory.getDanceClassDao(transaction).readByPeriod(startDate, endDate);

			// fill bean with data
			for (DanceClass danceClass : danceClasses) {

				Schedule schedule = factory.getScheduleDao(transaction)
						.readEntityById(danceClass.getSchedule().getId());

				Group group = factory.getGroupDao(transaction).readEntityById(schedule.getGroup().getId());
				schedule.setGroup(group);
				danceClass.setSchedule(schedule);

				// select visits of the class for the given group
				if (danceClass.getSchedule().getGroup().getId() == groupId) {
					visits = factory.getVisitDao(transaction).readByDanceClass(danceClass);
				}
				if (visits != null) {
					for (Visit visit : visits) {
						if(!result.contains(visit)) {
						result.add(visit);
						}
					}
				}
			}
			transaction.close();
		} catch (DaoException e) {
			throw new ServiceException();
		}
		logger.debug("visits for group {}", result);
		return result;
	}

	@Override
	public Map<String, Integer> countVisitsForPeriodByAllGroups(LocalDate startDate, LocalDate endDate)
			throws ServiceException {

		Map<String, Integer> countVisits = new HashMap<>();
		try {
			List<Group> groups = factory.getGroupDao(transaction).readAll();

			for (Group group : groups) {
			
				int count = 0;
				List<Visit> visits = this.readByGroupAndPeriod(group.getId(), startDate, endDate);
				
				for (Visit visit : visits) {
					if (visit.getStatus() == Status.ATTENDED) {
						count++;
					}
				}
				countVisits.put(group.getTitle(), count);

			}
			transaction.close();
		} catch (DaoException e) {
			throw new ServiceException();
		}

		return countVisits;
	}

	@Override
	public Map<String, Integer> countVisitsForPeriodByTeacherGroups(Integer teacherId, LocalDate startDate,
			LocalDate endDate) throws ServiceException {

		Map<String, Integer> countVisits = new HashMap<>();

		try {
			List<Group> groups = factory.getGroupDao(transaction).readByTeacherId(teacherId);

			for (Group group : groups) {
				int count = 0;
				List<Visit> visits = this.readByGroupAndPeriod(group.getId(), startDate, endDate);

				if (visits != null) {
					for (Visit visit : visits) {
						if (visit.getStatus() == Status.ATTENDED) {
							count++;
						}
					}
				}
				countVisits.put(group.getTitle(), count);
			}
			transaction.close();
		} catch (DaoException e) {
			throw new ServiceException();
		}
		return countVisits;
	}

	@Override
	public boolean markPresence(Integer visitId, Status status) throws ServiceException {
		boolean result = false;
		try {
			Visit visit = factory.getVisitDao(transaction).readEntityById(visitId);
			Membership membership = factory.getMembershipDao(transaction).readEntityById(visit.getMembership().getId());
			MembershipType type = factory.getMembershipDao(transaction).readTypeById(membership.getType().getId());
			membership.setType(type);
			visit.setMembership(membership);
			logger.debug("membership {}", membership);

			/* additional check whether the client membership is valid */
			if ((Status.ATTENDED == status) && (visit.getMembership().getBalanceClassQuantity() == 0)
					&& (visit.getMembership().getType().getTitle() != Type.UNLIM)) {
				logger.debug("membership is not valid");

			} else if ((visit.getStatus() != Status.PLANNED)) {
				logger.debug("visit status is not valid for marking presence");

			} else {
				transaction.setAutoCommit(false);
				factory.getVisitDao(transaction).updateStatus(visit, status);

				if ((Status.ATTENDED == status) || (visit.getMembership().getType().getTitle() != Type.UNLIM)) {
					factory.getMembershipDao(transaction).decreasebalanceClassQuantity(visit.getMembership().getId());
					logger.debug("membership balance quantity has been decreased");
				}
				transaction.commit();
				logger.debug("presence has been marked successfully");
				transaction.close();
				result = true;
			}

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
			}
		}
		return result;
	}

	@Override
	public boolean cancelMarkPresence(Integer visitId) throws ServiceException {
		boolean result = false;
		try {
			Visit visit = factory.getVisitDao(transaction).readEntityById(visitId);
			Membership membership = factory.getMembershipDao(transaction).readEntityById(visit.getMembership().getId());
			visit.setMembership(membership);

			Status current = visit.getStatus();
			logger.debug("current status {}", current);
			if (current != Status.PLANNED) {
				transaction.setAutoCommit(false);
				factory.getVisitDao(transaction).cancelUpdateStatus(visit);

				if (Status.ATTENDED == current) {
					factory.getMembershipDao(transaction).increasebalanceClassQuantity(visit.getMembership().getId());
					logger.debug("membership balance quantity has been increased");
				}
				transaction.commit();
				logger.debug("precense has been cancelled");
				transaction.close();

				result = true;
			}
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
			}

		}
		return result;
	}
}
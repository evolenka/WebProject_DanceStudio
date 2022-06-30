package by.jwd.finaltaskweb.service;

import by.jwd.finaltaskweb.dao.DaoException;
import by.jwd.finaltaskweb.dao.TransactionFactoryImpl;
import by.jwd.finaltaskweb.service.builder.BuilderClient;
import by.jwd.finaltaskweb.service.builder.BuilderGroup;
import by.jwd.finaltaskweb.service.builder.BuilderMembership;
import by.jwd.finaltaskweb.service.builder.BuilderSchedule;
import by.jwd.finaltaskweb.service.builder.BuilderTeacher;
import by.jwd.finaltaskweb.service.builder.BuilderVisit;
import by.jwd.finaltaskweb.service.impl.DanceClassServiceImpl;
import by.jwd.finaltaskweb.service.impl.GroupServiceImpl;
import by.jwd.finaltaskweb.service.impl.MembershipServiceImpl;
import by.jwd.finaltaskweb.service.impl.ScheduleServiceImpl;
import by.jwd.finaltaskweb.service.impl.UserServiceImpl;
import by.jwd.finaltaskweb.service.impl.VisitServiceImpl;

public class ServiceFactory {

	private static final ServiceFactory instance = new ServiceFactory();
	private static final TransactionFactoryImpl factory = TransactionFactoryImpl.getInstance();

	private StudioServiceImpl userservice = new UserServiceImpl();
	private StudioServiceImpl membershipService = new MembershipServiceImpl();
	private StudioServiceImpl danceClassService = new DanceClassServiceImpl();
	private StudioServiceImpl groupService = new GroupServiceImpl();
	private StudioServiceImpl visitService = new VisitServiceImpl();
	private StudioServiceImpl scheduleService = new ScheduleServiceImpl();
	private BuilderClient clientBuilder = new BuilderClient();
	private BuilderTeacher teacherBuilder = new BuilderTeacher();
	private BuilderMembership membershipBuilder = new BuilderMembership();
	private BuilderVisit visitbuilder = new BuilderVisit();
	private BuilderGroup groupbuilder = new BuilderGroup();
	private BuilderSchedule schedulebuilder = new BuilderSchedule();

	private ServiceFactory() {
	}

	public static ServiceFactory getInstance() {
		return instance;
	}

	public UserService getUserService() throws ServiceException {

		try {
			userservice.setTransaction(factory.createTransaction());
		} catch (DaoException e) {
			throw new ServiceException();
		}
		return (UserService) userservice;
	}

	public MembershipService getMembershipService() throws ServiceException {

		try {
			membershipService.setTransaction(factory.createTransaction());
		} catch (DaoException e) {
			throw new ServiceException();
		}
		return (MembershipService) membershipService;
	}

	public DanceClassService getDanceClassService() throws ServiceException {

		try {
			danceClassService.setTransaction(factory.createTransaction());
		} catch (DaoException e) {
			throw new ServiceException();
		}
		return (DanceClassService) danceClassService;
	}

	public VisitService getVisitService() throws ServiceException {

		try {
			visitService.setTransaction(factory.createTransaction());
		} catch (DaoException e) {
			throw new ServiceException();
		}
		return (VisitService) visitService;
	}

	public GroupService getGroupService() throws ServiceException {

		try {
			groupService.setTransaction(factory.createTransaction());
		} catch (DaoException e) {
			throw new ServiceException();
		}
		return (GroupService) groupService;
	}

	public ScheduleServiceImpl getScheduleService() throws ServiceException {

		try {
			scheduleService.setTransaction(factory.createTransaction());
		} catch (DaoException e) {
			throw new ServiceException();
		}
		return (ScheduleServiceImpl) scheduleService;
	}

	public BuilderClient getClientBuilder() {
		return clientBuilder;
	}

	public BuilderMembership getMembershipBuilder() {
		return membershipBuilder;
	}

	public BuilderVisit getVisitbuilder() {
		return visitbuilder;
	}

	public BuilderTeacher getTeacherBuilder() {
		return teacherBuilder;
	}

	public BuilderGroup getGroupbuilder() {
		return groupbuilder;
	}

	public BuilderSchedule getSchedulebuilder() {
		return schedulebuilder;
	}	
}
package by.jwd.finaltaskweb.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.impl.CancelUpdateStatusCommandImpl;
import by.jwd.finaltaskweb.controller.impl.CancelVisitCommandImpl;
import by.jwd.finaltaskweb.controller.impl.ChooseMembershipCommandImpl;
import by.jwd.finaltaskweb.controller.impl.ConfirmVisitCommandImpl;
import by.jwd.finaltaskweb.controller.impl.CreateClassesCommandImpl;
import by.jwd.finaltaskweb.controller.impl.CreateMembershipCommandImpl;
import by.jwd.finaltaskweb.controller.impl.CreateVisitCommandImpl;
import by.jwd.finaltaskweb.controller.impl.DeleteClientCommandImpl;
import by.jwd.finaltaskweb.controller.impl.DeleteGroupCommandImpl;
import by.jwd.finaltaskweb.controller.impl.DeleteTeacherCommandImpl;
import by.jwd.finaltaskweb.controller.impl.ChooseClientToEditCommandImpl;
import by.jwd.finaltaskweb.controller.impl.ChooseGroupToEditCommandImpl;
import by.jwd.finaltaskweb.controller.impl.ChooseTeacherToEditCommandImpl;
import by.jwd.finaltaskweb.controller.impl.CloseClassForEnrollmentCommandImpl;
import by.jwd.finaltaskweb.controller.impl.EmptyCommandImpl;
import by.jwd.finaltaskweb.controller.impl.CreateGroupCommandImpl;
import by.jwd.finaltaskweb.controller.impl.LoginationCommandImpl;
import by.jwd.finaltaskweb.controller.impl.LogoutCommandImpl;
import by.jwd.finaltaskweb.controller.impl.MembershipsStaticticsCommandImpl;
import by.jwd.finaltaskweb.controller.impl.ReadActiveClassesByDateComandImpl;
import by.jwd.finaltaskweb.controller.impl.ReadAllClientCommandImpl;
import by.jwd.finaltaskweb.controller.impl.ReadAllGroupCommandImpl;
import by.jwd.finaltaskweb.controller.impl.ReadAllLevelCommandImpl;
import by.jwd.finaltaskweb.controller.impl.ReadAllMembershipTypesCommandImpl;
import by.jwd.finaltaskweb.controller.impl.ReadAllScheduleCommandImpl;
import by.jwd.finaltaskweb.controller.impl.ReadAllStyleCommandImpl;
import by.jwd.finaltaskweb.controller.impl.ReadAllTeacherCommandImpl;
import by.jwd.finaltaskweb.controller.impl.ReadAllTeacherByAdminCommandImpl;
import by.jwd.finaltaskweb.controller.impl.ReadAllWeekDayCommandImpl;
import by.jwd.finaltaskweb.controller.impl.ReadClientsByDanceClassCommandImpl;
import by.jwd.finaltaskweb.controller.impl.ReadGroupForEnrollmentByDateCommandImpl;
import by.jwd.finaltaskweb.controller.impl.ReadGroupsByDateCommandImpl;
import by.jwd.finaltaskweb.controller.impl.ReadGroupByLevelCommandImpl;
import by.jwd.finaltaskweb.controller.impl.ReadGroupByScheduleCommandImpl;
import by.jwd.finaltaskweb.controller.impl.ReadGroupByStyleCommandImpl;
import by.jwd.finaltaskweb.controller.impl.ReadGroupsByTeacherCommandImpl;
import by.jwd.finaltaskweb.controller.impl.ReadMembershipByClientAndPeriodCommandImpl;
import by.jwd.finaltaskweb.controller.impl.ReadPlannedClassesByTeacherCommandImpl;
import by.jwd.finaltaskweb.controller.impl.ReadPlannedVisitsByClientCommandImpl;
import by.jwd.finaltaskweb.controller.impl.ReadValidMembershipsByClientCommandImpl;
import by.jwd.finaltaskweb.controller.impl.ReadVisitsByClientAndPeriodCommandImpl;
import by.jwd.finaltaskweb.controller.impl.ReadVisitsCountByGroupsAndPeriodCommandImpl;
import by.jwd.finaltaskweb.controller.impl.ReadVisitsCountByTeacherGroupsAndPeriodCommandImpl;
import by.jwd.finaltaskweb.controller.impl.ReadDanceClassByGroupAndDateCommandImpl;
import by.jwd.finaltaskweb.controller.impl.RegistrationCommandImpl;
import by.jwd.finaltaskweb.controller.impl.TeacherRegistrationCommandImpl;
import by.jwd.finaltaskweb.controller.impl.UpdateClientCommandImpl;
import by.jwd.finaltaskweb.controller.impl.UpdateGroupCommandImpl;
import by.jwd.finaltaskweb.controller.impl.UpdatePasswordCommandImpl;
import by.jwd.finaltaskweb.controller.impl.UpdateTeacherCommandImpl;
import by.jwd.finaltaskweb.controller.impl.UpdateVisitStatusCommandImpl;

/**
 * CommandFactory gets command object that corresponds the command name from the
 * request
 * 
 * @author Evlashkina
 *
 */
public class CommandFactory {

	private static Logger logger = LogManager.getLogger(CommandFactory.class);

	private static final CommandFactory instance = new CommandFactory();

	private static final String COMMAND = "command";

	private Map<CommandEnum, Command> commands = new HashMap<>();

	private CommandFactory() {
		commands.put(CommandEnum.READALLSCHEDULE, new ReadAllScheduleCommandImpl());
		commands.put(CommandEnum.READALLTEACHER, new ReadAllTeacherCommandImpl());
		commands.put(CommandEnum.READALLTEACHERBYADMIN, new ReadAllTeacherByAdminCommandImpl());
		commands.put(CommandEnum.READALLCLIENT, new ReadAllClientCommandImpl());
		commands.put(CommandEnum.EDITCLIENT, new ChooseClientToEditCommandImpl());
		commands.put(CommandEnum.DELETECLIENT, new DeleteClientCommandImpl());
		commands.put(CommandEnum.EDITTEACHER, new ChooseTeacherToEditCommandImpl());
		commands.put(CommandEnum.DELETETEACHER, new DeleteTeacherCommandImpl());
		commands.put(CommandEnum.READALLGROUP, new ReadAllGroupCommandImpl());
		commands.put(CommandEnum.READALLMEMBERSHIPTYPES, new ReadAllMembershipTypesCommandImpl());
		commands.put(CommandEnum.LOGINATION, new LoginationCommandImpl());
		commands.put(CommandEnum.REGISTRATION, new RegistrationCommandImpl());
		commands.put(CommandEnum.TEACHERREGISTRATION, new TeacherRegistrationCommandImpl());
		commands.put(CommandEnum.CREATEGROUP, new CreateGroupCommandImpl());
		commands.put(CommandEnum.CREATECLASSES, new CreateClassesCommandImpl());
		commands.put(CommandEnum.LOGOUT, new LogoutCommandImpl());
		commands.put(CommandEnum.UPDATECLIENT, new UpdateClientCommandImpl());
		commands.put(CommandEnum.UPDATETEACHER, new UpdateTeacherCommandImpl());
		commands.put(CommandEnum.UPDATEPASSWORD, new UpdatePasswordCommandImpl());
		commands.put(CommandEnum.PLANNEDVISITS, new ReadPlannedVisitsByClientCommandImpl());
		commands.put(CommandEnum.MYVALIDMEMBERSHIPS, new ReadValidMembershipsByClientCommandImpl());
		commands.put(CommandEnum.MYMEMBERSHIPSPERIOD, new ReadMembershipByClientAndPeriodCommandImpl());
		commands.put(CommandEnum.CREATEMEMBERSHIP, new CreateMembershipCommandImpl());
		commands.put(CommandEnum.CHOOSEMEMBERSHIP, new ChooseMembershipCommandImpl());
		commands.put(CommandEnum.MYVISITS, new ReadVisitsByClientAndPeriodCommandImpl());
		commands.put(CommandEnum.CANCELVISIT, new CancelVisitCommandImpl());
		commands.put(CommandEnum.READGROUPBYSCHEDULE, new ReadGroupByScheduleCommandImpl());
		commands.put(CommandEnum.READGROUPBYSTYLE, new ReadGroupByStyleCommandImpl());
		commands.put(CommandEnum.READGROUPBYLEVEL, new ReadGroupByLevelCommandImpl());
		commands.put(CommandEnum.READGROUPFORENROLLMENTBYDATE, new ReadGroupForEnrollmentByDateCommandImpl());
		commands.put(CommandEnum.READGROUPSBYDATE, new ReadGroupsByDateCommandImpl());
		commands.put(CommandEnum.READALLSTYLE, new ReadAllStyleCommandImpl());
		commands.put(CommandEnum.READALLLEVEL, new ReadAllLevelCommandImpl());
		commands.put(CommandEnum.READALLWEEKDAY, new ReadAllWeekDayCommandImpl());
		commands.put(CommandEnum.READACTIVECLASSESBYDATE, new ReadActiveClassesByDateComandImpl());
		commands.put(CommandEnum.CREATEVISIT, new CreateVisitCommandImpl());
		commands.put(CommandEnum.CONFIRMVISIT, new ConfirmVisitCommandImpl());
		commands.put(CommandEnum.READPLANNEDCLASSESBYTEACHER, new ReadPlannedClassesByTeacherCommandImpl());
		commands.put(CommandEnum.MARKPRESENCE, new UpdateVisitStatusCommandImpl());
		commands.put(CommandEnum.READCLIENTSBYDANCECLASS, new ReadClientsByDanceClassCommandImpl());
		commands.put(CommandEnum.CLOSEENROLLMENT, new CloseClassForEnrollmentCommandImpl());
		commands.put(CommandEnum.CANCELMARKEDPRESENCE, new CancelUpdateStatusCommandImpl());
		commands.put(CommandEnum.READVISITSBYGROUPANDDATE, new ReadDanceClassByGroupAndDateCommandImpl());
		commands.put(CommandEnum.READGROUPSBYTEACHER, new ReadGroupsByTeacherCommandImpl());
		commands.put(CommandEnum.EDITGROUP, new ChooseGroupToEditCommandImpl());
		commands.put(CommandEnum.UPDATEGROUP, new UpdateGroupCommandImpl());
		commands.put(CommandEnum.DELETEGROUP, new DeleteGroupCommandImpl());
		commands.put(CommandEnum.READVISITCOUNTBYTEACHERGROUPSANDPERIOD,
				new ReadVisitsCountByTeacherGroupsAndPeriodCommandImpl());
		commands.put(CommandEnum.READVISITCOUNTBYGROUPSANDPERIOD, new ReadVisitsCountByGroupsAndPeriodCommandImpl());
		commands.put(CommandEnum.MEMBERSHIPSTATICSFORPERIOD, new MembershipsStaticticsCommandImpl());
		commands.put(CommandEnum.WRONGCOMMAND, new EmptyCommandImpl());
	}

	public static CommandFactory getInstance() {
		return instance;
	}

	public Command getCommand(SessionRequestContent content) {

		Command command;
		
				
		// invoking command name
		String name = content.getRequestParameter(COMMAND);
		logger.debug("command name {}", name);
		
		if (name == null || "".equals(name)) {
			command = commands.get(CommandEnum.WRONGCOMMAND);
		} else {
			// getting the respective command object
			try {
				CommandEnum currentEnum = CommandEnum.valueOf(name.toUpperCase());
				command = commands.get(currentEnum);

			} catch (IllegalArgumentException e) {
				command = commands.get(CommandEnum.WRONGCOMMAND);
			}
		}
		return command;
	}
}
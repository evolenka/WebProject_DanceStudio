	
	package by.jwd.finaltaskweb.controller.impl;

	import java.util.Arrays;
	import java.util.List;

	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpSession;

	import org.apache.logging.log4j.LogManager;
	import org.apache.logging.log4j.Logger;

	import by.jwd.finaltaskweb.controller.Command;
	import by.jwd.finaltaskweb.controller.ConfigurationManager;
		import by.jwd.finaltaskweb.entity.WeekDay;
	
	/**
	* ReadAllWeekDaysCommandImpl implements command for viewing all weekdays to choose on the enrollment page
	* 
	* @author Evlashkina
	*
	*/
	public class ReadAllWeekDayCommandImpl implements Command {

		private static Logger logger = LogManager.getLogger(ReadAllWeekDayCommandImpl.class);

		@Override
		public String execute(HttpServletRequest request) {

			String page = null;
			HttpSession session = request.getSession(true);
					
				List<WeekDay> weekdays = Arrays.asList(WeekDay.values());
				session.setAttribute("weekdays", weekdays);
				logger.debug("weekdays {}", weekdays);
				page = ConfigurationManager.getProperty("path.page.chooseGroupByWeekDay");
		
			return page;
		}
	}
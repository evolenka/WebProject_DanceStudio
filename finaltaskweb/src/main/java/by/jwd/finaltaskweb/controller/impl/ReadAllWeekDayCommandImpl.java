
package by.jwd.finaltaskweb.controller.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.PageResult;
import by.jwd.finaltaskweb.controller.SessionRequestContent;
import by.jwd.finaltaskweb.entity.WeekDay;

/**
 * ReadAllWeekDaysCommandImpl implements command for viewing by client all
 * weekdays to choose groups by weekdays
 * 
 * @author Evlashkina
 *
 */
public class ReadAllWeekDayCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(ReadAllWeekDayCommandImpl.class);

	@Override
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;

		Integer clientId = (Integer)(content.getSessionAttribute("clientId"));
		logger.debug("clientId {}", clientId);

		if (clientId != null) {

			List<WeekDay> weekdays = Arrays.asList(WeekDay.values());
			content.setSessionAttribute("weekdays", weekdays);
			logger.debug("weekdays {}", weekdays);

			result = new PageResult(ConfigurationManager.getProperty("path.page.chooseGroupByWeekDay"), false);
		}

		return result;
	}
}
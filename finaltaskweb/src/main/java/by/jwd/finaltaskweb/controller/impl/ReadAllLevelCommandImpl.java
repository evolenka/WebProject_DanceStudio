
package by.jwd.finaltaskweb.controller.impl;

import java.util.Arrays;
import java.util.List;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.PageResult;
import by.jwd.finaltaskweb.controller.SessionRequestContent;
import by.jwd.finaltaskweb.entity.Level;

/**
 * ReadAllLevelsCommandImpl implements command for viewing by client all levels  to choose
 * groups by level
 * 
 * @author Evlashkina
 *
 */
public class ReadAllLevelCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(ReadAllLevelCommandImpl.class);

	@Override
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;

		Integer clientId = (Integer)(content.getSessionAttribute("clientId"));
		logger.debug("clientId {}", clientId);
		
		if (clientId != null) {

			List<Level> levels = Arrays.asList(Level.values());
			content.setSessionAttribute("levels", levels);
			logger.debug("levels {}", levels);

			result = new PageResult(ConfigurationManager.getProperty("path.page.chooseGroupByLevel"), false);
		}

		return result;
	}
}
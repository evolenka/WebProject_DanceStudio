package by.jwd.finaltaskweb.controller.impl;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
* ReadAllStylesCommandImpl implements command for viewing all dance styles to choose on the enrollment page
* 
* @author Evlashkina
*
*/
public class ReadAllStyleCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(ReadAllStyleCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public String execute(HttpServletRequest request) {

		String page = null;
		HttpSession session = request.getSession(true);
				
		try {
			List<String> styles = factory.getUserService().readAllDanceStyle();
			session.setAttribute("styles", styles);
			logger.debug("styles {}", styles);
			page = ConfigurationManager.getProperty("path.page.chooseGroupByStyle");
			
		} catch (ServiceException e) {
			logger.error(e);
		}
		return page;
	}
}
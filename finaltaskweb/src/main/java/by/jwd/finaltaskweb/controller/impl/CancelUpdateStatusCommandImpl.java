package by.jwd.finaltaskweb.controller.impl;

import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.controller.PageResult;
import by.jwd.finaltaskweb.controller.SessionRequestContent;
import by.jwd.finaltaskweb.entity.DanceClass;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * CancelUpdateStatusCommandImpl implements command to cancel marked by mistake
 * presence or absence of the client on the dance class
 * 
 * @author Evlashkina
 *
 */
public class CancelUpdateStatusCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(CancelUpdateStatusCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public PageResult execute(SessionRequestContent content) {
		
		PageResult result = null;
		
		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);
	
		Integer teacherId = (Integer)(content.getSessionAttribute ("teacherId"));
		logger.debug("teacher id {}", teacherId);
		
		Integer visitId = Integer.parseInt(content.getRequestParameter ("visitId"));
		logger.debug("visit id {}", visitId);

		try {
			if (teacherId != null && visitId != null) {
				
				factory.getVisitService().cancelMarkPresence(visitId);
								
				Integer groupId = Integer.parseInt((String) content.getSessionAttribute("groupId"));
				logger.debug("group id {}", groupId);
				LocalDate date = LocalDate.parse((String)content.getSessionAttribute("visitDate"));
				logger.debug("visitDate {}", date);
				DanceClass danceClass = factory.getDanceClassService().readByDateAndGroup(date, groupId);
				
				content.setSessionAttribute("danceClass", danceClass);
				
			
			result = new PageResult (ConfigurationManager.getProperty("path.page.visitsByTeacherSecondStep"), true);
			}
			
		} catch (ServiceException e) {
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult (ConfigurationManager.getProperty("path.page.error"), false);
		}
        
		return result;
	}
}
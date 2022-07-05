package by.jwd.finaltaskweb.controller.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.controller.PageResult;
import by.jwd.finaltaskweb.controller.SessionRequestContent;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * CreateClassesCommandImpl implements command for creating new danceclass by
 * admin
 * 
 * @author Evlashkina
 *
 */

public class CreateClassesCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(CreateClassesCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;

		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);

		Integer adminId = (Integer)(content.getSessionAttribute("adminId"));
		logger.debug("adminId {}", adminId);
		
		String [] groupId = (String []) content.getRequestAttribute("groupId");
					
		LocalDate date = (LocalDate)content.getSessionAttribute("classDate");
		logger.debug("date {}", date);

		try {
			if (adminId != null && groupId !=null && date != null) {
				
				List<Integer> groupsId = new ArrayList<>();
				for (String id : groupId) {
					groupsId.add(Integer.parseInt(id));
				}

				if (factory.getDanceClassService().createClassesByDateAndGroups(date, groupsId)) {
					content.setSessionAttribute("successCreateClassMessage",
							MessageManager.getProperty("successCreateClassMessage", language));
				} else {
					content.setSessionAttribute("errorRegMessage",
							MessageManager.getProperty("errorRegMessage", language));
				}

				result = new PageResult(ConfigurationManager.getProperty("path.page.createClass"), true);
			}
		
		}catch(

	ServiceException e)
	{
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
		}

	return result;
}}
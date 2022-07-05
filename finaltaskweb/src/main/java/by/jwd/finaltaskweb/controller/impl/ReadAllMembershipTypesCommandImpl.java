package by.jwd.finaltaskweb.controller.impl;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.controller.PageResult;
import by.jwd.finaltaskweb.controller.SessionRequestContent;
import by.jwd.finaltaskweb.entity.MembershipType;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * ReadAllMembershipTypesCommandImpl implements command for viewing all types of
 * memberships
 * 
 * @author Evlashkina
 *
 */

public class ReadAllMembershipTypesCommandImpl implements Command {

	static Logger logger = LogManager.getLogger(ReadAllMembershipTypesCommandImpl.class);

	@Override
	public PageResult execute(SessionRequestContent content) {

		PageResult result = null;

		String language = (String) content.getSessionAttribute("language");
		logger.debug("language {}", language);

		try {
			List<MembershipType> membershipTypes;
			membershipTypes = ServiceFactory.getInstance().getMembershipService().readAllTypes();
			content.setSessionAttribute("membershipTypes", membershipTypes);
			
			result = new PageResult(ConfigurationManager.getProperty("path.page.membershipTypes"), false);

		} catch (ServiceException e) {
			content.setRequestParameter("errorMessage", MessageManager.getProperty("errorMessage", language));
			result = new PageResult(ConfigurationManager.getProperty("path.page.error"), false);
		}

		return result;
	}
}
package by.jwd.finaltaskweb.controller.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.entity.MembershipType;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * ReadAllMembershipTypesCommandImpl implements command for viewing all types of
 * the dance memberships
 * 
 * @author Evlashkina
 *
 */

public class ReadAllMembershipTypesCommandImpl implements Command {

	static Logger logger = LogManager.getLogger(ReadAllMembershipTypesCommandImpl.class);

	@Override
	public String execute(HttpServletRequest request) {

		String page = null;

		HttpSession session = request.getSession(true);
		String language = session.getAttribute("language").toString();

		logger.debug("language {}", language);

		List<MembershipType> membershipTypes;
		try {

			membershipTypes = ServiceFactory.getInstance().getMembershipService().readAllTypes();
			request.setAttribute("membershipTypes", membershipTypes);
			page = ConfigurationManager.getProperty("path.page.membershipTypes");
		} catch (ServiceException e) {
			logger.error(e);
		}
		return page;
	}
}
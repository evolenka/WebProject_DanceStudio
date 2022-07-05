package by.jwd.finaltaskweb.controller.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.MessageManager;
import by.jwd.finaltaskweb.entity.Client;
import by.jwd.finaltaskweb.service.ServiceException;
import by.jwd.finaltaskweb.service.ServiceFactory;

/**
 * UpdateClientCommandImpl implements command for changing personal details of
 * the client by himself in his private account
 * 
 * @author Evlashkina
 *
 */
public class UpdateClientCommandImpl implements Command {

	private static Logger logger = LogManager.getLogger(UpdateClientCommandImpl.class);

	private ServiceFactory factory = ServiceFactory.getInstance();

	@Override
	public String execute(HttpServletRequest request) {

		String page = null;

		HttpSession session = request.getSession(true);
		String language = (String) session.getAttribute("language");

		logger.debug("language {}", language);

		MessageManager manager;

		switch (language) {
		case "en", "en_US":
			manager = MessageManager.EN;
			break;
		case "ru", "ru_RU":
			manager = MessageManager.RU;
			break;
		case "be", "be_BY":
			manager = MessageManager.BY;
			break;
		default:
			manager = MessageManager.EN;
		}
		
		try {
			
		if (request.getParameter("clientId") != null) {
			session.setAttribute("clientId", request.getParameter("clientId"));
		}
		Integer clientId = Integer.parseInt((String) session.getAttribute("clientId"));
		logger.debug("clientId {}", clientId);
		
			if (clientId != null) {
				
				Client client = (Client) factory.getUserService().readEntityById(clientId);

				String surname = request.getParameter("surname");
				logger.debug("surname {}", surname);
				String name = request.getParameter("name");
				logger.debug("name {}", name);
				String patronymic = request.getParameter("patronymic");
				logger.debug("patronymic {}", patronymic);
				String email = request.getParameter("email");
				logger.debug("email {}", email);
				String phone = request.getParameter("phone");
				logger.debug("phone {}", phone);

				client = factory.getClientBuilder().buildClient(client.getLogin(), client.getPassword(), surname, name,
						patronymic, email, phone);
				client.setId(clientId);
				if (factory.getUserService().update(client)) {
					request.setAttribute("successUpdateUserMessage",
							manager.getProperty("successUpdateUserMessage"));

				} else {
					request.setAttribute("errorMessage", manager.getProperty("errorMessage"));
				}
				session.setAttribute("client", client);
			}
			page = ConfigurationManager.getProperty("path.page.updateClient");
		} catch (ServiceException e) {
			request.setAttribute("errorMessage", manager.getProperty("errorMessage"));
			page = ConfigurationManager.getProperty("path.page.error");
		}
		return page;
	}
}
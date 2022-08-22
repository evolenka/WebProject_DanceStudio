package by.jwd.finaltaskweb.controller;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.dao.DaoException;
import by.jwd.finaltaskweb.dao.pool.ConnectionPool;

/**
 * CommandServlet acts as the controller of the application
 * 
 * 
 * @author Evlashkina
 *
 */


@WebServlet("/jsp/action")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)

public class CommandServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static Logger logger = LogManager.getLogger(CommandServlet.class);

	@Override
	public void init() {
		try {
			ResourceBundle resourceBundle = ResourceBundle.getBundle("database");

			String driverClass = resourceBundle.getString("db.driver");
			String url = resourceBundle.getString("db.url");
			String user = resourceBundle.getString("user");
			String password = resourceBundle.getString("password");
			int startSize = Integer.parseInt(resourceBundle.getString("startsize"));
			int maxSize = Integer.parseInt(resourceBundle.getString("maxsize"));
			int checkConnectionTimeout = Integer.parseInt(resourceBundle.getString("checkConnectionTimeout"));

			ConnectionPool.getInstance().init(driverClass, url, user, password, startSize, maxSize,
					checkConnectionTimeout);
		} catch (DaoException e) {
			logger.error("initializatin failed");
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		logger.debug("receive request");
		String page = null;
		
		SessionRequestContent content = new SessionRequestContent(request);
		content.extractValues(request);
			
	   /*define command received from jsp*/
		CommandFactory factory = CommandFactory.getInstance();
		Command command = factory.getCommand(content);

		
		/*invoke method execute() and pass to it the content with all attributes and parameters of the request*/
		PageResult result = command.execute(content);
		
		/*pass parameters and attributes from content to the request*/
		content.insertAttributes(request);
		
		
		if (result != null) {
			  page = result.getPage();
			  logger.debug("page {}", page);
			if (result.isRedirect()) {
				response.sendRedirect(request.getContextPath() + page);
				logger.debug("redirect {}", result.isRedirect());

			} else {
				logger.debug("redirect {}", result.isRedirect());
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
				dispatcher.forward(request, response);
			}
		} else {
			/*page with message error*/
			page = ConfigurationManager.getProperty("path.page.error");
			response.sendRedirect(request.getContextPath() + page);
		}
	}
}
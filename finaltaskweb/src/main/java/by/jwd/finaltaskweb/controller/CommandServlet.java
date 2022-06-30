package by.jwd.finaltaskweb.controller;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.jwd.finaltaskweb.dao.DaoException;
import by.jwd.finaltaskweb.dao.pool.ConnectionPool;

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

		HttpSession session = request.getSession(true);
		
		logger.debug(session.getAttribute("language"));

		if (session.getAttribute("language") == null) {
			String locale = Locale.getDefault().toString();
			logger.debug("locale {}", locale);
			session.setAttribute("language", locale);
		}

		String language = session.getAttribute("language").toString();
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

		if (session.getAttribute("role") == null) {
			session.setAttribute("role", request.getParameter("role"));
		}

		// определение команды, пришедшей из JSP
		CommandProvider provider = new CommandProvider();
		Command command = provider.getCommand(request);

		/*
		 * вызов реализованного метода execute() и передача параметров
		 * классу-обработчику конкретной команды
		 */
		page = command.execute(request);
		logger.debug("page {}", page);

		if (page != null) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
			// вызов страницы ответа на запрос
			dispatcher.forward(request, response);
		} else {
			// установка страницы c cообщением об ошибке
			page = ConfigurationManager.getProperty("path.page.error");
			request.getSession().setAttribute("errorMessage", manager.getProperty("errorMessage"));
			response.sendRedirect(request.getContextPath() + page);
		}
	}
}
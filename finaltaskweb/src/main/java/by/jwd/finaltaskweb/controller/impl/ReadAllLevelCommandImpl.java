
	package by.jwd.finaltaskweb.controller.impl;

	import java.util.Arrays;
	import java.util.List;

	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpSession;

	import org.apache.logging.log4j.LogManager;
	import org.apache.logging.log4j.Logger;

	import by.jwd.finaltaskweb.controller.Command;
	import by.jwd.finaltaskweb.controller.ConfigurationManager;
	import by.jwd.finaltaskweb.entity.Level;
	
	/**
	* ReadAllLevelsCommandImpl implements command for viewing all levels to choose on the enrollment page
	* 
	* @author Evlashkina
	*
	*/
	public class ReadAllLevelCommandImpl implements Command {

		private static Logger logger = LogManager.getLogger(ReadAllLevelCommandImpl.class);


		@Override
		public String execute(HttpServletRequest request) {

			String page = null;
			HttpSession session = request.getSession(true);
			String language = session.getAttribute("language").toString();
			logger.debug("language {}", language);

				List<Level> levels = Arrays.asList(Level.values());
				session.setAttribute("levels", levels);
				logger.debug("levels {}", levels);
				
				page = ConfigurationManager.getProperty("path.page.chooseGroupByLevel");
			
			return page;
		}
	}
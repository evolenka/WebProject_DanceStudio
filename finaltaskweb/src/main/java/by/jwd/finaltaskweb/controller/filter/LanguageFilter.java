package by.jwd.finaltaskweb.controller.filter;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * LanguageFilter set language received from jsp to the session attributes or if
 * absent set default language
 * 
 * @author Evlashkina
 *
 */

public class LanguageFilter implements Filter {

	private static Logger logger = LogManager.getLogger(LanguageFilter.class);
	private static final String LANGUAGE = "language";

	@Override
	public void init(FilterConfig fConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();

		if (request.getParameter(LANGUAGE) != null) {
			logger.debug("language from request {}", request.getParameter(LANGUAGE));
			session.setAttribute(LANGUAGE, request.getParameter(LANGUAGE));
		}

		if (session.getAttribute(LANGUAGE) == null) {
			String locale = Locale.getDefault().toString();
			session.setAttribute("language", locale);
			logger.debug("language from session {}", session.getAttribute(LANGUAGE));
		}

		logger.debug("language filter has been applied");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}
}
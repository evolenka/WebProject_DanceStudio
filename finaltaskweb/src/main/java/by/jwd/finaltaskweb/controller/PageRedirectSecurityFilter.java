package by.jwd.finaltaskweb.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PageRedirectSecurityFilter implements Filter {

	private static Logger logger = LogManager.getLogger(PageRedirectSecurityFilter.class);

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		
	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(true);
		
		Integer id = (Integer) session.getAttribute("userId");
		logger.debug("userId {}", id);

		if (id == null) {

			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/jsp/index.jsp");
			dispatcher.forward(httpRequest, httpResponse);
			logger.debug("page redirect filter has been applied");
			return;
		}
		chain.doFilter(request, response);
	}
	@Override
	public void destroy() {
	}
}

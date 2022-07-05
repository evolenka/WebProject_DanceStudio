package by.jwd.finaltaskweb.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SetCharacterEncodingFilter implements Filter {

	private static Logger logger = LogManager.getLogger(SetCharacterEncodingFilter.class);

	private  String encoding;

	@Override
	public void init(FilterConfig config) throws ServletException {
		
		 encoding = config.getInitParameter("encoding");

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain next)
			throws IOException, ServletException {

		String encodingReq = request.getCharacterEncoding();
		
		
		 if (encoding != null && !encoding.equalsIgnoreCase(encodingReq)) {
		 request.setCharacterEncoding(encoding);
		 response.setCharacterEncoding(encoding);
		
			logger.debug("encoding filter has been applied");
			next.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
		encoding = null;
	}
}
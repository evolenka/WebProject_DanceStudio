package by.jwd.finaltaskweb.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * SessionRequestContent provides keeping all parameters and attributes from the
 * request and its session, using them while handling the command and passing them back to the request
 * 
 * @author Evlashkina
 *
 */

public class SessionRequestContent {

	private static Logger logger = LogManager.getLogger(SessionRequestContent.class);

	private Map<String, String> requestParameters;
	private Map<String, Object> requestAttributes;
	private Map<String, Object> sessionAttributes;
	private HttpServletRequest request;

	public SessionRequestContent(HttpServletRequest request) {
		this.request = request;
		requestParameters = new HashMap<>();
		sessionAttributes = new HashMap<>();
		requestAttributes = new HashMap<>();
	}

	public SessionRequestContent(Map<String, String> requestParameters, Map<String, Object> requestAttributes,
			Map<String, Object> sessionAttributes) {
		super();
		this.requestParameters = requestParameters;
		this.sessionAttributes = sessionAttributes;
		this.requestAttributes = requestAttributes;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public Map<String, String> getAllRequestParameters() {
		return requestParameters;
	}

	public void setAllRequestParameters(Map<String, String> requestParameters) {
		this.requestParameters = requestParameters;
	}

	public String getRequestParameter(String key) {
		return requestParameters.get(key);
	}

	public void setRequestParameter(String key, String value) {
		this.requestParameters.put(key, value);
	}

	public Map<String, Object> getAllRequestAttributes() {
		return requestAttributes;
	}

	public void setAllRequestAttributes(Map<String, Object> requestAttributes) {
		this.requestAttributes = requestAttributes;
	}

	public Object getRequestAttribute(String key) {
		return this.requestAttributes.get(key);
	}

	public void setRequestAttribute(String key, Object value) {
		this.requestAttributes.put(key, value);
	}

	public Map<String, Object> getAllSessionAttributes() {
		return sessionAttributes;
	}

	public void setAllSessionAttributes(Map<String, Object> sessionAttributes) {
		this.sessionAttributes = sessionAttributes;
	}

	public Object getSessionAttribute(String key) {
		return this.sessionAttributes.get(key);
	}

	public void setSessionAttribute(String key, Object value) {
		this.sessionAttributes.put(key, value);
	}

	// invokig parameters and attributes from the request
	public void extractValues(HttpServletRequest request) {

		Enumeration<String> requestParametersNames = request.getParameterNames();
		while (requestParametersNames.hasMoreElements()) {
			String name = requestParametersNames.nextElement();
			requestParameters.put(name, request.getParameter(name));
		}

		Enumeration<String> requestAttributesNames = request.getAttributeNames();
		while (requestAttributesNames.hasMoreElements()) {
			String name = requestAttributesNames.nextElement();
			requestAttributes.put(name, request.getAttribute(name));
		}
		requestParametersNames = request.getParameterNames();
		while (requestParametersNames.hasMoreElements()) {
			String name = requestParametersNames.nextElement();
			requestAttributes.put(name, request.getParameterValues(name));
		}

		HttpSession session = request.getSession();
		logger.debug("session {}", session);

		Enumeration<String> sessionAttributesNames = session.getAttributeNames();
		while (sessionAttributesNames.hasMoreElements()) {
			String name = sessionAttributesNames.nextElement();
			sessionAttributes.put(name, session.getAttribute(name));
		}
		logger.debug("parameters and session attributes have been extracted");
	}

	// метод добавления в запрос данных для передачи в jsp
	public void insertAttributes(HttpServletRequest request) {

		for (Entry<String, String> entry : requestParameters.entrySet()) {
			request.setAttribute(entry.getKey(), entry.getValue());
		}

		for (Entry<String, Object> entry : requestAttributes.entrySet()) {
			request.setAttribute(entry.getKey(), entry.getValue());
		}

		// put session attributes to the current session of the request, do not create
		// new session, if not exist
		HttpSession session = request.getSession(false);
		logger.debug("session {}", session);

		if (session != null) {

			for (Entry<String, Object> entry : sessionAttributes.entrySet()) {
				session.setAttribute(entry.getKey(), entry.getValue());
			}
		}
		logger.debug("parameters and session attributes have been inserted");
	}
}
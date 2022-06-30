package by.jwd.finaltaskweb.controller.impl;

import javax.servlet.http.HttpServletRequest;

import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;

public class EmptyCommandImpl implements Command {
	@Override
	public String execute(HttpServletRequest request) {
		/*
		 * в случае ошибки или прямого обращения к контроллеру переадресация на страницу
		 * ввода логина
		 */
		String page = ConfigurationManager.getProperty("path.page.index");
		return page;
	}
}
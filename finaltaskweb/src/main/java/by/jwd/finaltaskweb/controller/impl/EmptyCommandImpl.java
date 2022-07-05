package by.jwd.finaltaskweb.controller.impl;


import by.jwd.finaltaskweb.controller.Command;
import by.jwd.finaltaskweb.controller.ConfigurationManager;
import by.jwd.finaltaskweb.controller.PageResult;
import by.jwd.finaltaskweb.controller.SessionRequestContent;

public class EmptyCommandImpl implements Command {
	
	@Override
	public PageResult execute(SessionRequestContent content) {
		return new PageResult (ConfigurationManager.getProperty("path.page.index"), false);
	}
}
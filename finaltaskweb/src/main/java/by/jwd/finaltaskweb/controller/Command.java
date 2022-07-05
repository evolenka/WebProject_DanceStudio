package by.jwd.finaltaskweb.controller;

/**
 * Command provides execution of the command received from request
 * 
 * @author User
 *
 */
public interface Command {

	public PageResult execute(SessionRequestContent content);
}
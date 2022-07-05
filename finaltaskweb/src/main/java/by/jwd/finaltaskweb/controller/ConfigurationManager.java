package by.jwd.finaltaskweb.controller;

import java.util.ResourceBundle;


/**
 * ConfigurationManager invokes information from the file config.properties
 * 
 * 
 * @author Evlashkina
 *
 */

public class ConfigurationManager {

	private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("config");

	private ConfigurationManager() {
	}

	public static String getProperty(String key) {
		return resourceBundle.getString(key);
	}
}
package by.jwd.finaltaskweb.controller;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * MessageManager invokes information from the files pagecontent.properties 
 * 
 * @author Evlashkina
 *
 */

public class MessageManager {

	private static final MessageManager instance = new MessageManager();
	private static final String PAGECONTENT = "pagecontent";
	
	private MessageManager() {
	}

	public static String getProperty(String key, String language) {
		
		ResourceBundle bundle;
				
		switch (language) {
		case "en", "en_US":
			bundle = ResourceBundle.getBundle(PAGECONTENT, new Locale("en", "US"));
			break;
		case "ru", "ru_RU":
			bundle = ResourceBundle.getBundle(PAGECONTENT, new Locale("ru", "RU"));
			break;
		case "be", "be_BY":
			bundle = ResourceBundle.getBundle(PAGECONTENT, new Locale("be", "BY"));
			break;
		default:
			bundle = ResourceBundle.getBundle(PAGECONTENT, new Locale("en", "US"));
		}

		return bundle.getString(key);
	}

	public static MessageManager getInstance() {
		return instance;
	}
}
package by.jwd.finaltaskweb.controller;

import java.util.Locale;
import java.util.ResourceBundle;

public enum MessageManager {

	EN(ResourceBundle.getBundle("pagecontent", new Locale("en", "US"))),
	RU(ResourceBundle.getBundle("pagecontent", new Locale("ru", "RU"))),
	BY(ResourceBundle.getBundle("pagecontent", new Locale("be", "BY")));

	private ResourceBundle bundle;

	MessageManager(ResourceBundle bundle) {
		this.bundle = bundle;
	}

	public String getProperty(String key) {
		return bundle.getString(key);
	}
}
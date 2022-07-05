package by.jwd.finaltaskweb.controller;

/**
 * PageResult creates result of the command execution, consists of the page and boolean flag isRedirect 
 * which contains info about which method should be applied for the response (redirect() or forward())
 * 
 * @author Evlashkina
 *
 */

public class PageResult {
	
	private String page;
	private boolean isRedirect;

	public PageResult(String page, boolean isRedirect) {
		super();
		this.page = page;
		this.isRedirect = isRedirect;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public boolean isRedirect() {
		return isRedirect;
	}

	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
}

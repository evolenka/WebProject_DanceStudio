package by.jwd.finaltaskweb.service;

/**
 * PaginationPageCount calculates the number of pages for viewing all items
 * 
 * 
 * @author Evlashkina
 *
 */

public class PaginationPageCount {

	public int count(int quantity, int step) {

		int pageQuantity;

		if (quantity % step != 0) {
			pageQuantity = quantity / step + 1;
		} else {
			pageQuantity = quantity / step;
		}
		return pageQuantity;
	}

}

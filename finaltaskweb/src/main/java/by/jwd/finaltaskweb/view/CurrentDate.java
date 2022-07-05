package by.jwd.finaltaskweb.view;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * CurrentDate class provides handling of the custom tag which returns current
 * date
 * 
 * @author Evlashkina
 *
 */

@SuppressWarnings("serial")
public class CurrentDate extends TagSupport {

	@Override
	public int doStartTag() throws JspException {

		String date = String.valueOf(LocalDate.now());

		try {
			JspWriter out = pageContext.getOut();
			out.write(date);
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

}
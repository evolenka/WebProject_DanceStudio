package by.jwd.finaltaskweb.view;

import java.io.IOException;
import java.time.LocalDate;


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

@SuppressWarnings("serial")
public class InfoYearTag  extends TagSupport {
		
	@Override
	public int doStartTag() throws JspException {
		
	String year= String.valueOf(LocalDate.now().getYear());
		
	 try {
	 JspWriter out = pageContext.getOut();
	out.write(year);
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
	

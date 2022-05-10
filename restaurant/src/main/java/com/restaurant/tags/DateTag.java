package com.restaurant.tags;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class DateTag extends TagSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8957814470638295186L;

	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		Date dateNow = new Date();
	    SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy | HH:mm");
		try {
			out.print(formatForDateNow.format(dateNow));
			out.print("<br/>");
		} catch (IOException e) {
			throw new JspException();
		}
		return SKIP_BODY;
	}

}

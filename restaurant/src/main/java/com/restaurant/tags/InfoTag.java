package com.restaurant.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class InfoTag extends TagSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8957814470638295186L;
	
	private String msg;
	
	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			out.print(String.format("<p align=\"center\" style=\"color: green;\">%s</p>", msg));
		} catch (IOException e) {
			throw new JspException();
		}
		return SKIP_BODY;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
}

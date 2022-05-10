package com.restaurant.servlet;

import java.io.IOException;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;


/**
 * IndexServlet for invalidate session in redirect to index.jsp
 * 
 * @author Serhii Yakovenko.
 * 
 */
@WebServlet("/IndexServlet")
public class IndexServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(IndexServlet.class);
	
	private static final long serialVersionUID = -5448215589486301714L;
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException  {
		HttpSession session = req.getSession();
		session.invalidate();
		log.info("Index Servlet: session invalidate");
		resp.sendRedirect("index");
		
	}

}

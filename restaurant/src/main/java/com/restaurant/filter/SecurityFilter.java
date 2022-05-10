package com.restaurant.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * NoLoginFilter
 * 
 * @author Serhii Yakovenko.
 * 
 */
@WebFilter("*")
public class SecurityFilter extends HttpFilter implements Filter {

	private static final Logger log = Logger.getLogger(SecurityFilter.class);

	private static final long serialVersionUID = 4019657010253595399L;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();

		String path = req.getRequestURI();
		if (path.contains("/login") || path.contains("/registration")) {
			if (session.getAttribute("user") == null) {
				chain.doFilter(request, response);
			} else {
				log.info("Attempt to enter the login/registration pages while being logged in");
				resp.sendRedirect("IndexServlet");
			}
		} else if (session.isNew()) {
			resp.sendRedirect("index");
		} else {
			chain.doFilter(request, response);
		}

	}

	public void init(FilterConfig fConfig) throws ServletException {
		log.info("Init SecurityFilter");
	}

}

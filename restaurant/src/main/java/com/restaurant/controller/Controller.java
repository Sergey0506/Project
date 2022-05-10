package com.restaurant.controller;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.apache.log4j.Logger;

import com.restaurant.command.*;
import com.restaurant.dao.AppException;
import com.restaurant.dao.entity.User;

/**
 * Main Servlet Controller
 * 
 * @author Serhii Yakovenko.
 * 
 */
@MultipartConfig
@WebServlet("/controller")
public class Controller extends HttpServlet {

	private static final Logger log = Logger.getLogger(Controller.class);

	private static final long serialVersionUID = 7469080221888162776L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String address = process(req, resp);
		log.info("Controller doGet()");
		req.getRequestDispatcher(address).forward(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String address = process(req, resp);
		log.info("Controller doPost()");
		resp.sendRedirect(address);
	}

	/**
	 * Main method of this controller where execute commands.
	 */
	private String process(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		ServletContext sc = request.getServletContext();
		Locale locale = Locale.forLanguageTag((String) sc.getAttribute("defaultLocale"));
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user != null) {
			locale = Locale.forLanguageTag(user.getLocale());
		}
		ResourceBundle exampleBundle = ResourceBundle.getBundle("resources", locale);

		String address = "error";
		request.setCharacterEncoding("UTF-8");

		log.info("Controller process method");

		String commandName = request.getParameter("command");
		log.info("Request parameter: command --> " + commandName);

		Command command = CommandContainer.getCommand(commandName);

		if (command != null) {
			try {
				address = command.execute(request, response);
			} catch (AppException e) {
				log.error(e.getMessage());
				request.getSession().setAttribute("error", e.getMessage());
			}
		} else {
			log.error("Command is null!");
			request.getSession().setAttribute("error", exampleBundle.getString("error_UnknownCommand"));
		}
		return address;

	}

}
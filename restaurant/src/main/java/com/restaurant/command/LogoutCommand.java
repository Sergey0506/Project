package com.restaurant.command;

import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.*;

import com.restaurant.dao.entity.User;

/**
 * Command for logout users.
 * 
 * @author Serhii Yakovenko.
 * 
 */

public class LogoutCommand implements Command {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");

		ServletContext sc = req.getServletContext();

		@SuppressWarnings("unchecked")
		HashMap<String, HttpSession> sessions = (HashMap<String, HttpSession>) sc.getAttribute("sessions");
		if (sessions != null) sessions.remove(user.getLogin());

		return "IndexServlet";
	}

}

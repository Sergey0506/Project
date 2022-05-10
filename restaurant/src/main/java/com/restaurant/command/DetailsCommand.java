package com.restaurant.command;

import javax.servlet.http.*;
import com.restaurant.dao.entity.User;

/**
 * Command for add attribute "details".
 * 
 * @author Serhii Yakovenko.
 * 
 */

public class DetailsCommand implements Command {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String address = "error";

		HttpSession session = req.getSession();

		session.setAttribute("details", req.getParameter("id"));

		User user = (User) session.getAttribute("user");

		if (user.getRole().equals("user")) {
			address = "controller?command=menu&button=myOrder";
		} else if (user.getRole().equals("manager")) {
			address = "controller?command=menu&button=allOrders";
		}
		return address;

	}
}

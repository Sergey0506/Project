package com.restaurant.command;

import javax.servlet.http.*;

/**
 * Command for pagination function.
 * 
 * @author Serhii Yakovenko.
 * 
 */

public class PaginationCommand implements Command {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {

		HttpSession session = req.getSession();

		session.setAttribute("page", req.getParameter("page"));
		return "index";

	}
}

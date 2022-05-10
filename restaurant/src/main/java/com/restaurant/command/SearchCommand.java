package com.restaurant.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.*;

/**
 * Command for default search settings.
 * 
 * @author Serhii Yakovenko.
 * 
 */

public class SearchCommand implements Command {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String address = "index";

		HttpSession session = req.getSession();

		Map<String, String> form = new HashMap<>();
		Map<String, String> search = new HashMap<>();

		search.put("sortBy", req.getParameter("sortBy"));
		search.put("sortType", req.getParameter("sortType"));
		search.put("searchType", req.getParameter("searchType"));
		search.put("onPage", req.getParameter("onPage"));
		session.setAttribute("search", search);

		form.put(req.getParameter("sortBy"), "checked=\"checked\"");
		form.put(req.getParameter("sortType"), "checked=\"checked\"");
		form.put(req.getParameter("searchType"), "selected");
		form.put(req.getParameter("onPage"), "selected");
		session.setAttribute("form", form);

		session.removeAttribute("page");
		session.removeAttribute("menu");
		return address;

	}
}

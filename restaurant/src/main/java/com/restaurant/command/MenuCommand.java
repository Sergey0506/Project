package com.restaurant.command;

import java.sql.Connection;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.*;

import org.apache.log4j.Logger;

import com.restaurant.controller.Controller;
import com.restaurant.dao.AppException;
import com.restaurant.dao.DAOFactory;
import com.restaurant.dao.DBException;
import com.restaurant.dao.Pool;

import com.restaurant.dao.entity.Order;
import com.restaurant.dao.entity.User;

/**
 * Command for settings user interface on index page.
 * 
 * @author Serhii Yakovenko.
 * 
 */

public class MenuCommand implements Command {
	
	private static final Logger log = Logger.getLogger(Controller.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
		String address = "index";

		ServletContext sc = req.getServletContext();
		Locale locale = Locale.forLanguageTag((String) sc.getAttribute("defaultLocale"));
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		if (user != null) {
			locale = Locale.forLanguageTag(user.getLocale());
		}
		ResourceBundle exampleBundle = ResourceBundle.getBundle("resources", locale);		
		
		if (session.getAttribute("details") == null) {
			session.removeAttribute("archive");
		}

		String choise = req.getParameter("button");

		Map<String, String> show = new HashMap<>();
		show.put(choise, choise.toLowerCase());
		session.setAttribute("show", show);

		if (choise.equals("myOrder") || choise.equals("allOrders")) {
			List<Order> orders = null;
			

			if (req.getParameter("type") != null) {
				session.setAttribute("archive", "show");
			}

			if ((choise.equals("myOrder") && user.getRole().equals("manager"))
					|| (choise.equals("allOrders") && user.getRole().equals("user"))) {
				address = "error";

			} else {

				Connection con = Pool.getInstance().getConnection();
				if(con == null) {
					session.setAttribute("error", exampleBundle.getString("error_Data"));
					return "error";
				}

				int userId;

				if (user.getRole().equals("user")) {
					userId = user.getId();
				} else {
					userId = 0;
				}
				try {
					orders = DAOFactory.getInstatance().getOrderDAO().findOrders(con, userId);
				} catch (DBException e) {
					log.error("Unable to find orders! " + e.getMessage());
					throw new AppException(exampleBundle.getString("error_Unable_to_find_orders") +  exampleBundle.getString(e.getMessage()), e);
				}

				session.setAttribute("orderList", orders);
			}
		}

		return address;

	}
}

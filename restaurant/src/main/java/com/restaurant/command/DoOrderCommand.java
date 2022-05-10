package com.restaurant.command;

import java.sql.Connection;

import java.util.HashMap;
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

import com.restaurant.dao.entity.Basket;

import com.restaurant.dao.entity.User;

/**
 * Command for add order to DB.
 * 
 * @author Serhii Yakovenko.
 * 
 */

public class DoOrderCommand implements Command {
	
	private static final Logger log = Logger.getLogger(Controller.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
		String address = "controller?command=menu&button=myOrder";
		ServletContext sc = req.getServletContext();
		Locale locale = Locale.forLanguageTag((String) sc.getAttribute("defaultLocale"));
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		if (user != null) {
			locale = Locale.forLanguageTag(user.getLocale());
		}
		ResourceBundle exampleBundle = ResourceBundle.getBundle("resources", locale);

		Basket basket = (Basket) session.getAttribute("basket");

		int userId = user.getId();
		Connection con = Pool.getInstance().getConnection();
		
		if(con == null) {
			session.setAttribute("error", exampleBundle.getString("error_Data"));
			return "error";
		}

		try {
			DAOFactory.getInstatance().getOrderDAO().createOrder(con, userId, basket);
		} catch (DBException e) {
			log.error("Unable to create order! " + e.getMessage());
			throw new AppException(exampleBundle.getString("error_Unable_to_create_order") + exampleBundle.getString(e.getMessage()), e);
		}

		Map<String, String> show = new HashMap<>();
		show.put("myOrder", "myorder");
		session.setAttribute("show", show);

		session.removeAttribute("basket");
		return address;

	}
}

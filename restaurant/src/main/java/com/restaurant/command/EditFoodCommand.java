package com.restaurant.command;

import java.sql.Connection;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.*;

import org.apache.log4j.Logger;

import com.restaurant.controller.Controller;
import com.restaurant.dao.AppException;
import com.restaurant.dao.DAOFactory;
import com.restaurant.dao.DBException;
import com.restaurant.dao.Pool;
import com.restaurant.dao.entity.Dish;
import com.restaurant.dao.entity.User;

/**
 * Command for edit food in DB.
 * 
 * @author Serhii Yakovenko.
 * 
 */

public class EditFoodCommand implements Command {
	
	private static final Logger log = Logger.getLogger(Controller.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
		String address = "error";

		ServletContext sc = req.getServletContext();
		Locale locale = Locale.forLanguageTag((String) sc.getAttribute("defaultLocale"));
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		if (user != null) {
			locale = Locale.forLanguageTag(user.getLocale());
		}
		ResourceBundle exampleBundle = ResourceBundle.getBundle("resources", locale);

		if (user.getRole().equals("manager")) {
			address = "index";
			int id = Integer.parseInt(req.getParameter("id"));

			Connection con = Pool.getInstance().getConnection();
			
			if(con == null) {
				session.setAttribute("error", exampleBundle.getString("error_Data"));
				return "error";
			}
			
			Dish dish = null;
			try {
				dish = DAOFactory.getInstatance().getDishDAO().findDish(con, id);
			} catch (DBException e) {
				log.error("Menu search error! " + e.getMessage());
				throw new AppException(exampleBundle.getString("error_Menu_search_error") +  exampleBundle.getString(e.getMessage()), e);
			}

			session.setAttribute("details", dish);

		}
		return address;

	}
}

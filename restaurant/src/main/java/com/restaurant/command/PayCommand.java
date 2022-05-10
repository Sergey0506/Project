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
import com.restaurant.dao.entity.User;

/**
 * Command for change Order status.
 * 
 * @author Serhii Yakovenko.
 * 
 */

public class PayCommand implements Command {
	
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
		
		int id = Integer.parseInt(req.getParameter("id"));

		Connection con = Pool.getInstance().getConnection();
		
		if(con == null) {
			session.setAttribute("error", exampleBundle.getString("error_Data"));
			return "error";
		}

		try {
			DAOFactory.getInstatance().getOrderDAO().changeOrderStatus(con, id, "Оплачен");
		} catch (DBException e) {
			log.error("Unable to change order status! " + e.getMessage());
			throw new AppException(exampleBundle.getString("error_Unable_to_change_order_status")+  exampleBundle.getString(e.getMessage()), e);
		}

		session.removeAttribute("details");

		return address;

	}
}

package com.restaurant.command;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.*;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import com.restaurant.controller.Controller;
import com.restaurant.dao.AppException;
import com.restaurant.dao.DAOFactory;
import com.restaurant.dao.DBException;
import com.restaurant.dao.Pool;
import com.restaurant.dao.entity.User;

/**
 * Command for login users.
 * 
 * @author Serhii Yakovenko.
 * 
 */

public class LoginCommand implements Command {
	
	private static final Logger log = Logger.getLogger(Controller.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
		String address = "login";

		ServletContext sc = req.getServletContext();
		Locale locale = Locale.forLanguageTag((String) sc.getAttribute("defaultLocale"));
		HttpSession session = req.getSession();
		ResourceBundle exampleBundle = ResourceBundle.getBundle("resources", locale);
	
		if (session.getAttribute("user") != null) {

			return "index";
		}

		String login = req.getParameter("login");
		String password = req.getParameter("password");

		if (Pattern.matches("^([a-zA-Z0-9]{4,10}$)", login) && Pattern.matches("^([a-zA-Z0-9]{4,10}$)", password)) {
			String pass = DigestUtils.md5Hex(password);
			Connection con = Pool.getInstance().getConnection();
			
			if(con == null) {
				session.setAttribute("error", exampleBundle.getString("error_Data"));
				return "error";
			}

			User user = null;
			try {
				user = DAOFactory.getInstatance().getUserDAO().findUser(con, login, pass);
			} catch (DBException e) {
				log.error("Unable to find user! " + e.getMessage());
				throw new AppException(exampleBundle.getString("error_Unable_to_find_user")+  exampleBundle.getString(e.getMessage()), e);
			}
			if (user != null) {
				session.setAttribute("user", user);
				
				@SuppressWarnings("unchecked")
				HashMap<String,HttpSession> sessions = (HashMap<String, HttpSession>) sc.getAttribute("sessions");
				if(sessions==null) sessions = new HashMap<>();
				sessions.put(user.getLogin(), session);
				sc.setAttribute("sessions", sessions);
				
				if (user.getRole().equals("user")) {
					address = "index";
				} else {
					address = "controller?command=menu&button=allOrders";
				}
			} else {
				session.setAttribute("error", exampleBundle.getString("error_There_is_no_such_user"));
			}
		} else {
			address = "error";
			session.setAttribute("error", exampleBundle.getString("error_Hacking_action"));
		}

		return address;
	}
}

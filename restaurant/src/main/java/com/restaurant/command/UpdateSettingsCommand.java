package com.restaurant.command;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

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
 * Command for change user options.
 * 
 * @author Serhii Yakovenko.
 * 
 */

public class UpdateSettingsCommand implements Command {
	
	private static final Logger log = Logger.getLogger(Controller.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
		String address = "controller?command=menu&button=mySettings";
		
		ServletContext sc = req.getServletContext();
		Locale loc = Locale.forLanguageTag((String) sc.getAttribute("defaultLocale"));
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		if (user != null) {
			loc = Locale.forLanguageTag(user.getLocale());
		}
		ResourceBundle exampleBundle = ResourceBundle.getBundle("resources", loc);
		
		String login = user.getLogin();
		String oldPass = req.getParameter("oldPassword");
		String newPass = req.getParameter("newPassword");
		String locale = null;

		Connection con = Pool.getInstance().getConnection();
		
		if(con == null) {
			session.setAttribute("error", exampleBundle.getString("error_Data"));
			return "error";
		}
		
		//change user role by admin
		if(req.getParameter("login")!=null) {
			String userLogin = req.getParameter("login");
			String role = req.getParameter("role");
			
			if(userLogin.equals(login)) {
				session.setAttribute("error", exampleBundle.getString("error_You_can't_change_your_own_role"));
				return address;				
			}
			
			User changed = null;
			
			try {
				changed = DAOFactory.getInstatance().getUserDAO().findUser(con, userLogin);
				
				if(changed==null) {
					session.setAttribute("error", exampleBundle.getString("error_No_such_user"));
					return address;
				} else if(changed.getRole().equals(role)) {
					session.setAttribute("error", exampleBundle.getString("error_User_already_has_this_role"));
					return address;
				}
			
				con = Pool.getInstance().getConnection();			
				DAOFactory.getInstatance().getUserDAO().changeUserRole(con, userLogin, role);
				session.setAttribute("info", exampleBundle.getString("info_User_role_changed"));
				
				@SuppressWarnings("unchecked")
				HashMap<String,HttpSession> sessions = (HashMap<String, HttpSession>) sc.getAttribute("sessions");
				if(sessions.containsKey(userLogin)) {
					HttpSession s = sessions.get(userLogin);
					s.invalidate();
				}
				
				return address;
				
			} catch (DBException e) {
				log.error("Unable to change user role! " + e.getMessage());
				throw new AppException(exampleBundle.getString("error_Unable_to_change_user_role")+  exampleBundle.getString(e.getMessage()), e);
			}		
		}
		
		//change locale or old password
		if (oldPass == null) {
			locale = req.getParameter("loc");
		} else {
			oldPass = DigestUtils.md5Hex(oldPass);
			newPass = DigestUtils.md5Hex(newPass);
			locale = user.getLocale();
		}

		try {

			if (oldPass != null && !DAOFactory.getInstatance().getUserDAO().checkUserPassword(con, login, oldPass)) {
				session.setAttribute("error", exampleBundle.getString("error_The_old_password_is_not_correct"));
				return address;
			} else {
				con = Pool.getInstance().getConnection();
				DAOFactory.getInstatance().getUserDAO().updateUser(con, login, newPass, locale);
				loc = Locale.forLanguageTag(locale);
				exampleBundle = ResourceBundle.getBundle("resources", loc);
				session.setAttribute("info", exampleBundle.getString("info_Changes_applied"));
			}
		} catch (DBException e) {
			log.error("Unable to change user settings! " + e.getMessage());
			throw new AppException(exampleBundle.getString("error_Unable_to_change_user_settings")+  exampleBundle.getString(e.getMessage()), e);
		}

		if (locale != null) {
			user.setLocale(locale);
			session.setAttribute("user", user);
		}

		return address;
	}

}

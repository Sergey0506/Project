package com.restaurant.command;

import java.sql.Connection;
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
import com.restaurant.dao.entity.Dish;
import com.restaurant.dao.entity.User;

/**
 * Command for add food to basket.
 * 
 * @author Serhii Yakovenko.
 * 
 */

public class ToBasketCommand implements Command {
	
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

		Basket basket = null;

		if (session.getAttribute("basket") == null) {
			basket = new Basket();
		} else {
			basket = (Basket) session.getAttribute("basket");
		}

		int dishId = Integer.parseInt(req.getParameter("id"));
		int number = Integer.parseInt(req.getParameter("number"));

		Connection con = Pool.getInstance().getConnection();
		
		if(con == null) {
			session.setAttribute("error", exampleBundle.getString("error_Data"));
			return "error";
		}

		Dish dish = null;
		try {
			dish = DAOFactory.getInstatance().getDishDAO().findDish(con, dishId);
		} catch (DBException e) {
			log.error("Unable to search menu! " + e.getMessage());
			throw new AppException(exampleBundle.getString("error_Unable_to_search_menu")+  exampleBundle.getString(e.getMessage()), e);
		}

		Map<Dish, Integer> basketMap = basket.getBasket();

		if (basketMap.containsKey(dish)) {
			basketMap.put(dish, basketMap.get(dish) + number);
		} else {
			basketMap.put(dish, number);
		}

		int sum = basketMap.entrySet().stream().mapToInt(k -> k.getKey().getPrice() * k.getValue()).sum();
		int count = basketMap.entrySet().stream().mapToInt(k -> k.getValue()).sum();

		basket.setCount(count);
		basket.setSum(sum);

		session.setAttribute("basket", basket);

		return address;

	}
}

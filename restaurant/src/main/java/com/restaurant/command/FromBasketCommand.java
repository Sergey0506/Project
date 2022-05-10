package com.restaurant.command;

import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.http.*;

import org.apache.log4j.Logger;

import com.restaurant.controller.Controller;
import com.restaurant.dao.AppException;
import com.restaurant.dao.entity.Basket;
import com.restaurant.dao.entity.Dish;
import com.restaurant.dao.entity.User;

/**
 * Command for add food to basket.
 * 
 * @author Serhii Yakovenko.
 * 
 */

public class FromBasketCommand implements Command {

	private static final Logger log = Logger.getLogger(Controller.class);

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
		String address = "controller?command=menu&button=basket";

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
			session.setAttribute("error", exampleBundle.getString("error_Hacking_action"));
			log.error("Hacking action!");
			return "error";
		} else {
			basket = (Basket) session.getAttribute("basket");
		}

		int oldSum = basket.getSum();
		int oldCount = basket.getCount();
		int removedFoodId = Integer.parseInt(req.getParameter("id"));
		int removedNumber = Integer.parseInt(req.getParameter("number"));

		Map<Dish, Integer> basketMap = basket.getBasket();
		Map<Dish, Integer> cloneMap = new TreeMap<>(basketMap);
		for (Entry<Dish, Integer> e : cloneMap.entrySet()) {
			if (e.getKey().getId() == removedFoodId) {
				Dish dish = e.getKey();
				
				if(removedNumber > e.getValue()) {
					log.error("Hacking action!");
					return "error";
				}
				
				basket.setCount(oldCount - removedNumber);
				basket.setSum(oldSum - removedNumber * dish.getPrice());
				
				if(removedNumber == e.getValue()) {
					basketMap.remove(dish);	
				} else {
					basketMap.put(dish, e.getValue()-removedNumber);
				}
				log.info("Item successfully delete from menu!");
				break;
			}
		}
		
		if(basketMap.isEmpty()) {
			session.removeAttribute("basket");
			log.info("The basket is empty now!");
		}
		
		return address;

	}
}

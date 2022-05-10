package com.restaurant.command;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

import org.junit.Test;

import com.restaurant.dao.AppException;
import com.restaurant.dao.DBException;
import com.restaurant.dao.entity.Basket;
import com.restaurant.dao.entity.Dish;
import com.restaurant.dao.entity.User;

import java.sql.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FromBasketCommandTest {

	@Test
	public void testToBasketCommand() throws SQLException, DBException {

		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		when(req.getSession()).thenReturn(session);
		
		User user = new User(1, "login", "user", "ua");
		when(session.getAttribute("user")).thenReturn(user);

		
		ServletContext sc = mock(ServletContext.class);
		when(req.getServletContext()).thenReturn(sc);
		when(sc.getAttribute(isA(String.class))).thenReturn("ua");

		Basket basket = new Basket();
		Dish food1 = new Dish(1, "Салаты", "Цезарь", "Салатный микс", 100, "avalable");
		Dish food2 = new Dish(2, "Салаты1", "Цезарь", "Салатный микс", 30, "avalable");
		
		basket.getBasket().put(food1, 3);
		basket.getBasket().put(food2, 2);
		basket.setCount(5);
		basket.setSum(food1.getPrice()*3+food2.getPrice()*2);
		when(session.getAttribute("basket")).thenReturn(basket);

		when(req.getParameter("id")).thenReturn("1");
		when(req.getParameter("number")).thenReturn("2");

		FromBasketCommand command = new FromBasketCommand();
		String actual = "";

		try {
			actual = command.execute(req, resp);
		} catch (AppException e) {
			e.printStackTrace();
		}

		String expected = "controller?command=menu&button=basket";

		assertEquals(expected, actual);
		
	}

}

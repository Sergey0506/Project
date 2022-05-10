package com.restaurant.command;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.MockedStatic;

import com.restaurant.dao.AppException;
import com.restaurant.dao.DBException;
import com.restaurant.dao.Pool;
import com.restaurant.dao.entity.Basket;
import com.restaurant.dao.entity.Dish;
import com.restaurant.dao.entity.User;

import java.sql.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DoOrderCommandTest {

	@Test
	public void testDoOrderCommand() throws SQLException, DBException {

		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		when(req.getSession()).thenReturn(session);
		
		ServletContext sc = mock(ServletContext.class);
		when(req.getServletContext()).thenReturn(sc);
		when(sc.getAttribute(isA(String.class))).thenReturn("ua");

		Basket basket = new Basket();
		Dish dish = new Dish(1, "Салаты", "Цезарь с курицей и беконом", "Салатный микс", 129, "avalable");

		basket.getBasket().put(dish, 1);
		basket.setSum(129);
		basket.setCount(1);

		when(session.getAttribute("basket")).thenReturn(basket);

		when(session.getAttribute("user")).thenReturn(new User(1, "login", "user", "ru"));

		Connection con = mock(Connection.class);

		Pool pool = mock(Pool.class);

		when(pool.getConnection()).thenReturn(con);

		MockedStatic<Pool> mocked = mockStatic(Pool.class);

		mocked.when(Pool::getInstance).thenReturn(pool);

		PreparedStatement pstmt = mock(PreparedStatement.class);
		ResultSet rs = mock(ResultSet.class);

		when(con.prepareStatement(isA(String.class), eq(1))).thenReturn(pstmt);
		when(con.prepareStatement(isA(String.class))).thenReturn(pstmt);

		when(pstmt.executeUpdate()).thenReturn(1).thenReturn(1);

		when(pstmt.getGeneratedKeys()).thenReturn(rs);

		when(rs.next()).thenReturn(true);

		when(rs.getInt(1)).thenReturn(1);

		DoOrderCommand command = new DoOrderCommand();
		String actual = "";

		try {
			actual = command.execute(req, resp);
		} catch (AppException e) {
			e.printStackTrace();
		}

		String expected = "controller?command=menu&button=myOrder";

		assertEquals(expected, actual);
		mocked.close();

	}

}

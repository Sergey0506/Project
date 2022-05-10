package com.restaurant.command;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.MockedStatic;

import com.restaurant.dao.AppException;
import com.restaurant.dao.DAOFactory;
import com.restaurant.dao.DBException;
import com.restaurant.dao.Pool;
import com.restaurant.dao.entity.Dish;

import java.sql.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ToBasketCommandTest {

	@Test
	public void testToBasketCommand() throws SQLException, DBException {

		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		when(req.getSession()).thenReturn(session);

		ServletContext sc = mock(ServletContext.class);
		when(req.getServletContext()).thenReturn(sc);
		when(sc.getAttribute(isA(String.class))).thenReturn("ua");

		when(session.getAttribute("basket")).thenReturn(null);

		when(req.getParameter("id")).thenReturn("1");

		when(req.getParameter("number")).thenReturn("5");

		Connection con = mock(Connection.class);

		Pool pool = mock(Pool.class);

		when(pool.getConnection()).thenReturn(con);

		MockedStatic<Pool> mocked = mockStatic(Pool.class);

		mocked.when(Pool::getInstance).thenReturn(pool);

		PreparedStatement pstmt = mock(PreparedStatement.class);
		ResultSet rs = mock(ResultSet.class);

		when(con.prepareStatement(isA(String.class))).thenReturn(pstmt);

		when(pstmt.executeQuery()).thenReturn(rs);

		when(rs.next()).thenReturn(true);

		Dish dish = new Dish(1, "Салаты", "Цезарь с курицей и беконом", "Салатный микс", 129, "avalable");

		when(rs.getInt("id")).thenReturn(dish.getId());

		when(rs.getString("category")).thenReturn(dish.getCategory());

		when(rs.getString("name")).thenReturn(dish.getName());

		when(rs.getString("description")).thenReturn(dish.getDescription());

		when(rs.getInt("price")).thenReturn(dish.getPrice());

		when(rs.getString("status")).thenReturn(dish.getStatus());

		ToBasketCommand command = new ToBasketCommand();
		String actual = "";

		try {
			actual = command.execute(req, resp);
		} catch (AppException e) {
			e.printStackTrace();
		}

		String expected = "index";

		Dish actualDish = DAOFactory.getInstatance().getDishDAO().findDish(con, 1);

		assertEquals(expected, actual);
		assertEquals(dish, actualDish);
		mocked.close();

	}

}

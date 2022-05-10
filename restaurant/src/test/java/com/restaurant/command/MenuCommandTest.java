package com.restaurant.command;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.MockedStatic;

import com.restaurant.dao.AppException;

import com.restaurant.dao.Pool;
import com.restaurant.dao.entity.Dish;

import com.restaurant.dao.entity.User;

import java.sql.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MenuCommandTest {

	@Test
	public void testMenuCommand() throws SQLException, AppException {

		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		when(req.getSession()).thenReturn(session);
		
		ServletContext sc = mock(ServletContext.class);
		when(req.getServletContext()).thenReturn(sc);
		when(sc.getAttribute(isA(String.class))).thenReturn("ua");

		User user = new User(1, "login", "user", "rus");
		Dish food1 = new Dish(1, "Салаты", "Салат 1", "Описание", 100, "avalable");
		Dish food2 = new Dish(2, "Салаты", "Салат 2", "Описание", 200, "avalable");

		when(session.getAttribute("details")).thenReturn(null);

		when(req.getParameter("button")).thenReturn("myOrder");
		when(session.getAttribute("user")).thenReturn(user);

		when(req.getParameter("type")).thenReturn("type");

		Connection con = mock(Connection.class);

		Pool pool = mock(Pool.class);

		when(pool.getConnection()).thenReturn(con);

		MockedStatic<Pool> mocked = mockStatic(Pool.class);

		mocked.when(Pool::getInstance).thenReturn(pool);

		PreparedStatement pstmt1 = mock(PreparedStatement.class);
		PreparedStatement pstmt2 = mock(PreparedStatement.class);
		ResultSet rs1 = mock(ResultSet.class);
		ResultSet rs2 = mock(ResultSet.class);
		when(con.prepareStatement(isA(String.class))).thenReturn(pstmt1).thenReturn(pstmt2).thenReturn(pstmt2);

		when(pstmt1.executeQuery()).thenReturn(rs1);

		when(pstmt2.executeQuery()).thenReturn(rs2).thenReturn(rs2);

		when(rs1.next()).thenReturn(true).thenReturn(true).thenReturn(false);

		when(rs2.next()).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false);

		when(rs2.getInt(1)).thenReturn(food1.getId()).thenReturn(food2.getId());

		when(rs2.getString(2)).thenReturn(food1.getCategory()).thenReturn(food2.getCategory());

		when(rs2.getString(3)).thenReturn(food1.getName()).thenReturn(food2.getName());

		when(rs2.getString(4)).thenReturn(food1.getDescription()).thenReturn(food2.getDescription());

		when(rs2.getInt(5)).thenReturn(food1.getPrice()).thenReturn(food2.getPrice());

		when(rs2.getInt(6)).thenReturn(2).thenReturn(3);

		when(rs2.getString(7)).thenReturn(food1.getStatus()).thenReturn(food2.getStatus());

		when(rs1.getInt(1)).thenReturn(1).thenReturn(2);

		when(rs1.getInt(2)).thenReturn(1).thenReturn(1);

		when(rs1.getInt(3)).thenReturn(200).thenReturn(600);

		when(rs1.getString(4)).thenReturn("Новый").thenReturn("Новый");

		MenuCommand command = new MenuCommand();
		String actual = "";

		actual = command.execute(req, resp);

		String expected = "index";
		assertEquals(expected, actual);
		mocked.close();

	}

}

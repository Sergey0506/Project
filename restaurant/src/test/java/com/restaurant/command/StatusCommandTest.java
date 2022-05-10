package com.restaurant.command;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.MockedStatic;

import com.restaurant.dao.AppException;
import com.restaurant.dao.DBException;
import com.restaurant.dao.Pool;
import com.restaurant.dao.entity.User;

import java.sql.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class StatusCommandTest {

	@Test
	public void testStatusCommand() throws SQLException, DBException {

		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		when(req.getSession()).thenReturn(session);
		
		ServletContext sc = mock(ServletContext.class);
		when(req.getServletContext()).thenReturn(sc);
		when(sc.getAttribute(isA(String.class))).thenReturn("ua");

		when(session.getAttribute("user")).thenReturn(new User(1, "login", "user", "locale"));

		when(req.getParameter("id")).thenReturn("1");

		when(req.getParameter("status")).thenReturn("Новый");

		Connection con = mock(Connection.class);

		Pool pool = mock(Pool.class);

		when(pool.getConnection()).thenReturn(con);

		MockedStatic<Pool> mocked = mockStatic(Pool.class);

		mocked.when(Pool::getInstance).thenReturn(pool);

		PreparedStatement pstmt = mock(PreparedStatement.class);

		try {
			when(con.prepareStatement(isA(String.class))).thenReturn(pstmt);
			when(pstmt.executeUpdate()).thenReturn(1);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		StatusCommand command = new StatusCommand();
		String actual = "";

		try {
			actual = command.execute(req, resp);
		} catch (AppException e) {
			e.printStackTrace();
		}

		String expected = "controller?command=menu&button=allOrders";

		assertEquals(expected, actual);
		mocked.close();

	}

}

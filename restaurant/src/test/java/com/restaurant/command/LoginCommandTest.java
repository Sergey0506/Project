package com.restaurant.command;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.MockedStatic;
import com.restaurant.dao.AppException;
import com.restaurant.dao.Pool;
import com.restaurant.dao.entity.User;

import java.sql.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommandTest {

	@Test
	public void testLoginCommand() throws SQLException, AppException {

		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		when(req.getSession()).thenReturn(session);
		when(req.getParameter("login")).thenReturn("login");
		when(req.getParameter("password")).thenReturn("password");

		ServletContext sc = mock(ServletContext.class);
		when(req.getServletContext()).thenReturn(sc);
		when(sc.getAttribute(isA(String.class))).thenReturn("ua").thenReturn(null);

		User user = new User(1, "login", "manager", "rus");
		System.out.println(user);
		when(session.getAttribute("user")).thenReturn(null);

		Connection con = mock(Connection.class);

		Pool pool = mock(Pool.class);

		when(pool.getConnection()).thenReturn(con);

		MockedStatic<Pool> mocked = mockStatic(Pool.class);

		mocked.when(Pool::getInstance).thenReturn(pool);

		PreparedStatement pstmt = mock(PreparedStatement.class);
		ResultSet rs = mock(ResultSet.class);

		when(rs.next()).thenReturn(true).thenReturn(false);

		when(rs.getInt("id")).thenReturn(user.getId());
		when(rs.getString("login")).thenReturn(user.getLogin());
		when(rs.getString("role")).thenReturn(user.getRole());
		when(rs.getString("language")).thenReturn(user.getLocale());

		when(con.prepareStatement(isA(String.class))).thenReturn(pstmt);
		when(pstmt.executeQuery()).thenReturn(rs);

		LoginCommand command = new LoginCommand();
		String actual = "";

		actual = command.execute(req, resp);

		String expected = "controller?command=menu&button=allOrders";

		assertEquals(expected, actual);
		mocked.close();

	}

}

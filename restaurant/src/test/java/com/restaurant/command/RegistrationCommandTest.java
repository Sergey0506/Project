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

public class RegistrationCommandTest {

	@Test
	public void testRegistrationCommand() throws SQLException {

		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		when(req.getSession()).thenReturn(session);
		when(session.getAttribute("user")).thenReturn(null);

		when(req.getParameter("login")).thenReturn("login");
		when(req.getParameter("password")).thenReturn("password");

		User user = new User(1, "login", "manager", "rus");

		ServletContext sc = mock(ServletContext.class);
		when(req.getServletContext()).thenReturn(sc);
		when(sc.getAttribute(isA(String.class))).thenReturn("ua").thenReturn(null);

		Connection con = mock(Connection.class);

		Pool pool = mock(Pool.class);

		when(pool.getConnection()).thenReturn(con);

		MockedStatic<Pool> mocked = mockStatic(Pool.class);

		mocked.when(Pool::getInstance).thenReturn(pool);

		PreparedStatement pstmt = mock(PreparedStatement.class);
		ResultSet rs = mock(ResultSet.class);

		when(con.prepareStatement(isA(String.class))).thenReturn(pstmt);

		when(pstmt.executeQuery()).thenReturn(rs);

		when(rs.next()).thenReturn(false).thenReturn(true);

		when(con.prepareStatement(isA(String.class), eq(1))).thenReturn(pstmt);

		when(pstmt.executeUpdate()).thenReturn(1);

		when(pstmt.getGeneratedKeys()).thenReturn(rs);

		when(rs.getInt("id")).thenReturn(user.getId());
		when(rs.getString("login")).thenReturn(user.getLogin());
		when(rs.getString("role")).thenReturn(user.getRole());
		when(rs.getString("language")).thenReturn(user.getLocale());

		RegistrationCommand command = new RegistrationCommand();
		String actual = "";

		try {
			actual = command.execute(req, resp);
		} catch (AppException e) {
			e.printStackTrace();
		}
		String expected = "index";

		assertEquals(expected, actual);
		mocked.close();

	}

}

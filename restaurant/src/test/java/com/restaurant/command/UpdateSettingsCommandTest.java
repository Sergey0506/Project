package com.restaurant.command;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.mockito.MockedStatic;

import com.restaurant.dao.AppException;
import com.restaurant.dao.DBException;
import com.restaurant.dao.Pool;
import com.restaurant.dao.entity.User;

import java.sql.*;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateSettingsCommandTest {

	@Test
	public void testChangeLocale() throws SQLException, DBException {

		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		when(req.getSession()).thenReturn(session);
		
		ServletContext sc = mock(ServletContext.class);
		when(req.getServletContext()).thenReturn(sc);
		when(sc.getAttribute(isA(String.class))).thenReturn("ua");

		when(session.getAttribute("user")).thenReturn(new User(1, "login", "user", "locale"));

		when(req.getParameter("oldPassword")).thenReturn("asdf");

		when(req.getParameter("newPassword")).thenReturn("1234");

		Connection con = mock(Connection.class);

		Pool pool = mock(Pool.class);

		when(pool.getConnection()).thenReturn(con).thenReturn(con);

		MockedStatic<Pool> mocked = mockStatic(Pool.class);

		mocked.when(Pool::getInstance).thenReturn(pool).thenReturn(pool);

		PreparedStatement pstmt = mock(PreparedStatement.class);
		ResultSet rs = mock(ResultSet.class);

		when(con.prepareStatement(isA(String.class))).thenReturn(pstmt).thenReturn(pstmt);

		when(pstmt.executeQuery()).thenReturn(rs);

		when(rs.next()).thenReturn(true);

		when(rs.getString("password")).thenReturn(DigestUtils.md5Hex("asdf"));

		UpdateSettingsCommand command = new UpdateSettingsCommand();
		String actual = "";

		try {
			actual = command.execute(req, resp);
		} catch (AppException e) {
			e.printStackTrace();
		}

		String expected = "controller?command=menu&button=mySettings";

		assertEquals(expected, actual);
		mocked.close();

	}
	
	@Test
	public void testChangeRole() throws SQLException, DBException {
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		when(req.getSession()).thenReturn(session);
		
		ServletContext sc = mock(ServletContext.class);
		when(req.getServletContext()).thenReturn(sc);
		when(sc.getAttribute(isA(String.class))).thenReturn("ua");

		when(session.getAttribute("user")).thenReturn(new User(1, "login", "user", "locale"));

		when(req.getParameter("oldPassword")).thenReturn("asdf");

		when(req.getParameter("newPassword")).thenReturn("1234");

		Connection con = mock(Connection.class);

		Pool pool = mock(Pool.class);

		when(pool.getConnection()).thenReturn(con).thenReturn(con);

		MockedStatic<Pool> mocked = mockStatic(Pool.class);

		mocked.when(Pool::getInstance).thenReturn(pool).thenReturn(pool);
		
		when(req.getParameter("login")).thenReturn("user");
		when(req.getParameter("role")).thenReturn("user");
		
		PreparedStatement pstmt = mock(PreparedStatement.class);
		ResultSet rs = mock(ResultSet.class);

		when(con.prepareStatement(isA(String.class))).thenReturn(pstmt).thenReturn(pstmt);

		when(pstmt.executeQuery()).thenReturn(rs);

		when(rs.next()).thenReturn(true);

		when(rs.getInt("id")).thenReturn(2);
		
		when(rs.getString("login")).thenReturn("user");
		
		when(rs.getString("role")).thenReturn("manager");
		
		when(rs.getString("locale")).thenReturn("ua");
		
		when(pstmt.executeUpdate()).thenReturn(1);
		
		HashMap<String,HttpSession> sessions = new HashMap<>();
		sessions.put("user", session);
		
		when(sc.getAttribute("sessions")).thenReturn(sessions);

		UpdateSettingsCommand command = new UpdateSettingsCommand();
		String actual = "";

		try {
			actual = command.execute(req, resp);
		} catch (AppException e) {
			e.printStackTrace();
		}

		String expected = "controller?command=menu&button=mySettings";

		assertEquals(expected, actual);
		mocked.close();
	}

}

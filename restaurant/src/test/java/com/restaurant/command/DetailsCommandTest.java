package com.restaurant.command;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


import org.junit.Test;

import com.restaurant.dao.DBException;
import com.restaurant.dao.entity.User;

import java.sql.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DetailsCommandTest {
	
	

	@Test
	public void testDetailsCommand() throws SQLException, DBException {
		
		HttpServletRequest req = mock(HttpServletRequest.class); 
		HttpServletResponse resp = mock(HttpServletResponse.class); 
		HttpSession session = mock(HttpSession.class);
		when(req.getSession()).thenReturn(session);
	
		when(req.getParameter("id"))
		.thenReturn("1");
		
		User user = new User(1,"login","user","ru");
				
		when(session.getAttribute("user"))
		.thenReturn(user);
		
		
		
		DetailsCommand add = new DetailsCommand();
		String actual = add.execute(req, resp);
		
		String expected = "controller?command=menu&button=myOrder";
		
		assertEquals(expected, actual);
		
	}

}

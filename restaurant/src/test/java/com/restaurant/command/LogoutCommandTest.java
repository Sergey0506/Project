package com.restaurant.command;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


import org.junit.Test;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;




public class LogoutCommandTest {
	
	

	@Test
	public void testLogoutCommand() {

		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		when(req.getSession()).thenReturn(session);
		ServletContext sc = mock(ServletContext.class);
		when(req.getServletContext()).thenReturn(sc);
		
		LogoutCommand command = new LogoutCommand();
		String actual = "";
		
		actual = command.execute(req, resp);
		
		String expected = "IndexServlet";
		
		assertEquals(expected, actual);
		
	}

}

package com.restaurant.controller;

import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

import org.junit.Test;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ControllerTest {

	@Test
	public void testGet() throws ServletException, IOException {

		Controller controller = new Controller();
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		when(req.getSession()).thenReturn(session);

		ServletContext sc = mock(ServletContext.class);
		when(req.getServletContext()).thenReturn(sc);
		when(sc.getAttribute(isA(String.class))).thenReturn("ua");

		doNothing().when(session).setAttribute(isA(String.class), anyMap());
		doNothing().when(session).removeAttribute(isA(String.class));

		when(req.getParameter(isA(String.class))).thenReturn("search");

		RequestDispatcher disp = mock(RequestDispatcher.class);
		when(req.getRequestDispatcher(isA(String.class))).thenReturn(disp);

		controller.doGet(req, resp);

		verify(req).getRequestDispatcher(isA(String.class));
		verify(disp).forward(req, resp);

	}

	@Test
	public void testPost() throws ServletException, IOException {
		Controller controller = new Controller();
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		when(req.getSession()).thenReturn(session);
		
		ServletContext sc = mock(ServletContext.class);
		when(req.getServletContext()).thenReturn(sc);
		when(sc.getAttribute(isA(String.class))).thenReturn("ua");

		when(req.getParameter(isA(String.class))).thenReturn("search");

		doNothing().when(resp).sendRedirect(isA(String.class));
		doNothing().when(req).setCharacterEncoding(isA(String.class));

		controller.doPost(req, resp);

		verify(resp).sendRedirect(isA(String.class));

	}
}

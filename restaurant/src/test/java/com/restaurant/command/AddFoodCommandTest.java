package com.restaurant.command;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.MockedStatic;

import com.restaurant.dao.AppException;
import com.restaurant.dao.Pool;
import com.restaurant.dao.entity.Dish;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

public class AddFoodCommandTest {

	@Test
	public void testAddFoodCommand() throws SQLException {

		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		when(req.getSession()).thenReturn(session);

		ServletContext sc = mock(ServletContext.class);
		when(req.getServletContext()).thenReturn(sc);
		when(sc.getAttribute(isA(String.class))).thenReturn("ua");

		Dish dish = new Dish(1, "Салаты", "Цезарь", "Салатный микс", 129, "avalable");

		when(req.getParameter("name")).thenReturn(dish.getName());

		when(req.getParameter("category")).thenReturn(dish.getCategory());

		when(req.getParameter("description")).thenReturn(dish.getDescription());

		when(req.getParameter("price")).thenReturn(String.valueOf(dish.getPrice()));

		when(req.getParameter("status")).thenReturn(dish.getStatus());

		Connection con = mock(Connection.class);

		Pool pool = mock(Pool.class);

		when(pool.getConnection()).thenReturn(con);

		MockedStatic<Pool> mocked = mockStatic(Pool.class);

		mocked.when(Pool::getInstance).thenReturn(pool);

		when(req.getParameter("type")).thenReturn(null).thenReturn(null);

		PreparedStatement pstmt = mock(PreparedStatement.class);
		ResultSet rs = mock(ResultSet.class);

		when(con.prepareStatement(isA(String.class), isA(Integer.class))).thenReturn(pstmt);
		when(con.prepareStatement(isA(String.class))).thenReturn(pstmt);

		when(pstmt.executeQuery()).thenReturn(rs);

		when(rs.next()).thenReturn(true).thenReturn(true);

		when(rs.getInt(1)).thenReturn(1).thenReturn(1);

		when(pstmt.executeUpdate()).thenReturn(1);

		when(pstmt.getGeneratedKeys()).thenReturn(rs);

		Part filePart = mock(Part.class);
		try {
			when(req.getPart(isA(String.class))).thenReturn(filePart);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}

		when(filePart.getSubmittedFileName()).thenReturn("test.jpg");

		when(filePart.getSize()).thenReturn(50L);

		when(sc.getRealPath(isA(String.class))).thenReturn("img");

		BufferedImage bufImage = mock(BufferedImage.class);
		MockedStatic<ImageIO> io = mockStatic(ImageIO.class);
		try {

			when(ImageIO.read(isA(File.class))).thenReturn(bufImage);

			when(ImageIO.read(new File("img\test.jpg"))).thenReturn(bufImage);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		when(bufImage.getHeight()).thenReturn(70);

		when(bufImage.getWidth()).thenReturn(70);

		MockedStatic<Files> files = mockStatic(Files.class);

		AddFoodCommand command = new AddFoodCommand();
		String actual = "";

		try {
			actual = command.execute(req, resp);
		} catch (AppException e) {
			e.printStackTrace();
		}

		String expected = "controller?command=menu&button=addFood";

		assertEquals(expected, actual);
		mocked.close();
		io.close();
		files.close();

	}

}

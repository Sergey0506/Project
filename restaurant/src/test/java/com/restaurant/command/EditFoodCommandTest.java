package com.restaurant.command;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


import org.junit.Test;
import org.mockito.MockedStatic;

import com.restaurant.dao.AppException;
import com.restaurant.dao.DBException;
import com.restaurant.dao.Pool;
import com.restaurant.dao.entity.Dish;
import com.restaurant.dao.entity.User;

import java.sql.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditFoodCommandTest {
	
	

	@Test
	public void testEditFoodCommand() throws SQLException, DBException {
		
		HttpServletRequest req = mock(HttpServletRequest.class); 
		HttpServletResponse resp = mock(HttpServletResponse.class); 
		HttpSession session = mock(HttpSession.class);
		when(req.getSession()).thenReturn(session);	 
		
		ServletContext sc = mock(ServletContext.class);
		when(sc.getAttribute(isA(String.class))).thenReturn("ua");
		when(req.getServletContext()).thenReturn(sc);
		
		User user = new User(1,"login","manager","ru");
		when(session.getAttribute("user")).thenReturn(user);
		 
		
		when(req.getParameter("id"))
		.thenReturn("1");
		
		Connection con = mock(Connection.class);
		
		Pool pool = mock(Pool.class);
		
		when(pool.getConnection())
			.thenReturn(con);
		
		
		MockedStatic<Pool> mocked =
			mockStatic(Pool.class);
		
		mocked.when(Pool::getInstance)
			.thenReturn(pool);
		
	  
		PreparedStatement pstmt = mock(PreparedStatement.class);
		ResultSet rs = mock(ResultSet.class);
		
		when(con.prepareStatement(isA(String.class)))
			.thenReturn(pstmt);
	
		when(pstmt.executeQuery())
			.thenReturn(rs);
		
		when(rs.next())
			.thenReturn(true);
		
		Dish dish = new Dish(1,"Салаты","Цезарь с курицей и беконом",
				"Салатный микс",129,"avalable");

		when(rs.getInt("id"))
			.thenReturn(dish.getId());
		
		when(rs.getString("category"))
			.thenReturn(dish.getCategory());
		
		when(rs.getString("name"))
		.thenReturn(dish.getName());
		
		when(rs.getString("description"))
		.thenReturn(dish.getDescription());
		
		when(rs.getInt("price"))
		.thenReturn(dish.getPrice());
		
		when(rs.getString("status"))
		.thenReturn(dish.getStatus());
		
		EditFoodCommand command = new EditFoodCommand();
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

package com.restaurant.dao.mysql;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import org.junit.Test;

import com.restaurant.dao.entity.Dish;

import java.sql.*;
import java.util.List;


public class MysqlFoodDAOTest {
	
	@Test
	public void testEditDish() throws Exception {
		Connection con = mock(Connection.class);
		MysqlDishDAO dishDAO = mock(MysqlDishDAO.class);
		doCallRealMethod().when(dishDAO)
		.editDish(con,1,"Салаты","Цезарь",
				"Салатный микс",129,"avalable");
		
		PreparedStatement pstmt = mock(PreparedStatement.class);
		ResultSet rs = mock(ResultSet.class);
		
		when(con.prepareStatement(isA(String.class)))
		.thenReturn(pstmt)
		.thenReturn(pstmt);
		
		when(pstmt.executeQuery())
		.thenReturn(rs);
		
		
		doNothing().when(pstmt).setString(anyInt(),isA(String.class));
		
		
		when(rs.next())
		.thenReturn(true);
		
		when(rs.getInt(1))
		.thenReturn(1);
		
		when(pstmt.executeUpdate())
		.thenReturn(1);
		
		dishDAO.editDish(con,1,"Салаты","Цезарь",
				"Салатный микс",129,"avalable");
		
		verify(pstmt, times(1)).executeUpdate();
	}
	
	@Test
	public void testFindAllFromDishesSortBy() throws Exception {
		Connection con = mock(Connection.class);
		MysqlDishDAO dishDAO = mock(MysqlDishDAO.class);
		doCallRealMethod().when(dishDAO)
		.findAllFromDishesSortBy(con,null,"name","asc");
		
		Statement stmt = mock(Statement.class);
		ResultSet rs = mock(ResultSet.class);
		
		when(con.createStatement())
		.thenReturn(stmt);
		
		when(stmt.executeQuery(isA(String.class)))
		.thenReturn(rs);
		
		when(rs.next())
		.thenReturn(true)
		.thenReturn(false);
		
		Dish dish = new Dish(1,"Салаты","Цезарь",
				"Салатный микс",129,"avalable");
		
		System.out.println(dish);
		
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
		
		List<Dish> result = dishDAO.findAllFromDishesSortBy(con,null,"name","asc");
		
		assertTrue(result.contains(dish));
	}
	
	@Test
	public void testFindCategoryValues() throws Exception {
		Connection con = mock(Connection.class);
		MysqlDishDAO dishDAO = mock(MysqlDishDAO.class);
		doCallRealMethod().when(dishDAO)
		.findCategoriesValues(con);
		
		Statement stmt = mock(Statement.class);
		ResultSet rs = mock(ResultSet.class);
		
		when(con.createStatement())
		.thenReturn(stmt);
		
		when(stmt.executeQuery(isA(String.class)))
		.thenReturn(rs);
		
		when(rs.next())
		.thenReturn(true)
		.thenReturn(true)
		.thenReturn(true)
		.thenReturn(true)
		.thenReturn(true)
		.thenReturn(true)
		.thenReturn(true)
		.thenReturn(false);
	
		when(rs.getString(isA(String.class)))
		.thenReturn("Салаты")
		.thenReturn("Бургеры")
		.thenReturn("Закуски")
		.thenReturn("Десерты")
		.thenReturn("Гриль")
		.thenReturn("Гарниры")
		.thenReturn("Основные блюда из мяса");
		
		List<String> result = dishDAO.findCategoriesValues(con);
		
		assertTrue(result.contains("Десерты"));
		assertTrue(result.size()==7);
	}
	
}

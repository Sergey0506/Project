package com.restaurant.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.restaurant.dao.DBException;
import com.restaurant.dao.DishDAO;
import com.restaurant.dao.Query;
import com.restaurant.dao.Util;
import com.restaurant.dao.entity.Dish;
import com.restaurant.filter.IndexFilter;

/**
 * Data access object for Food entity.
 * 
 * @author Serhii Yakovenko.
 */
public class MysqlDishDAO implements DishDAO {

	private static final Logger log = Logger.getLogger(IndexFilter.class);

	/**
	 * Return identifier of created Dish entity in DB by given all entity param and
	 * Connection.
	 *
	 * @param con         Pool connection.
	 * @param name        Dish entity name.
	 * @param category    Dish entity category id.
	 * @param description Dish entity description.
	 * @param price       Dish entity price.
	 * 
	 * @return identifier of created Dish entity.
	 * 
	 * @throws DBException if there is a database access error.
	 */
	@Override
	public int createDish(Connection con, String name, String category, String description, int price)
			throws DBException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int categoryId;
		int dishId = 0;
		try {
			pstmt = con.prepareStatement(Query.FIND_ID_CATEGORY);
			pstmt.setString(1, category);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				categoryId = rs.getInt(1);
				Util.close(pstmt);
				pstmt = con.prepareStatement(Query.INSERT_DISH, Statement.RETURN_GENERATED_KEYS);

				pstmt.setInt(1, categoryId);
				pstmt.setString(2, name);
				pstmt.setString(3, description);
				pstmt.setInt(4, price);

				if (pstmt.executeUpdate() > 0) {
					Util.close(rs);
					rs = pstmt.getGeneratedKeys();
					if (rs.next()) {
						dishId = rs.getInt(1);
					}
				}
			}

		} catch (SQLException e) {
			log.error("Database access error in MysqlDishDao.createDish()");
			throw new DBException("error_Data", e);
		} finally {
			Util.close(rs);
			Util.close(pstmt);
			Util.close(con);
		}
		return dishId;
	}

	/**
	 * Find and return Dish entity from DB by given Food id and Connection.
	 *
	 * @param con    Pool connection.
	 * @param dishId Dish identifier.
	 * 
	 * @return Dish entity from DB.
	 * 
	 * @throws DBException if there is a database access error.
	 */
	@Override
	public Dish findDish(Connection con, int foodId) throws DBException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Dish dish = null;

		try {
			pstmt = con.prepareStatement(Query.FIND_DISH);
			pstmt.setInt(1, foodId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				dish = extractDish(rs);
			}

		} catch (SQLException e) {
			log.error("Database access error in MysqlDishDao.findDish()");
			throw new DBException("error_Data", e);
		} finally {
			Util.close(rs);
			Util.close(pstmt);
			Util.close(con);
		}
		return dish;
	}

	/**
	 * Edit Dish entity in DB by given all entity param and Connection.
	 *
	 * @param con         Pool connection.
	 * @param name        Dish entity name.
	 * @param category    Dish entity category id.
	 * @param description Dish entity description.
	 * @param price       Dish entity price.
	 * @param status      Dish entity status.
	 * 
	 * @throws DBException if there is a database access error.
	 */
	@Override
	public void editDish(Connection con, int id, String name, String category, String description, int price,
			String status) throws DBException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int categoryId;
		try {
			pstmt = con.prepareStatement(Query.FIND_ID_CATEGORY);
			pstmt.setString(1, category);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				categoryId = rs.getInt(1);

				Util.close(pstmt);
				pstmt = con.prepareStatement(Query.EDIT_DISH);

				pstmt.setInt(1, categoryId);
				pstmt.setString(2, name);
				pstmt.setString(3, description);
				pstmt.setInt(4, price);
				pstmt.setString(5, status);
				pstmt.setInt(6, id);

				if (pstmt.executeUpdate() == 0) {
					throw new SQLException();
				}

			}

		} catch (SQLException e) {
			log.error("Database access error in MysqlDishDao.editDish()");
			throw new DBException("error_Data", e);
		} finally {
			Util.close(rs);
			Util.close(pstmt);
			Util.close(con);
		}

	}

	/**
	 * Returns List of Dish entity from DB by different sort rules.
	 *
	 * @param con      Pool connection.
	 * @param category null or for SQL query "where category=?"
	 * @param sortBy   rule for SQL query. Can be: name, price.
	 * @param sortRule rule for SQL query. Can be: ASC, DESC.
	 * 
	 * @return List<Dish> List of dish by different sort rules.
	 * 
	 * @throws DBException if there is a database access error.
	 */
	@Override
	public List<Dish> findAllFromDishesSortBy(Connection con, String category, String sortBy, String sortRule)
			throws DBException {
		Statement stmt = null;
		ResultSet rs = null;
		List<Dish> result = new ArrayList<>();
		String search;
		if (category == null || category.equals("full")) {
			search = "";
		} else {
			search = "WHERE category='" + category + "'";
		}

		String query = "SELECT dishes.id, category, name, description, price,status FROM dishes "
				+ "JOIN categories on (dishes.category_id=categories.id) " + search + " order by " + sortBy + " " + sortRule;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				result.add(extractDish(rs));
			}

		} catch (SQLException e) {
			log.error("Database access error in MysqlDishDao.findAllFromDishesSortBy()");
			throw new DBException("error_Data", e);
		} finally {
			Util.close(rs);
			Util.close(stmt);
			Util.close(con);
		}
		return result;
	}

	/**
	 * Returns List of existing categories from DB.
	 *
	 * @param con Pool connection.
	 * 
	 * @return List<String> List of existing categories.
	 * 
	 * @throws DBException if there is a database access error.
	 */
	@Override
	public List<String> findCategoriesValues(Connection con) throws DBException {
		Statement stmt = null;
		ResultSet rs = null;
		List<String> result = new ArrayList<>();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(Query.SELECT_NAME_FROM_CATEGORIES);

			while (rs.next()) {
				result.add(rs.getString("category"));
			}

		} catch (SQLException e) {
			log.error("Database access error in MysqlDishDao.findCategoriesValues()");
			Util.close(con);
			throw new DBException("error_Data", e);
		} finally {
			Util.close(rs);
			Util.close(stmt);
		}
		return result;
	}

	/**
	 * Extracts a dish from the result set row.
	 * 
	 * @param rs ResultSet row.
	 * 
	 * @return Dish entity.
	 * 
	 * @throws SQLException if the columnLabel is not valid; if a database access
	 *                      error occurs or this method iscalled on a closed result
	 *                      set.
	 */
	private Dish extractDish(ResultSet rs) throws SQLException {
		Dish item = new Dish(rs.getInt("id"), rs.getString("category"), rs.getString("name"),
				rs.getString("description"), rs.getInt("price"), rs.getString("status"));

		return item;
	}

}

package com.restaurant.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import java.util.TreeMap;

import com.restaurant.dao.DBException;
import com.restaurant.dao.OrderDAO;
import com.restaurant.dao.Query;
import com.restaurant.dao.Util;
import com.restaurant.dao.entity.Basket;
import com.restaurant.dao.entity.Dish;
import com.restaurant.dao.entity.Order;
import com.restaurant.filter.IndexFilter;

/**
 * Data access object for Food entity.
 * 
 * @author Serhii Yakovenko.
 */
public class MysqlOrderDAO implements OrderDAO {
	
	private static final Logger log = Logger.getLogger(IndexFilter.class);

	/**
	 * Return List of Order entity from DB by User identifier and Connection.
	 *
	 * @param con    Pool connection.
	 * @param userId User identifier. Can be null
	 * 
	 * @return List of User Orders or List of ALL Orders when userId=null.
	 * 
	 * @throws DBException if there is a database access error.
	 */
	@Override
	public List<Order> findOrders(Connection con, int userId) throws DBException {
		List<Order> orders = new ArrayList<>();
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		Statement stmt = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		try {
			if (userId != 0) {
				pstmt1 = con.prepareStatement(Query.FIND_USER_ORDERS);
				pstmt1.setInt(1, userId);
				rs1 = pstmt1.executeQuery();
			} else {
				stmt = con.createStatement();
				rs1 = stmt.executeQuery(Query.FIND_ALL_ORDERS);
			}

			while (rs1.next()) {

				pstmt2 = con.prepareStatement(Query.FIND_ORDERS_DISHES);
				pstmt2.setInt(1, rs1.getInt(1));

				rs2 = pstmt2.executeQuery();

				TreeMap<Dish, Integer> map = new TreeMap<>();

				while (rs2.next()) {
					map.put(new Dish(rs2.getInt(1), rs2.getString(2), rs2.getString(3), rs2.getString(4), rs2.getInt(5),
							rs2.getString(7)), rs2.getInt(6));
				}

				orders.add(new Order(rs1.getInt(1), rs1.getInt(2), map, rs1.getInt(3), rs1.getString(4)));
			}

		} catch (SQLException e) {
			log.error("Database access error in MysqlOrderDao.findOrders()");
			throw new DBException("error_Data", e);
		} finally {
			Util.close(rs1);
			Util.close(rs2);
			Util.close(stmt);
			Util.close(pstmt1);
			Util.close(pstmt2);
			Util.close(con);
		}
		return orders;
	}

	/**
	 * Change Order entity status by given Connection, Order identifier and new
	 * status.
	 *
	 * @param con    Pool connection.
	 * @param id     Order identifier
	 * @param status new status
	 * 
	 * @throws DBException if there is a database access error.
	 */
	@Override
	public void changeOrderStatus(Connection con, int id, String status) throws DBException {
		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(Query.UPDATE_ORDER_STATUS);
			pstmt.setString(1, status);
			pstmt.setInt(2, id);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			log.error("Database access error in MysqlOrderDao.changeOrderStatus()");
			throw new DBException("error_Data", e);
		} finally {
			Util.close(pstmt);
			Util.close(con);
		}

	}

	/**
	 * DB transaction. Create new Order entity in DB by given Connection, User
	 * identifier and his Basket entity.
	 *
	 * @param con    Pool connection.
	 * @param userId User identifier
	 * @param basket Basket entity of current User
	 * 
	 * @throws DBException if there is a database access error.
	 */
	@Override
	public void createOrder(Connection con, int userId, Basket basket) throws DBException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int id = 0;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(Query.NEW_ORDER, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, userId);
			pstmt.setInt(2, basket.getSum());

			if (pstmt.executeUpdate() > 0) {
				rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					id = rs.getInt(1);

				}
			}

			Map<Dish, Integer> basketMap = basket.getBasket();

			for (Entry<Dish, Integer> entry : basketMap.entrySet()) {
				pstmt = con.prepareStatement(Query.ORDER_DISHES);
				pstmt.setInt(1, id);
				pstmt.setInt(2, entry.getKey().getId());
				pstmt.setInt(3, entry.getValue());
				pstmt.executeUpdate();

			}

			con.commit();
		} catch (SQLException e) {
			Util.rollback(con);
			log.error("Database access error in MysqlOrderDao.createOrder()");
			throw new DBException("error_Data", e);
		} finally {
			Util.close(rs);
			Util.close(pstmt);
			Util.close(con);
		}

	}

}

package com.restaurant.dao;

import java.sql.Connection;
import java.util.List;

import com.restaurant.dao.entity.Basket;
import com.restaurant.dao.entity.Order;

public interface OrderDAO {

	void createOrder(Connection con, int userId, Basket basket) throws DBException;

	List<Order> findOrders(Connection con, int userId) throws DBException;

	void changeOrderStatus(Connection con, int id, String status) throws DBException;

}

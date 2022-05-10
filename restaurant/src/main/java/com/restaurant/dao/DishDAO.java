package com.restaurant.dao;

import java.sql.Connection;
import java.util.List;

import com.restaurant.dao.entity.Dish;

public interface DishDAO {

	int createDish(Connection con, String name, String category, String description, int price) throws DBException;

	Dish findDish(Connection con, int foodId) throws DBException;

	void editDish(Connection con, int id, String name, String category, String description, int price, String status)
			throws DBException;

	public List<Dish> findAllFromDishesSortBy(Connection con, String category, String sortBy, String sortRule)
			throws DBException;

	public List<String> findCategoriesValues(Connection con) throws DBException;
}

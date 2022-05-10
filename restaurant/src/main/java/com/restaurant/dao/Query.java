package com.restaurant.dao;

/**
 * Holder for queries.
 * 
 * @author Serhii Yakovenko.
 * 
 */
public class Query {

	// DishDAO
	public static final String SELECT_NAME_FROM_CATEGORIES = "SELECT category FROM categories";
	public static final String FIND_DISH = "SELECT dishes.id, category, name, description, price, status FROM dishes "
			+ "JOIN categories on (dishes.category_id=categories.id) Where dishes.id=?";
	public static final String FIND_ID_CATEGORY = "SELECT id FROM categories WHERE category=?";
	public static final String INSERT_DISH = "INSERT INTO dishes VALUES(default,?,?,?,?, default)";
	public static final String EDIT_DISH = "UPDATE dishes SET category_id=?, name=?, description=?, price=?,status=?  WHERE id=?";
	public static final String SELECT_FROM_DISHES_WITH_SORT = "SELECT id, category, name, description, price,status FROM dishes "
			+ "JOIN categories on (dishes.category_id=categories.id) order by ? ?)";
	public static final String SELECT_FROM_DISHES_BY_CATEGORY_AND_SORT = "SELECT id, category, name, description, price,status FROM dishes "
			+ "JOIN categories on (dishes.category_id=categories.id) WHERE category=? order by ? ?";

	// OrderDAO

	public static final String NEW_ORDER = "INSERT into orders values(default, ?, ?, default)";
	public static final String ORDER_DISHES = "INSERT into orders_dishes values(?, ?, ?)";
	public static final String UPDATE_ORDER_STATUS = "UPDATE orders SET status = ? WHERE id = ?";
	public static final String FIND_ALL_ORDERS = "Select * from orders";
	public static final String FIND_USER_ORDERS = "Select * from orders Where user_id=?";
	public static final String FIND_ORDERS_DISHES = "Select dish_id, category, name, description, price, count, status FROM orders_dishes "
			+ "JOIN dishes on (orders_dishes.dish_id=dishes.id) "
			+ "JOIN categories on (dishes.category_id=categories.id) WHERE order_id=?";

	// UserDAO

	public static final String FIND_USER = "SELECT id, login, role, locale FROM users WHERE login=? AND password=?";
	public static final String FIND_USER_BY_LOGIN = "SELECT id, login, password, role, locale FROM users WHERE login=?";
	public static final String INSERT_USER = "INSERT INTO users values (DEFAULT, ?, ?, DEFAULT, DEFAULT)";
	public static final String CHANGE_USER_LOCALE = "UPDATE users SET locale = ? WHERE login = ?";
	public static final String CHANGE_USER_PASSWORD = "UPDATE users SET password = ? WHERE login = ?";
	public static final String CHANGE_USER_ROLE = "UPDATE users SET role = ? WHERE login = ?";

}

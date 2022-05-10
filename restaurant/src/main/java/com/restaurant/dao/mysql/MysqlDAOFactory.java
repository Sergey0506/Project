package com.restaurant.dao.mysql;
// com.my.dao.mysql.MysqlDAOFactory

import com.restaurant.dao.*;

public class MysqlDAOFactory extends DAOFactory {

	@Override
	public UserDAO getUserDAO() {
		return new MysqlUserDAO();
	}

	@Override
	public OrderDAO getOrderDAO() {
		return new MysqlOrderDAO();
	}

	@Override
	public DishDAO getDishDAO() {
		return new MysqlDishDAO();
	}
	

}

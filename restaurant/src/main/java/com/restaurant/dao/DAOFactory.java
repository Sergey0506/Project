
package com.restaurant.dao;

/**
 * Abstract DAO Factory
 * 
 * @author Serhii Yakovenko.
 * 
 */
public abstract class DAOFactory {

	private static DAOFactory instance;

	public static synchronized DAOFactory getInstatance() {
		if (instance == null) {
			try {
				instance = (DAOFactory) Class.forName("com.restaurant.dao.mysql.MysqlDAOFactory")
						.getDeclaredConstructor().newInstance();
			} catch (ReflectiveOperationException ex) {
				ex.printStackTrace();
				throw new IllegalStateException("Cannot instantiate DAOFactory", ex);
			}
		}
		return instance;
	}

	protected DAOFactory() {
	}

	public abstract UserDAO getUserDAO();

	public abstract OrderDAO getOrderDAO();

	public abstract DishDAO getDishDAO();

}

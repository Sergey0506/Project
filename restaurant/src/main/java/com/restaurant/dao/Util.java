package com.restaurant.dao;


import java.sql.Connection;
import java.sql.SQLException;


/**
 * DB util methods
 * 
 * @author Serhii Yakovenko.
 * 
 */
public class Util {

	/**
	 * close the given object.
	 * 
	 * @param object AutoClosable object.
	 */
	public static void close(AutoCloseable object) {
		if (object != null) {
			try {
				object.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Rollbacks the given connection.
	 * 
	 * @param con Connection to be rollbacked and closed.
	 */
	public static void rollback(Connection con) {
		if (con != null) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}

package com.restaurant.dao;

import java.sql.SQLException;

/**
 * Database Exception
 * 
 * @author Serhii Yakovenko.
 * 
 */
public class DBException extends Exception {

	private static final long serialVersionUID = -7954534356354103240L;

	public DBException(String message, SQLException cause) {
		super(message, cause);
	}

}

package com.restaurant.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.restaurant.filter.IndexFilter;

/**
 * Connection Pool. Works with Mysql DB.
 * 
 * @author Serhii Yakovenko.
 * 
 */
public class Pool {
	
	private static final Logger log = Logger.getLogger(IndexFilter.class);

	// //////////////////////////////////////////////////////////
	// singleton
	// //////////////////////////////////////////////////////////

	private static Pool instance;

	public static synchronized Pool getInstance() {
		if (instance == null) {
			instance = new Pool();
		}
		return instance;
	}

	private DataSource ds;

	private Pool() {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource) envContext.lookup("jdbc/restaurant");
		} catch (NamingException ex) {
			log.error("Cannot obtain a data source", ex);
			throw new IllegalStateException("Cannot obtain a data source", ex);
		}
	}

	/**
	 * Returns a DB connection from the Pool Connections. Before using this method
	 * you must configure the Date Source and the Connections Pool in your
	 * WEB_APP_ROOT/META-INF/context.xml file.
	 * 
	 * @return A DB connection.
	 */
	public Connection getConnection() {
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException ex) {
			log.error("Cannot obtain a connection from the pool", ex);
		}

		return con;
	}

}

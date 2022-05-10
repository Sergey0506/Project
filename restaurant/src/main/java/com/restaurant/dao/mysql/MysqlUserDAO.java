package com.restaurant.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.restaurant.dao.DBException;
import com.restaurant.dao.Query;
import com.restaurant.dao.UserDAO;
import com.restaurant.dao.Util;
import com.restaurant.dao.entity.User;
import com.restaurant.filter.IndexFilter;

/**
 * Data access object for User entity.
 * 
 * @author Serhii Yakovenko.
 */
public class MysqlUserDAO implements UserDAO {

	private static final Logger log = Logger.getLogger(IndexFilter.class);

	/**
	 * Create and return user with given Connection, login and password.
	 *
	 * @param con      Pool connection.
	 * @param login    User login.
	 * @param password User password.
	 * 
	 * @return User entity.
	 * 
	 * @throws DBException if there is a database access error.
	 */
	@Override
	public User createUser(Connection con, String login, String password) throws DBException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = null;
		try {
			pstmt = con.prepareStatement(Query.INSERT_USER, Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, login);
			pstmt.setString(2, password);

			if (pstmt.executeUpdate() > 0) {
				rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					user = new User(rs.getInt(1), login, "user", "ua");

				}
			}

		} catch (SQLException e) {
			log.error("Database access error in MysqlUserDao.createUser()");
			throw new DBException("error_Data", e);
		} finally {
			Util.close(rs);
			Util.close(pstmt);
			Util.close(con);
		}
		return user;
	}

	/**
	 * Returns a user with given Connection, login and password.
	 *
	 * @param con      Pool connection.
	 * @param login    User login.
	 * @param password User password.
	 * 
	 * @return User entity.
	 * 
	 * @throws DBException if there is a database access error.
	 */
	@Override
	public User findUser(Connection con, String login, String password) throws DBException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = null;

		try {
			pstmt = con.prepareStatement(Query.FIND_USER);
			pstmt.setString(1, login);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				user = new User(rs.getInt("id"), rs.getString("login"), rs.getString("role"), rs.getString("locale"));
			} else {
				return null;
			}
		} catch (SQLException e) {
			log.error("Database access error in MysqlUserDao.findUser()");
			throw new DBException("error_Data", e);
		} finally {
			Util.close(rs);
			Util.close(pstmt);
			Util.close(con);
		}

		return user;
	}

	/**
	 * Returns a user with given Connection by login.
	 *
	 * @param con   Pool connection.
	 * @param login User login.
	 * 
	 * @return User entity.
	 * @throws DBException if there is a database access error.
	 */
	@Override
	public User findUser(Connection con, String login) throws DBException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = null;

		try {
			pstmt = con.prepareStatement(Query.FIND_USER_BY_LOGIN);
			pstmt.setString(1, login);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				user = new User(rs.getInt("id"), rs.getString("login"), rs.getString("role"), rs.getString("locale"));
			} else {
				return null;
			}
		} catch (SQLException e) {
			log.error("Database access error in MysqlUserDao.findUser()");
			throw new DBException("error_Data", e);
		} finally {
			Util.close(rs);
			Util.close(pstmt);
			Util.close(con);
		}

		return user;
	}

	/**
	 * Returns a user with the given identifier.
	 *
	 * @param con      Pool connection.
	 * @param login    User login.
	 * @param password User password.
	 * 
	 * @throws DBException if there is a database access error.
	 */
	@Override
	public boolean checkUserPassword(Connection con, String login, String password) throws DBException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = con.prepareStatement(Query.FIND_USER_BY_LOGIN);
			pstmt.setString(1, login);
			rs = pstmt.executeQuery();

			if (rs.next() && password.equals(rs.getString("password"))) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			log.error("Database access error in MysqlUserDao.checkUserPassword()");
			throw new DBException("error_Data", e);
		} finally {
			Util.close(rs);
			Util.close(pstmt);
			Util.close(con);
		}

	}

	/**
	 * Returns a user with the given identifier.
	 *
	 * @param con      Pool connection.
	 * @param login    User login.
	 * @param password User password.
	 * @param locale   User locale.
	 * 
	 * @throws DBException if there is a database access error.
	 */
	@Override
	public void updateUser(Connection con, String login, String password, String locale) throws DBException {

		PreparedStatement pstmt = null;

		try {
			if (password == null) {
				pstmt = con.prepareStatement(Query.CHANGE_USER_LOCALE);
				pstmt.setString(1, locale);
			} else {
				pstmt = con.prepareStatement(Query.CHANGE_USER_PASSWORD);
				pstmt.setString(1, password);
			}
			pstmt.setString(2, login);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			log.error("Database access error in MysqlUserDao.updateUser()");
			throw new DBException("error_Data", e);
		} finally {
			Util.close(pstmt);
			Util.close(con);
		}
	}

	/**
	 * Change user role with the given login and role.
	 *
	 * @param con   Pool connection.
	 * @param login User login.
	 * @param role  User role.
	 * 
	 * @throws DBException if there is a database access error.
	 */
	@Override
	public void changeUserRole(Connection con, String login, String role) throws DBException {
		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(Query.CHANGE_USER_ROLE);
			pstmt.setString(1, role);
			pstmt.setString(2, login);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			log.error("Database access error in MysqlUserDao.changeUserRole()");
			throw new DBException("error_Data", e);
		} finally {
			Util.close(pstmt);
			Util.close(con);
		}
	}
}

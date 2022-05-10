package com.restaurant.dao;

import java.sql.Connection;

import com.restaurant.dao.entity.User;

public interface UserDAO {

	public User createUser(Connection con, String login, String password) throws DBException;

	User findUser(Connection con, String login, String password) throws DBException;

	User findUser(Connection con, String login) throws DBException;

	boolean checkUserPassword(Connection con, String login, String password) throws DBException;

	void updateUser(Connection con, String login, String password, String locale) throws DBException;

	void changeUserRole(Connection con, String login, String role) throws DBException;

}

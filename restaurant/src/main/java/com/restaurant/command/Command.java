package com.restaurant.command;

import javax.servlet.http.*;

import com.restaurant.dao.AppException;

/**
 * Interface for the implementation Command pattern .
 * 
 * @author Serhii Yakovenko.
 * 
 */
public interface Command {

	/**
	 * Execution method for commands.
	 * 
	 * @return URL address.
	 * @throws AppException
	 */
	String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException;

}

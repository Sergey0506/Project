package com.restaurant.dao;

/**
 * Application Exception
 * 
 * @author Serhii Yakovenko.
 * 
 */
public class AppException extends Exception {

	private static final long serialVersionUID = 6816051399128046000L;

	public AppException(String message, Exception cause) {
		super(message, cause);
	}

}

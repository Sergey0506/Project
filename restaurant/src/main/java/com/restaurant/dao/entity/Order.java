package com.restaurant.dao.entity;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

/**
 * Order entity
 * 
 * @author Serhii Yakovenko.
 * 
 */
public class Order implements Serializable {

	private static final long serialVersionUID = -2756032863190580361L;

	private int order_id;
	private int user_id;
	private Map<Dish, Integer> order = null;
	private int sum;
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Order(int order_id, int user_id, TreeMap<Dish, Integer> basket, int sum, String status) {
		this.order_id = order_id;
		this.user_id = user_id;
		order = basket;
		this.sum = sum;
		this.status = status;
	}

	public Map<Dish, Integer> getOrder() {
		return order;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public int getSum() {
		return sum;
	}

	public int getUser_id() {
		return user_id;
	}

}

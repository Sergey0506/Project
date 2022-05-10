package com.restaurant.dao.entity;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

/**
 * Basket entity
 * 
 * @author Serhii Yakovenko.
 * 
 */
public class Basket implements Serializable {

	private static final long serialVersionUID = -2700901098315491559L;

	private Map<Dish, Integer> basket = null;
	private int sum;
	private int count;

	public Basket() {
		basket = new TreeMap<>();
	}

	public Map<Dish, Integer> getBasket() {
		return basket;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getSum() {
		return sum;
	}

	public int getCount() {
		return count;
	}

}

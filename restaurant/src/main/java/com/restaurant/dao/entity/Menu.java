package com.restaurant.dao.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Menu entity
 * 
 * @author Serhii Yakovenko.
 * 
 */
public class Menu implements Serializable {

	private static final long serialVersionUID = 2380051194278127013L;

	private List<Map<String, List<Dish>>> pages;
	private Map<String, List<Dish>> page;

	public Menu() {
		pages = new ArrayList<>();
	}

	public Map<String, List<Dish>> createPage() {
		page = new TreeMap<>();
		return page;
	}

	public void addPage(Map<String, List<Dish>> page) {
		pages.add(page);
	}

	public List<Map<String, List<Dish>>> getMenu() {
		return pages;
	}

}

package com.restaurant.dao.entity;

import java.io.Serializable;

/**
 * Food entity
 * 
 * @author Serhii Yakovenko.
 * 
 */
public class Dish implements Comparable<Dish>, Serializable {

	private static final long serialVersionUID = 6986082052660868073L;

	private int id;
	private String category;
	private String name;
	private String description;
	private int price;
	private String status;

	public Dish(int id, String category, String name, String description, int price, String status) {
		this.id = id;
		this.category = category;
		this.name = name;
		this.description = description;
		this.price = price;
		this.status = status;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", category=" + category + ", name=" + name + ", description=" + description
				+ ", price=" + price + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dish other = (Dish) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public int compareTo(Dish o) {
		if (this.id > o.id) {
			return 1;
		} else if (this.id < o.id) {
			return -1;
		}
		return 0;
	}

}

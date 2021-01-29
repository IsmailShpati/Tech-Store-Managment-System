package models;

import java.io.Serializable;

public class Category implements Serializable {

	private static final long serialVersionUID = 4023275679380795015L;
	
	private String category;
	public Category(String category) {
		this.category = category;
	}
	public String getCategory() {
		return category;
	}
	public String toString() {
		return category;
	}
}

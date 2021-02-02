package models;

import java.io.Serializable;

public abstract class Item implements Serializable {

	private static final long serialVersionUID = -1343625581795496272L;
	
	private String itemName, itemModel;
	private double sellingPrice;
	
	public Item(String itemName, double sellingPrice) {
		this.itemName = itemName;
		this.sellingPrice = sellingPrice;
	}
	public Item(String itemName, String itemModel, double sellingPrice) {
		this.itemName = itemName;
		this.itemModel = itemModel;
		this.sellingPrice = sellingPrice;
	}
	
	public String getItemName() { return itemName; }
	public String getItemModel() { return itemModel; }
	public String getSellingPrice() { return sellingPrice+""; }
	public double getsellingPrice() { return sellingPrice; }
	
	public void setItemName(String itemName) { this.itemName = itemName; }
	public void setItemModel(String itemModel) { this.itemModel = itemModel; } 
	public void setSellingPrice(double sellingPrice) { this.sellingPrice = sellingPrice; }
	
	public String toString() {
		return itemName;
	}
	
}

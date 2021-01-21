package models;

public abstract class Item {

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
	public double getSellingPrice() { return sellingPrice; }
	
	public void setItemName(String itemName) { this.itemName = itemName; }
	public void setItemModel(String itemModel) { this.itemModel = itemModel; } 
	public void setSellingPrice(double sellingPrice) { this.sellingPrice = sellingPrice; }
	
}

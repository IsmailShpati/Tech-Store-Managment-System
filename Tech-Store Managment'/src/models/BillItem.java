package models;

public class BillItem extends Item{

	private int quantity;
	
	public BillItem(String itemName, String itemModel, double sellingPrice, int quantity) {
		super(itemName, itemModel, sellingPrice);
		this.quantity = quantity;
	}
	
	public BillItem(String itemName, double sellingPrice, int quantity) {
		super(itemName, sellingPrice);
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	

}

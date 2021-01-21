package models;

public class BillItem extends Item{

	private int quantity;
	private PurchaseDate date;
	
	public BillItem(String itemName, String itemModel, 
			double sellingPrice, int quantity, PurchaseDate date) {
		super(itemName, itemModel, sellingPrice);
		this.quantity = quantity;
		this.date = date;
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
	
	public PurchaseDate getDate() { return date; }
	public void setDate(PurchaseDate date) { this.date = date; }
	
	

}

package models;

public class BillItem extends Item{

	private static int BillNo;
	private int quantity, id;
	private PurchaseDate date;
	//private Cashier cashier;
	
	
	public BillItem(String itemName, String itemModel, 
			double sellingPrice, int quantity, PurchaseDate date) {
		super(itemName, itemModel, sellingPrice);
		BillNo++;
		id = BillNo;
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
	
	public double getTotalBillPrice() {
		return quantity * getSellingPrice();
	}

	
	public int getBillNO() { return id; }
}

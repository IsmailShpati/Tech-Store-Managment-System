package models;

public class BillItem extends Item{

	private static final long serialVersionUID = 3898208526204791983L;
	
	
	private static int BillNo;
	private int quantity, id;
	
	
	public BillItem(String itemName, String itemModel, 
			double sellingPrice, int quantity) {
		super(itemName, itemModel, sellingPrice);
		BillNo++;
		id = BillNo;
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
	
	
	public double getTotalBillPrice() {
		return quantity * getSellingPrice();
	}

	
	public int getBillNO() { return id; }
}

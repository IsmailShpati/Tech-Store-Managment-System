package models;

public class BillItem extends Item implements Comparable<BillItem>{

	private static final long serialVersionUID = 3898208526204791983L;
	
	
	private int quantity;
	
	
	public BillItem(String itemName, String itemModel, 
			double sellingPrice, int quantity) {
		super(itemName, itemModel, sellingPrice);
		this.quantity = quantity;
	}
	
	//For deep copying
	public BillItem(BillItem i) {
		this(i.getItemName(), i.getsellingPrice(), i.getQuantity());
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
	
	
	public boolean equals(BillItem i) {
		if(getItemName().equals(i.getItemName()))
				return true;
		return false;
	}
	
	
	public double getTotalBillPrice() {
		return quantity * getsellingPrice();
	}


	@Override
	public int compareTo(BillItem b) {
		return getItemName().compareTo(b.getItemName());
	}
}

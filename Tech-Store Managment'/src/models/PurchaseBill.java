package models;

import java.io.Serializable;
import java.time.LocalDate;

public class PurchaseBill implements Serializable {

	private static final long serialVersionUID = 7116010254554672250L;
	
	private LocalDate purchaseDate;
	private String itemName, managerName;
	private int quantityPurchased;
	private double price;
	
	public PurchaseBill(String itemName, String managerName, int quantityPurchased,
			double price) {
		this.purchaseDate = LocalDate.now();
		this.itemName = itemName;
		this.managerName = managerName;
		this.quantityPurchased = quantityPurchased;
		this.price = price;
	}

	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}

	public String getItemName() {
		return itemName;
	}

	public String getManagerName() {
		return managerName;
	}

	public int getQuantityPurchased() {
		return quantityPurchased;
	}

	public double getPrice() {
		return price;
	}
	
	
	public double getBillPrice() { return price*quantityPurchased; }
	
}

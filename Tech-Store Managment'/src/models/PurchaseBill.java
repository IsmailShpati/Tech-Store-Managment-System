package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PurchaseBill implements Serializable {

	private static final long serialVersionUID = 7116010254554672250L;
	
	private LocalDateTime purchaseDate;
	private String itemName, managerName;
	private int quantityPurchased;
	private double price;
	
	public PurchaseBill(String itemName, String managerName, int quantityPurchased,
			double price) {
		this.purchaseDate = LocalDateTime.now();
		this.itemName = itemName;
		this.managerName = managerName;
		this.quantityPurchased = quantityPurchased;
		this.price = price;
	}

	public LocalDateTime getPurchaseDate() {
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

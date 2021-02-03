package models;

import java.io.Serializable;
import java.time.LocalDateTime;

import interfaces.StatisticBill;

public class PurchaseBill implements Serializable, StatisticBill {

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

	@Override
	public LocalDateTime getDate() {
		return purchaseDate;
	}
	
	public String getPurchaseDate() {
		return purchaseDate.toString();
	}

	public String getItemName() {
		return itemName;
	}

	public String getManagerName() {
		return managerName;
	}

	public int getQuantity() {
		return quantityPurchased;
	}
	
	public String getQuantityPurchased() {
		return quantityPurchased+"";
	}

	public String getPrice() {
		return price+"";
	}
	
	public double getprice() {
		return price;
	}
	
	@Override
	public double getTotal() { return price*quantityPurchased; }
	
}

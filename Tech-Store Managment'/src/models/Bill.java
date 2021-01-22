package models;

import java.util.ArrayList;

public class Bill {

	
	private ArrayList<BillItem> billItems = new ArrayList<>(); 
	//private Cashier
	private PurchaseDate billDate;
	private double totalBillPrice;
	
	public Bill(/*Cashier, PurchaseDate*/) {
		//Cashier = cashier
		//Date = date
		billDate = new PurchaseDate();
	}
	
	public void addBillItem(BillItem b) {
		billItems.add(b);
		totalBillPrice += b.getTotalBillPrice();
	}
	
	public void editItem(int index, BillItem newItem) {
		totalBillPrice -= billItems.get(index).getTotalBillPrice();
		totalBillPrice += newItem.getTotalBillPrice();
		billItems.set(index, newItem);
	}
	
	//Getters setters
	public PurchaseDate getDate() { return billDate; }
	public double getTotalBillPrice() { return totalBillPrice; }
	public ArrayList<BillItem> getItems() { return billItems;}
//  public Cashier getCashier() { return cashier; }
	
	
}

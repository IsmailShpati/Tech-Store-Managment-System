package models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Bill implements Serializable {

	private static final long serialVersionUID = 7394936681757934974L;
	
	private ArrayList<BillItem> billItems = new ArrayList<>(); 
	//private Cashier
	private LocalDateTime billDate;
	private double totalBillPrice;

	//TODO Check later whether cashier is needed here or not probably nope;
	public Bill(/*Cashier, PurchaseDate*/) {
		//Cashier = cashier
		billDate = LocalDateTime.now();
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
	public LocalDateTime getDate() { return billDate; }
	public double getTotalBillPrice() { return totalBillPrice; }
	public ArrayList<BillItem> getItems() { return billItems;}
//  public Cashier getCashier() { return cashier; }
	
	
}

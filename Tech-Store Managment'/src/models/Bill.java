package models;

import java.util.ArrayList;
import java.util.Date;

public class Bill {

	
	private ArrayList<BillItem> billItems = new ArrayList<>(); 
	//private Cashier
	private PurchaseDate billDate;
	private double totalBillPrice;
	private String billNO;
	
	public Bill(/*Cashier, PurchaseDate*/) {
		//Cashier = cashier
		//Date = date
		billDate = new PurchaseDate();
		billNO = billDate.toString();
	}
	
	public void addBillItem(BillItem b) {
		System.out.println("BillItem to be added " + b.getItemName());
		billItems.add(b);
		for(BillItem i : billItems)
			System.out.println(i.getItemName());
		totalBillPrice += b.getTotalBillPrice();
	}
	
	public void editItem(int index, BillItem newItem) {
		System.out.println(index);
		totalBillPrice -= billItems.get(index).getTotalBillPrice();
		billItems.set(index, newItem);
	}
	
	//Getters setters
	public PurchaseDate getDate() { return billDate; }
	public double getTotalBillPrice() { return totalBillPrice; }
	public ArrayList<BillItem> getItems() { return billItems;}
	public String getBillNO() { return billNO; }
//  public Cashier getCashier() { return cashier; }
	
	
}

package models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

import interfaces.StatisticBill;

public class Bill implements Serializable, StatisticBill {

	private static final long serialVersionUID = 7394936681757934974L;
	
	private ArrayList<BillItem> billItems = new ArrayList<>(); 
	private LocalDateTime billDate;
	private double totalBillPrice;

	public Bill() {
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
	
	@Override
	public LocalDateTime getDate() { return billDate; }
	
	@Override
	public double getTotal() { return totalBillPrice; }
	
	public ArrayList<BillItem> getItems() { return billItems;}





	
	
}

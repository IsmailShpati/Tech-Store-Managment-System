package view_models;

import java.util.ArrayList;
import java.util.Collections;

import interfaces.ViewException;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import models.Bill;
import models.BillItem;

public class Statistics extends BorderPane {

//	public static void main(String[] args) throws ViewException {
//		BillItem i1 = new BillItem("shoe", 120, 10);
//		BillItem i2 = new BillItem("lop", 220, 1);
//		BillItem i3 = new BillItem("leke", 22, 3);
//		
//		Bill bill = new Bill();
//		bill.addBillItem(i1);
//		bill.addBillItem(i2);
//		bill.addBillItem(i3);
//		bill.addBillItem(i1);
//		bill.addBillItem(i2);
//		bill.addBillItem(i3);
//		bill.addBillItem(i3);
//		
//		for(BillItem i : Statistics.getItems(bill))
//			System.out.println(i.getItemName() + " " + i.getQuantity());
//	}
	
	public Statistics() {
		
	}
	
	
	//Remove duplicate items and increase the quantity of existing ones
	public static ArrayList<BillItem> getItems(Bill bill) throws ViewException{
		ArrayList<BillItem> itemsUsed = new ArrayList<>(bill.getItems());
		if(itemsUsed.size() < 1)
			throw new ViewException("No items avaible", AlertType.ERROR);
		Collections.sort(itemsUsed);

		return removeDuplicates(itemsUsed);
	}
	
	private static ArrayList<BillItem> removeDuplicates(ArrayList<BillItem> items){
		ArrayList<BillItem> updatedItems = new ArrayList<>();
		BillItem current = new BillItem(items.get(0));
		updatedItems.add(current);
		for(BillItem i : items ) {
			if(i.equals(current)) 
				current.setQuantity(current.getQuantity() + i.getQuantity());
			else {
				current = new BillItem(i);
				updatedItems.add(current);
			}
		}
		return updatedItems;
	}
}

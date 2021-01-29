package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import interfaces.ViewException;
import javafx.scene.control.Alert.AlertType;
import models.BillItem;
import models.StockItem;

@SuppressWarnings("unchecked")
public class StockController {
	private static ArrayList<StockItem> itemsAvaible = new ArrayList<>();
	private static final File path = new File("databases/Stock Database/Stock.dat");
	
	public static void main(String[] args) {
		for(StockItem i : itemsAvaible) {
			System.out.println(i.getItemName() + " " + i.getCategory());
		}
	}
	
	static {
		//TODO construct object here
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
				itemsAvaible = (ArrayList<StockItem>)in.readObject();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		
		
//		itemsAvaible.add(new StockItem("Shoe", "IDK", 45.5, 
//				CategorieController.reset(), 4, 42));
	}
	
	public static ArrayList<StockItem> getItems(){
		return itemsAvaible;
	}

	public static void purchaseStock(int index, int quantity) {
		itemsAvaible.get(index).addStock(quantity);
		save();
		//Store a bill for the purchase
	}
	
	public static void purchaseStock(StockItem i, int quantity) {
//		for(StockItem item : itemsAvaible)
//			if(item == i)
//				item.addStock(quantity);
		i.addStock(quantity);
		save();
	}
	
	public static void addItem(StockItem i) throws ViewException{
		for(StockItem item : itemsAvaible)
			if(item.getItemName().equals(i.getItemName()))
				throw new ViewException("An item with that name alredy exists.", AlertType.ERROR);
		
		itemsAvaible.add(i);
		save();
	}
	
	public static String getLowStockItems() {
		StringBuilder lowStock = new StringBuilder();
		for(StockItem i : itemsAvaible)
			if(i.getStockQuantity() < 5)
				lowStock.append(i.getItemName() + " " + i.getStockQuantity() + "\n");
		return lowStock.toString();
	}
	
	public static void addCategory(String category) {
		try {
			CategorieController.addCategory(category);
		} catch (ViewException e) {
			e.showAlert();
		}
	}
 
//	private static StockItem getItem(StockItem i) throws ViewException {
//		for(StockItem s : itemsAvaible)
//			if(s.equals(i)) {
//			   if(s.getStockQuantity() >= i.getStockQuantity())
//				   return s;
//			   else {
//				   throw new ViewException("Not enough stock left for that item, only " 
//							+ i.getStockQuantity() +" items left in stock", 
//										AlertType.ERROR);
//			   }
//			}
//		throw new ViewException("No item with that name was found", AlertType.ERROR);
//				
//	}
	
	private static void save() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
			out.writeObject(itemsAvaible);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//editingEntry in case we are editing an item in CashierView 
	public static BillItem getItem(String name, int quantity, int oldQuantity) throws ViewException {
		for(StockItem i : itemsAvaible)
			if(i.getItemName().equals(name)) {
				if(i.getStockQuantity() >= quantity) {
					i.sellStock(quantity - oldQuantity);
					return new BillItem(name, i.getSellingPrice(), quantity);
				}
				else {//TODO throw Exeption, not enough stock, only x items left}
					throw new ViewException("Not enough stock left for that item, only " 
				+ i.getStockQuantity() +" items left in stock", 
							AlertType.ERROR);
				}
			}
		throw new ViewException("No item with that name exists.", AlertType.ERROR);
	}

	public static void delete(StockItem i) {
		itemsAvaible.remove(i);
		save();
		
	}
}

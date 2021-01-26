package controlers;

import java.util.ArrayList;

import interfaces.ViewException;
import javafx.scene.control.Alert.AlertType;
import models.BillItem;
import models.Categories;
import models.StockItem;

public class StockController {
	private static ArrayList<StockItem> itemsAvaible = new ArrayList<>();
	
	public static void main(String[] args) {
		System.out.println(StockController.getLowStockItems());
	}
	
	static {
		//TODO construct object here
		itemsAvaible.add(new StockItem("Shoe", "IDK", 45.5, 
				Categories.categoriesAvaible.get(0), 4, 42));
	}
	
	public static ArrayList<StockItem> getItems(){
		return itemsAvaible;
	}

	public static void purchaseStock(int index, int quantity) {
		itemsAvaible.get(index).addStock(quantity);
		//Store a bill for the purchase
	}
	
	public static void addItem(StockItem i) throws ViewException{
		for(StockItem item : itemsAvaible)
			if(item.equals(i))
				throw new ViewException("An item with that name alredy exists.", AlertType.ERROR);
		
		itemsAvaible.add(i);		
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
			Categories.addCategory(category);
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
}

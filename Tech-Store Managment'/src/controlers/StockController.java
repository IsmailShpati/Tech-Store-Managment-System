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
		itemsAvaible.add(new StockItem("Hello", "IDK", 45.5, 
				Categories.categoriesAvaible.get(0), 4, 42));
	}
	
	public static void buyItems(StockItem i, int quantityBought) throws ViewException {
		getItem(i).addStock(quantityBought);
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
	
	public static boolean addCategory(String category) {
		return Categories.addCategory(category);
	}
 
	private static StockItem getItem(StockItem i) throws ViewException {
		for(StockItem s : itemsAvaible)
			if(s.equals(i))
				return s;
		throw new ViewException("No item with that name was found", AlertType.ERROR);
				
	}
	//editingEntry in case we are editing an item in CashierView and we alredy  
	public static BillItem getItem(String name, int quantity, int editingEntry) throws ViewException {
		for(StockItem i : itemsAvaible)
			if(i.getItemName().equals(name)) {
				if(i.getStockQuantity() >= quantity) {
					i.sellStock(quantity-editingEntry);
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

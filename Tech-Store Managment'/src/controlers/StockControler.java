package controlers;

import java.util.ArrayList;
import models.BillItem;
import models.Categories;
import models.StockItem;

public class StockControler {
	private static ArrayList<StockItem> itemsAvaible = new ArrayList<>();
	
	static {
		//TODO construct object here
		itemsAvaible.add(new StockItem("Hell", "IDK", 45.5, 
				Categories.categoriesAvaible.get(0), 120, 42));
	}
	
	public static void buyItems(StockItem i, int quantityBought) {
		getItem(i).addStock(quantityBought);
	}
	
	public static boolean addItem(StockItem i) {
		for(StockItem item : itemsAvaible)
			if(item.equals(i))
				return false;
		itemsAvaible.add(i);
		return true;
				
	}
	
	public static boolean addCategory(String category) {
		return Categories.addCategory(category);
	}
 
	private static StockItem getItem(StockItem i) {
		for(StockItem s : itemsAvaible)
			if(s.equals(i))
				return s;
		return null;
				
	}
	//editingEntry in case we are editing an item in CashierView and we alredy  
	public static BillItem getItem(String name, int quantity, int editingEntry) {
		for(StockItem i : itemsAvaible)
			if(i.getItemName().equals(name)) {
				if(i.getStockQuantity() >= quantity) {
					i.sellStock(quantity-editingEntry);
					return new BillItem(name, i.getSellingPrice(), quantity);
				}
				else {//TODO throw Exeption, not enough stock, only x items left}
				}
	
			}
		return null;
	}
}

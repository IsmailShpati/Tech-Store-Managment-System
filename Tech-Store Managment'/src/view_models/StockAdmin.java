package view_models;

import interfaces.Returnable;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

public class StockAdmin extends BorderPane implements Returnable {
	private MenuBar menuBar = new MenuBar();
	private PurchaseStock stockView = new PurchaseStock();
	private SideMenu menu;
	public StockAdmin(SideMenu menu) {
		this.menu = menu;
		initMenusForAdmin();
		setCenter(stockView.getStockView());
		setBottom(menuBar);
	}
	private void initMenusForAdmin() {
		Label deleteLabel = new Label("Delete");
		deleteLabel.setOnMouseClicked(e -> {
				stockView.delete();	
		});
		
		MenuItem newItem = new MenuItem("Item");
		newItem.setOnAction(e -> {
			menu.changeRightSide(new AddItemView(this));
		});
		MenuItem newCategory = new MenuItem("Category");
		newCategory.setOnAction(e -> {
			menu.changeRightSide(new AddNewCategory(this));
		});
		
		Menu newMenu = new Menu("New");
		
		newMenu.getItems().addAll(newItem, newCategory);
		menuBar.getMenus().addAll(newMenu, new Menu("", deleteLabel));
	}
	
	@Override
	public void returnBack() {
		menu.changeRightSide(this);
	}
}

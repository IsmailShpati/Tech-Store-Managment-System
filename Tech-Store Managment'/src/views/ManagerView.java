package views;

import java.util.Optional;

import controllers.StockController;
import interfaces.Returnable;
import interfaces.Viewable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.Manager;
import view_models.AddItemView;
import view_models.AddNewCategory;
import view_models.ImageGetter;
import view_models.PurchaseStock;
import view_models.SideMenu;
import view_models.ManagerStatistics;
import view_models.SuppliersView;

public class ManagerView implements Viewable, Returnable {
	
	private ManagerPannel pannel;
	private Manager manager;
	private SideMenu menu;
	
	public ManagerView(Manager manager) {
		this.manager = manager;
	}
	
	@Override
	public void setView(Stage stage) {
		pannel = new ManagerPannel(stage);
		pannel.setView();
		pannel.lowStock();
	}
	
	public void returnBack() {
		pannel.returnBack();
	}
	
	
	public class ManagerPannel extends BorderPane implements Returnable{
		private Stage stage;
		private MenuBar menuBar = new MenuBar();
		private PurchaseStock stockView = new PurchaseStock(manager);
		
		public ManagerPannel(Stage stage) {
			this.stage = stage;
			menu = new SideMenu(stage, manager);
			menu.addButton(this, "Stock Managment",
					ImageGetter.getImage("Resources/buttons/stock.png", 38, 38));
			menu.addButton(new SuppliersView(menu), "Suppliers",
					ImageGetter.getImage("Resources/buttons/users.png", 32, 32));
			menu.addButton(new ManagerStatistics(manager), "Statistics",
					ImageGetter.getImage("Resources/buttons/barChart.png", 32, 32));
			initMenus();
			setCenter(stockView.getStockView());
			setBottom(menuBar);
		}
		
		public ManagerPannel() {
			initMenusForAdmin();
			setCenter(stockView.getStockView());
			setBottom(menuBar);
		}
		private void initMenusForAdmin() {
			Label deleteLabel = new Label("Delete", 
					ImageGetter.getImage("Resources/buttons/delete_white.png", 20, 20));
			deleteLabel.setOnMouseClicked(e -> {
					stockView.delete();	
			});
			
			MenuItem newItem = new MenuItem("Item");
			newItem.setOnAction(e -> {
				menu.changeRightSide(new AddItemView(ManagerView.this));
			});
			MenuItem newCategory = new MenuItem("Category");
			newCategory.setOnAction(e -> {
				menu.changeRightSide(new AddNewCategory(ManagerView.this));
			});
			
			Menu newMenu = new Menu("New",
					ImageGetter.getImage("Resources/buttons/add_white.png", 20, 20));
			newMenu.getItems().addAll(newItem, newCategory);
			menuBar.getMenus().addAll(newMenu, new Menu("", deleteLabel));
		}
		
		public void returnBack() {
			stockView.refresh();
			menu.changeRightSide(this);
		}
		
		public void setView()  {
			stage.setTitle("Manager view");
			stage.setScene(new Scene(menu));
			stage.centerOnScreen();
			stage.setOnCloseRequest(e->{
				Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to close the application?",
						ButtonType.YES, ButtonType.NO);
				Optional<ButtonType> butons = alert.showAndWait();
				if(butons.get() == ButtonType.NO) {
					e.consume();
				}
			});

		}
		
		public void lowStock() {
			String lowStock = StockController.getLowStockItems();
			if(lowStock.length() > 1)
				new Alert(AlertType.WARNING, "Low stock items\n" + lowStock).showAndWait();
		}
		
		private void initMenus() {
			Label deleteLabel = new Label("Delete", 
					ImageGetter.getImage("Resources/buttons/delete_white.png", 20, 20));
			
			deleteLabel.setOnMouseClicked(e -> {
					stockView.delete();	
			});
			
			Label purchaseLabel = new Label("Purchase",
					ImageGetter.getImage("Resources/buttons/purchase.png", 20, 20));

			purchaseLabel.setOnMouseClicked(e->{
				stockView.purchaseItem();
			});
			
			MenuItem newItem = new MenuItem("Item");
			newItem.setOnAction(e -> {
				menu.changeRightSide(new AddItemView(ManagerView.this));
			});
			MenuItem newCategory = new MenuItem("Category");
			newCategory.setOnAction(e -> {
				menu.changeRightSide(new AddNewCategory(ManagerView.this));
			});
			
			Menu newMenu = new Menu("New",
					ImageGetter.getImage("Resources/buttons/add_white.png", 20, 20));
			newMenu.getItems().addAll(newItem, newCategory);
			Menu deleteMenu = new Menu("", deleteLabel);
			Menu purchaseMenu = new Menu("", purchaseLabel);
			menuBar.getMenus().addAll(newMenu, purchaseMenu, deleteMenu);
		}
	}

}

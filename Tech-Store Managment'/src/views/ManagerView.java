package views;

import interfaces.Viewable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import models.Manager;
import view_models.AddItemView;
import view_models.AddNewCategory;
import view_models.PurchasePopUp;
import view_models.PurchaseStock;
import view_models.SideMenu;
import view_models.Statistics;
import view_models.SuppliersView;

public class ManagerView implements Viewable {
	
	private ManagerPannel pannel;
	private Manager manager;
	
	public ManagerView(Manager manager) {
		this.manager = manager;
	}
	
	@Override
	public void setView(Stage stage) {
		pannel = new ManagerPannel(stage);
		pannel.setView();
		
	}
	
	public void returnBack() {
		pannel.returnBack();
	}
	
	
	class ManagerPannel extends BorderPane{
		private SideMenu menu;
		//private VBox btnContainer = new VBox(30); 
		private Stage stage;
		private MenuBar menuBar = new MenuBar();
		private PurchaseStock stockView = new PurchaseStock();
		
		public ManagerPannel(Stage stage) {
			this.stage = stage;
			menu = new SideMenu(stage, manager);
			menu.addButton(this, "Stock Managment");
			menu.addButton(new SuppliersView(), "Suppliers");
			menu.addButton(new Statistics(), "Statistics");
			initMenus();
			VBox container = new VBox();
			container.getChildren().addAll(stockView, menuBar);
			setCenter(container);
			
			
		}
		
		public void returnBack() {
			menu.changeRightSide(this);
		}
		
		public void setView() {
			stage.setTitle("Manager view");
			stage.setScene(new Scene(menu));
		}
		
		private void initMenus() {
			Label deleteLabel = new Label("Delete");
			deleteLabel.setOnMouseClicked(e -> {
					stockView.delete();	
			});
			
//			Label suppliersLabel = new Label("Suppliers");
//			suppliersLabel.setOnMouseClicked(e->{
//				menu.changeRightSide(new SuppliersView(ManagerView.this));
//			});
			
			Label purchaseLabel = new Label("Purchase");
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
			
			Menu newMenu = new Menu("New");
			
			newMenu.getItems().addAll(newItem, newCategory);
			Menu deleteMenu = new Menu("", deleteLabel);
	//		Menu suppliersMenu = new Menu("", suppliersLabel);
			Menu purchaseMenu = new Menu("", purchaseLabel);
			menuBar.getMenus().addAll(newMenu, purchaseMenu, deleteMenu);
			//setBottom(menuBar);
		}
	}

}

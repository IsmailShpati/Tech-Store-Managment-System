package views;



import java.util.Optional;

import interfaces.Viewable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view_models.AddItemView;
import view_models.AddNewCategory;
import view_models.PurchaseStock;
import view_models.SideMenu;
import view_models.Statistics;

public class ManagerView implements Viewable {
	
	private ManagerPannel pannel;
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
			menu = new SideMenu(stage);
			menu.addButton(this, "Stock Managment");
			menu.addButton(new Statistics(), "Statistics");
			initMenus();

			setCenter(stockView);
			
			BorderPane.setAlignment(getCenter(), Pos.CENTER);
			
		}
		
		public void returnBack() {
			stockView.refresh();
			menu.changeRightSide(this);
		}
		
		public void setView() {
			stage.setTitle("Manager view");
			stage.setScene(new Scene(menu));
		}
		
		private void initMenus() {
			Label deleteLabel = new Label("Delete");
			deleteLabel.setOnMouseClicked(e -> {
				Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to delete that item?",
						ButtonType.YES, ButtonType.NO);
				Optional<ButtonType> butons = alert.showAndWait();
				if(butons.get() == ButtonType.YES)
					stockView.delete();
				
			});
			Label suppliersLabel = new Label("Suppliers");
			
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
			Menu purchaseMenu = new Menu("", deleteLabel);
			Menu suppliersMenu = new Menu("", suppliersLabel);
			
			menuBar.getMenus().addAll(newMenu, purchaseMenu, suppliersMenu);
			setBottom(menuBar);
		}
	}

}

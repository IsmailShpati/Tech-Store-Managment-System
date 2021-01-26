package views;

import controlers.StockController;
import interfaces.Viewable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import view_models.PurchaseStock;
import view_models.SideMenu;

public class ManagerView implements Viewable {

	
	private SideMenu menu = new SideMenu();
	private VBox btnContainer = new VBox(30); 
	private BorderPane root = new BorderPane();
	private Text stockWarning = new Text();
	
	
	public ManagerView() {
		root.setPrefWidth(300);
		menu.addButton(root, "Stock Managment");
		
		Button purchaseBtn = new Button("Purchase stock");
		purchaseBtn.setOnAction(e -> {
			menu.changeRightSide(new PurchaseStock(this));
		});
		Button addItem = new Button("Add new item");
		Button addCategory = new Button("Add new category");
		
		btnContainer.getChildren().addAll(purchaseBtn, addItem, addCategory);
		stockWarning.setText("Low stock for the following items\n"+StockController.getLowStockItems());
		btnContainer.setAlignment(Pos.CENTER);
		stockWarning.setTextAlignment(TextAlignment.CENTER);
		root.setCenter(btnContainer);
		root.setBottom(stockWarning);
		
		BorderPane.setAlignment(stockWarning, Pos.CENTER);
		BorderPane.setAlignment(btnContainer, Pos.CENTER);
		
	}
	
	public void returnBack() {
		menu.changeRightSide(root);
	}
	
	
	@Override
	public void setView(Stage stage) {
		stage.setScene( new Scene(menu));
		
	}

}

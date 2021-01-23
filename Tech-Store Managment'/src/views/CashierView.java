package views;

import controlers.BillGenerator;
import interfaces.Viewable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import models.SideMenu;
import view_models.AddItem;
import view_models.ItemsView;

public class CashierView  implements Viewable{
	

	private BorderPane root = new BorderPane();
	private ItemsView leftSide = new ItemsView();
	private AddItem rightSide = new AddItem(leftSide);
    private SideMenu menu = new SideMenu();
	
	public CashierView() {
		root.setLeft(leftSide);
		leftSide.setAddItemView(rightSide);
		root.setRight(rightSide);
		initBottom();
		initSideMenu();
	}
	
	/*
	 * public CashierView(Cashier cashier){
	 * 		this.cashier = cashier;
	 * }
	 */
	
	private void initSideMenu() {
		menu.addButton(root, "Scan items");
		StackPane pn = new StackPane();
		pn.getChildren().add(new Circle(100, Color.BLACK));
		menu.addButton(pn, "Prov");
	}
	
	
	private void initBottom() {
	
		Button printBtn = new Button("Print Bill");
		printBtn.setOnAction(e -> {
			if(!leftSide.isEmpty()) {
				BillGenerator.printBill(leftSide.getBill());
				leftSide.clearItems();
				rightSide.resetTotalPrice();
				
				new Alert(AlertType.CONFIRMATION, "Bill printed", ButtonType.OK).showAndWait();
			
			}
		});
		BorderPane.setMargin(printBtn, new Insets(20));
		BorderPane.setAlignment(printBtn, Pos.CENTER_RIGHT);
		root.setBottom(printBtn);
	}
	
	
	@Override
	public void setView(Stage stage) {

		stage.sizeToScene();
		stage.setTitle("Cashier view");
		stage.setScene(new Scene(menu));
		
	}
}



package views;

import controllers.BillGenerator;
import interfaces.Viewable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.Bill;
import models.Cashier;
import view_models.AddItem;
import view_models.ItemsView;
import view_models.SideMenu;

public class CashierView  implements Viewable{
	
	private Cashier cashier;
	
	public CashierView(Cashier cashier) {
		this.cashier = cashier;
	}
	
	@Override
	public void setView(Stage stage) {

		stage.setTitle("Cashier view");
		stage.setScene(new Scene(new CashierPannel(stage).getMenu()));
		stage.sizeToScene();
	}
	
	class CashierPannel extends BorderPane{
		private ItemsView leftSide = new ItemsView();
		private AddItem rightSide = new AddItem(leftSide);
	    private SideMenu menu ;
	    
	    				
		public CashierPannel( Stage stage) {
			menu = new SideMenu(stage);
			setLeft(leftSide);
			leftSide.setAddItemView(rightSide);
			setRight(rightSide);
			initBottom();
			initSideMenu();
		}

		private void initSideMenu() {
			menu.addButton(this, "Scan items");
		}
		
		private void initBottom() {
			Button printBtn = new Button("Print Bill");		
			printBtn.setOnAction(e -> {
				if(!leftSide.isEmpty()) {
					Bill b = leftSide.getBill();
					cashier.addBill(b);
					BillGenerator.printBill(b, cashier);
					leftSide.clearItems();
					rightSide.resetTotalPrice();
					
					new Alert(AlertType.CONFIRMATION, 
							"Bill printed", ButtonType.OK).showAndWait();	
				}
			});
			
			BorderPane.setMargin(printBtn, new Insets(20));
			BorderPane.setAlignment(printBtn, Pos.CENTER_RIGHT);
			setBottom(printBtn);
		}
		public SideMenu getMenu() {
			return menu;
		}
	}
	
}



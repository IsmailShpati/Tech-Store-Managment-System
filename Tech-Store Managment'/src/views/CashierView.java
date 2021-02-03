package views;

import java.util.Optional;

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
import view_models.BillsView;
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
		CashierPannel cp = new CashierPannel(stage);
		stage.setOnCloseRequest(e->{
			if(!cp.isBillEmpty()) {
				new Alert(AlertType.ERROR,
						"There are items alredy added to the bill. Choose what you will do with them.").showAndWait();
				e.consume();
			}
			else {
				Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to close the application?",
						ButtonType.YES, ButtonType.NO);
				Optional<ButtonType> butons = alert.showAndWait();
				if(butons.get() == ButtonType.NO) {
					e.consume();
				}
			}
		});
		stage.setScene(new Scene(cp.getMenu()));
		stage.sizeToScene();
		stage.centerOnScreen();

	}
	
	class CashierPannel extends BorderPane{
		private ItemsView leftSide = new ItemsView();
		private AddItem rightSide = new AddItem(leftSide);
	    private SideMenu menu ;
	    private BillsView billsView;
	    
	    				
		public CashierPannel( Stage stage) {
			menu = new SideMenu(stage, cashier);
			setLeft(leftSide);
			billsView = new BillsView(cashier);
			leftSide.setAddItemView(rightSide);
			setRight(rightSide);
			initBottom();
			initSideMenu();
		}

		private void initSideMenu() {
			menu.addButton(this, "Scan items");
			menu.addButton(billsView, "Bills");
		}
		
		private void initBottom() {
			Button printBtn = new Button("Print Bill");		
			printBtn.setOnAction(e -> {
				if(!leftSide.isEmpty()) {
					Bill b = leftSide.getBill();
					cashier.addBill(b);
					BillGenerator.printBill(b, cashier);
					billsView.refresh();
					leftSide.clearItems();
					rightSide.resetTotalPrice();
					new Alert(AlertType.CONFIRMATION, 
							"Bill printed", ButtonType.OK).showAndWait();	
				}
				else 
					new Alert(AlertType.WARNING, 
							"No items added to bill", ButtonType.OK).showAndWait();
			});
			
			BorderPane.setMargin(printBtn, new Insets(20));
			BorderPane.setAlignment(printBtn, Pos.CENTER_RIGHT);
			setBottom(printBtn);
		}
		public SideMenu getMenu() {
			return menu;
		}
		
		public boolean isBillEmpty() {
			return leftSide.isEmpty();
		}
	}
	
}



package views;

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
import view_models.AddItem;
import view_models.ItemsView;

public class CashierView  implements Viewable{
	
	private BorderPane root = new BorderPane();
	private ItemsView leftSide = new ItemsView();
	private AddItem rightSide = new AddItem(leftSide);
	
	
	public CashierView() {
		root.setLeft(leftSide);
		root.setRight(rightSide);
		initBottom();
	}
	
	
	private void initBottom() {
	
		Button printBtn = new Button("Print Bill");
		printBtn.setOnAction(e -> {
		if(!leftSide.isEmpty()) {
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

		stage.setTitle("Cashier view");
		stage.setScene(new Scene(root));
		
	}
}



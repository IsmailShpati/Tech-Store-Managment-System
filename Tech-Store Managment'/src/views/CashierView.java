package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view_models.ItemsView;

public class CashierView {
	
	public void setView(Stage stage) {
		BorderPane root = new BorderPane();
		//root.setPadding(new Insets(0, 30, 0, 30));
		ItemsView iv = new ItemsView();
		root.setLeft(iv);
		
		VBox rightPane = new VBox();
		GridPane right = new GridPane();
		right.setPadding(new Insets(10, 30, 0, 30));
		right.setVgap(10);
		right.setHgap(20);
		Label nameLabel = new Label("Name");
		Label quantityLabel = new Label("Quantity");
		right.add(nameLabel, 1, 0);
		right.add(quantityLabel, 2, 0);
		
		TextField nameField = new TextField();
		Label quantField = new Label("1");
		right.add(nameField, 1, 1);
		HBox btnCont = new HBox(5);
		btnCont.setAlignment(Pos.CENTER);
		Button plus = new Button("+");
		plus.setOnAction(e -> {
			quantField.setText((Integer.parseInt(quantField.getText())+1)+"");
		});
		Button minues = new Button("-");
		minues.setOnAction(e -> {
			int val = Integer.parseInt(quantField.getText());
			if(val > 1)
				quantField.setText((val-1)+"");
				
		});
		btnCont.getChildren().addAll(plus, quantField, minues);
		right.add(btnCont, 2, 1);
		
		Button addItem = new Button("ADD ITEM");
		right.add(addItem, 2, 4);
		

		HBox container = new HBox();
		Label totalLabel = new Label("Total price: 0.0");
		container.getChildren().add(totalLabel);
		container.setAlignment(Pos.BOTTOM_LEFT);
		rightPane.getChildren().addAll(right, container);
		root.setRight(rightPane);
		
		HBox bottom = new HBox();
		Button printBtn = new Button("Print Bill");
		bottom.getChildren().add(printBtn);
		bottom.setAlignment(Pos.CENTER_RIGHT);
		printBtn.setOnAction(e -> {
			if(!iv.isEmpty()) {
			iv.clearItems();
		
			  new Alert(AlertType.CONFIRMATION, "Bill printed", ButtonType.OK).showAndWait();
			}
		});
		root.setBottom(bottom);
		BorderPane.setMargin(bottom, new Insets(0, 20, 20, 0));
		
		addItem.setOnAction(e -> {
			iv.addItem(nameField.getText(), Integer.parseInt(quantField.getText()));
			nameField.clear();
			quantField.setText("1");
			totalLabel.setText("Total price: " + String.format("%.2f", iv.getTotalPrice()));
			
		});
		
		stage.setScene(new Scene(root));
		
	}
	
	
}



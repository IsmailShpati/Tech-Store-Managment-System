package view_models;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

public class AddItem extends GridPane {

	private ItemsView itemsView;
	private QuantityPlusMinus quantity = new QuantityPlusMinus(1);
	private TextField nameField = new TextField();
	private Label totalPrice = new Label("Total price: 0.00");
	private int startingColumn, startingRow; //Initialized by 0
	
	public AddItem(ItemsView itemsView) {
		this.itemsView = itemsView;
		initGrid();
		initLabelsFields();
		initAddBtn();
		add(totalPrice, startingColumn, startingRow++);
	}
	
	private void initGrid() {
		setPadding(new Insets(10, 30, 0, 30));
		setVgap(10);                          
		setHgap(20);                          
	}
	
	private void initLabelsFields() {
		Label nameLabel = new Label("Name");
		Label quantityLabel = new Label("Quantity");
		add(nameLabel, startingColumn, startingRow);
		add(quantityLabel, startingColumn+1, startingRow++);
		
		add(nameField, startingColumn, startingRow);
		add(quantity, startingColumn+1, startingRow++);
	}
	
	private void initAddBtn() {
		Button addItem = new Button("ADD ITEM");
		addItem.setOnAction(E -> {
			if(nameField.getText().length() < 1) {
				new Alert(AlertType.ERROR, "Please fill the name").showAndWait();
			}
			else {
				itemsView.addItem(nameField.getText(), quantity.getQuantity());
				nameField.clear();
				totalPrice.setText("Total price: " +
				String.format("%.2f", itemsView.getTotalPrice()));
			}
		});
		add(addItem, startingColumn+1, startingRow++);
	}
	
	public void resetTotalPrice() {
	   totalPrice.setText("Total price: 0.00");	
	}
	
}

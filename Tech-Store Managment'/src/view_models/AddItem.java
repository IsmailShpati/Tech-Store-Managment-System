package view_models;

import controlers.StockController;
import interfaces.ViewException;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import models.BillItem;

//CASHIER VIEW
//Contains all needed nodes to perform the addition of a product [Right Side of the CashierView]
public class AddItem extends GridPane {

	private ItemsView itemsView;
	private QuantityPlusMinus quantity = new QuantityPlusMinus(1);
	private TextField nameField = new TextField();
	private Label totalPrice = new Label("Total price: 0.00");
	private double totalP;
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
				ViewException.showAlert( "Please fill the name", AlertType.ERROR);
			}
			else {
				try {
				    BillItem b = StockController.getItem(nameField.getText(), quantity.getQuantity(), 0);
				    System.out.println(b.getItemName() + " " + b.getSellingPrice() + " " + b.getQuantity());
					itemsView.addItem(b);
					quantity.reset();
					nameField.clear();
					changeTotalPrice(b.getQuantity()*b.getSellingPrice());
				}catch(ViewException e) {
					e.showAlert();
				}
				
			}
		});
		add(addItem, startingColumn+1, startingRow++);
	}
	
	public void changeTotalPrice(double totalPrice) {
		totalP += totalPrice;
		this.totalPrice.setText("Total price: " + String.format("%.2f", totalP));
	}
	
	public void resetTotalPrice() {
	   nameField.clear();
	   totalP = 0.0;
	   quantity.reset();
	   totalPrice.setText("Total price: 0.00");	
	}
	
}

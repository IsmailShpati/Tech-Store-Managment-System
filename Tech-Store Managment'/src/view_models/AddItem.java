package view_models;

import controllers.StockController;
import interfaces.ViewException;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import models.BillItem;
import models.StockItem;

//CASHIER VIEW
//Contains all needed nodes to perform the addition of a product [Right Side of the CashierView]
public class AddItem extends GridPane {

	private ItemsView itemsView;
	private QuantityPlusMinus quantity = new QuantityPlusMinus(1);
	private ComboBox<StockItem> items;
	private Label totalPrice = new Label("Total price: 0.00");
	private double totalP;
	private int startingColumn, startingRow; //Initialized by 0
	
	public AddItem(ItemsView itemsView) {
		this.itemsView = itemsView;
		items = new ComboBox<>();
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
		
		add(items, startingColumn, startingRow);
		items.setPrefWidth(200);
		items.setItems(FXCollections.observableArrayList(StockController.getItems()));
		items.setValue(StockController.getItems().get(0));
		add(quantity, startingColumn+1, startingRow++);
	}
	
	private void initAddBtn() {
		Button addItem = new Button("ADD ITEM");
		addItem.setOnAction(E -> {
			StockItem i = items.getValue();
			BillItem b = new BillItem(i.getItemName(), i.getsellingPrice(), quantity.getQuantity());
			itemsView.addItem(b);
			quantity.reset();
			items.setValue(StockController.getItems().get(0));
			changeTotalPrice(b.getQuantity()*b.getsellingPrice());
		});
		add(addItem, startingColumn+1, startingRow++);
	}
	
	public void changeTotalPrice(double totalPrice) {
		totalP += totalPrice;
		this.totalPrice.setText("Total price: " + String.format("%.2f", totalP));
	}
	
	public void resetTotalPrice() {
	   totalP = 0.0;
	   items.setValue(StockController.getItems().get(0));
	   quantity.reset();
	   totalPrice.setText("Total price: 0.00");	
	}
	
}

package view_models;

import controllers.StockController;
import interfaces.ViewException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import models.StockItem;

public class PurchaseStock extends BorderPane {

	private StockView leftSide = new StockView();
	private QuantityPlusMinus quantity = new QuantityPlusMinus(1);
	private GridPane purchaseSide = new GridPane();
	
	
	public PurchaseStock() {
		setPadding(new Insets(30));
		setCenter(leftSide);
		initPurchaseSide();
	}
	
	public void delete() {
		try {
			leftSide.deleteItem();
		} catch (ViewException e) {
			e.showAlert();
		}
	}
	
	public void refresh() {
		leftSide.refresh();
	}
	
	private void initPurchaseSide() {
		Label quantityLabel = new Label("Quantity");
		Button purchaseBtn = new Button("Purchase");

		purchaseSide.setHgap(20);
		purchaseSide.setVgap(10);
		purchaseSide.add(quantityLabel, 1, 1);
		purchaseSide.add(quantity, 2, 1);
		initPurchaseBtn(purchaseBtn);
		purchaseSide.add(purchaseBtn, 2, 2);
		setBottom(purchaseSide);
		
	}
	
	private void initPurchaseBtn(Button btn) {
		btn.setOnAction(e -> {
			new Alert(AlertType.CONFIRMATION).show();
			try {
				leftSide.purchaseItem(quantity.getQuantity());
			} catch (ViewException e1) {
				e1.showAlert();
			}
			quantity.reset();
		});
	}
	
	
	 private class StockView extends VBox{
		
		private TableView<StockItem> stockTable = new TableView<>();
		private ObservableList<StockItem> items = FXCollections.observableArrayList(StockController.getItems());
		
		public StockView() {
			initTable();
			setPadding(new Insets(10));
			getChildren().add(stockTable);
		}
		
		public void purchaseItem(int quantity) throws ViewException {
			StockItem i = stockTable.getSelectionModel().getSelectedItem();
			if(i != null )
				StockController.purchaseStock(i, quantity);
			else {
				throw new ViewException("Please select an item", AlertType.ERROR);
			}
			refresh();
		}
		
		public void deleteItem() throws ViewException {
			StockItem i = stockTable.getSelectionModel().getSelectedItem();
			if(i != null )
				StockController.delete(i);
			else {
				throw new ViewException("Please select an item", AlertType.ERROR);
			}
			refresh();
		}
		
		private void initTable() {
			addColumn("itemName", "Name");
			addColumn("stockQuantity", "Quantity");
			addColumn("purchasingPrice", "Purchasing Price");
			stockTable.setPrefWidth(300);
			stockTable.setItems(items);
			
		}
		
		public void refresh() {
			items.setAll(StockController.getItems());
		}
		
		private void addColumn( String attributeName, String columnName) {
			TableColumn<StockItem, String> column = new TableColumn<>(columnName);
			column.setCellValueFactory(
					new PropertyValueFactory<>(attributeName));
			stockTable.getColumns().add(column);
		}
		
		
	}	
}

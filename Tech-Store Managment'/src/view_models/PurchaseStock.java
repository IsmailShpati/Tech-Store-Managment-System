package view_models;

import controlers.StockController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import views.ManagerView;

public class PurchaseStock extends BorderPane {

	private ManagerView parentView;
	private StockView leftSide = new StockView();
	private QuantityPlusMinus quantity = new QuantityPlusMinus(1);
	private GridPane purchaseSide = new GridPane();
	
	
	public PurchaseStock(ManagerView view) {
		this.parentView = view;
		setPadding(new Insets(30));
		setLeft(leftSide);
		initPurchaseSide();
		Button back = new Button("BACK");
		setBottom(back);
		setAlignment(back, Pos.CENTER_LEFT);
		back.setOnAction(e -> {
			parentView.returnBack();
		});
		
	}
	
	private void initPurchaseSide() {
		Label quantityLabel = new Label("Quantity");
		Button purchaseBtn = new Button("Purchase");
		Button cancel = new Button("Cancel");
		cancel.setOnAction(e -> {
			setRight(null);
		});
		purchaseSide.setHgap(20);
		purchaseSide.setVgap(10);
		purchaseSide.add(quantityLabel, 1, 1);
		purchaseSide.add(quantity, 2, 1);
		initPurchaseBtn(purchaseBtn);
		purchaseSide.add(purchaseBtn, 2, 2);
		purchaseSide.add(cancel, 1, 2);
		
	}
	
	private void initPurchaseBtn(Button btn) {
		btn.setOnAction(e -> {
			new Alert(AlertType.CONFIRMATION).show();
			leftSide.purchaseItem(quantity.getQuantity());
			StockController.purchaseStock(leftSide.getSelectedItemIndex(), quantity.getQuantity());
			System.out.println(quantity.getQuantity());
			quantity.reset();
		});
	}
	
	
	 private class StockView extends VBox{
		
		private TableView<StockItem> stockTable = new TableView<>();
		private ObservableList<StockItem> items = FXCollections.observableArrayList();
		private int selectedIndex;
		
		public StockView() {
			initTable();
			setPadding(new Insets(10));
			getChildren().add(stockTable);
		}
		
		public void purchaseItem(int quantity) {
			StockItem i = items.get(selectedIndex);
			i.addStock(quantity);
			items.set(selectedIndex, i);
		}
		
		private void initTable() {
			addColumn("itemName", "Name");
			addColumn("stockQuantity", "Quantity");
			addColumn("purchasingPrice", "Purchasing Price");
			stockTable.setPrefWidth(300);
			for(StockItem i : StockController.getItems())
				items.add(i);
			stockTable.setItems(items);
			initTableAction();
			
		}
		
		private void initTableAction() {
			stockTable.setOnMouseClicked(e -> {
				selectedIndex = stockTable.getSelectionModel().getSelectedIndex();
				System.out.println(selectedIndex);
				if(selectedIndex > -1 && selectedIndex < items.size()) {
					PurchaseStock.this.setRight(PurchaseStock.this.purchaseSide);
				}
			});
		}
		
		public int getSelectedItemIndex() {
			return selectedIndex;
		}
		
		private void addColumn( String attributeName, String columnName) {
			TableColumn<StockItem, String> column = new TableColumn<>(columnName);
			column.setCellValueFactory(
					new PropertyValueFactory<>(attributeName));
			stockTable.getColumns().add(column);
		}
		
		
	}	
}

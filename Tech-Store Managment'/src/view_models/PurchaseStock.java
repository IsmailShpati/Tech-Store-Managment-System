package view_models;

import java.util.Optional;

import controllers.StockController;
import interfaces.ViewException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.StockItem;

public class PurchaseStock extends BorderPane {

	private StockView leftSide = new StockView();
	
	
	public PurchaseStock() {
		setCenter(leftSide);
	}
	
	
	
	public void purchaseItem() {
			try {
				leftSide.purchaseItem();
			} catch (ViewException e) {
				e.showAlert();
			}
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
	

	

	
	
	 public class StockView extends TableView<StockItem>{
		
		//private TableView<StockItem> stockTable = new TableView<>();
		private ObservableList<StockItem> items = FXCollections.observableArrayList(StockController.getItems());
		
		public StockView() {
			initTable();
		}
		
		public void purchaseItem() throws ViewException {
			StockItem i = getSelectionModel().getSelectedItem();
			if(i != null ) {
				new PurchasePopUp(i, this).start(new Stage());
				refresh();
			}
			else {
				throw new ViewException("Please select an item", AlertType.ERROR);
			}
		
		}
		
		public void deleteItem() throws ViewException {
			StockItem i = getSelectionModel().getSelectedItem();
			if(i != null ) {
				Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to delete that item?",
						ButtonType.YES, ButtonType.NO);
				Optional<ButtonType> butons = alert.showAndWait();
				if(butons.get() == ButtonType.YES)
					StockController.delete(i);
			}
			else {
				throw new ViewException("Please select an item", AlertType.ERROR);
			}
			refresh();
		}
		
		private void initTable() {
			addColumn("itemName", "Name");
			addColumn("stockQuantity", "Quantity");
			addColumn("purchasingPrice", "Purchasing Price");
			setPrefWidth(600);
			setPrefHeight(580);
			setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			setItems(items);
			
		}
		
		public void refresh() {
			items.setAll(StockController.getItems());
		}
		
		private void addColumn(String attributeName, String columnName) {
			TableColumn<StockItem, String> column = new TableColumn<>(columnName);
			column.setCellValueFactory(
					new PropertyValueFactory<>(attributeName));
			getColumns().add(column);
		}
		
		
	}




	
}

package view_models;

import java.util.Optional;

import controllers.StockController;
import interfaces.ViewException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.Manager;
import models.StockItem;

public class PurchaseStock extends BorderPane {

	private Manager manager;
	private StockView leftSide = new StockView();
	
	
	public PurchaseStock(Manager manager) {
		setCenter(leftSide);
		this.manager = manager;
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
		
		public Manager getManager() { return manager; }
		
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
		
		@SuppressWarnings("unchecked")
		private void initTable() {
			//double sellingPrice,
			//String category
			setEditable(true);
			TableColumn<StockItem, String> nameColumn = new TableColumn<>("Item name");
			nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
			nameColumn.setOnEditCommit(e->{
				try {
					StockController.exists(e.getNewValue());
					e.getRowValue().setItemName(e.getNewValue());
					StockController.save();
					refresh();
				} catch (ViewException e2) {
					e2.showAlert();
				}
			});
			
			TableColumn<StockItem, String> modelColumn = new TableColumn<>("Item brand");
 			modelColumn.setCellValueFactory(new PropertyValueFactory<>("itemModel"));
			modelColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			modelColumn.setOnEditCommit(e->{
				if(e.getNewValue().length() > 1) {
					e.getRowValue().setItemModel(e.getNewValue());
					StockController.save();
					refresh();
				}
			});
			
			
			TableColumn<StockItem, String> quantityColumn = new TableColumn<>("Quantity");
			quantityColumn.setCellValueFactory(new PropertyValueFactory<>("stockQuantity"));
			
			TableColumn<StockItem, String> sellingPriceColumn = new TableColumn<>("Selling price");
			sellingPriceColumn.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
			sellingPriceColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			sellingPriceColumn.setOnEditCommit(e->{
				try {
					e.getRowValue().setSellingPrice(Double.parseDouble(e.getNewValue()));
					StockController.save();
					refresh();
				}catch(NumberFormatException err) {
					new Alert(AlertType.ERROR, "Enter a valid number in selling price").showAndWait();
				}
			});
			
			TableColumn<StockItem, String> purchasingPriceColumn = new TableColumn<>("Purchasing price");
			purchasingPriceColumn.setCellValueFactory(new PropertyValueFactory<>("purchasingPrice"));
			purchasingPriceColumn.setCellFactory(TextFieldTableCell.forTableColumn());
			purchasingPriceColumn.setOnEditCommit(e->{
				try {
					e.getRowValue().setPurchasingPrice(Double.parseDouble(e.getNewValue()));
					StockController.save();
					refresh();
				}catch(NumberFormatException err) {
					new Alert(AlertType.ERROR, "Enter a valid number in selling price").showAndWait();
				}
			});
			
			TableColumn<StockItem, String> categoryColumn = new TableColumn<>("Category");
			categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
			
			getColumns().addAll(nameColumn, modelColumn, quantityColumn, 
					sellingPriceColumn, purchasingPriceColumn, categoryColumn);
			setPrefWidth(600);
			setPrefHeight(580);
			setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			setItems(items);
			
		}
		
		public void refresh() {
			items.setAll(StockController.getItems());
		}
		

		
		
	}




	
}

package view_models;

import java.util.Optional;

import controllers.StockController;
import interfaces.ViewException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Bill;
import models.BillItem;

//Shows all items that cashier add [Left side of the cashier view]
public class ItemsView extends BorderPane {

	private ObservableList<BillItem> items = FXCollections.observableArrayList();
	private TableView<BillItem> table;
	private AddItem addItemView;
	private HBox bottom = new HBox(30);
	private int selectedRow = 0;
	private GridPane itemsShow = new GridPane();
	private double totalPrice; 
	private Bill bill = new Bill();
	
	public ItemsView() {
		setPadding(new Insets(0, 30, 0,30));
		itemsShow.setHgap(30);
		itemsShow.setVgap(10);
		itemsShow.setAlignment(Pos.CENTER);
		addTitle();
		addItems();
		setBottom();
	}
	
	private void addTitle() {
		Text title = new Text("Items");
		title.setFont(Font.font("default", FontWeight.BOLD, 30));
		setTop(title);
		setMargin(title, new Insets(0, 0, 10, 0));
		setAlignment(title, Pos.CENTER);
	}
	
	private void addItems() {
		table = new TableView<>();
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		addColumn(table, "itemName", "Name");
		addColumn(table, "sellingPrice", "Price");
		addColumn(table, "quantity", "Quant.");
		table.setItems(items);
		table.setPrefWidth(400);
		table.setOnMouseClicked(e -> {	
			System.out.println("selected");
			
			selectedRow = table.getSelectionModel().getSelectedIndex();
			if( selectedRow > -1)
			   setBottom(bottom);
		});
		setCenter(table);
	}
	
	private void addColumn(TableView<BillItem> table, String attributeName, String columnName) {
		TableColumn<BillItem, String> column = new TableColumn<>(columnName);
		column.setCellValueFactory(
				new PropertyValueFactory<>(attributeName));
		table.getColumns().add(column);
	}
	
	public void addItem(String itemName, double price, int quantity) {
		boolean contains = false;
		for(BillItem item : items) {
			System.out.println(item.getItemName());
			if(item.getItemName().equals(itemName)) {
				System.out.println(item.getItemName() + " " + itemName);
				item.setQuantity(item.getQuantity() + quantity);
				contains = true;
			}
		if(!contains) {
			System.out.println("Doesn't contin");
			BillItem b = new BillItem(itemName, price, quantity);
			items.add(b);
			bill.addBillItem(b);
		}
			
		}
	}
	
	public void editSelected(String name, int quantity) throws ViewException{
	     //if name is changed find the item with corresponding name
		if( selectedRow < items.size()) {
			BillItem b = StockController.getItem(name, quantity, items.get(selectedRow).getQuantity());
			System.out.println(b.getItemName() + " " + b.getSellingPrice() + " " + b.getQuantity());
			items.set(selectedRow, b);
			bill.editItem(selectedRow, b);
			addItemView.changeTotalPrice(b.getQuantity() * b.getsellingPrice());
		}
			else {
				throw new ViewException( "No item with that name exists", AlertType.ERROR);
		}
	}
	
	
	private void setBottom() {		
		Button deselect = new Button("Cancel");
		deselect.setOnAction(E -> {
		      
		      setBottom(null);
		      table.getSelectionModel().select(null);
		});
		bottom.setAlignment(Pos.CENTER);
		Button edit = new Button("Edit");
		edit.setOnAction((event) -> {
			BillItem i = table.getSelectionModel().getSelectedItem();
			
			new EditItemPopUp(i.getItemName(), i.getQuantity(), this).start(new Stage());
		});
		Button delete = new Button("Delete");
		delete.setOnAction(e->{
			Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to delete the selected item from the bill?",
					ButtonType.YES, ButtonType.NO);
			Optional<ButtonType> butons = alert.showAndWait();
			if(butons.get() == ButtonType.YES) {
				BillItem i = table.getSelectionModel().getSelectedItem();
				addItemView.changeTotalPrice(-1*i.getTotalBillPrice());
				StockController.purchaseStock(StockController.getItem(i.getItemName()), i.getQuantity());
				items.remove(i);
			}
		});
		bottom.getChildren().addAll(deselect, edit, delete);
		//setBottom(bottom);
	}
	
//	public double getTotalPrice() {
//		return totalPrice;
//	}
	
	public void clearItems() {
		bill = new Bill();
		items.clear();
	}
	
	public boolean isEmpty() {
		return items.size() == 0;
	}

	public void addItem(BillItem b) {
		boolean contains = false;
		for(BillItem item : items) {
			System.out.println(item.getItemName());
			if(item.getItemName().equals(b.getItemName())) {
				item.setQuantity(item.getQuantity() + b.getQuantity());
				contains = true;
				table.refresh();
			}		
		}
		if(!contains) {
			System.out.println("Doesn't contin");
			items.add(b);
			bill.addBillItem(b);
		}
	}
	
	public Bill getBill() {
		return bill;
	}
	
	public void setAddItemView(AddItem addItemView) {
		this.addItemView = addItemView;
	}
}

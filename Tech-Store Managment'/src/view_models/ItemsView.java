package view_models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.BillItem;

//Cashier
public class ItemsView extends BorderPane {

	private ObservableList<BillItem> items = FXCollections.observableArrayList();
	private TableView<BillItem> table;
	private HBox bottom = new HBox(30);
	private int selectedRow = 0;
	private GridPane itemsShow = new GridPane();
	private double totalPrice; 
	
	
	public ItemsView() {
	//	setPrefWidth(300);
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
		addColumn(table, "itemName", "Name");
		addColumn(table, "sellingPrice", "Price");
		addColumn(table, "quantity", "Quant.");
		table.setItems(items);
	
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
		//Check first if item exists from StockControler, than check quantity if is enough
		items.add(new BillItem(itemName, price, quantity));
		
	}
	
	public void addItem(String itemName, int quantity) {
		double price = Math.random()*100;
		totalPrice += price*quantity;
		items.add(new BillItem(itemName, price, quantity));
	}
	
	public void editSelected(String name, int quantity) {
	     //if name is changed find the item with corresponding name
		if( selectedRow < items.size()) {
		  BillItem current = items.get(selectedRow);
	      items.set(selectedRow, new BillItem(name, current.getSellingPrice(), quantity));	 
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
			BillItem i = items.get(selectedRow);
			new EditItemPopUp(i.getItemName(), i.getQuantity(), this).start(new Stage());
			//(new EditItemPopUp()).start(new Stage());
		});
		bottom.getChildren().addAll(deselect, edit);
		//setBottom(bottom);
	}
	
	public double getTotalPrice() {
		return totalPrice;
	}
	
	public void clearItems() {
		items.clear();
	}
	
	public boolean isEmpty() {
		return items.size() == 0;
	}
}

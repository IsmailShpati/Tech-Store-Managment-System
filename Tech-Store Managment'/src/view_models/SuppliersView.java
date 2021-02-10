package view_models;

import java.util.ArrayList;
import java.util.Optional;

import controllers.StockController;
import controllers.SupplierController;
import interfaces.ViewException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import models.StockItem;
import models.Supplier;

public class SuppliersView extends BorderPane {

	
	private TableView<Supplier> table = new TableView<>();
	private ObservableList<Supplier> suppliers = 
			FXCollections.observableArrayList(SupplierController.getSuppliers());
	private MenuBar menuBar = new MenuBar();

	private AddSupplier newSupplier;
	private SideMenu menu;
	
	public SuppliersView(SideMenu menu) {
		this.menu = menu;
		initMenuBar();
		initTable();
	}
	
	private void initMenuBar() {
		Label newLabel = new Label("New",
				ImageGetter.getImage("Resources/buttons/add_white.png", 20, 20));
		newLabel.setOnMouseClicked(e->{
			newSupplier = new AddSupplier();
			menu.changeRightSide(newSupplier);
			refresh();
		});
		Label deleteLabel = new Label("Delete", ImageGetter.
				getImage("Resources/buttons/delete_white.png", 20, 20));
		deleteLabel.setOnMouseClicked(e->{
			try {
				deleteSupplier();
				refresh();
			} catch (ViewException e1) {
				e1.showAlert();
			}
		});
		
		Label showInformation = new Label("Info", 
				ImageGetter.getImage("Resources/buttons/info_white.png", 20, 20));
		showInformation.setOnMouseClicked(e->{
			Supplier sup = table.getSelectionModel().getSelectedItem();
			if(sup != null) {
				menu.changeRightSide(new SupplierView(sup));
			}
			else
				new Alert(AlertType.WARNING, 
						"Please select a supplier").showAndWait();
		});
		
		menuBar.getMenus().addAll(new Menu("", newLabel), 
				new Menu("", deleteLabel), new Menu("", showInformation));
		
	}
	
	private void returnBack() {
		menu.changeRightSide(this);
	}

	private void deleteSupplier() throws ViewException {
		Supplier supplier = table.getSelectionModel().getSelectedItem();
		if(supplier == null)
			throw new ViewException("Please select a supplier", AlertType.ERROR);
		else {
			Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to delete that item?",
					ButtonType.YES, ButtonType.NO);
			Optional<ButtonType> butons = alert.showAndWait();
			if(butons.get() == ButtonType.YES) {
				SupplierController.deleteSupplier(supplier);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void initTable() {
		//initSuppliers();
		
		Label title = new Label("Suppliers");
		title.setId("title");
		setMargin(title, new Insets(10, 0, 10, 0));
		title.setFont(Font.font(20));
		TableColumn<Supplier, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(
				new PropertyValueFactory<>("name"));
		
		TableColumn<Supplier, String> numberColumn = new TableColumn<>("Phone number");
		numberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
		table.setPrefWidth(600);
		table.setPrefHeight(550);
		table.setItems(suppliers);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.getColumns().setAll(nameColumn, numberColumn);
		setTop(title);
		setAlignment(title, Pos.CENTER);
		setCenter(table);
		setBottom(menuBar);
		//getChildren().addAll(title, table, menuBar);
	}
	
	private void refresh() {
		suppliers.setAll(SupplierController.getSuppliers());
	}
	

	public class AddSupplier extends VBox{
		private GridPane pane;
		private ListView<String> list;
		private ComboBox<String> itemsAvaible;
		private TextField nameField = new TextField();
		private TextField phoneField = new TextField();
		public AddSupplier() {
			pane = new GridPane();
			setPadding(new Insets(30, 100, 20, 100));
			list = new ListView<>();
			list.setItems(FXCollections.observableArrayList());
			initGrid();
			getChildren().addAll(pane, list);
			initAddButton();
			initBackBtn();
		}
		
		private void initAddButton() {
			HBox div = new HBox();
			Button addSupplier = new Button("Add supplier",
					ImageGetter.getImage("Resources/buttons/add.png", 20, 20));
			addSupplier.setOnAction(e->{
				try {
					validate();
					SupplierController.addSupplier(new Supplier(nameField.getText(), 
							phoneField.getText(), new ArrayList<String>(list.getItems())));
					refresh();
					returnBack();
					new Alert(AlertType.CONFIRMATION, "Supplier added succesfully").showAndWait();
				} catch (ViewException e1) { e1.showAlert(); }
			});
			div.getChildren().add(addSupplier);
			div.setAlignment(Pos.CENTER_RIGHT);
			getChildren().addAll(div);
		}
		
		private void initBackBtn() {
			Button back = new Button("Back", 
					ImageGetter.getImage("Resources/buttons/back.png", 20, 20));
			
			back.setOnAction(e->{
				returnBack();
			});
			getChildren().add(back);
		}
		
		private void initItems() {
			itemsAvaible = new ComboBox<>();
			itemsAvaible.setItems(FXCollections.observableArrayList());
			for(StockItem i : StockController.getItems())
				itemsAvaible.getItems().add(i.getItemName());
			itemsAvaible.setValue(itemsAvaible.getItems().get(0));
		}

		private void initGrid() {
			pane.setVgap(10);
			pane.setHgap(20);
			pane.setPadding(new Insets(30));
			pane.add(new Label("Name"), 0, 0);
			Tooltip phoneTooltip = new Tooltip("Enter a valid Albanian number\n ex. \"+355 followed by 9 digits");
			phoneField.setTooltip(phoneTooltip);
			pane.add(new Label("Phone Number"), 0, 1);
			pane.add(nameField, 1, 0);
			pane.add(phoneField, 1, 1);
			initItems();
			pane.add(itemsAvaible, 1, 2);
			Button addItem = new Button("Add item",
					ImageGetter.getImage("Resources/buttons/add.png", 20, 20));
			addItem.setOnAction(e->{
				if(!list.getItems().contains(itemsAvaible.getValue()))
					list.getItems().add(itemsAvaible.getValue());
				else
					new Alert(AlertType.ERROR, "Item alredy added").showAndWait();
			});
			pane.add(addItem, 0, 2);
//			Button newBtn = new Button("Add Supplier");
//			pane.add(newBtn, 1, 2);
//			newBtn.setOnAction(e->{
//				try {
//					validate();
//					SupplierController.addSupplier(new Supplier(nameField.getText(), 
//							phoneField.getText(), new ArrayList<String>(list.getItems())));
//					stage.close();
//					refresh();
//				} catch (ViewException e1) {
//					e1.showAlert();
//				}
//			});
		}
		
		private void validate() throws ViewException{
			if(nameField.getText().length() < 1)
				throw new ViewException("Please enter a name", AlertType.ERROR);
			
			if(!phoneField.getText().replaceAll(" ", "").matches("^(\\+355){1}\\d{9,}$") )
				throw new ViewException("Please enter a valid phone number", AlertType.ERROR);
			if(list.getItems().size() < 1)
				throw new ViewException("Add at least one item.", AlertType.ERROR);
		}
	}
	
	
	public class SupplierView extends VBox{
		private GridPane initialInfo;
		private ListView<String> itemsOffered;
		private Supplier supplier;
		
		public SupplierView(Supplier supplier) {
			this.supplier = supplier;
			setPadding(new Insets(10, 100, 0, 100));
			setAlignment(Pos.CENTER);
			initGrid();
			initBackBtn();
		}
		private void initBackBtn() {
			HBox div = new HBox();
			div.setAlignment(Pos.BOTTOM_LEFT);
			Button back = new Button("Back", 
					ImageGetter.getImage("Resources/buttons/back.png", 20, 20));
			
			back.setOnAction(e->{
				returnBack();
			});
			setMargin(div, new Insets(0, 0, 20,0));
			div.getChildren().add(back);
			getChildren().add(div);
		}

		private void initGrid() {
			initialInfo = new GridPane();
			initialInfo.setAlignment(Pos.CENTER);
			initialInfo.setHgap(10);
			initialInfo.setVgap(10);
			initialInfo.add(new Label("Name:"), 0, 0);
			initialInfo.add(new Label("Contact:"), 0, 1);
			initialInfo.add(new Label(supplier.getName()), 1, 0);
			initialInfo.add(new Label(supplier.getPhoneNumber()), 1, 1);
			Button copyBtn = new Button("",
					ImageGetter.getImage("Resources/buttons/clipboardCopy.png", 20, 20));
			copyBtn.setOnAction(E->{
				Clipboard clipboard = Clipboard.getSystemClipboard();
		        ClipboardContent content = new ClipboardContent();
		        content.putString(supplier.getName()+"\n"+supplier.getPhoneNumber());
		        clipboard.setContent(content);
		        new Alert(AlertType.CONFIRMATION, "Copied on clipboard.").showAndWait();
			});
			
			initialInfo.add(copyBtn, 2, 1);
			itemsOffered = new ListView<>
			(FXCollections.observableArrayList(supplier.getOfferedItems()));
			setMargin(itemsOffered, new Insets(0, 0, 50, 0));
			Label label = new Label("Items Offered");
			label.setId("title");
			HBox titleDiv = new HBox(label);
			titleDiv.setAlignment(Pos.BOTTOM_CENTER);
			getChildren().addAll(initialInfo, titleDiv,itemsOffered);
		}
	}
	
}

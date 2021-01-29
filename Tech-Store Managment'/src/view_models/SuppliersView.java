package view_models;

import java.util.IllegalFormatCodePointException;
import java.util.Optional;

import controllers.CategorieController;
import controllers.StockController;
import controllers.SupplierController;
import interfaces.ViewException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import models.Supplier;
import views.ManagerView;

public class SuppliersView extends VBox {

	
	private TableView<Supplier> table = new TableView<>();
	private ObservableList<Supplier> suppliers = 
			FXCollections.observableArrayList(SupplierController.getSuppliers());
	private MenuBar menuBar = new MenuBar();
	private TextField nameField = new TextField();
	private TextField phoneField = new TextField();
	
	
	public SuppliersView() {
		initMenuBar();
		initTable();
	}
	
	private void initMenuBar() {
		Label newLabel = new Label("New");
		newLabel.setOnMouseClicked(e->{
			newSupplier();
			refresh();
		});
		Label deleteLabel = new Label("Delete");
		deleteLabel.setOnMouseClicked(e->{
			try {
				deleteSupplier();
				refresh();
			} catch (ViewException e1) {
				e1.showAlert();
			}
		});
		
		menuBar.getMenus().addAll(new Menu("", newLabel), new Menu("", deleteLabel));
		
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

	private void newSupplier() {
		GridPane pane = new GridPane();
		pane.setVgap(10);
		pane.setHgap(20);
		pane.setPadding(new Insets(30));
		pane.add(new Label("Name"), 0, 0);
		pane.add(new Label("Phone Number"), 0, 1);
		Stage stage = new Stage();
		pane.add(nameField, 1, 0);
		pane.add(phoneField, 1, 1);
		
		Button newBtn = new Button("Add Supplier");
		pane.add(newBtn, 1, 2);
		newBtn.setOnAction(e->{
			try {
				validate();
				SupplierController.addSupplier(new Supplier(nameField.getText(), phoneField.getText()));
				stage.close();
			} catch (ViewException e1) {
				e1.showAlert();
			}
		});
		
		stage.setScene(new Scene(pane));
		stage.setTitle("New supplier");
		stage.show();
	}
	
	private void validate() throws ViewException{
		if(nameField.getText().length() < 1)
			throw new ViewException("Please enter a name", AlertType.ERROR);
		if(phoneField.getText().length() < 1)
			throw new ViewException("Please enter a phone number", AlertType.ERROR);
	}
	

	private void initTable() {
		//initSuppliers();
		
		Label title = new Label("Suppliers");
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
		
		setAlignment(Pos.CENTER);
		getChildren().addAll(title, table, menuBar);
	}
	
	private void refresh() {
		suppliers.setAll(SupplierController.getSuppliers());
	}
	

	
	
}

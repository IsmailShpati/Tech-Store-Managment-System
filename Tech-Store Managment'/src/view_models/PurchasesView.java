package view_models;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import models.Manager;
import models.PurchaseBill;

public class PurchasesView extends BorderPane {
	
	private TableView<PurchaseBill> table;
	private HBox picker = new HBox(20);
	private DatePicker from, to;
	private Manager manager;
	
	public PurchasesView(Manager manager) {
		this.manager = manager;
		initTable();
		initSelectors();
	}
	
	private void initSelectors() {		
		if(manager.getBills().size() > 0) {
			from = new DatePicker();
			from.setValue(manager.getBills().get(0).getDate().toLocalDate());
			to = new DatePicker();
			to.setValue(LocalDate.now());
		}
		else {
			from = new DatePicker(LocalDate.now());
			to = new DatePicker(LocalDate.now());
		}
		initTableContents();
		from.setOnAction(e->{
			initTableContents();
		});
		to.setOnAction(e->{
			initTableContents();
		});
		
		HBox fromCont = new HBox(10);
		fromCont.getChildren().addAll(new Label("From: "), from);
		HBox toCont = new HBox(10);
		toCont.getChildren().addAll(new Label("To: "), to);
		picker.getChildren().addAll(fromCont, toCont);
		setTop(picker);
		setMargin(picker, new Insets(30, 30, 5, 30));
	}
	
	private void initTable(){
		table = new TableView<>();
		setCenter(table);
		table.setPrefWidth(650);
		table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		addColumn("Date", "purchaseDate");
		addColumn("Item name", "itemName");
		addColumn("Quant.", "quantityPurchased");
		addColumn("Price", "price");
	}
	
	public void initTableContents() {
		table.setItems(FXCollections.observableArrayList(
				manager.getBillsInPeriod(from.getValue(), to.getValue())));
	}
	
	private void addColumn(String columnName, String attributeName) {
		TableColumn<PurchaseBill, String> column = new TableColumn<>(columnName);
		column.setCellValueFactory(new PropertyValueFactory<>(attributeName));
		table.getColumns().add(column);
	}
}

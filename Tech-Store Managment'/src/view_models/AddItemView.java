package view_models;

import controllers.CategorieController;
import controllers.StockController;
import interfaces.ViewException;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import models.StockItem;
import views.ManagerView;

public class AddItemView extends BorderPane{
	
	private ManagerView mainView;
	private int startingColumn = 0, startingRow = 0;
	private GridPane body = new GridPane();
	//private ObservableList<String> categories = FXCollections.observableArrayList(CategorieController.getCategories());
	private Label[] nameLabels = {
		new Label("Name"),
		new Label("Model"),
		new Label("Purchasing Price"),
		new Label("Selling Price"),
		new Label("Category")
		
	};
	
	private TextField[] fields = {
		new TextField(),
		new TextField(),
		new TextField(),
		new TextField()
	};
	
	private ComboBox<String> categorySelector = new ComboBox<>(); 
	
	
	public AddItemView(ManagerView mainView) {
		this.mainView = mainView;
		setPrefWidth(600);
		initPane();
		putNodes();
		initAddBtn();
		setCenter(body);
		initBackBtn();
	}
	
	private void putNodes() {
		for(int i = 0; i < fields.length; i++) {
			body.add(nameLabels[i], startingColumn, startingRow);
			body.add(fields[i],startingColumn+1, startingRow++);
		}
		initCategories();
		body.add(nameLabels[nameLabels.length-1], startingColumn, startingRow);
		body.add(categorySelector, startingColumn+1, startingRow++);
	}
	
	private void initCategories() {
		categorySelector.setPrefWidth(200);
		categorySelector.setValue(CategorieController.reset());
		categorySelector.setOnMouseClicked(e -> {
			categorySelector.setItems(FXCollections.observableArrayList(CategorieController.getCategories()));
			categorySelector.setValue(CategorieController.reset());
		});
		
	}
	
	private void initPane() {
		body.setHgap(30);
		body.setVgap(10);
		body.setAlignment(Pos.CENTER);
		body.setPadding(new Insets(30));
	}
	
	private void initAddBtn() {
		Button addItemBtn = new Button("NEW ITEM");
		addItemBtn.setOnAction(event -> {
			try {
				addItem();
				reset();
				new Alert( AlertType.CONFIRMATION, "Added succesfully").show();
			}catch(ViewException e) {
				e.showAlert();
			}
		});
		body.add(addItemBtn, startingColumn+1, startingRow++);
		
	}
	
	private void addItem() throws ViewException{
		String name = fields[0].getText();
		String model = fields[1].getText();
		double sellingPrice, purchasingPrice;
		
		if(name.length() < 1)
			throw new ViewException("Please fill the name field", AlertType.ERROR);
		if(model.length() < 1)
			throw new ViewException("Please fill the model field", AlertType.ERROR);
		
		try {
			sellingPrice = Double.parseDouble(fields[2].getText());
			purchasingPrice = Double.parseDouble(fields[3].getText());
		}catch(NumberFormatException e) {
			throw new ViewException("Please enter only numbers in price fields", AlertType.ERROR);
		}
		if(categorySelector.getValue() == null) {
			throw new ViewException("Please add a category", AlertType.ERROR);
		}
		
		StockItem i = new StockItem(name, model, sellingPrice, categorySelector.getValue(),
				0, purchasingPrice);
		
		StockController.addItem(i);
		
	}
	
	private void initBackBtn() {
		Button backBtn = new Button("Back");
		setBottom(backBtn);
		BorderPane.setAlignment(backBtn, Pos.CENTER_LEFT);
		BorderPane.setMargin(backBtn, new Insets(30));
		backBtn.setOnAction(e -> {
			mainView.returnBack();
		});
	}
	
	
	private void reset() {
		for(TextField f: fields) {
			f.clear();
		}
		categorySelector.setValue(CategorieController.reset());
	}
}

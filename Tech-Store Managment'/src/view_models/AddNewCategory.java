package view_models;

import controlers.CategorieController;
import interfaces.ViewException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import models.Category;
import views.ManagerView;

public class AddNewCategory extends BorderPane{

	private ManagerView main;
	private TextField categoryField;
	private TableView<Category> tableView = new TableView<>();
	private ObservableList<Category> categories = FXCollections.observableArrayList();
	
	public AddNewCategory(ManagerView main) {
		this.main = main;
		setPadding(new Insets(20));
		addGrid();
		initTable();
		initBackBtn();
	}
	
	private void initTable() {
		initCategories();
		tableView.setItems(categories);
		TableColumn<Category, String> column = new TableColumn<>("CategorieController");
		column.setCellValueFactory(new PropertyValueFactory<Category, String>("category"));
		tableView.getColumns().add(column);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		setTop(tableView);
				
	}
	
	private void initCategories() {
		categories.setAll(CategorieController.getCategories());
	}
	
	private void addGrid() {
		GridPane body = new GridPane();
		body.setHgap(20);
		body.setVgap(10);
		Label categoryLab = new Label("Category name ");
		body.add(categoryLab, 0, 1);
		categoryField = new TextField();
		body.add(categoryField, 1, 1);
		body.setAlignment(Pos.CENTER);
		Button addCategoryBtn = new Button("Add category");
		body.add(addCategoryBtn, 1, 2);
		addCategoryBtn.setOnAction(e -> {
			String cat = categoryField.getText();
			try {
				if(cat.length() < 1)
					throw new ViewException("Please fill the category field", AlertType.ERROR);
				CategorieController.addCategory(cat);
				initCategories();
				categoryField.clear();
				new Alert(AlertType.CONFIRMATION, "Added succesfully").show();
			} catch (ViewException e1) {
				e1.showAlert();
			}
			
		});
		
		setCenter(body);
		setAlignment(body, Pos.CENTER);
	}
	
	private void initBackBtn() {
		Button backBtn = new Button("Back");
		backBtn.setOnAction(E -> {
			main.returnBack();
		});
		setBottom(backBtn);
		setAlignment(backBtn, Pos.BOTTOM_LEFT);
		setMargin(backBtn, new Insets(0, 0, 0, 20));
		
	}
	
}

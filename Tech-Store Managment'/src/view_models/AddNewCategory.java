package view_models;

import java.util.Optional;

import controllers.CategorieController;
import interfaces.Returnable;
import interfaces.ViewException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
public class AddNewCategory extends BorderPane{

	private Returnable main;
	private TextField categoryField;
	private ListView<String> listView = new ListView<>();
	private ObservableList<String> categories = 
			FXCollections.observableArrayList(CategorieController.getCategories());
	private MenuBar menuBar = new MenuBar();
	
	
	public AddNewCategory(Returnable main) {
		this.main = main;
		setPrefWidth(650);
		setPrefHeight(600);
		//addGrid();
		initMenuBar();
		initTable();
		//initBackBtn();
	}
	
	private void initMenuBar() {
		Label newLabel = new Label("New",
				ImageGetter.getImage("Resources/buttons/add_white.png", 20, 20));

		newLabel.setOnMouseClicked(e->{
			newCategory();
		});
		Label deleteLabel = new Label("Delete",
				ImageGetter.getImage("Resources/buttons/delete_white.png", 20, 20));

		deleteLabel.setOnMouseClicked(e->{
			try {
				delete();
			} catch (ViewException e1) {
				e1.showAlert();
			}
		});
		Label backLabel = new Label("Back",
				ImageGetter.getImage("Resources/buttons/back_white.png", 20, 20));

		backLabel.setOnMouseClicked(e->{
			main.returnBack();
		});
		
		menuBar.getMenus().addAll(new Menu("", newLabel), 
				new Menu("", deleteLabel), new Menu("", backLabel));
	}

	private void initTable() {
		Label title = new Label("Categories");
		title.setFont(Font.font(20));
		setPrefWidth(680);
		listView.setItems(categories);
		listView.setEditable(true);
		listView.setCellFactory(TextFieldListCell.forListView());
		listView.setOnEditCommit(e->{
			int index = listView.getSelectionModel().getSelectedIndex();
			if(index < 0)
				new Alert(AlertType.ERROR, "Please select a category").showAndWait();
			else
				try {
					CategorieController.editCategory(index, e.getNewValue());
					refresh();
				} catch (ViewException e1) {
					e1.showAlert();
				}
			
		});
		listView.setPrefHeight(550);
		//getChildren().addAll(title, listView, menuBar);
		setTop(title);
		setAlignment(title, Pos.CENTER);
		setCenter(listView);
		setBottom(menuBar);
				
	}
	
	private void refresh() {
		categories.setAll(CategorieController.getCategories());
	}
	
	private void newCategory() {
		GridPane body = new GridPane();
		Stage stage = new Stage();
		body.setPadding(new Insets(30));
		body.setHgap(20);
		body.setVgap(10);
		Label categoryLab = new Label("Category name ");
		body.add(categoryLab, 0, 1);
		categoryField = new TextField();
		body.add(categoryField, 1, 1);
		body.setAlignment(Pos.CENTER);
		Button addCategoryBtn = new Button("Add category",
				ImageGetter.getImage("Resources/buttons/add.png", 20,20));
		body.add(addCategoryBtn, 1, 2);
		addCategoryBtn.setOnAction(e -> {
			String cat = categoryField.getText();
			try {
				if(cat.length() < 1)
					throw new ViewException("Please fill the category field", AlertType.ERROR);
				CategorieController.addCategory(cat);
				refresh();
				categoryField.clear();
				stage.close();
				new Alert(AlertType.CONFIRMATION, "Added succesfully").show();
				
			} catch (ViewException e1) {
				e1.showAlert();
			}
			
		});
		
		stage.setScene(new Scene(body));
		stage.setTitle("New Categorie");
		stage.show();
		
		
	}
	
	private void delete() throws ViewException {
		String s = listView.getSelectionModel().getSelectedItem();
		if(s == null)
			throw new ViewException("Please select a category", AlertType.ERROR);
		else {
			Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to delete that item?",
					ButtonType.YES, ButtonType.NO);
			Optional<ButtonType> butons = alert.showAndWait();
			if(butons.get() == ButtonType.YES)
				CategorieController.deleteCategory(s);
			
			refresh();
		}
	}
}

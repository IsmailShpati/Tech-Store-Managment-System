package view_models;

import interfaces.ViewException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class EditItemPopUp extends Application {

	private String billName = "";
	private int quantity;
	private ItemsView itemView = null;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public EditItemPopUp(String billName, int quantity, ItemsView itemView) {
	    super();
		this.billName = billName;
		this.quantity = quantity;
		this.itemView = itemView;
	}
	
	@Override
	public void start(Stage stage) {
	    GridPane root = new GridPane();
	    root.setPadding(new Insets(30));
	    root.setHgap(20);
	    root.setVgap(10);
	    Label nameLabel = new Label("Item name");
	    TextField nameField = new TextField(billName);
	    root.add(nameLabel, 1, 1);
	    root.add(nameField, 2, 1);
	    
	    Label quantLabel =  new Label("Quantity");
	    TextField quantityField = new TextField(quantity+"");
	    root.add(quantLabel, 1, 2);
	    root.add(quantityField, 2, 2);
	    Button save = new Button("Save");
	    root.add(save, 2, 3);
	    
	    save.setOnAction(e -> {
	    	try {
				itemView.editSelected(nameField.getText(), Integer.parseInt(quantityField.getText()));
			} catch (NumberFormatException e1) {
				ViewException.showAlert("Please enter a number", AlertType.ERROR);
			}
	    	catch(ViewException e1) {
	    		e1.showAlert();
	    	}
	    	
	    	stage.close();
	    });
	    stage.setScene(new Scene(root));
	    stage.setTitle("Edit item entry");
	    stage.show();
		
	}

	
	
}

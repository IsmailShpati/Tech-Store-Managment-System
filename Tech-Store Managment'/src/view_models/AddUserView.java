package view_models;

import java.time.LocalDate;

import controllers.UserController;
import interfaces.ViewException;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import models.Administrator;
import models.Cashier;
import models.Manager;
import models.PermissionLevel;

public class AddUserView extends GridPane {

	private int startingRow, startingColumn;
	private PasswordField passwordField = new PasswordField();
	private ComboBox<String> permissionLevel = new ComboBox<>();
	private DatePicker date = new DatePicker(LocalDate.now());
	private Button addUser = new Button("ADD USER");
	private TextField[] fields = {
		new TextField(), //username	
		new TextField(), //name
		new TextField(), //surname
		new TextField() //salary
	};
	
	public AddUserView() {
		setVgap(10);
		setHgap(20);
		setAlignment(Pos.CENTER);
		setPadding(new Insets(20));
		initFields();
		initButton();
		
	}
	
	
	private void initFields() {
		permissionLevel.setItems(
				FXCollections.observableArrayList("CASHIER", "MANAGER", "ADMINISTRATOR"));
		permissionLevel.setValue("CASHIER");
		permissionLevel.setOnAction(e -> {
			addUser.setText("ADD "+ permissionLevel.getValue());
		});
		add(permissionLevel, startingColumn, startingRow++);
		
		add(new Label("Username: "), startingColumn, startingRow);
		add(fields[0], startingColumn+1, startingRow++);
		
		add(new Label("Password: "), startingColumn, startingRow);
		add(passwordField, startingColumn+1, startingRow++);
	
		add(new Label("Name: "), startingColumn, startingRow);
		add(fields[1], startingColumn+1, startingRow++);
		
		add(new Label("Surname: "), startingColumn, startingRow);
		add(fields[2], startingColumn+1, startingRow++);
		
		add(new Label("Salary: "), startingColumn, startingRow);
		add(fields[3], startingColumn+1, startingRow++);
		
		add(new Label("Birthday: "), startingColumn, startingRow);
		add(date, startingColumn+1, startingRow++);
		
	}
	
	private void initButton() {
		add(addUser, startingColumn+1, startingRow++);
		addUser.setOnAction(e -> {
			try {
				validateFields();
				new Alert(AlertType.CONFIRMATION, "Added succesfully").showAndWait();
				reset();
			} catch (ViewException e1) {
				e1.showAlert();
			}
		});
	}
	
	
	private void validateFields() throws ViewException {
		
		String username = fields[0].getText();
		String password = passwordField.getText();
		String name = fields[1].getText();
		String surname = fields[2].getText();
		double salary = 0;
		if(username.length() < 1)
			throw new ViewException("Please enter an username", AlertType.ERROR);
		if(password.length() < 1)
			throw new ViewException("Please enter a password", AlertType.ERROR);
		if(name.length() < 1)
			throw new ViewException("Please enter a name", AlertType.ERROR);
		if(surname.length() < 1)
			throw new ViewException("Please enter a surname", AlertType.ERROR);
		try {
			salary = Double.parseDouble(fields[3].getText());
		    if(salary < 0)
		    	throw new Exception();
		}catch(Exception e) {
			throw new ViewException("Please enter a valid number in salary", AlertType.ERROR);
		}
		
		int permission = permissionLevel.getSelectionModel().getSelectedIndex();

		System.out.println(permission);
		switch(permission) {
		case 0:
			UserController.addUser(new Cashier(username, password,
					name, surname, PermissionLevel.CASHIER, salary, date.getValue()));
			break;
		case 1:
			UserController.addUser(new Manager(username, password,
					name, surname, PermissionLevel.MANAGER, salary, date.getValue()));
			break;
		case 2:
			UserController.addUser(new Administrator(username, password,
					name, surname, PermissionLevel.ADMINISTRATOR, salary, date.getValue()));
			break;
		}
	}
	
	private void reset() {
		for(TextField f : fields)
			f.clear();
		passwordField.clear();
		permissionLevel.setValue("CASHIER");
		date.setValue(LocalDate.now());
	}
	
	
	
	
	
}

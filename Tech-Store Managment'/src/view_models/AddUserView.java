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
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import models.Administrator;
import models.Cashier;
import models.Manager;
import models.PermissionLevel;
import views.AdministratorView.AdministratorPannel;

public class AddUserView extends BorderPane {

	private AdministratorPannel mainView;
	private int startingRow, startingColumn;
	private PasswordField passwordField = new PasswordField();
	private ComboBox<String> permissionLevel = new ComboBox<>();
	private DatePicker date = new DatePicker(LocalDate.now());
	private Button addUser = new Button("Add cashier");
	private GridPane body = new GridPane();
	private TextField[] fields = {
		new TextField(), //username	
		new TextField(), //name
		new TextField(), //surname
		new NumberField(), //salary
		new TextField()  //phoneNumber
	};
	
	public AddUserView(AdministratorPannel mainView) {
		this.mainView = mainView;
		body.setVgap(10);
		body.setHgap(20);
		body.setAlignment(Pos.CENTER);
		setPrefWidth(400);
		setPadding(new Insets(20));
		setCenter(body);
		Button backBtn = new Button("Back");
		backBtn.setOnAction(e-> { mainView.returnBack();  });
		setBottom(backBtn);
		setAlignment(backBtn, Pos.CENTER_LEFT);
		setMargin(backBtn, new Insets(30));
		initFields();
		initButton();
		
	}
	
	
	private void initFields() {
		permissionLevel.setItems(
				FXCollections.observableArrayList("Cashier", "Manager", "Admin"));
		permissionLevel.setValue("Cashier");
		permissionLevel.setOnAction(e -> {
			addUser.setText("Add "+ permissionLevel.getValue());
		});
		body.add(permissionLevel, startingColumn, startingRow++);
		
		body.add(new Label("Username: "), startingColumn, startingRow);
		body.add(fields[0], startingColumn+1, startingRow++);
		
		body.add(new Label("Password: "), startingColumn, startingRow);
		body.add(passwordField, startingColumn+1, startingRow++);
		Tooltip password = new Tooltip("\"Password must contian at least 1 digit, 1 upper case,\n "
				+ "1 lower case and 1 of the following #?!_%^\",");
		passwordField.setTooltip(password);
		body.add(new Label("Name: "), startingColumn, startingRow);
		body.add(fields[1], startingColumn+1, startingRow++);
		
		body.add(new Label("Surname: "), startingColumn, startingRow);
		body.add(fields[2], startingColumn+1, startingRow++);
		
		body.add(new Label("Phone number: "), startingColumn, startingRow);
		body.add(fields[4], startingColumn+1, startingRow++);
		
		body.add(new Label("Salary: "), startingColumn, startingRow);
		body.add(fields[3], startingColumn+1, startingRow++);
		
		body.add(new Label("Birthday: "), startingColumn, startingRow);
		body.add(date, startingColumn+1, startingRow++);
		
	}
	
	private void initButton() {
		body.add(addUser, startingColumn+1, startingRow++);
		addUser.setOnAction(e -> {
			try {
				validateFields();
				new Alert(AlertType.CONFIRMATION, "Added succesfully").showAndWait();
				reset();
				mainView.refresh();
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
		String phoneNumber = fields[4].getText();
		double salary = 0;
		if(username.length() < 1)
			throw new ViewException("Please enter an username", AlertType.ERROR);
		validatePassword(password);
		if(name.length() < 1)
			throw new ViewException("Please enter a name", AlertType.ERROR);
		if(surname.length() < 1)
			throw new ViewException("Please enter a surname", AlertType.ERROR);
		if(phoneNumber.length() < 1)
			throw new ViewException("Please enter a phone number", AlertType.ERROR);
		
		salary = ((NumberField)fields[3]).getValue();
		if(salary < 0)
			throw new ViewException("Salary can't be negative", AlertType.ERROR);
		    	
		
		int permission = permissionLevel.getSelectionModel().getSelectedIndex();

		System.out.println(permission);
		switch(permission) {
		case 0:
			UserController.addUser(new Cashier(username, password,
					name, surname, PermissionLevel.CASHIER, salary, date.getValue(), phoneNumber));
			break;
		case 1:
			UserController.addUser(new Manager(username, password,
					name, surname, PermissionLevel.MANAGER, salary, date.getValue(), phoneNumber));
			break;
		case 2:
			UserController.addUser(new Administrator(username, password,
					name, surname, PermissionLevel.ADMINISTRATOR, salary, date.getValue(), phoneNumber));
			break;
		}
	}
	
	public static void validatePassword(String pass) throws ViewException{
		if(pass.length() < 8)
			throw new ViewException("Password must be at least 8 characters long",
					AlertType.ERROR);
		if(!pass.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!_%^])$"))
			throw new ViewException("Password must contian at least 1 digit, 1 upper case, "
	+ "1 lower case and 1 of the following #?!_%^",
					AlertType.ERROR);
	}
	
	
	private void reset() {
		for(TextField f : fields)
			f.clear();
		passwordField.clear();
		permissionLevel.setValue("Cashier");
		date.setValue(LocalDate.now());
	}
	
	
	
	
	
}

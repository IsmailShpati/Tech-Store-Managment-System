package view_models;

import java.time.LocalDate;
import java.util.Optional;

import controllers.StockController;
import controllers.UserController;
import interfaces.ViewException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.User;

public class UsersView extends TableView<User> {

	private ObservableList<User> users = 
			FXCollections.observableArrayList(UserController.getUsers());
	
	public UsersView() {
		setItems(users);
		setMinWidth(700);
		initColumns();
		
	}
	
	
	private void initColumns() {
		setEditable(true);
		
		TableColumn<User, String> levelColumn = new TableColumn<>("Status");
		levelColumn.setCellValueFactory(new PropertyValueFactory<>("permissionLevel"));
		
		TableColumn<User, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		nameColumn.setOnEditCommit(e->{
			if(e.getNewValue().length() > 1) {
				e.getRowValue().setName(e.getNewValue());
				UserController.save();
				//refresh();
			}
		});
		
		TableColumn<User, String> surnameColumn = new TableColumn<>("Surname");
		surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
		surnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		surnameColumn.setOnEditCommit(e->{
			if(e.getNewValue().length() > 1) {
				e.getRowValue().setSurname(e.getNewValue());
				UserController.save();
				//refresh();
			}
		});
		
		TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
		usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
		usernameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		usernameColumn.setOnEditCommit(e->{
			String username = e.getNewValue();
			try {
				UserController.exists(username);
				e.getRowValue().setUsername(username);
				UserController.save();
				
			} catch (ViewException e1) {
				e1.showAlert();
			}
		});
		
		TableColumn<User, String> passwordColumn = new TableColumn<>("Password");
		passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
		passwordColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		passwordColumn.setOnEditCommit(e->{
			try {
				AddUserView.validatePassword(e.getNewValue());
				e.getRowValue().setPassword(e.getNewValue());
				UserController.save();
			}catch(ViewException err) {
				err.showAlert();
			}
			
		});
		
		TableColumn<User, String> phoneColumn = new TableColumn<>("Phone");
		phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
		phoneColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		phoneColumn.setOnEditCommit(e->{
			if(e.getNewValue().length() > 1) {
				e.getRowValue().setPhoneNumber(e.getNewValue());
				UserController.save();
			}
		});
		
		TableColumn<User, String> salaryColumn = new TableColumn<>("Salary");
		salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
		salaryColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		salaryColumn.setOnEditCommit(e->{
			try {
				e.getRowValue().setSalary(Double.parseDouble(e.getNewValue()));
				UserController.save();
			}catch(NumberFormatException err) {
				new Alert(AlertType.ERROR, "Enter a valid number in salary").showAndWait();
				
			}
					
				
		});
		
		TableColumn<User, LocalDate> birthdayColumn = new TableColumn<>("Birthday");
		birthdayColumn.setCellValueFactory(new PropertyValueFactory<>("birthday"));
//		birthdayColumn.setOnEditCommit(e-> {
//			HBox container = new HBox(20);
//			DatePicker picker = new DatePicker();
//			Button button = new Button("Edit");
//			button.setOnAction(clickEvent->{
//				LocalDate newBD = picker.getValue();
//				if(newBD.getYear() < LocalDate.now().getYear()-16) {
//					new Alert(AlertType.ERROR, "Can't be younger than 16 years old").showAndWait();
//				}
//				else {
//					e.getRowValue().setBirthday(newBD);
//					UserController.save();
//					refresh();
//				}
//			});
//			Stage stage = new Stage();
//			stage.setScene(new Scene(container));
//			stage.setAlwaysOnTop(true);
//		});
		
		getColumns().addAll(levelColumn, usernameColumn, passwordColumn, nameColumn,
				surnameColumn, salaryColumn, phoneColumn, birthdayColumn);
	}
	
	public void refresh() {
		users.setAll(UserController.getUsers());
	}


	public void delete() {
		User u = getSelectionModel().getSelectedItem();
		if(u == null)
			new Alert(AlertType.WARNING, "Please select an users.").showAndWait();
		else {
			Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to delete that item?",
					ButtonType.YES, ButtonType.NO);
			Optional<ButtonType> butons = alert.showAndWait();
			if(butons.get() == ButtonType.YES) {
				UserController.removeUser(u);
				refresh();
			}
		}
	}
	
	
}

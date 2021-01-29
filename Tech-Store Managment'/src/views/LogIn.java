package views;

import controllers.UserController;
import interfaces.ViewException;
import interfaces.Viewable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.Administrator;
import models.Cashier;
import models.Manager;
import models.PermissionLevel;
import models.User;

public class LogIn implements Viewable {

	@Override
	public void setView(Stage stage) {
		Scene scene = new Scene(new LogInView(stage));
		stage.setScene(scene);
		stage.show();
	}

	
	class LogInView extends BorderPane{
		private TextField usernameField = new TextField();
		private PasswordField passwordField = new PasswordField();
		private GridPane body = new GridPane();
		private User user;
		private Stage stage;
		
		public LogInView(Stage stage) {
			this.stage = stage;
			initBody();
			setCenter(body);
			BorderPane.setMargin(body, new Insets(30));
			BorderPane.setAlignment(body, Pos.CENTER);
		}
		
		private void initBody() {
			body.setHgap(20);
			body.setVgap(10);
			Label usernameLabel = new Label("Username:");
			Label passwordLabel = new Label("Password:");
			body.add(usernameLabel, 0, 0);
			body.add(passwordLabel, 0, 1);
			body.add(usernameField, 1, 0);
			body.add(passwordField, 1, 1);
			Button button = new Button("Log in");
			body.add(button, 1, 2);
			button.setOnAction(E -> {
				String username = usernameField.getText();
				String password = passwordField.getText();
				try {
					verifyFields();
					user = UserController.logIn(username, password);
					changeView(user);
				}catch(ViewException e) {
					e.showAlert();
				}
				
			});
		}
		
		private void changeView(User u) {
			PermissionLevel p = u.getPermissionLevel();
			switch(p) {
			case CASHIER:
				new CashierView((Cashier)u).setView(stage);
				break;
			case MANAGER:
				new ManagerView((Manager)u).setView(stage);
				break;
			case ADMINISTRATOR:
				new AdministratorView((Administrator)u).setView(stage);;
				break;
			}
		}
		
		private void verifyFields() throws ViewException {
			if(usernameField.getText().length() < 1)
				throw new ViewException("Please enter an username", AlertType.ERROR);
			if(passwordField.getText().length() < 1)
				throw new ViewException("Please enter an password", AlertType.ERROR);
		}
		
	}
}

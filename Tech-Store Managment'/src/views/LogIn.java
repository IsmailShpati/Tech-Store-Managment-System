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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.Administrator;
import models.Cashier;
import models.Manager;
import models.PermissionLevel;
import models.User;
import view_models.ImageGetter;

public class LogIn implements Viewable {

	@Override
	public void setView(Stage stage) {
		Scene scene = new Scene(new LogInView(stage));
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setTitle("Log in");
		stage.show();
	}

	
	class LogInView extends BorderPane{
		private TextField usernameField = new TextField();
		private PasswordField passwordField = new PasswordField();
		private GridPane body = new GridPane();
		private User user;
		private Stage stage;
		private String imagePath = "Resources/tech-store.jpeg";
		
		public LogInView(Stage stage) {
			BackgroundImage myBI= new BackgroundImage(new Image("Resources/21.jpg",200,600,false,true),
			        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
			          BackgroundSize.DEFAULT);
			setBackground(new Background(myBI));
			this.stage = stage;
			initBody();
			setCenter(body);
			body.setAlignment(Pos.CENTER);
			BorderPane.setMargin(body, new Insets(30));
			
			try {
				setLeft(new ImageView(new Image(imagePath)));
			} catch (Exception e) {
				System.err.println("[Log in]Image reading error");
			}
		}
		
		private void initBody() {
			body.setHgap(20);
			body.setVgap(10);
			Label usernameLabel = new Label("Username:");
			Label passwordLabel = new Label("Password:");
			body.add(usernameLabel, 0, 0);
			body.add(passwordLabel, 0, 1);
			passwordField.setOnKeyPressed(e->{
				if(e.getCode() == KeyCode.ENTER) 
					logIn();
			});
			body.add(usernameField, 1, 0);
			body.add(passwordField, 1, 1);
			Button button = new Button("Log in", ImageGetter.
					getImage("Resources/buttons/login.png", 26, 26));
			body.add(button, 1, 2);
			button.setOnAction(E -> {
				logIn();
			});
		}
		
		private void logIn() {
			String username = usernameField.getText();
			String password = passwordField.getText();
			try {
				verifyFields();
				user = UserController.logIn(username, password);
				changeView(user);
			}catch(ViewException e) {
				e.showAlert();
			}
		}
		
		private void changeView(User u) {
			PermissionLevel p = u.getPermission();
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

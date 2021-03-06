package view_models;

import java.util.Optional;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import models.PermissionLevel;
import models.User;
import views.LogIn;
import views.CashierView;

public class SideMenu extends HBox {
	private SidePannel sideMenu;
	private Pane rightSide = new Pane();
	private Stage stage;
	private User user;
	
	public SideMenu(Stage stage, User user) {
		this.stage = stage;
		this.user = user;
		sideMenu = new SidePannel();
		setPrefHeight(600);
		getStylesheets().add("SideMenu.css");
		getChildren().addAll(sideMenu, this.rightSide);
	}
	
	public void changeRightSide(Pane pane) {
		this.rightSide = pane;
		getChildren().remove(1);
		getChildren().add(rightSide);
		stage.sizeToScene();
	}

	public void addButton(Pane pane, String btnText, ImageView imageView) {
		sideMenu.addButton(pane, btnText, imageView);
	}
	
	//Inner class
	class SidePannel extends BorderPane{
		private VBox body = new VBox();
		private int nrButtons = 0;
		private Button selectedBtn = new Button();
		public SidePannel() {
			
			body.setSpacing(40);
			
			setId("menuPannel");
			//body.setPadding(new Insets(50, 0, 50, 0));
			body.setAlignment(Pos.TOP_CENTER);
			
			ImageView logOutGraphic = new ImageView(new Image("Resources/buttons/logout.png"));
			logOutGraphic.setFitWidth(32);
			logOutGraphic.setFitHeight(32);
			Button logOut = new Button("Log out",
				ImageGetter.getImage("Resources/buttons/logout.png", 32, 32));
			logOut.setId("logOutBtn");
			addHead();
			setBottom(logOut);
			setMargin(logOut, new Insets(70, 0, 10, 0));
			setCenter(body);
			logOut.setOnAction(e->{
				System.out.println("LogginOut");
				if(user.getPermission() == PermissionLevel.CASHIER) {
					if(!CashierView.isEmpty()) {
						new Alert(AlertType.ERROR,
								"There are items alredy added to the bill. Choose what you will do with them.").showAndWait();
						return;
					}
				}
				Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to log out?", ButtonType.YES, ButtonType.NO);
				Optional<ButtonType> butons = alert.showAndWait();
					if(butons.get() == ButtonType.YES) 
						new LogIn().setView(stage);
				
			});
		}

		public void addButton(Pane pane, String btnText, ImageView graphic) {
			Button btn = new Button(btnText,graphic);
			initButton(btn, pane);
		}
		
		
		private void initButton(Button btn, Pane pane) {
			btn.setId("sideMenuBtn");
			if(nrButtons == 0) {
				selectedBtn = btn;
				btn.setId("selectedMenuBtn");
				changeRightSide(pane);
			}
			body.getChildren().add(nrButtons++, btn);
			btn.setOnAction(e -> {
			 if(selectedBtn != btn) {
				selectedBtn.setId("sideMenuBtn");
				btn.setId("selectedMenuBtn");
	 			selectedBtn = btn;
				changeRightSide(pane);
			 }
			});
		}
		
		private void addHead() {
			VBox rect = new VBox(5);
			rect.setAlignment(Pos.CENTER);
			Circle pic = new Circle(64, Color.WHITE);
			rect.setPrefWidth(300);
			Label label = new Label();
			String text = "";
			switch(user.getPermission()) {
			case CASHIER:
				text += "CASHIER";
				pic.setFill(new ImagePattern(new Image("Resources/cashier.png")));
				break;
			case MANAGER:
				text += "MANAGER";
				pic.setFill(new ImagePattern(new Image("Resources/man.png")));
				break;
			case ADMINISTRATOR:
				text += "ADMINSTRATOR";
				pic.setFill(new ImagePattern(new Image("Resources/admin.png")));
				break;
			}
			text += "\n" + user.getName() + " " + user.getSurname();
			label.setText(text);
			label.setTextAlignment(TextAlignment.CENTER);
			label.setFont(Font.font("", FontWeight.SEMI_BOLD, 20));
			label.setTextFill(Color.WHITE);
			rect.getChildren().addAll(pic, label);
			setTop(rect);
			setMargin(rect, new Insets(10, 0, 70, 0));
		}
		
	}
}

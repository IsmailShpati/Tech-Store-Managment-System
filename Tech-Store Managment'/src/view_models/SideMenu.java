package view_models;

import java.util.Optional;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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
import models.User;
import views.LogIn;

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
	
	public void addButton(Pane pane, String btnText) {
		sideMenu.addButton(pane, btnText);
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
			Button logOut = new Button("Log out");
			logOut.setId("logOutBtn");
			addHead();
			setBottom(logOut);
			setMargin(logOut, new Insets(70, 0, 10, 0));
			setCenter(body);
			logOut.setOnAction(e->{
				System.out.println("LogginOut");	
				Alert alert = new Alert(AlertType.CONFIRMATION, "Wanna log out?", ButtonType.YES, ButtonType.NO);
				Optional<ButtonType> butons = alert.showAndWait();
				if(butons.get() == ButtonType.YES)
					new LogIn().setView(stage);
			});
		}
		
		public void addButton(Pane pane, String btnText) {
			Button btn = new Button(btnText);
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

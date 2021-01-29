package view_models;

import java.util.Optional;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import views.LogIn;

public class SideMenu extends HBox {
	private SidePannel sideMenu = new SidePannel();
	private Pane rightSide = new Pane();
	private Stage stage;
	
	public SideMenu(Stage stage) {
		this.stage = stage;
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
			Circle pic = new Circle(50, Color.WHITE);
			rect.setPrefSize(300, 150);
			Label text = new Label("Manager");
			text.setFont(Font.font("", FontWeight.SEMI_BOLD, 20));
			text.setTextFill(Color.WHITE);
			rect.getChildren().addAll(pic, text);
			setTop(rect);
			setMargin(rect, new Insets(10, 0, 20, 0));
		}
		
	}
}

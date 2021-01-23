package models;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class SideMenu extends HBox {
	private SidePannel sideMenu = new SidePannel();
	private Pane rightSide = new Pane();
	
	
	public SideMenu() {
		getStylesheets().add("SideMenu.css");
		setSpacing(40);
		//this.rightSide = rightSide;
		getChildren().addAll(sideMenu, this.rightSide);
	}
	
	public void changeRightSide(Pane pane) {
		this.rightSide = pane;
		getChildren().remove(1);
		getChildren().add(rightSide);
	}
	
	public void addButton(Pane pane, String btnText) {
		sideMenu.addButton(pane, btnText);
	}
	
	
	//Inner class
	class SidePannel extends VBox{
		private int nrButtons = 0;
		private Button selectedBtn = new Button();
		public SidePannel() {
			setPrefWidth(300);
			setSpacing(40);
			setId("menuPannel");
			setPadding(new Insets(50, 0, 50, 0));
			setAlignment(Pos.TOP_CENTER);
			//getStylesheets().addAll("menuPannel", "sideMenuBtn");
			Button logOut = new Button("Log out");
			logOut.setId("sideMenuBtn");
			getChildren().add(logOut);
			logOut.setOnAction(e->{
				System.out.println("LogginOut");
			Alert alert = new Alert(AlertType.CONFIRMATION, "Wanna log out?", ButtonType.YES, ButtonType.NO);
			alert.show();
		
		
			});
		}
		
		public void addButton(Pane pane, String btnText) {
			Button btn = new Button(btnText);
			btn.setId("sideMenuBtn");
			getChildren().add(nrButtons++, btn);
			btn.setOnAction(e -> {
			 if(selectedBtn != btn) {
				selectedBtn.setId("sideMenuBtn");
				btn.setId("selectedMenuBtn");
	 			selectedBtn = btn;
				changeRightSide(pane);
			 }
			});
		}
		
	}
}

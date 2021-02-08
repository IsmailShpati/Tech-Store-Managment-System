package views;

import java.util.Optional;

import interfaces.Viewable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.Administrator;
import view_models.AddUserView;
import view_models.AdminStatistics;
import view_models.ImageGetter;
import view_models.SideMenu;
import view_models.StockAdmin;
import view_models.UserStatistics;
import view_models.UsersView;

public class AdministratorView implements Viewable{

	private Administrator user;
	public AdministratorView(Administrator user) {
		this.user = user;
	}
	
	@Override
	public void setView(Stage stage) {
		new AdministratorPannel(stage).setView();
		stage.centerOnScreen();
		stage.setOnCloseRequest(e->{
			Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to close the application?",
					ButtonType.YES, ButtonType.NO);
			Optional<ButtonType> butons = alert.showAndWait();
			if(butons.get() == ButtonType.NO) {
				e.consume();
			}
		});
	}
	
	public class AdministratorPannel extends BorderPane{
		private Stage stage;
		private SideMenu menu;
		private AddUserView addUser;
		private MenuBar menuBar = new MenuBar();
		private UsersView usersView;
		public AdministratorPannel(Stage stage) {
			this.stage = stage;
			usersView = new UsersView();
			addUser = new AddUserView(this);
			setCenter(usersView);
			initMenuBar();
			setBottom(menuBar);
			initMenu();
		}
		public void refresh() {
			usersView.refresh();
		}
		
		private void initMenu() {
			menu = new SideMenu(stage, user);
			menu.addButton(this, "Users",
					ImageGetter.getImage("Resources/buttons/users.png", 32, 32));
			menu.addButton(new AdminStatistics(), "Income",
					ImageGetter.getImage("Resources/buttons/sales.png", 40, 40));

			menu.addButton(new UserStatistics(), "Employee Statistics",
					ImageGetter.getImage("Resources/buttons/barChart.png", 32, 32));

			menu.addButton(new StockAdmin(menu), "Stock",
					ImageGetter.getImage("Resources/buttons/stock.png", 38, 38));

		}
		
		public void setView() {
			stage.setTitle("Admin view");
			stage.setScene(new Scene(menu));
		}
		
		private void initMenuBar() {
			Label newLabel = new Label("New",
					ImageGetter.getImage("Resources/buttons/add_white.png", 20, 20));
			newLabel.setOnMouseClicked(e -> {
				menu.changeRightSide(addUser);
			});
			
			Label deleteLabel = new Label("Delete",
					ImageGetter.getImage("Resources/buttons/delete_white.png", 20, 20));

			deleteLabel.setOnMouseClicked(e->{
				usersView.delete();
			});
			
			menuBar.getMenus().addAll(new Menu("", newLabel), new Menu("", deleteLabel));
		}
		
		public void returnBack() {
			menu.changeRightSide(this);
		}
		
	}

}

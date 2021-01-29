package views;

import interfaces.Viewable;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import view_models.AddUserView;
import view_models.SideMenu;

public class AdministratorView implements Viewable{

	

	@Override
	public void setView(Stage stage) {
		new AdministratorPannel(stage).setView();
		
	}
	
	class AdministratorPannel extends BorderPane{
		private Stage stage;
		private SideMenu menu;
		private AddUserView addUser = new AddUserView();
		
		public AdministratorPannel(Stage stage) {
			this.stage = stage;
			setCenter(addUser);
			initMenu();
		}
		
		private void initMenu() {
			menu = new SideMenu(stage);
			menu.addButton(this, "Add user");
		}
		
		public void setView() {
			stage.setTitle("Admin view");
			stage.setScene(new Scene(menu));
		}
		
		
		
	}

}

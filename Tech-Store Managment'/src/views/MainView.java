package views;

import javafx.application.Application;
import javafx.stage.Stage;
import models.Cashier;
import models.PermissionLevel;



public class MainView extends Application{

	public static void main(String[] args) { launch(args); }
	@Override
	public void start(Stage stage) throws Exception {
		Cashier c = new Cashier("Jeff", "j", PermissionLevel.CASHIER, "I", "S");
		new CashierView(c).setView(stage);
		//new ManagerView().setView(stage);
		//new AdministratorView().setView(stage);
		stage.show();
	}

}

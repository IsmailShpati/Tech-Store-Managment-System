package views;

import javafx.application.Application;
import javafx.stage.Stage;



public class MainView extends Application{

	public static void main(String[] args) { launch(args); }
	@Override
	public void start(Stage stage) throws Exception {
		new LogIn().setView(stage);
		//new AdministratorView().setView(stage);
		//new ManagerView().setView(stage);
		stage.show();
	}

}

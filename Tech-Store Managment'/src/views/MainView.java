package views;

import javafx.application.Application;
import javafx.stage.Stage;



public class MainView extends Application{

	public static void main(String[] args) { launch(args); }
	@Override
	public void start(Stage stage) throws Exception {
		
		new CashierView().setView(stage);
		stage.show();
	}

}

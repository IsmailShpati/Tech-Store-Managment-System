package views;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;



public class MainView extends Application{

	public static void main(String[] args) { launch(args); }
	@Override
	public void start(Stage stage) throws Exception {
		new LogIn().setView(stage);
		stage.show();
		stage.getIcons().add(new Image
				("Resources/tech-icon.png"));
	}

}

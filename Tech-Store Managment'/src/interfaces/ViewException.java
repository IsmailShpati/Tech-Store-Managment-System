package interfaces;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ViewException extends Exception {

	private static final long serialVersionUID = 450714705226975601L;

	private AlertType type;
	public ViewException(String msg, AlertType type) {
		super(msg);
		this.type = type;
	}
	
	public void showAlert() {
		new Alert(type, getMessage()).showAndWait();
	}
	
	public static void showAlert(String msg, AlertType type) {
		new Alert(type, msg).showAndWait();
	}
}

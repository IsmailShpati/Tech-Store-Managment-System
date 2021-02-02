package view_models;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class NumberField extends TextField {

	private boolean dotPressed = false;
	public NumberField() {
		setOnKeyReleased(event ->{
			String charTyped = event.getText();
			char c = charTyped.length() > 0 ? charTyped.charAt(0) : '1';
			if(!dotPressed && c == '.')
				dotPressed = true;
			else if((c > 57 || c < 48)){
				new Alert(AlertType.ERROR, "Only valid numbers allowed");
				deletePreviousChar();
			}
		});
	}
	
	public double getValue() {
		try {
			return Double.parseDouble(getText());
		}catch(NumberFormatException e) {
			//new Alert(AlertType.ERROR, "Only valid numbers allowed");
		}
		return 0;
	}
}

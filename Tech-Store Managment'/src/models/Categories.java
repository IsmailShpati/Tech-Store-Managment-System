package models;

import java.util.ArrayList;

import interfaces.ViewException;
import javafx.scene.control.Alert.AlertType;

public class Categories {
	public static ArrayList<String> categoriesAvaible = new ArrayList<>();
	
	static {
		//TODO read from file and initialize categories
		categoriesAvaible.add("CEMBRA");
	}
	
	
	
	public static void addCategory(String category) throws ViewException {
		
		for(String s : categoriesAvaible)
			if(s.equals(category))
				throw new ViewException("Category alredy exists.", AlertType.WARNING);
		categoriesAvaible.add(category);
		
	}
	
	
	
	
}

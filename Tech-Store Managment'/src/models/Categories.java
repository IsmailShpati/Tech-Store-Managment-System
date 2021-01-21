package models;

import java.util.ArrayList;

public class Categories {
	public static ArrayList<String> categoriesAvaible = new ArrayList<>();
	
	static {
		//TODO read from file and initialize categories
		categoriesAvaible.add("CEMBRA");
	}
	
	
	
	public static boolean addCategory(String category) {
		
		for(String s : categoriesAvaible)
			if(s.equals(category))
				return false;
		categoriesAvaible.add(category);
		return true;
	}
	
	
	
	
}

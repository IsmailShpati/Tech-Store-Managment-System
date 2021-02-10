package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import interfaces.ViewException;
import javafx.scene.control.Alert.AlertType;

@SuppressWarnings("unchecked")
public class CategorieController {
	
	
	
	private static ArrayList<String> categories = new ArrayList<>();
	private static final File path = new File("databases/Stock Database/categories.dat");
	static {
		//TODO read from file and initialize categories
	
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
			categories = (ArrayList<String>)in.readObject();
			in.close();
		}  catch (Exception e) {
			System.err.println("[CategorieController]Reading problem");
		}
		
	}
	
	public static ArrayList<String> getCategories(){
		return categories;
	}
	
	
	public static void addCategory(String category) throws ViewException {
		exists(category);
		categories.add(category);
		save();
	}
	
	public static void deleteCategory(String category) {
		categories.remove(category);
		save();
	}
	
	public static void editCategory(int index, String newCategory) throws ViewException{
		exists(newCategory);
		categories.set(index, newCategory);
		save();
	}
	
	private static void exists(String category) throws ViewException{
		for(String s : categories)
			if(s.equals(category))
				throw new ViewException("Category " + s + " alredy exists.", AlertType.WARNING);
	}
	
	public static String reset() {
		if(categories.size() > 0)
			return categories.get(0);
		return null;
	}
	
	private static void save() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
			out.writeObject(categories);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

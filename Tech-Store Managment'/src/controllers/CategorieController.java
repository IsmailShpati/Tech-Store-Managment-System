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
import models.Category;

@SuppressWarnings("unchecked")
public class CategorieController {
	
	
	
	private static ArrayList<Category> categoriesObservable = new ArrayList<>();
	private static final File path = new File("databases/Stock Database/categories.dat");
	static {
		//TODO read from file and initialize categories
	
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
			categoriesObservable = (ArrayList<Category>)in.readObject();
			in.close();
		}  catch (Exception e) {
			System.err.println("[CategorieController]Reading problem");
		}
		
	}
	
	public static ArrayList<Category> getCategories(){
		return categoriesObservable;
	}
	
	
	public static void addCategory(String category) throws ViewException {
		for(Category s : categoriesObservable)
			if(s.getCategory().equals(category))
				throw new ViewException("Category " + s + " alredy exists.", AlertType.WARNING);
		categoriesObservable.add(new Category(category));
		save();
	}
	
	public static Category reset() {
		if(categoriesObservable.size() > 0)
			return categoriesObservable.get(0);
		return null;
	}
	
	
	private static void save() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
			out.writeObject(categoriesObservable);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}

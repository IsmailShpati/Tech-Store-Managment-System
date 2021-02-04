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
import models.Supplier;

@SuppressWarnings("unchecked")
public class SupplierController {

	
	private static ArrayList<Supplier> suppliers = new ArrayList<>();
	
	private static final File path = new File("databases/Stock Database/suppliers.dat");
	static {
		//TODO read from file and initialize categories
	
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
			suppliers = (ArrayList<Supplier>)in.readObject();
			in.close();
		}  catch (Exception e) {
			System.err.println("[SupplierController]File empty\n" + e.getMessage());
			
		}
		
	}
	
	public static ArrayList<Supplier> getSuppliers(){
		return suppliers;
	}
	
	public static void addSupplier(Supplier s) throws ViewException {
		for(Supplier sup : suppliers)
			if(sup.equals(s))
				throw new ViewException("Supplier alredy exists", AlertType.ERROR);
		suppliers.add(s);
		save();
	}
	
	public static void deleteSupplier(Supplier s) {
		suppliers.remove(s);
		save();
	}

	
	public static Supplier reset() {
		if(suppliers.size() > 0)
			return suppliers.get(0);
		return null;
	}
	
	
	private static void save() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
			out.writeObject(suppliers);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

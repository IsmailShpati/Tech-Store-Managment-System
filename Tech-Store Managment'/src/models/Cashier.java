package models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;

public class Cashier extends User {

	private ArrayList<Bill> bills = new ArrayList<>();
	private String name, surname;
	private File file;
	private String dbPath;
	
	public Cashier(String username, String password, PermissionLevel permissionLevel
			,String name, String surname) {
		super(username, password, permissionLevel);
		this.name = name;
		this.surname = surname;
		
		initFile();
		 
	}
	
	@SuppressWarnings("unchecked")
	private void initFile() {
		dbPath = "databases/Cashier Database/" + name + surname; 
		file = new File(dbPath);
		if(!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.err.println("Error creating new file Cashier" + e.getMessage());
			}
		else {
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
				
				bills = (ArrayList<Bill>) ois.readObject();
				
				ois.close();
			} catch ( IOException |ClassNotFoundException e) {
				System.err.println("Error reading from file Cashier" +  e.getMessage());;
			} 
		}
	}
	
	
	public void addBill(Bill b) {
		bills.add(b);
		saveChange();
	}
	
	
	private void saveChange() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(bills.get(bills.size()-1));
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	
	public ArrayList<Bill> getBillsInPeriod(LocalDate start, LocalDate end){
		ArrayList<Bill> billsInPeriod = new ArrayList<>();
		for(Bill b : bills) {
			if(start.compareTo(b.getDate().toLocalDate()) < 0 && 
					end.compareTo(b.getDate().toLocalDate()) > 0)
				billsInPeriod.add(b);		
		}
		
		
		return null;
	}

}

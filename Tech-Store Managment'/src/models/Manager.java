package models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;

public class Manager extends User {

	private static final long serialVersionUID = -1072318023215336986L;
	
	private ArrayList<PurchaseBill> purchases = new ArrayList<>();
	private String dbPath;
	private File file;
	
	public Manager(String username, String password,String name, String surname,
			  PermissionLevel permissionLevel, double salary, LocalDate birthday ) {
			super(username, password, name, surname, permissionLevel, salary, birthday);
			
		initFile();
	}

	@SuppressWarnings("unchecked")
	private void initFile() {
		dbPath = "databases/Manager Database/" + getName() + getSurname(); 
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
				purchases = (ArrayList<PurchaseBill>) ois.readObject();
				ois.close();
			} catch ( IOException |ClassNotFoundException e) {
				System.err.println("Error reading from file Cashier" +  e.getMessage());;
			} 
		}
	}
	
	public void addPurchase(PurchaseBill pb) {
		purchases.add(pb);
		saveChange();
	}
	
	private void saveChange() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(purchases.get(purchases.size()-1));
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public double getTotalPurchasesDate(LocalDate start, LocalDate end) {
		double purchasesMade = 0;
		for(PurchaseBill p : purchases) {
			if(start.compareTo(p.getPurchaseDate()) <= 0 && 
					end.compareTo(p.getPurchaseDate()) >= 0) 
				purchasesMade += p.getBillPrice();
		}
		return purchasesMade;
	}
	
	public double getTotalPurchaseDate(LocalDate date) {
		double totalPurchases = 0;
		for(PurchaseBill p : purchases) {
			if(date.compareTo(p.getPurchaseDate()) == 0)
				totalPurchases += p.getBillPrice();
		}
		return totalPurchases;
	}

	public double getTotalPurchases() {
		double total = 0;
		for(PurchaseBill p : purchases)
			total += p.getBillPrice();
		return total;
	}
}

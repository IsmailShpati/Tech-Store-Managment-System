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

	private static final long serialVersionUID = 5195613300800700695L;

	private ArrayList<Bill> bills = new ArrayList<>();
	private File file;
	private String dbPath;
	
	public Cashier(String username, String password,String name, String surname,
		  PermissionLevel permissionLevel, double salary, LocalDate birthday ) {
		super(username, password, name, surname, permissionLevel, salary, birthday);
		
		initFile();
		 
	}
	
	@SuppressWarnings("unchecked")
	private void initFile() {
		dbPath = "databases/Cashier Database/" + getName() + getSurname(); 
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
			oos.writeObject(bills);
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
	
	public ArrayList<Bill> getBillsInDate(LocalDate date){
		ArrayList<Bill> billsInDate = new ArrayList<>();
		for(Bill b : bills) {
			if(date.compareTo(b.getDate().toLocalDate()) == 0)
				billsInDate.add(b);
		}
		return billsInDate;
	}
	
	public double getTotalIncome() {
		double income = 0;
		for(Bill b : bills)
			income += b.getTotalBillPrice();
		return income;
	}
}

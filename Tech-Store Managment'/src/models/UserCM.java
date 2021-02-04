package models;

import java.time.LocalDate;
import java.util.ArrayList;

import controllers.UserController;
import interfaces.StatisticBill;

public abstract class UserCM<T extends StatisticBill> extends User {
	//User - cashier and manager
	private static final long serialVersionUID = 7451340318639271154L;

	private ArrayList<T> bills = new ArrayList<>();
	
	public UserCM(String username, String password, String name, String surname, PermissionLevel permissionLevel,
			double salary, LocalDate birthday, String phoneNumber) {
		super(username, password, name, surname, permissionLevel, salary, birthday, phoneNumber);
	}
	
	public void addBill(T b) {
		bills.add(b);
		UserController.save();
	}
	
	public ArrayList<T> getBills() { return bills; }
	
	
	public ArrayList<T> getBillsInPeriod(LocalDate start, LocalDate end){
		ArrayList<T> billsInPeriod = new ArrayList<>();
		for(T b : bills) {
			if(start.compareTo(b.getDate().toLocalDate()) <= 0 && 
					end.compareTo(b.getDate().toLocalDate()) >= 0)
				billsInPeriod.add(b);		
		}
		
		return billsInPeriod;
	}
	
	public ArrayList<T> getBillInDate(LocalDate date){
		ArrayList<T> billsInDate = new ArrayList<>();
		for(T b : bills) 
			if(date.compareTo(b.getDate().toLocalDate()) == 0)
				billsInDate.add(b);
		
		return billsInDate;
	}

	
	public double getTotal(LocalDate start, LocalDate end) {
		double total = 0;
		for(T b : bills) {
			if(start.compareTo(b.getDate().toLocalDate()) <= 0 && 
					end.compareTo(b.getDate().toLocalDate()) >= 0)
			total += b.getTotal();
		}
		
		return total;
	}
	
}

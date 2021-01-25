package models;

import java.util.ArrayList;

public class Cashier extends User {

	private ArrayList<Bill> bills = new ArrayList<>();
	
	public Cashier(String username, String password, PermissionLevel permissionLevel) {
		super(username, password, permissionLevel);
		//Read bills from the database
		
	}

}

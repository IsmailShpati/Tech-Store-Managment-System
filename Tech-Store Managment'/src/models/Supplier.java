package models;

import java.io.Serializable;
import java.util.ArrayList;

public class Supplier implements Serializable{

	private static final long serialVersionUID = 3888450302923422125L;
	
	private String name;
	private String phoneNumber;
	private ArrayList<String> offeredItems;
	public Supplier(String name, String phoneNumber, ArrayList<String> arrayList) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		offeredItems = arrayList;
	}
	
	

	public String getName() { return name; }
	public String getPhoneNumber() { return phoneNumber; }
	public ArrayList<String> getOfferedItems() { return offeredItems; }
	
	
	public boolean equals(Supplier s) {
		if(s.getName().equals(name) && s.getPhoneNumber().equals(phoneNumber))
			return true;
		return false;
	}
}

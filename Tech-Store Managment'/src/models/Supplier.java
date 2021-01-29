package models;

import java.io.Serializable;

public class Supplier implements Serializable{

	private static final long serialVersionUID = 3888450302923422125L;
	
	private String name;
	private String phoneNumber;
	
	public Supplier(String name, String phoneNumber) {
		this.name = name;
		this.phoneNumber = phoneNumber;
	}
	
	public String getName() { return name; }
	public String getPhoneNumber() { return phoneNumber; }
	
	public boolean equals(Supplier s) {
		if(s.getName().equals(name) && s.getPhoneNumber().equals(phoneNumber))
			return true;
		return false;
	}
}

package models;

public class Supplier {

	private String name;
	private String phoneNumber;
	
	public Supplier(String name, String phoneNumber) {
		this.name = name;
		this.phoneNumber = phoneNumber;
	}
	
	public String getName() { return name; }
	public String getPhoneNumber() { return phoneNumber; }
}

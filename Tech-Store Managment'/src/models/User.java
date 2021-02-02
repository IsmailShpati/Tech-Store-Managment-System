package models;

import java.io.Serializable;
import java.time.LocalDate;

import controllers.UserController;

public abstract class User implements Serializable {

	private static final long serialVersionUID = 3013305226201820630L;
	
	private String username, password;
	private String name, surname;
	private PermissionLevel permissionLevel;
	private double salary;
	private LocalDate birthday;
	
	

	private String phoneNumber;
	
	public User(String username, String password, String name, String surname,
			PermissionLevel permissionLevel, double salary, LocalDate birthday, String phoneNumber) {
		this.username = username;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.name = name;
		this.surname = surname;
		this.permissionLevel = permissionLevel;
		this.salary = salary;
		this.birthday = birthday;
	}


	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public PermissionLevel getPermission() {
		return permissionLevel;
	}
	
	//Used for table column
	public String getPermissionLevel() {
		return permissionLevel.toString();
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public String getSalary() { return salary+""; }
	public double getsalary() { return salary; }

	public String getName() { return name; }
	public String getSurname() { return surname; }
	
	public LocalDate getBirthday() { return birthday; }
	
	public boolean verify(String username, String password)  {
		if(this.username.equals(username) && this.password.equals(password))
			return true;
		return false;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}


	public void setSalary(double salary) {
		this.salary = salary;
	}


	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@Override
	public String toString() {
		return name + " " + surname;
	}
}


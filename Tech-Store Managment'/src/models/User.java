package models;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class User implements Serializable {

	private static final long serialVersionUID = 3013305226201820630L;
	
	private String username, password;
	private String name, surname;
	private PermissionLevel permissionLevel;
	private double salary;
	private LocalDate birthday;
	
	public User(String username, String password, String name, String surname,
			PermissionLevel permissionLevel, double salary, LocalDate birthday) {
		this.username = username;
		this.password = password;
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

	public PermissionLevel getPermissionLevel() {
		return permissionLevel;
	}
	
	public double getSalary() { return salary; }

	public String getName() { return name; }
	public String getSurname() { return surname; }
	
	public LocalDate getBirthday() { return birthday; }
	
	public boolean verify(String username, String password)  {
		if(this.username.equals(username) && this.password.equals(password))
			return true;
		return false;
	}
}


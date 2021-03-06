package models;

import java.time.LocalDate;

public class Administrator extends User {

	private static final long serialVersionUID = 4705226404061259886L;

	public Administrator(String username, String password, String name, 
			String surname,
			double salary, LocalDate birthday, String phoneNumber) {
		super(username, password, name, surname, PermissionLevel.ADMINISTRATOR, salary, birthday, phoneNumber);
		
	}

}

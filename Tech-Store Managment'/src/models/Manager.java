package models;

import java.time.LocalDate;

public class Manager extends UserCM<PurchaseBill> {

	public Manager(String username, String password, String name, String surname,
			double salary, LocalDate birthday, String phoneNumber) {
		super(username, password, name, surname, PermissionLevel.MANAGER, 
				salary, birthday, phoneNumber);
	}

	private static final long serialVersionUID = -1072318023215336986L;
}

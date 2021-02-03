package models;

import java.time.LocalDate;

public class Cashier extends UserCM<Bill> {

	private static final long serialVersionUID = 5195613300800700695L;
	
	public Cashier(String username, String password,String name, String surname,
		 double salary, LocalDate birthday, String phoneNumber ) {
		super(username, password, name, surname, PermissionLevel.CASHIER, salary, birthday, phoneNumber);

	}

	public int getNrBillsInPeriod(LocalDate start, LocalDate end) {
		int cnt = 0;
		for(Bill b : getBills()) {
			if(start.compareTo(b.getDate().toLocalDate()) <= 0 && 
					end.compareTo(b.getDate().toLocalDate()) >= 0)
				cnt++;		
		}
		return cnt;
	}
	
	

	
}

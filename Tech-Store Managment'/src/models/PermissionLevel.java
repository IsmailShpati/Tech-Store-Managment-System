package models;

public enum PermissionLevel {
	CASHIER, 
	MANAGER, 
	ADMINISTRATOR;
	
//	public static PermissionLevel getPermissionLevel(int num) {
//		switch(num) {
//		case 0:
//			return CASHIER;
//		case 1:
//			return MANAGER;
//		case 2:
//			return ADMINISTRATOR;
//		default:
//			return null;
//		}
//	}
//	
//	
	public String toString() {
		switch(this) {
		case CASHIER:
			return "CASHIER";
		case MANAGER:
			return "MANAGER";
		case ADMINISTRATOR:
			return "ADMINISTRATOR";
		default:
			return null;
		}
	}
	
}

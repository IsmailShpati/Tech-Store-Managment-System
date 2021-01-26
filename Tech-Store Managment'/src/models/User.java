package models;

public abstract class User {

	private String username, password;
	private PermissionLevel permissionLevel;

	public User(String username, String password, PermissionLevel permissionLevel) {
		this.username = username;
		this.password = password;
		this.permissionLevel = permissionLevel;
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
	
	
}


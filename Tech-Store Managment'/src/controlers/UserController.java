package controlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;

import interfaces.ViewException;
import javafx.scene.control.Alert.AlertType;
import models.Cashier;
import models.Manager;
import models.PermissionLevel;
import models.User;

@SuppressWarnings("unchecked")
public class UserController {

	private static ArrayList<User> users = new ArrayList<>();
	private static final File db = new File("databases/UserDB.dat");
	
	static {
		//Read from the database all users
//		Cashier c = new Cashier("cashier", "1234", "c", "c", PermissionLevel.CASHIER, 129, LocalDate.now());
//		Manager m = new Manager("manager", "1234", "m", "m", PermissionLevel.MANAGER, 129,LocalDate.now());
//		users.add(m);
//		users.add(c);
//		Manager d = new Manager("ili", "1234", "e", "e", PermissionLevel.MANAGER, 129,LocalDate.now());
//		users.add(d);
		
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(db));
			users = (ArrayList<User>)in.readObject();
			in.close();
		} catch (IOException e) {
			System.err.println("[UserController]Problem reading from file");
		} catch (ClassNotFoundException e) {
			System.err.println("[UserController]Class not found");
		}
	}
	
	public static User logIn(String username, String password) throws ViewException{
		for(User u : users) {
				if(u.verify(username, password))
					return u;
		}
		
		throw new ViewException("User with those credentials doesn't exist",
				AlertType.ERROR);

	}
	
	public static void addUser(User u) throws ViewException {
		for(User current : users) {
			equals(current, u); //It will throw the exception
				
		}
		users.add(u);
		save();
		for(User user : users) {
			System.out.println(user.getUsername() + " " + user.getPassword());
		}
	}
	
	
	
	private static boolean equals(User current, User newU) throws ViewException {
		if(current.getUsername().equals(newU.getUsername()))
			throw new ViewException("Username alredy exists", AlertType.ERROR);
		if(current.getPermissionLevel() == newU.getPermissionLevel())
			if(current.getUsername().equals(newU.getUsername()))
				if(current.getName().equals(newU.getName()))
					if(current.getSurname().equals(newU.getSurname()))
						throw new ViewException("User with the same name and surname alredy exists", 
								AlertType.ERROR);
		return false;
	}

	public static void removeCashier(User u) {
		for(User user : users) {
			try {
				if(equals(u, user)){
					users.remove(user);
					save();
				}
			} catch (ViewException e) {
				e.showAlert();
			}
		}
	}
	
	public static ArrayList<Cashier> getCashiers(){
		ArrayList<Cashier> cashiers = new ArrayList<>();
		for(User u : users)
			if(u.getPermissionLevel() == PermissionLevel.CASHIER)
				cashiers.add((Cashier)u);
		return cashiers;
	}
	
	public static ArrayList<Manager> getManagers(){
		ArrayList<Manager> cashiers = new ArrayList<>();
		for(User u : users)
			if(u.getPermissionLevel() == PermissionLevel.MANAGER)
				cashiers.add((Manager)u);
		return cashiers;
	}
	
 	public static void save() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(db));
			out.writeObject(users);
			out.close();
		} catch (IOException e) {
			System.err.println("[UserController]problems with save");
		}
	}
}

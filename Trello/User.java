import java.util.*;
import java.io.*;
class User implements Serializable{
	int id;
	String name;
	String email;
	
	User(int userID, String name, String email){
		this.id = userID;
		this.name = name;
		this.email = email;
	}

	public String toString(){
		String temp = String.format("\n\t| %-10d | %-15s | %-30s |", id, name, email);
		return temp;
	}
	
}
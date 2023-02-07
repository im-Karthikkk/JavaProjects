import java.util.*;
import java.io.*;
class Card implements Serializable{
	int id;
	String name;
	String description;
	String assignedUser;
	
	Card(int cardID, String name){
		this.id = cardID;
		this.name = name;
		description = "";
		assignedUser = "Unassigned";
	}
	public String toString(){
		String temp = String.format("\n\t| %-10d | %-15s | %-30s | %-15s |", id, name, description, assignedUser);
		return temp;
	}
	
}
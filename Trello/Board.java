import java.util.*;
import java.io.*;
class Board implements Serializable{
	
	int id;
	String name;
	String privacy;
	HashMap<Integer, User> members;		// userID, UserID
	HashMap<Integer, MyList> lists;		// listID, ListID
	
	Board(int id, String name){
		this.id = id;
		this.name = name;
		this.privacy = "Public";
		members = new HashMap<>();
		lists = new HashMap<>();
	}
	
	public String toString(){
		String temp = "\n\tBoard ID: "+id+"\t\t Name: "+name+"\t Privacy: "+privacy;
		if(!members.isEmpty()){
			temp += "\n\tMembers: ";
			temp += "\n\t+------------+-----------------+--------------------------------+";
			temp += String.format("\n\t| %-10s | %-15s | %-30s |", "User ID", "Name", "Email");
			temp += "\n\t+------------+-----------------+--------------------------------+";
			for(User u: members.values()){
				temp += u.toString();
				temp += "\n\t+------------+-----------------+--------------------------------+";
			}
			temp += "\n";
		}
		if(!lists.isEmpty()){
			for(MyList l: lists.values()){
				temp += l.toString();
				temp += "\n";
			}
		}
		return temp;
	}
}
import java.util.*;
import java.io.*;
class User implements Serializable{
	private static final long serialVersionUID = 927l;
	
	String name;
	String number;
	String password;
	HashMap<Integer, Ticket> bookingHistory;
	
	User(String name, String number, String password){
		this.name = name;
		this.number = number;
		this.password = password;
		this.bookingHistory = new HashMap<>();
	}
}
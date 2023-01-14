import java.io.*;
import java.util.*;
class User implements Serializable{
	private static final long serialVersionUID = 921l;
	String userID;
	String name;
	String password;
	ArrayList<Book> holding;
	
	User(String userID, String name, String password){
		this.userID = userID;
		this.name = name;
		this.password = password;
		this.holding = new ArrayList<>();
		createFile(userID);
	}
	public void createFile(String userID){
		try{
			File f = new File(userID+".txt");
			f.createNewFile();
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(String.format("%15s%10s%15s%20s", "Copy ID", "Book ID", "Due Date", "Operation\n").getBytes());
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public void writeOnFile(String s){
		try{
		FileOutputStream fos = new FileOutputStream(new File(userID+".txt"), true);
		fos.write(s.getBytes());
		}catch(Exception e){e.printStackTrace();}
	}
}
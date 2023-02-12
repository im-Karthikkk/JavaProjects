import java.io.*;
class Admin implements Serializable{
	private static final long serialVersionUID = 921l;
	String adminID;
	String name;
	String password;
	
	Admin(String adminID, String name, String password){
		this.adminID = adminID;
		this.name = name;
		this.password = password;
	}
}
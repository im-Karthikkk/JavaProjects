import java.util.*;
import java.io.*;
class Agent implements Serializable{
	
	private String email;
	private String password;
	private int agentId;
	
	Agent(String email, String password, int id){
		this.email = email;
		this.password = password;
		this.agentId = id;
	}
	int getId(){return this.agentId;}
	boolean verifyEmail(String email){return this.email.equals(email);}
	boolean verifyPassword(String password){return this.password.equals(password);}
}
import java.util.*;
import java.io.*;
class Customer{
	static int idGen = 11; 
	int transID=1;
	int custID;
	int accountNo;
	String name;
	int transactionCount;
	private int balance;
	private String pass;
	private String encryptedPass;
	ArrayList<Transaction> transactions;
	LinkedList<String> pastPasswords;
	boolean passwordChanged;
	int transCountForPass;
	
	Customer(int id, int accNo, String name, int bal, String ePass, String pass){
		this.custID = id;
		this.accountNo = accNo;
		this.name =name;
		setBalance(bal);
		setEncryptedPass(ePass);
		setPass(pass);
		transactions = new ArrayList<>();
		pastPasswords = new LinkedList<>();
		pastPasswords.add(pass);
		int transactionCount=0;
		idGen +=11;
		passwordChanged = true;
		transCountForPass = 0;
	}
	Customer(String name, String pass){
		this.name = name;
		setPass(pass);
		transactions = new ArrayList<>();
		pastPasswords = new LinkedList<>();
		pastPasswords.add(pass);
		this.custID = idGen;
		this.accountNo = Integer.parseInt(idGen+"0"+idGen);
		setBalance(10000);
		idGen += 11;
		int transactionCount=0;
		passwordChanged = true;
		transCountForPass = 0;
	}
	
	public void setPass(String pass){
		this.pass = pass;
	}
	public String getPass(){
		return this.pass;
	}
	public void setBalance(int bal){
		this.balance = bal;
	}
	public int getBalance(){
		return this.balance;
	}
	public void setEncryptedPass(String ePass){
		this.encryptedPass = ePass;
	}
	public String getEncryptedPass(){
		return this.encryptedPass;
	}
}
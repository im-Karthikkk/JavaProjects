import java.util.*;
import java.io.*;
class User implements Serializable{
	
	private String name;
	private String email;
	private long number;
	private String H_Id;
	private String password;
	private boolean approved;
	private int ZId;
	private double rcWallet;
	private double zcWallet;
	private HashMap<Integer, Transaction> transactionHistory;
	double commissionRetrieved;
	
	User(String name,String email,long number, String H_id, String password, double initDeposit, int Zid){
		this.name = name;
		this.email = email;
		this.number = number;
		this.H_Id = H_id;
		this.password = password;
		this.ZId = Zid;
		this.rcWallet = initDeposit;
		this.zcWallet = 0;
		this.approved = false;
		this.transactionHistory = new HashMap<>();	
		this.commissionRetrieved = 0;
	}
	
	String getName(){return this.name;}			// getters
	String getEmail(){return this.email;}
	long getNumber(){return this.number;}
	String getHId(){return this.H_Id;}
	int getZId(){return this.ZId;} 
	HashMap<Integer, Transaction> getHistory(){return this.transactionHistory;}
	
	void setPassword(String password){this.password = password;} 	// setter
	void setApprovalStatus(boolean flag){approved = flag;}
	
	boolean verifyEmail(String email){return this.email.equals(email);}
	boolean verifyPassword(String password){return this.password.equals(password);}
	boolean getApprovalStatus(){return this.approved;}
	
	void deposit(double amount){
		rcWallet += amount;
	}
	boolean withdraw(double amount){
		if(rcWallet<amount)return false;
		rcWallet -= amount;
		return true;
	}
	void depositZC(double amount){
		zcWallet += amount;
	}
	boolean withdrawZC(double amount){
		if(zcWallet<amount)return false;
		zcWallet -= amount;
		return true;
	}
	void viewTransactions(){
		if(transactionHistory.isEmpty()){
			System.out.print("\n\tUser has not carried out any transactions yet.");
			return;
		}
		System.out.print("\n\t Name: "+name+"\t\t ZId: "+ZId);
		System.out.print("\n\t+-------+------------+------+------+------------------------------------------+");
		System.out.print("\n\t"+String.format("| %-5s | %-10s | %-4s | %-4s | %-40s |", "ID", "Type", "From", "To", "Transaction Details"));
		System.out.print("\n\t+-------+------------+------+------+------------------------------------------+");
		for(Transaction t: transactionHistory.values())
			System.out.print(t);
		System.out.print("\n\t+-------+------------+------+------+------------------------------------------+");
		System.out.println();
	}
	
	void viewProfile(){
		System.out.print(
		"\n\t Name : "+name+
		"\n\t Email: "+email+
		"\n\t Mobile: "+number+
		"\n\t H_Id: "+H_Id+
		"\n"+
		"\n\t Real currency balance: "+rcWallet+"/-"+
		"\n\t ZCoin wallet         : "+zcWallet+"zc\n\n");
	}
}

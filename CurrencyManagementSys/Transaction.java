import java.util.*;
import java.io.*;
class Transaction implements Serializable{
	
	int transId;
	String type;		// Deposit, Withdraw, Transfer, Conversion
	int from;   		// Zid of sender
	int to;     		// Zid of receiver
	String details;
	
	Transaction(int transId, String type, int from, int to, String details){
		this.transId = transId;
		this.type = type;
		this.from = from;
		this.to = to;
		this.details = details;
	}
	
	public String toString(){
		// | ID    | Type      | From | To   | Details                               |
		String temp = "\n\t"+String.format("| %-5s | %-10s | %-4s | %-4s | %-40s |", transId+"", type, from+"", to+"", details);
		return temp;
	}
}
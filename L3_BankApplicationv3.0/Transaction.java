import java.util.*;
import java.io.*;
class Transaction{
	
	int transID;
	String transType;
	int amount;
	int balance;
	
	Transaction(Customer c){
		this.transID = c.transID;
		c.transID++;
		c.transactionCount++;
		c.transCountForPass++;
		this.transType = "Opening";
		this.amount = 10000;
		this.balance = 10000;
		c.transactions.add(this);
	}
	Transaction(Customer c, String transType, int amount){
		this.transID = c.transID;
		c.transID++;
		c.transactionCount++;
		c.transCountForPass++;
		this.transType = transType;
		this.amount = amount;
		this.balance = c.getBalance();
		c.transactions.add(this);
	}
}
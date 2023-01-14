import java.util.*;
import java.util.regex.*;
import java.io.*;
class Bank{
	ArrayList<Customer> custBase;
	static Scanner sc = new Scanner(System.in);
	Bank(){
		custBase = new ArrayList<>();
	}
	public void initCustBase() throws Exception{				// Create database from file
		File db = new File("bank_db.txt");
		Scanner ip = new Scanner(db);
		int it=0;
		int tempID=0;
		int AccountNo=0;
		String tName = "";
		int balance=0;
		String tempEncPass="";
		String tempPass ="";
		int i=0;  
		String contents = "";
		while(ip.hasNextLine()){
			//contents += (char)(i);
			if(it<5){
				ip.next();
				it++;
			}
			else{
				try{
				switch(it%5){
					case 0:
					tempID = Integer.parseInt(ip.next());break;
					case 1:
					AccountNo = Integer.parseInt(ip.next());break;
					case 2:
					tName = ip.next();break;
					case 3: 
					balance = Integer.parseInt(ip.next());break;
					case 4:
					tempEncPass = ip.next();
					tempPass = decryptPass(tempEncPass);
					Customer c1 = new Customer(tempID, AccountNo, tName, balance, tempEncPass, tempPass);
					this.custBase.add(c1);
					break;
				}}
				catch(NoSuchElementException e){}
				catch(Exception e){
					e.printStackTrace();
				}
				it++;
			}
		}
		String[] arr = contents.split(" ");
	}
	public void addCustomer(){								// Add customer
		Scanner ip = new Scanner(System.in);
		System.out.println("Enter Name");
		String name = ip.next();
		System.out.println("Enter password");
		String pass = ip.next();
		System.out.println("Enter password again");
		String pass2 = ip.next();
		while(!pass.equals(pass2)){
			System.out.println("Passwords didnt match, try again");
			pass = ip.next();
			System.out.println("Enter password again");
			pass2 = ip.next();
		}
		Customer c1= new Customer(name, pass);
		Transaction t1 = new Transaction(c1);
		writeOnCustomer(c1);
		c1.setEncryptedPass(encryptPass(pass));
		custBase.add(c1);
		try{
			FileOutputStream fos = new FileOutputStream("bank_db.txt", true);
			fos.write((c1.custID+"\t\t\t"+c1.accountNo+"\t\t\t"+c1.name+"\t\t\t\t"+c1.getBalance()+"\t\t\t\t"+c1.getEncryptedPass()+"\n").getBytes());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("Customer added successfully");
		System.out.println("Customer ID: "+c1.custID+"\n"+"Account No: "+c1.accountNo+"\n"+"Name: "+c1.name+"\n"
		+"Balance: "+c1.getBalance()+"\n"+"Encrypted Password: "+c1.getEncryptedPass());
		
	}
	public void writeOnCustomer(Customer c){
		try{
		File f = new File(c.custID+".txt");
		f.createNewFile();
		FileOutputStream fos = new FileOutputStream(f,true);
		String intro = "\t\t\tName: "+c.name+"\n"+"\t\t\tCustomer ID: "+c.custID+"\n"+"\t\t\tAccount No: "+c.accountNo+"\n\n";
		String table = String.format("%-15s%-25s%-15s%-15s","TransID","TransType","Amount","Balance");
		String first = String.format("%-15d%-25s%-15d%-15d",1, "Opening", 10000, c.getBalance());
		fos.write(intro.getBytes());
		fos.write((table+"\n").getBytes());
		fos.write((first+"\n").getBytes());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void writeOnTransactions(Customer c, Transaction t){
		try{
		File f = new File(c.custID+".txt");
		f.createNewFile();
		FileOutputStream fos = new FileOutputStream(f,true);
		String first = String.format("%-15d%-25s%-15d%-15d",t.transID, t.transType, t.amount, c.getBalance());
		fos.write((first+"\n").getBytes());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void writeAsNewDB(){ 						// write in database
		FileOutputStream fos = null;
		String s = "";
		try{
			fos = new FileOutputStream("bank_db.txt");
			String intro = String.format("%-10s%-15s%-20s%-15s%-25s","CustID", "AccountNo","Name","Balance","EncryptedPass");
			fos.write((intro+"\n").getBytes());
			for(Customer c: custBase){
				s = String.format("%-10d%-15d%-20s%-15d%-25s", c.custID, c.accountNo, c.name, c.getBalance(), c.getEncryptedPass());
				fos.write((s+"\n").getBytes());
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String encryptPass(String s){			// Encryption
		String encrypted = "";
		for(int i=0; i<s.length(); i++){
			char temp = ' ';
			if(s.charAt(i)=='Z'){
					temp = 'A';
			}
			else if(s.charAt(i)=='z'){
					temp = 'a';
			}
			else if(s.charAt(i)=='9'){
					temp = '0';
			}
			else
				temp = (char)(s.charAt(i)+1);
			encrypted += temp;
		}
		return encrypted;
	}
	public String decryptPass(String s){			// Decryption
		String decrypted = "";
		for(int i=0; i<s.length(); i++){
			char ch = ' ';
			if(s.charAt(i)=='A')ch='Z';
			else if(s.charAt(i)=='a')ch = 'z';
			else if(s.charAt(i)=='0')ch = '9';
			else ch = (char)(s.charAt(i)-1);
			decrypted += ch;
		}
		return decrypted;
	}
	
	public boolean authenticate(int id, String pass){		// Authentication
		for(Customer c: custBase){
			if(c.custID == id){
				if(!pass.equals(null) && c.getPass().equals(pass))
					return true;
			}
		}
		return false;
	}
	
	public void operationalCheck(Customer c, int amount){		// Operational Check
		if(amount>5000){
			c.setBalance(c.getBalance()-10);
			Transaction t1 = new Transaction(c, "OperationalFee", 10);
			writeOnTransactions(c, t1);
		}
	}
	public void maintenanceCheck(Customer c){						// Maintenance Check
		Collections.sort(custBase, new SortCustomerByBalance());
		if(!(c.custID==custBase.get(0).custID) && !(c.custID==custBase.get(1).custID) && !(c.custID==custBase.get(2).custID)){
			if(c.transactionCount>=10){
				c.setBalance(c.getBalance()-100);
				Transaction t1 = new Transaction(c, "MaintenanceFee", 100);
				writeOnTransactions(c, t1);
				c.transactionCount=0;
			}
		}
		Collections.sort(custBase, new SortCustomerByID());
	}
	public void deposit(int id, String pass, int amt) throws Exception{		// Deposit
		Transaction t1 = null;
		Customer c1=null; 
		if(authenticate(id, pass)){
			for(Customer c: custBase){
				if(c.custID == id){
					c1 = c;
					if(forcePassChange(c.custID)){
						try{
							throw new PasswordNotChangedException();
						}catch(Exception e){e.printStackTrace();}
					}
					else{
						c.setBalance(c.getBalance()+amt);
						t1 = new Transaction(c, "Cash Deposit", amt);
						writeOnTransactions(c, t1);
						transCountCheck(c.custID);
						maintenanceCheck(c);
						break;
					}
				}
			}
			writeAsNewDB();
			System.out.println("Amount deposited successfully");
			checkHistory(c1);
		}
		else{
			try{
			throw new TransactionHaltException();
			}catch(Exception e){e.printStackTrace();}
		}
	}
	public void withdraw(int id, String pass, int amt) throws Exception{		// Withdraw
		Transaction t1 = null;
		Customer c1=null;
		if(authenticate(id, pass)){
			out:for(Customer c: custBase){
				if(c.custID == id){
					c1 = c;
					if(forcePassChange(c.custID)){
						try{
							throw new PasswordNotChangedException();
						}catch(Exception e){e.printStackTrace();}
					}
					else{
						if(c.getBalance()-amt>1000){
							c.setBalance(c.getBalance()-amt);
							t1 = new Transaction(c, "ATM Withdrawal", amt);
							writeOnTransactions(c, t1);
							transCountCheck(c.custID);
							maintenanceCheck(c);
							writeAsNewDB();
							System.out.println("Amount withdrawn successfully");
							break out;
						}
						else{
							try{
							throw new TransactionHaltException();
							}catch(Exception e){e.printStackTrace();}
						}
						break;
					}
				}
			}
			checkHistory(c1);
		}
		else{
			try{
			throw new TransactionHaltException();
			}catch(Exception e){e.printStackTrace();}
		}
	}
	public void transfer(int id, String pass, int benefAccNo, int amt) throws Exception{
		if(authenticate(id, pass)){					// 	Money transfer 
			boolean present = false;
			boolean transDone = false;
			Customer c1=null;
			Customer c2=null;
			for(Customer c: custBase){
				if(c.accountNo == benefAccNo)
					present = true;
			}
			if(present){
				out:for(Customer c: custBase){
					if(c.custID == id){
						c1 = c;
						if(forcePassChange(c.custID)){
							try{
								throw new PasswordNotChangedException();
							}catch(Exception e){e.printStackTrace();}
						}
						else{
							if(c.getBalance()-amt>1000){
								c.setBalance(c.getBalance()-amt);
								for(Customer cc: custBase){
									Transaction t1, t2 = null;
									if(cc.accountNo == benefAccNo){
										c2 = cc;
										cc.setBalance(cc.getBalance()+amt);
										transDone = true;
										t1 = new Transaction(c, "TransferTo "+cc.accountNo, amt);
										t2 = new Transaction(cc, "TransferFrom "+c.accountNo, amt);
										writeOnTransactions(c, t1);
										writeOnTransactions(cc, t2);
										transCountCheck(c.custID);
										transCountCheck(cc.custID);
										operationalCheck(c, amt);
										System.out.println("Amount transferred successfully");
									}
								}
							}
							else{
								try{
								throw new TransactionHaltException();
								}catch(Exception e){e.printStackTrace();}
							}
						}
					}
				}
				if(transDone){
					maintenanceCheck(c1);
					maintenanceCheck(c2);
				}
				writeAsNewDB();
			}
			else{
				try{
				throw new TransactionHaltException();
				}catch(Exception e){e.printStackTrace();}
			}
			checkHistory(c1);
		}
		else{
			System.out.println("ID or Password invalid, please try again");
		}
	}
	private void checkHistory(Customer c) throws Exception{		// Check Transaction History
		System.out.println("\n\tCheck Transaction History ?"+
							"\n\t1. Yes\n\t2. No");
		int check = sc.nextInt();
		if(check==1){
			File transHistory = new File(c.custID+".txt");
			Scanner tH = new Scanner(transHistory);
			while(tH.hasNextLine()){
				System.out.println(tH.nextLine());
			}
		}
	}
	public void fetchTopN() throws Exception{				// Fetch Top N Customer Report
		System.out.println("Enter N: ");
		int n = sc.nextInt();
		Collections.sort(custBase, new SortCustomerByBalance());
		File f = new File("TopNReport.txt");
		FileOutputStream fos = new FileOutputStream(f, true);
		String intro = String.format("%-10s%-15s%-20s%-15s%-25s","CustID", "AccountNo","Name","Balance","EncryptedPass");
		fos.write((intro+"\n").getBytes());
		for(int i=0; i<n && i<custBase.size(); i++){
			Customer c = custBase.get(i);
			String s = String.format("%-10d%-15d%-20s%-15d%-25s", c.custID, c.accountNo, c.name, c.getBalance(), c.getEncryptedPass());
			fos.write((s+"\n").getBytes());
		}
		fos.write(("\n - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n\n").getBytes());
		System.out.println("Top N report fetched successfully");
		Collections.sort(custBase, new SortCustomerByID());
	}
	public boolean validate(String s){						// Complex Password validation 
		String[] patterns = {"[A-Za-z0-9]*[a-z][A-Za-z0-9]*[a-z][A-Za-z0-9]*", "[A-Za-z0-9]*[A-Z][A-Za-z0-9]*[A-Z][A-Za-z0-9]*", "[A-Za-z0-9]*[0-9][A-Za-z0-9]*[0-9][A-Za-z0-9]*", ".{6}"};
		for(int i=0; i<patterns.length; i++){
			Pattern p = Pattern.compile(patterns[i]);
			Matcher m = p.matcher(s);
			if(!m.find())
				return false;
		}
		return true;
	}
	public void changePassword(int id, String pass){			// Change Password
		if(authenticate(id, pass)){
			System.out.println("Enter new password: ");
			String newPass = sc.next();
			System.out.println("Enter new password again: ");
			String newPass2 = sc.next();
			while(!newPass.equals(newPass2) || !validate(newPass)){
				System.out.println("Passwords didnt match or password not STRONG enough, enter new password: ");
				newPass = sc.next();
				System.out.println("Enter new password again: ");
				newPass2 = sc.next();
			}
			//Customer c1=null;
			for(Customer c1: custBase){
				if(c1.custID == id){
					if(c1.pastPasswords.size() < 3){
						c1.pastPasswords.add(newPass);
						c1.setPass(newPass);
						c1.setEncryptedPass(encryptPass(newPass));
						c1.passwordChanged=true;
						c1.transCountForPass = 0;
						writeAsNewDB();
						System.out.println("Password changed successfully");
					}
					else{
						boolean absent = true;
						for(String s: c1.pastPasswords){
							if(s.equals(newPass)){
								absent = false;
								System.out.println("You've used this password recently, try the process again with a new password");
								try{
									throw new PasswordMismatchException();
								}catch(Exception e){e.printStackTrace();break;}
							}
						}
						if(absent){
							c1.pastPasswords.removeFirst();
							c1.pastPasswords.add(newPass);
							c1.setPass(newPass);
							c1.setEncryptedPass(encryptPass(newPass));
							c1.passwordChanged=true;
							c1.transCountForPass = 0;
							writeAsNewDB();
							System.out.println("Password changed successfully");
						}
					}
					break;
				}
			}
		}
		else{
			try{
				throw new TransactionHaltException();
			}catch(Exception e){e.printStackTrace();}
		}
	}
	public void transCountCheck(int id){
		for(Customer c: custBase){
			if(c.custID == id){
				if(c.transCountForPass>=5)
					c.passwordChanged = false;
			}
		}
	}
	public boolean forcePassChange(int id){				// Force Password Change
		for(Customer c: custBase){
			if(c.custID == id){
				if(c.transCountForPass>=5 && !c.passwordChanged)
					return true;
				else 
					return false;
			}
		}
		return true;
	}
	public void display(){
		for(Customer c: custBase){
			System.out.println(c.custID+"  "+c.accountNo+"  "+c.name+"  "+c.getBalance()+" "+c.getEncryptedPass());
		}
	}
	public void displayTransCount(){
		for(Customer c: custBase){
			System.out.println("Count -> "+c.transactionCount);
		}
	}
}

class SortCustomerByBalance implements Comparator<Customer>{
	@Override
	public int compare(Customer a, Customer b){
		return b.getBalance()-a.getBalance();
	}
}

class SortCustomerByID implements Comparator<Customer>{
	@Override
	public int compare(Customer a, Customer b){
		return a.custID-b.custID;
	}
}
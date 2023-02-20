// Write methods to display commission received from particular users as well as total commission earned
import java.util.*;
import java.io.*;
class ZVerse implements Serializable{
	
	static ZVerse zv = null;
	static Scanner sc = new Scanner(System.in);
	
	private HashMap<Integer, User> users;  				// <ZId, user>
	HashMap<Integer, Agent> agents; 					// <Id, agent>
	double conversionFactor;							// (rc / zc)
	double commissionFactor;
	HashMap<Integer, Transaction> transactions;
	int userIDGen;
	int agentIDGen;
	int transIDGen;
	long totalZC;
	double commission;
	
	ZVerse(){
		users = new HashMap<>();
		agents = new HashMap<>();
		transactions = new HashMap<>();
		conversionFactor = 2; 
		commissionFactor = 0.15;
		commission = 0;
		agentIDGen = 100;
		userIDGen = 1000;
		transIDGen = 10000;
		totalZC = 0;
		
	}
	
	static void start(){
		try{
			File f = new File("ZVerse.zv");
			if(f.exists()){
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
				zv = (ZVerse)ois.readObject();
			}
			else
				zv = new ZVerse();
		}catch(Exception e){e.printStackTrace();}
		zv.loginWindow();
	}
	void loginWindow(){
		System.out.print(
		"\n\tWelcome to Zverse, please select one option: "+
		"\n\t 1. User login"+
		"\n\t 2. Agent login"+
		"\n\t 3. New user"+
		"\n\t 4. Exit\n\t");
		int choice = sc.nextInt();
		while(choice != 4){
			switch(choice){
				case 1:
				System.out.print("\n\tEnter email: ");
				String email = sc.next();
				System.out.print("\n\tEnter password: ");
				String password = sc.next();
				if(authenticate(email, password, 'U'))
					userWindow(email);
				else{
					System.out.print("\n\t Either invalid email or password, or your account might be just waiting for approval, please check your credentials and try again later"+
					"\n\t Press enter to continue...");
					sc.nextLine();
					sc.nextLine();
					return;
				}	
				break;
			
				case 2:
				System.out.print("\n\tEnter email: ");
				email = sc.next();
				System.out.print("\n\tEnter password: ");
				password = sc.next();
				if(authenticate(email, password, 'Z'))
					agentWindow();
				else{
					System.out.print("\n\t Invalid email or password, please check your credentials and try again later"+
					"\n\t Press enter to continue...");
					sc.nextLine();
					sc.nextLine();
					return;
				}
				break;
				
				case 3:
				System.out.print("\n\t Enter first name: ");
				String firstName = sc.next();
				System.out.print("\n\t Entrer last name: ");
				String lastName = sc.next();
				System.out.print("\n\t Enter email id: ");
				email = sc.next();
				while(containsEmail(email)){
					System.out.print("\n\t Email id is already taken, please enter a different email");
					email = sc.next();
				}
				System.out.print("\n\t Enter mobile number: ");
				long mobile = sc.nextLong();
				while(mobile<6000000000l || mobile>9999999999l){
					System.out.print("\n\t Invalid mobile number, please enter a different number: ");
					mobile = sc.nextLong();
				}
				System.out.print("\n\t Enter human Id: ");
				String HId = sc.next();
				while(containsHId(email)){
					System.out.print("\n\t Email id is already taken, please enter a different 	email");
					email = sc.next();
				}
				System.out.print("\n\t Enter password: ");
				password = sc.next();
				while(!validate(password, firstName+" "+lastName, email, mobile+"")){
					System.out.print("\n\t Invalid password, please enter a correct password");
					password = sc.next();
				}
				System.out.print("\n\t Enter initial deposit (of real currency): ");
				double initDeposit = sc.nextDouble();
				User u = new User(firstName+" "+lastName, email, mobile, HId, password, initDeposit, userIDGen++);
				users.put(u.getZId(), u);
				System.out.print("\n\t New user created and applied for approval successfully...");
				break;
				
				case 5:
				System.out.print("\n\t Enter email: ");
				email = sc.next();
				System.out.print("\n\t Enter password: ");
				password = sc.next();
				Agent a = new Agent(email, password, agentIDGen++);
				agents.put(a.getId(), a);
				System.out.print("\n\t New agent created successfully");
				break;
			}
			System.out.print(
			"\n\tWelcome to Zverse, please select one option: "+
			"\n\t 1. User login"+
			"\n\t 2. Agent login"+
			"\n\t 3. New user"+
			"\n\t 4. Exit\n\t");
			choice = sc.nextInt();
		}
		createZVFile();
	}
	void userWindow(String email){
		String name = "";
		User u1 = null;
		for(User u: users.values()){
			if(u.getEmail().equals(email)){
				name += u.getName();
				u1 = u;
				break;
			}
		}
		System.out.print(
		"\n\t Hello "+name+", please select one option: "+
		"\n\t 1. View profile and balance"+
		"\n\t 2. View transactions"+
		"\n\t 3. Change Password"+
		"\n\t 4. Deposit real currency"+
		"\n\t 5. Withdraw real currency"+
		"\n\t 6. Transfer ZCoin to another user"+
		"\n\t 7. Convert Real Currency to ZCoin"+
		"\n\t 8. Convert ZCoin to Real Currency"+
		"\n\t 9. Logout\n\t");
		int choice  = sc.nextInt();
		while(choice != 9){
			switch(choice){
				case 1:
				u1.viewProfile();
				break;
				
				case 2:
				u1.viewTransactions();
				break;
				
				case 3:
				System.out.print("\n\t Enter new password: ");
				String newPass = sc.next();
				while(!validate(newPass, u1.getName(), u1.getEmail(), u1.getNumber()+"")){
					System.out.print("\n\t Invalid password, please enter a correct password");
					newPass = sc.next();
				}
				u1.setPassword(newPass);
				System.out.print("\n\t Password changed successfully");
				break;
				
				case 4:
				case 5:
				case 6:
				case 7:
				case 8:
				carryTransaction(u1, choice-3);
				break;
			}
			System.out.print(
			"\n\t Hello "+name+", please select one option: "+
			"\n\t 1. View profile and balance"+
			"\n\t 2. View transactions"+
			"\n\t 3. Change Password"+
			"\n\t 4. Deposit real currency"+
			"\n\t 5. Withdraw real currency"+
			"\n\t 6. Transfer ZCoin to another user"+
			"\n\t 7. Convert Real Currency to ZCoin"+
			"\n\t 8. Convert ZCoin to Real Currency"+
			"\n\t 9. Logout\n\t");
			choice  = sc.nextInt();
		}
	}
	void agentWindow(){
		System.out.print(
		"\n\tHello agent, please select one option: "+
		"\n\t 1. View creation requests"+
		"\n\t 2. View user transactions"+
		"\n\t 3. View total ZCoins"+
		"\n\t 4. View all transactions"+
		"\n\t 5. Reset user password"+
		"\n\t 6. Logout\n\t");
		int choice = sc.nextInt();
		while(choice != 6){
			switch(choice){
				case 1:
				showUserRequests();
				break;
				
				case 2:
				System.out.print("\n\t Enter ZId of user");
				int ZId = sc.nextInt();
				if(users.containsKey(ZId)){
					users.get(ZId).viewTransactions();
				}
				else{
					System.out.print("\n\t Please enter a valid user ZId");
				}
				break;
				
				case 3:
				System.out.print("\n\t Total coins present in the system: "+totalZC);
				break;
				
				case 4:
				System.out.print("\n\t+-------+------------+------+------+------------------------------------------+");
				System.out.print("\n\t"+String.format("| %-5s | %-10s | %-4s | %-4s | %-40s |", "ID", "Type", "From", "To", "Transaction Details"));
				System.out.print("\n\t+-------+------------+------+------+------------------------------------------+");
				for(Transaction t: transactions.values())
					System.out.print(t);
				System.out.print("\n\t+-------+------------+------+------+------------------------------------------+");
				break;
				
				case 5:
				System.out.print("\n\tEnter user ZId: ");
				ZId = sc.nextInt();
				String newPass = "";
				if(users.containsKey(ZId)){
					newPass += users.get(ZId).getHId();
					users.get(ZId).setPassword(newPass);
					System.out.print("\n\t User password reset successfully, human Id of the user is the new password");
				}
				else{
					System.out.print("\n\t Please enter a valid user ZId");
				}
				break;
			}
			System.out.print(
			"\n\tHello agent, please select one option: "+
			"\n\t 1. View creation requests"+
			"\n\t 2. View user transactions"+
			"\n\t 3. View total ZCoins"+
			"\n\t 4. View all transactions"+
			"\n\t 5. Reset user password"+
			"\n\t 6. Logout\n\t");
			choice = sc.nextInt();
		}
	}
	void carryTransaction(User u, int choice){
		/*
		1 -> Deposit RC
		2 -> Withdraw RC
		3 -> Transfer ZC to another user
		4 -> Convert RC to ZC
		5 -> Convert ZC to RC
		*/
		Transaction t = null;
		switch(choice){
			case 1:
			System.out.print("\n\t Enter amount to deposit: ");
			double amount = sc.nextDouble();
			u.deposit(amount);
			t = new Transaction(transIDGen++, "Deposit", u.getZId(), u.getZId(), "Deposited "+amount+"/-");
			u.getHistory().put(t.transId, t);
			transactions.put(t.transId, t);
			System.out.print("\n\t Amount deposited successfully");
			break;
			
			case 2:
			System.out.print("\n\t Enter amount to withdraw: ");
			amount = sc.nextDouble();
			if(u.withdraw(amount)){
				System.out.print("\n\t Amount withdrawn successfully");
				t = new Transaction(transIDGen++, "Withdrawal", u.getZId(), u.getZId(), "Withdrawn "+amount+"/-");
				u.getHistory().put(t.transId, t);
				transactions.put(t.transId, t);
			}
			else
				System.out.print("\n\t Insufficient funds, please check the amount and try again later");
			break;
			
			case 3:
			System.out.print("\n\t Enter the user ZId of receiver: ");
			int receiverZId = sc.nextInt();
			if(!users.containsKey(receiverZId) || receiverZId == u.getZId()){
				System.out.print("\n\t User not found, please check and try again later");
				return;
			}
			User u2 = users.get(receiverZId);
			System.out.print("\n\t Enter amount to transfer: ");
			amount = sc.nextDouble();
			if(!u.withdrawZC(amount)){
				System.out.print("\n\t Insufficient funds, please check the amount and try again later");
				return;
			}
			u2.depositZC(amount);
			t = new Transaction(transIDGen++, "Transfer", u.getZId(), u2.getZId(), "Transferred "+amount+"zc from "+u.getName()+" to "+u2.getName());
			u.getHistory().put(t.transId, t);
			u2.getHistory().put(t.transId, t);
			transactions.put(t.transId, t);
			System.out.print("\n\t Amount transferred successfully");
			break;
			
			case 4:
			System.out.print("\n\t Enter sum of real currency to be converted: ");
			double realC = sc.nextDouble();
			if(u.withdraw(realC))
				System.out.print("\n\t Amount converted successfully");
			else{
				System.out.print("\n\t Insufficient funds, please check the amount and try again later");
				return;
			}
			u.depositZC(realC/conversionFactor);
			t = new Transaction(transIDGen++, "Conversion", u.getZId(), u.getZId(), "Converted "+realC+"/- to "+(realC/conversionFactor)+" ZCoins");
			u.getHistory().put(t.transId, t);
			transactions.put(t.transId, t);
			totalZC += realC/conversionFactor;
			break;
			
			case 5:
			System.out.print("\n\t Enter sum of ZCoins to be converted: ");
			double zC = sc.nextDouble();
			if(u.withdrawZC(zC))
				System.out.print("\n\t Amount converted successfully");
			else{
				System.out.print("\n\t Insufficient funds, please check the amount and try again later");
				return;
			}
			realC = zC*conversionFactor;
			double netRC = realC - realC*commissionFactor/100;
			t = new Transaction(transIDGen++, "Conversion", u.getZId(), u.getZId(), "Converted "+zC+"ZCoins to "+netRC+"/-");
			u.getHistory().put(t.transId, t);
			transactions.put(t.transId, t);
			commission += realC - netRC;
			u.commissionRetrieved += realC - netRC;
			u.deposit(netRC);
			totalZC -= zC;
		}
		System.out.println();
	}
	boolean authenticate(String email, String password, char isUser){
		switch(isUser){
			
			case 'Z':
			for(Agent a: agents.values()){
				if(a.verifyEmail(email) && a.verifyPassword(password))
					return true;
			}
			break;
			
			case 'U':
			for(User u: users.values()){
				if(u.verifyEmail(email) && u.verifyPassword(password) && u.getApprovalStatus())
					return true;
			}
			break;
		}
		return false;
	}
	
	void showUserRequests(){
		for(User u: users.values()){
			if(!u.getApprovalStatus()){
				System.out.print("\n\t"+String.format("%-20s", u.getName())+" "+u.getZId());
			}
		}
		System.out.print("\n\n\tSelect one option: "+
		"\n\t 1. Accept request"+
		"\n\t 2. Decline request"+
		"\n\t 3. Go back to main menu\n\t");
		int choice = sc.nextInt();
		switch(choice){
			
			case 1:
			System.out.print("\n\t Enter user ZId: ");
			int ZId = sc.nextInt();
			if(!users.containsKey(ZId)){
				System.out.print("\n\t Invalid user ZId, please check and try again later");
				return;
			}
			users.get(ZId).setApprovalStatus(true);
			System.out.print("\n\t User approved successfully");
			break;
			
			case 2:
			System.out.print("\n\t Enter user ZId: ");
			ZId = sc.nextInt();
			if(!users.containsKey(ZId)){
				System.out.print("\n\t Invalid user ZId, please check and try again later");
				return;
			}
			users.remove(ZId);
			System.out.print("\n\t User declined successfully");
			break;
			
			case 3:
			return;
		}
	}
	
	boolean validate(String password, String name, String email, String number){
		if(password.contains(name) || password.contains(number) ||password.contains(email))
			return false;
		if(password.length()<8)return false;
		String[] spl = {"!", "@", "#", "?", "$", "%", "^", "&", "*"};
		boolean flag = false;
		for(String s: spl){
			if(password.contains(s))flag = true;
		}
		return flag;
	}
	boolean containsEmail(String email){
		for(User u: users.values()){
			if(u.getEmail().equals(email))return true;
		}
		return false;
	}
	boolean containsHId(String HId){
		for(User u: users.values()){
			if(u.getHId().equals(HId))return true;
		}
		return false;
	}
	void createZVFile(){
		try{
		File f = new File("ZVerse.zv");
		f.createNewFile();
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
		oos.writeObject(this);
		}catch(Exception e){e.printStackTrace();}
	}
}
import java.util.*;
import java.io.*;
class BankDriver{
	public static void main(String[] args) throws Exception{
		Scanner  ip = new Scanner(System.in);
		Bank b1 = new Bank();
		b1.initCustBase();
		System.out.println("\tSelect any One Option :\n\t"+
		"1. Add New Customer\n\t"+
		"2. Cash Withdrawl\n\t"+
		"3. Cash Deposit\n\t"+
		"4. Money Transfer\n\t"+
		"5. Display database\n\t"+
		"6. Fetch Top N Report\n\t"+
		"7. Change Password\n\t"+
		"8. Quit");
		int n = ip.nextInt();
		
		while(n!=8){
			switch(n){
				case 1:b1.addCustomer();break;
				
				case 2:
				System.out.println("Enter Customer ID: ");
				int tempId = ip.nextInt();
				System.out.println("Enter Password: ");
				String pass = ip.next();
				System.out.println("Enter Amount: ");
				int amt = ip.nextInt();
				b1.withdraw(tempId, pass, amt);
				break;
				
				case 3:
				System.out.println("Enter Customer ID: ");
				tempId = ip.nextInt();
				System.out.println("Enter Password: ");
				pass = ip.next();
				System.out.println("Enter Amount: ");
				amt = ip.nextInt();
				b1.deposit(tempId, pass, amt);
				break;
				
				case 4:
				System.out.println("Enter Customer ID: ");
				tempId = ip.nextInt();
				System.out.println("Enter Password: ");
				pass = ip.next();
				System.out.println("Enter Beneficiary account no: ");
				int benAcc = ip.nextInt();
				System.out.println("Enter Amount: ");
				amt = ip.nextInt();
				b1.transfer(tempId, pass, benAcc, amt);
				break;
				
				case 5:
				b1.display();
				break;
				
				case 6:
				b1.fetchTopN();
				break;
				
				case 7:
				System.out.println("Enter Customer ID: ");
				tempId = ip.nextInt();
				System.out.println("Enter Password: ");
				pass = ip.next();
				b1.changePassword(tempId, pass);
				break;
				
				case 9:
				System.out.println(b1.validate(ip.next()));
				break;
			}
			System.out.println("\n\tSelect any One Option :\n\t"+
			"1. Add New Customer\n\t"+
			"2. Cash Withdrawl\n\t"+
			"3. Cash Deposit\n\t"+
			"4. Money Transfer\n\t"+
			"5. Display database\n\t"+
			"6. Fetch Top N Report\n\t"+
			"7. Change Password\n\t"+
			"8. Quit");
			n=ip.nextInt();
		}
	}
}
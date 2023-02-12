import java.util.*;
import java.io.*;
class Login implements Serializable{
	static Scanner sc = new Scanner(System.in);
	private static final long serialVersionUID = 927l;
	static boolean active = false;
	static RedBus rb;
	
	public static void start(){
		System.out.println(
		"\n\tCreate new RedBus or open an existing one ?"+
		"\n\t1. Create : create"+
		"\n\t2. Open   : open");
		String s = sc.next();
		String id = "";
		if(s.equals("create")){
			System.out.println("Enter redbus ID");
			id = sc.next();
			rb = new RedBus(id);
		}
		else{
			System.out.println("Enter redbus ID");
			id = sc.next();
			try{
				File ff = new File(id+".bus");
				FileInputStream f = new FileInputStream(ff);
				ObjectInputStream ois = new ObjectInputStream(f);
				if(ff.exists()){
					rb = (RedBus)ois.readObject();
					System.out.println("RedBus "+rb.id+" is active now");
				}
				else 
					System.out.println("File not found, enter correct ID");
			}
			catch(Exception e){e.printStackTrace();}
		}
		entry();
	}
	public static void entry(){
		
		String id = "";
		String pass = "";
		String name = "";
		
		System.out.println(
		"\n\t1. Sign up : new_user"+
		"\n\t2. Sign in : login"+
		"\n\t3. Exit    : exit");
		
		String s = sc.next();
		while(!s.equals("exit")){
			
			switch(s){
				// case "red":
				// rb.busList.get(1001).freeSeats = 20;
				// System.out.println("success!!!");
				// break;
				
				case "new_user":
					rb.addUser();
				break;
			
				case "login":
				System.out.println("Enter mobile number: ");
				id = sc.next();
				System.out.println("Enter password: ");
				pass = sc.next();
				if(rb.authenticate(id, pass)){
					rb.home(id);
				}
				else
					System.out.println("Invalid number or password");
				break;
			
				case "operator":
				// if(!active){
					// start();
					// active = true;
				// }
				System.out.println(
				"\n\t1. New Operator   : new_op"+
				"\n\t2. Operator login : op_login"+
				"\n\t3. Exit           : exit");
				String op= sc.next();
				while(!op.equals("exit")){
					switch(op){

						case "new_op":
						rb.addOperator();
						break;

						case "op_login":
						System.out.println("Enter Operator ID: ");
						id = sc.next();
						System.out.println("Enter password: ");
						pass = sc.next();
						String temp ="";
						if(rb.authenticate(id, pass)){
							System.out.println(
							"\n\t1. Add Bus     : add"+
							"\n\t2. Check Buses : check"+
							"\n\t3. Exit        : exit");
							temp = sc.next();
							while(!temp.equals("exit")){
								switch(temp){

									case "add":
									rb.addBus(id);
									break;

									case "check":
									rb.checkBuses(id);
									break;
								}
								System.out.println(
								"\n\t1. Add Bus     : add"+
								"\n\t2. Check Buses : check"+
								"\n\t3. Exit        : exit");
								temp = sc.next();
							}
						}
						else
							System.out.println("Invalid ID or Password");
						break;

					}
					System.out.println(
					"\n\t1. New Operator   : new_op"+
					"\n\t2. Operator login : op_login"+
					"\n\t3. Exit           : exit");
					op= sc.next();
				}
				break;
			}
			System.out.println(
			"\n\t1. Sign up : new_user"+
			"\n\t2. Sign in : login"+
			"\n\t3. Exit    : exit");
			s = sc.next();
		}
		System.out.println(
		"\n\tDo you want to save the application ?"+
		"\n\t1. Yes : Y/y"+
		"\n\t2. No  : N/n");
		s = sc.next();
		if(s.equalsIgnoreCase("Y")){
			try{
				if(rb!=null){
					rb.createRedBusFile();
					System.out.println("Application saved successfully");
				}
			}catch(Exception e){e.printStackTrace();}
		}
	}
}
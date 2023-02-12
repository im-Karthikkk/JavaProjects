import java.util.*;
import java.io.*;
import java.util.regex.*;
class RedBus implements Serializable{
	static Scanner sc = new Scanner(System.in);
	private static final long serialVersionUID = 927l;

	String id;
	int operatorCount;
	int busCount;
	int ticketCount;
	HashMap<String, BusOperator> operatorDB;		// <OperatorID, Operator>
	HashMap<String, User> userDB;					// <MobileNo, User>
	HashMap<Integer, Bus> busList;					// <BusID, Bus>
	HashMap<Integer, Ticket> ticketLog;
	
	RedBus(String id){
		this.id = id;
		operatorCount=100;
		busCount = 1000;
		ticketCount = 10000;
		operatorDB = new HashMap<>();
		userDB = new HashMap<>();
		ticketLog = new HashMap<>();
		busList = new HashMap<>();		// Similar to busDepot of Operator but keep track of all buses in application 
	}
	
	public void addOperator(){
		System.out.print("Enter operator name: ");
		String name = sc.next();
		System.out.println("Enter password: ");
		String pass = sc.next();
		String id = "BO"+((this.operatorCount++)+1);
		BusOperator b = new BusOperator(name, id, encryptPass(pass));
		operatorDB.put(b.operatorID, b);
		System.out.println("Bus Operator created -> Name: "+b.name+" ID: "+b.operatorID);
	}
	public void addBus(String operatorID){
		int id = (this.busCount++)+1;
		System.out.println("Enter bus source and destination: ");
		String source = sc.next();
		String destination = sc.next();
		System.out.println("Enter bus departure date in dd-mm-yyyy format and time in 24hrs format: ");
		String depDate = sc.next();
		String depTime = sc.next();
		System.out.println("Enter bus arrival date in dd-mm-yyyy format and time in 24hrs format: ");
		String arrivalDate = sc.next();
		String arrivalTime = sc.next();
		System.out.println("Enter Bus type: AC or Non-AC: ");
		String type = sc.next();
		System.out.println("Enter the seats count: ");
		int seats = sc.nextInt();
		System.out.println("Enter price per seat: ");
		int price = sc.nextInt();
		Bus b = new Bus(id,source,destination,depDate,depTime,arrivalDate,arrivalTime,type,seats,price,operatorDB.get(operatorID).name);
		busList.put(id, b);
		operatorDB.get(operatorID).busDepot.put(id, b);
	}
	public void addUser(){
		System.out.print("Enter name: ");
		String name = sc.next();
		System.out.println("Enter mobile number: ");
		String number = sc.next();
		System.out.println("Enter password: ");
		String pass = sc.next();
		User u=null;
		if(validate(number)){
			u = new User(name, number, encryptPass(pass));
			userDB.put(u.number, u); 
			System.out.println("User "+u.name+" created successfully");
		}
		else{
			System.out.println("Invalid mobile number, please try again");
		}
	}
	public void checkBuses(String id){
		for(Integer i: operatorDB.get(id).busDepot.keySet()){
			System.out.println(operatorDB.get(id).busDepot.get(i));
		}
	}
	public void home(String number){
		System.out.println(
		"\n\tWelcome to RedBus "+ userDB.get(number).name+", select one option: "+
		"\n\t1. Book Ticket   : book"+
		"\n\t2. Cancel Ticket : cancel"+
		"\n\t3. Check Tickets : check"+
		"\n\t4. Logout        : logout");
		String s = sc.next();
		while(!s.equals("logout")){
			switch(s){
				
				case "book":
				bookTicket(number);
				break;
				
				case "check":
				checkTickets(number);
				break;
				
				case "cancel":
				cancelTicket(number);
				break;
			}
			System.out.println(
			"\n\tWelcome to RedBus, select one option: "+
			"\n\t1. Book Ticket   : book"+
			"\n\t2. Cancel Ticket : cancel"+
			"\n\t3. Check Tickets : check"+
			"\n\t4. Logout        : logout");
			s = sc.next();
		}
	}
	public void bookTicket(String number){
		System.out.println("Enter source: ");
		String from = sc.next();
		System.out.println("Enter destination: ");
		String to = sc.next();
		System.out.println("Enter date(dd-mm-yyyy): ");
		String date = sc.next();
		boolean notAvailable = true;
		//System.out.println();
		int busId =0;
		String busInfo = "\n\t"+from+"-"+to+"\t\t"+date+"\n";
		ArrayList<Integer> busNumbers  = new ArrayList<>();
		for(Integer i: busList.keySet()){
			Bus b = busList.get(i);
			if(b.source.equalsIgnoreCase(from) && b.destination.equalsIgnoreCase(to) && b.departureDate.equals(date) && b.freeSeats>0){
				busInfo += b.toString()+"\n";
				busNumbers.add(b.busID);
				notAvailable = false;
			}
		}
		if(notAvailable){
			System.out.println("Sorry, no buses are available on this route for the selected date");
			return;
		}
		else{
			System.out.println(busInfo);
			System.out.println("Select a bus and enter bus ID: ");
			busId = sc.nextInt();
			while(busId!=0){	
				String choice = "";
				if(busNumbers.contains(busId) && busList.containsKey(busId)){
					busList.get(busId).displaySeats();
					System.out.println("Enter Y to select seats");
					choice = sc.next();
					if(choice.equalsIgnoreCase("Y")){
						selectSeats(number, busId);
					}
				}
				else
					System.out.println("Please enter valid bus ID");
				System.out.println(busInfo);
				System.out.println("Select a bus and enter bus ID: (or 0 to go back)");
				busId = sc.nextInt();
			}
		}
	}
	public void selectSeats(String number, int busID){
		System.out.println("Enter the seat numbers you want to book: (or 0 if you're done)");
		ArrayList<Integer> seatNumbers = new ArrayList<>();
		int seat = sc.nextInt();
		while(seat!=0){
			if(seat>=busList.get(busID).deck.length || busList.get(busID).deck[seat-1])
				System.out.println("Please enter available seat number");
			else
				seatNumbers.add(seat);
			seat = sc.nextInt();
		}
		System.out.println("Type 'yes' to confirm you are booking and check if seat numbers are correct: ");
		for(Integer i: seatNumbers)
			System.out.print(i+" ");
		System.out.println();
		String confirm = sc.next();
		if(!confirm.equalsIgnoreCase("yes"))
			return;
		System.out.println("Enter contact information: ");
		String contact = sc.next();
		TreeMap<Integer, Passenger> tempPassengerList = new TreeMap<>();
		for(int i=0; i<seatNumbers.size(); i++){
			System.out.println("Enter passenger details for seat no."+(seatNumbers.get(i))+", name : ");
			String name = sc.next();
			System.out.println("Enter age: ");
			int age = sc.nextInt();
			System.out.println("Enter gender: ");
			String gender = sc.next();
			gender = gender.toLowerCase();
			Passenger p = new Passenger(name, age, gender);
			tempPassengerList.put(seatNumbers.get(i), p);					// map to send to ticket constructor
			busList.get(busID).deck[seatNumbers.get(i)-1] = true;
			busList.get(busID).freeSeats--;
		}
		int ticketId = (this.ticketCount++)+1;
		Ticket t = new Ticket(busList.get(busID), contact, tempPassengerList, ticketId);
		this.ticketLog.put(ticketId, t);
		userDB.get(number).bookingHistory.put(ticketId, t);
		busList.get(busID).passengers.put(ticketId, t);
		System.out.println("Ticket booked successfully, your ticket details are: ");
		System.out.println(t);
	}
	public void cancelTicket(String number){
		System.out.println("Enter ticket ID: ");
		int id = sc.nextInt();
		Ticket t = ticketLog.get(id);
		if(t.status.equals("CANCELLED")){
			System.out.println("Can't cancel a ticket that has already been cancelled");
			return;
		}
		Bus b = busList.get(t.busNo);
		for(Integer i: t.passengerInfo.keySet()){
			b.deck[i-1] = false;
			b.freeSeats++;
		}
		b.passengers.remove(id);
		t.status = "CANCELLED";
		System.out.println("Ticket cancelled successfully");
	}
	public void checkTickets(String number){
		for(Integer i: userDB.get(number).bookingHistory.keySet()){
			System.out.println(userDB.get(number).bookingHistory.get(i));
		}
	}
	public boolean authenticate(String id, String pass){		// Authentication
		String encrypted = encryptPass(pass);
		if(this.userDB.containsKey(id)){
			if(this.userDB.get(id).password.equals(encrypted))
				return true;
			else 
				return false;
		}
		else if(this.operatorDB.containsKey(id)){
			if(operatorDB.get(id).password.equals(encrypted))
				return true;
			else 
				return false;
		}
		else
			return false;
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
	public boolean validate(String s){						// Complex Password validation 
		String[] patterns = {"^[6-9]{1}[0-9]{9}$"};
		for(int i=0; i<patterns.length; i++){
			Pattern p = Pattern.compile(patterns[i]);
			Matcher m = p.matcher(s);
			if(!m.find())
				return false;
		}
		return true;
	}
	public void createRedBusFile() throws Exception{
		File ff = new File(this.id+".bus");
		ff.createNewFile();
		FileOutputStream f = new FileOutputStream(ff);
		ObjectOutputStream os = new ObjectOutputStream(f);
		os.writeObject(this);
	}
}
import java.util.*;
import java.io.*;
import java.time.*;

class Bunk{
	static Scanner sc = new Scanner(System.in);
	static Bunk pb = null;
	
	String id;
	int petrolCapacity;
	double petrolPrice;
	int dieselCapacity;
	double dieselPrice;
	
	int currentPetrol;
	int currentDiesel;
	double revenuePetrol;
	double revenueDiesel;
	int recordIDGen;
	
	
	HashMap<String, Vehicle> vehicles;
	
	Bunk(String id, int petrolCapacity, double petrolPrice, int dieselCapacity, double dieselPrice){
		this.id = id;
		this.petrolCapacity = petrolCapacity;
		this.petrolPrice = petrolPrice;
		this.dieselCapacity = dieselCapacity;
		this.dieselPrice = dieselPrice;
		this.currentPetrol = petrolCapacity;
		this.currentDiesel = dieselCapacity;
		this.revenuePetrol = 0;
		this.revenueDiesel = 0;
		this.recordIDGen = 1000;
		vehicles = new HashMap<>();
	}
	
	public static void start(boolean create, String command) throws Exception{
		//System.out.print("\n\t comm -> "+command);
		String[] commands = command.split(" ");
		if(create){
			String id = commands[1];
			int petrolCapacity = Integer.parseInt(commands[2]);
			int dieselCapacity = Integer.parseInt(commands[4]);
			double petrolPrice = Double.parseDouble(commands[3]);
			double dieselPrice = Double.parseDouble(commands[5]);
			pb = new Bunk(id, petrolCapacity, petrolPrice, dieselCapacity, dieselPrice);
			pb.window();
			return;
		}
		try{
			File f = new File(command+".bunk");
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);
			pb = (Bunk)ois.readObject();
		}catch(Exception e){e.printStackTrace();}
		pb.window();
	}
	public static void entry(){
		System.out.print(
		"\n\tPlease enter a command: "+
		"\n\t1. Create new bunk    : create_bunk  <BUNK_ID> <PETROL_CAPACITY> <PETROL_PRICE> <DIESEL_CAPACITY> <DIESEL_PRICE>"+
		"\n\t2. Open existing bunk : open_bunk <BUNK_ID>\n\t");
		
		String command = sc.nextLine();
		String[] commands = command.split(" ");
		if(commands.length==2 && commands[0].equals("open_bunk")){
			try{
				start(false, commands[1]);
			}catch(Exception e){e.printStackTrace();}
			return;
		}
		while(!commands[0].equals("create_bunk")){
			System.out.print("\n\tPlease create a bunk first...");
			command = sc.nextLine();
			commands = command.split(" ");
			try{
				start(true, command);
			}catch(Exception e){e.printStackTrace();}
		}
	}
	public void refill(){
		this.currentPetrol = petrolCapacity;
		this.currentDiesel = dieselCapacity;
		System.out.print("\n\tRefilled to capacity for bunk "+id+".");
	}
	public void alterPrice(String fuel, double newPrice){
		switch(fuel){
			case "PETROL":
			petrolPrice = newPrice;
			System.out.print("\n\tPETROL price changed successfully.");
			break;
			
			case "DIESEL":
			dieselPrice = newPrice;
			System.out.print("\n\tDIESEL price changed successfully.");
			break;
			
			default:
			System.out.print("\n\tPlease enter a valid fuel type...");
			break;
		}
	}
	public void findVehicle(String regNo){
		if(!vehicles.containsKey(regNo)){
			System.out.print("\n\tVehicle "+regNo+" not found...please try again with correct registration no..");
			return;
		}
		System.out.print("\n\t"+regNo+" found.");
		System.out.print("\n\t"+vehicles.get(regNo));
	}
	public void viewField(String field){
		switch(field){
			case "holding":
			System.out.print("\n\t"+id+" Petrol: "+currentPetrol+"L  Diesel: "+currentDiesel+"L");
			break;
			
			case "revenue":
			System.out.print("\n\t"+id);
			System.out.print("\n\t\tRevenue from Petrol: "+revenuePetrol);
			System.out.print("\n\t\tRevenue from Diesel: "+revenueDiesel);
			break;
			
			case "price":
			System.out.print("\n\t"+id+" Petrol: "+petrolPrice+"/-  Diesel: "+dieselPrice+"/-");
			break;
			
			default:
			System.out.print("\n\tPlease enter a valid command ...");
			break;
		}
	}
	public void tankFill(String command){
		String[] commands = command.split(" ");
		if(!commands[4].equalsIgnoreCase("car") && !commands[4].equalsIgnoreCase("bike") && !commands[4].equalsIgnoreCase("truck")){
			System.out.print("\n\tPlease enter correct vehicle type...");
			return;
		}
		String type = commands[4];
		String fuel = commands[2];
		String regNo = commands[3];
		int quantity = 0;
		int price = 0;
		if(commands.length==7)
			quantity = Integer.parseInt(commands[5]) - Integer.parseInt(commands[6]);
		else
			quantity = Integer.parseInt(commands[5]);
		if(quantity<=0){
			System.out.print("\n\tInvalid fuel quantity... please try again later...");
			return;
		}
		if(fuel.equalsIgnoreCase("petrol")){
			price = quantity * (int)petrolPrice;
		}
		else if(fuel.equalsIgnoreCase("diesel")){
			price = quantity * (int)dieselPrice;
		}
		else{
			System.out.print("\n\tPlease enter a valid fuel type...");
			return;
		}
		
		switch(fuel){
			case "PETROL":
			if(currentPetrol<=quantity){
				System.out.print("\n\tNot enough PETROL to fill at the moment kindly revisit.");
				return;
			}
			else{
				currentPetrol -= quantity;
				revenuePetrol += price;
			}
			break;
			
			case "DIESEL":
			if(currentDiesel<=quantity){
				System.out.print("\n\tNot enough DIESEL to fill at the moment kindly revisit.");
				return;
			}
			else{
				currentDiesel -= quantity;
				revenueDiesel += price;
				
			}
			break;
		}
		
		Record r =null;
		Vehicle v = null;
		if(vehicles.containsKey(regNo)){
			 v = vehicles.get(regNo);
			 r = new Record(recordIDGen++, quantity, price, fuel, LocalDateTime.now());
			 v.records.put(r.id, r);
		}
		else{
			v = new Vehicle(regNo, type);
			vehicles.put(regNo, v);
			r = new Record(recordIDGen++, quantity, price, fuel, LocalDateTime.now());
			v.records.put(r.id, r);
		}
		System.out.print("\n\tFilled "+quantity+"l of "+fuel+" for "+v.regNo+" : "+price);
	}
	public void filter(String command, boolean doubleFilter){
		String[] commands = command.split(" ");
		if(doubleFilter){// commands[2] -> type
			boolean started = false;
			char sign = commands[5].charAt(0);
			int quantity = Integer.parseInt(commands[5].substring(1));
			for(Vehicle v: vehicles.values()){
				if(!v.type.equals(commands[2]))continue;
				started = false;
				for(Record r: v.records.values()){			
					switch(sign){
						case '>':
						if(r.quantity>quantity){
							if(!started){
								System.out.print("\n\t"+v.regNo+" "+v.type);
								started = true;
							}
							System.out.print("\n\t"+r);
							//empty = false;
						}
						break;
						
						case '<':
						if(r.quantity<quantity){
							if(!started){
								System.out.print("\n\t"+v.regNo+" "+v.type);
								started = true;
							}
							System.out.print("\n\t"+r);
							//empty = false;
						}
						break;
						
						case '=':
						if(r.quantity==quantity){
							if(!started){
								System.out.print("\n\t"+v.regNo+" "+v.type);
								started = true;
							}
							System.out.print("\n\t"+r);
							//empty = false;
						}
						break;
					}
				}
			}
		}
		else{
			switch(commands[1]){
				case "type":
				for(Vehicle v: vehicles.values()){
					if(v.type.equals(commands[2]))
						System.out.print("\n\t"+v);
				}
				break;
				
				case "litre":
				boolean started = false;
				char sign = commands[2].charAt(0);
				int quantity = Integer.parseInt(commands[2].substring(1));
				for(Vehicle v: vehicles.values()){
					started = false;
					for(Record r: v.records.values()){
						
						switch(sign){
							case '>':
							if(r.quantity>quantity){
								if(!started){
									System.out.print("\n\t"+v.regNo+" "+v.type);
									started = true;
								}
								System.out.print("\n\t"+r);
								//empty = false;
							}
							break;
							
							case '<':
							if(r.quantity<quantity){
								if(!started){
									System.out.print("\n\t"+v.regNo+" "+v.type);
									started = true;
								}
								System.out.print("\n\t"+r);
								//empty = false;
							}
							break;
							
							case '=':
							if(r.quantity==quantity){
								if(!started){
									System.out.print("\n\t"+v.regNo+" "+v.type);
									started = true;
								}
								System.out.print("\n\t"+r);
								//empty = false;
							}
							break;
						}
					}
				}
				break;
			}
		}
	}
	public void showCommands(){
		System.out.print("\n\tWelcome to Bunk "+id+", please select one of the options: "+
			"\n\t1.  Refill bunk              : refill"+
			"\n\t2.  Find Vehicle             : find <REGNO>"+
			"\n\t3.  View current holding     : view holding"+
			"\n\t4.  View revenue             : view revenue"+
			"\n\t5.  View price               : view price"+
			"\n\t6.  Alter fuel price         : alter <FUEL> <NEWPRICE>"+
			"\n\t7.  Filter by type           : filter type <TYPE>"+
			"\n\t8.  Filter by litre          : filter litre <RANGE>"+
			"\n\t9.  Filter by type and litre : filter type <TYPE> and litre <RANGE>"+
			"\n\t10. Fill by litre            : fill litre <FUEL_TYPE> <REG_NO> <VEHICLE_TYPE> <NUMBER_OF_LITRE>"+
			"\n\t11. Fill tank                : fill tankfill <FUEL_TYPE> <REG_NO> <VEHICLE_TYPE> <CAPACITY> <INITIAL_HOLDING>"+
			"\n\t12. Show commands            : help"+
			"\n\t13. Exit                     : exit\n\t");
		
	}
	public void window(){
		showCommands();
		String command = sc.nextLine();
		String commands[] = command.split(" ");
		while(!command.equalsIgnoreCase("exit")){
			switch(commands.length){
				
				case 1:
				if(commands[0].charAt(0)=='r')
				refill();
				else
				showCommands();
				break;
				
				case 2:
				switch(commands[0]){
					case "find":
					findVehicle(commands[1]);
					break;
					
					case "view":
					viewField(commands[1]);
					break;
				
				}
				break;
				
				case 3:
				switch(commands[0]){
					case "alter":
					alterPrice(commands[1], Double.parseDouble(commands[2]));
					break;
					
					case "filter":
					filter(command, false);
					break;
					
					default:
					System.out.print("\n\tPlease enter a valid command...");
					break;
				}
				break;
				
				case 6:
				switch(commands[0]){
					case "fill":
					if(!commands[1].equals("litre")){
						System.out.print("\n\tPlease enter a valid command...");
						break;
					}
					tankFill(command);
					break;
					
					case "filter":
					filter(command, true);
					break;
				}
				break;
				
				case 7:
				if(!commands[0].equals("fill") || !commands[1].equals("tankfill")){
					System.out.print("\n\tPlease enter a valid command...");
					break;
				}
				tankFill(command);
				break;
				
				default:
				System.out.print("\n\tPlease enter a valid command...");
				break;
			}
			System.out.println();
			command = sc.nextLine();
			while(command.equals("")){
				command = sc.nextLine();
			}
			commands = command.split(" ");
		}
	}
}
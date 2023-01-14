import java.util.*;
class LotDriver{
	
	
	
	public static Ticket park(ParkingLot pL1, Vehicle v){
		int flr=0;
		int slt=0;
		switch(v.type){										/////////
			case "CAR":
			out:	for(int i=0; i<pL1.floor; i++){
					for(int j=3; j<pL1.slots; j++){
						if(!pL1.map[i][j]){
							flr = i+1;
							slt = j+1;	//
							pL1.map[i][j] = true;
							break out;
						}
					}
				}
				if(flr==0 && slt==0){
					System.out.println("Parking Lot Full");
					return null;
				}
				else{
					Ticket t1 = new Ticket(pL1, flr, slt, v);
					return t1;
				}
			case "BIKE":
			out:	for(int i=0; i<pL1.floor; i++){
					for(int j=1; j<Math.min(3, pL1.slots); j++){
						if(!pL1.map[i][j]){
							flr = i+1;
							slt = j+1;	//
							pL1.map[i][j] = true;
							break out;
						}
					}
				}
				if(flr==0 && slt==0){
					System.out.println("Parking Lot Full");
					return null;
				}
				else{
					Ticket t2 = new Ticket(pL1, flr, slt, v);
					return t2;
				}
			case "TRUCK":
			out:	for(int i=0; i<pL1.floor; i++){
						if(!pL1.map[i][0]){
							flr = i+1;
							slt = 1;	//
							pL1.map[i][0] = true;
							break out;
						}
					}
				}
				if(flr==0 && slt==0){
					System.out.println("Parking Lot Full");
					return null;
				}
				else{
					Ticket t3 = new Ticket(pL1, flr, slt, v);
					return t3;
				}
			
	}
	
	public static void unpark(ParkingLot pL1,ArrayList<Ticket> ticketLog,ArrayList<Ticket> unparkedTickets, String tid){
		//// Parked Vehicle at P_2_5 but unparking with same id gives invalid & P_2_7 gives NullPointerException
		Ticket t = new Ticket(pL1, 0,0,null);
		for(Ticket it: ticketLog)
		{
			try{
				if((!it.id.equals(null)) && it.id.equals(tid))
				{
					t = it;
					break;
				}
			}
			catch(NullPointerException e){
				//e.printStackTrace();
			}
		}
		String[] parkID = tid.split("_");
		int i = Integer.parseInt(parkID[1]);
		int j = Integer.parseInt(parkID[2]);
		if((i-1)>=pL1.floor || (j-1)>=pL1.slots || !pL1.map[i-1][j-1])
		{
			System.out.println(/*"floor -> "+i+"slot -> "+j+*/"Invalid Ticket");
		}
		else
		{
			pL1.map[i-1][j-1] = false;
			unparkedTickets.add(t);
			ticketLog.remove(t);
			System.out.println("Unparked Vehicle with Registration Number: "+t.v.regNo+" and color: "+t.v.color);
		}
		//pL1.print();
	}
	
	public static void main(String[] args)
	{
		
		Scanner ip = new Scanner(System.in);
		ParkingLot pL1 = new ParkingLot("", 0, 0);
		ArrayList<Ticket> ticketLog = new ArrayList<>();
		ArrayList<Ticket> unparkedTickets = new ArrayList<>();
		
		while(true)
		{
			System.out.println("Enter Command: ");
			String s = ip.next();
			boolean started = false;
			if(s.equals("exit"))
				break;
			else{
				switch(s)
				{
					
					case "create_parking_lot":
						String pid = ip.next();
						int flr = ip.nextInt();
						int slt = ip.nextInt();
						pL1.setAttr(pid, flr, slt);
						System.out.println("Created parking lot with "+pL1.floor+" floors and "+pL1.slots+" slots per floor");
						break;
					
					case "display":
						String s2 = ip.next();
						switch(s2)
						{
							case "free_count":
								String s3 = ip.next();
								switch(s3)
								{
									case "CAR":
										for(int i=0; i<pL1.floor; i++)
										{
											int carSlots =0;
											for(int j=3; j<pL1.slots; j++)
											{
												if(!pL1.map[i][j])
													carSlots++;
											}
											System.out.println("No. of free slots for CAR on Floor "+(i+1)+": "+carSlots);
										}
										break;
									case "BIKE":
										for(int i=0; i<pL1.floor; i++){
											int bSlots =0;
											for(int j=1; j<3; j++){
												if(!pL1.map[i][j])
													bSlots++;
											}
											System.out.println("No. of free slots for BIKE on Floor "+(i+1)+": "+bSlots);
										}
										break;
									case "TRUCK":
										for(int i=0; i<pL1.floor; i++){
											int tSlots = 0;
											if(!pL1.map[i][0])
												tSlots = 1;
											System.out.println("No. of free slots for TRUCK on Floor "+(i+1)+": "+tSlots);
										}
										break;
								}
								break;
							case "free_slots":
								String s4 = ip.next();
								switch(s4){
									case "CAR":
										for(int i=0; i<pL1.floor; i++){
											System.out.print("Free slots for CAR on Floor "+(i+1)+": ");
											int carSlots =3;	//3
											for(int j=3; j<pL1.slots; j++){
												if(!pL1.map[i][j]){
													carSlots++;
													System.out.print((carSlots)+" ");
												}
												else
													carSlots++;
											}	
											System.out.println();
										}
										break;
									case "BIKE":
										for(int i=0; i<pL1.floor; i++){
											System.out.print("Free slots for BIKE on Floor "+(i+1)+": ");
											int bSlots =1;
											for(int j=1; j<3; j++){
												if(!pL1.map[i][j]){
													bSlots++;
													System.out.print((bSlots)+" ");
												}
												else
													bSlots++;
											}
											System.out.println();
										}
										break;
									case "TRUCK":
										for(int i=0; i<pL1.floor; i++){
											System.out.print("Free slots for TRUCK on Floor "+(i+1)+": ");
											if(!pL1.map[i][0])
												System.out.print(1);
											System.out.println();
										}
										break;
								}
								break;
							case "occupied_slots":
								String s5 = ip.next();
								switch(s5){
									case "CAR":
										for(int i=0; i<pL1.floor; i++){
											System.out.print("Occupied slots for CAR on Floor "+(i+1)+": ");
											int carSlots =3;
											for(int j=3; j<pL1.slots; j++){
												if(pL1.map[i][j]){
													carSlots++;
													System.out.print((carSlots)+" ");
												}
												else
													carSlots++;
											}	
											System.out.println();
										}
										break;
									case "BIKE":
										for(int i=0; i<pL1.floor; i++){
											System.out.print("Occupied slots for BIKE on Floor "+(i+1)+": ");
											int bSlots =1;
											for(int j=1; j<3; j++){
												if(pL1.map[i][j]){
													bSlots++;
													System.out.print((bSlots)+" ");
												}
												else
													bSlots++;
											}
											System.out.println();
										}
										break;
									case "TRUCK":
										for(int i=0; i<pL1.floor; i++){
											System.out.print("Occupied slots for TRUCK on Floor "+(i+1)+": ");
											if(pL1.map[i][0])
												System.out.print(1);
											System.out.println();
										}
										break;
								}
								break;
						}
						break;
						
					case "park_vehicle":
						
						String s6 = ip.next();
						String s7 = ip.next();
						String s8 = ip.next();
						Vehicle v1 = new Vehicle(s6, s7, s8);
						Ticket t = park(pL1, v1);
						if(t!=null)
							System.out.println("Parked vehicle. Ticket ID: "+t.id);
						ticketLog.add(t);
						break;
						
					case "unpark_vehicle":
						
						String s9 = ip.next();
						unpark(pL1, ticketLog, unparkedTickets, s9);
						break;
				}
			}
		}
		//System.out.println("Prog Closed");
	}
}
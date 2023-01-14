import java.util.*;
import java.io.*;
class Bus implements Serializable{
	private static final long serialVersionUID = 927l;
	
	int busID;
	String source;
	String destination;
	String departureDate;
	String departureTime;
	String arrivalDate;
	String arrivalTime;
	String operatorName;
	boolean[] deck;				// Shows the Available Seats 
	String type;				// AC or Non-AC
	int seats;	
	int freeSeats;
	int price;
	HashMap<Integer, Ticket> passengers;
	
	Bus(int busID, String source, String destination, String departureDate, String departureTime,
	String arrivalDate, String arrivalTime, String type, int seats, int price, String operatorName){
		this.busID = busID;
		this.source = source;
		this.destination = destination;
		this.departureDate = departureDate;
		this.departureTime = departureTime;
		this.arrivalDate = arrivalDate;
		this.arrivalTime = arrivalTime;
		this.type = type;
		this.seats = seats;						// Assuming every row has 4 seats
		this.freeSeats = seats;
		this.price = price;
		this.operatorName = operatorName;
		deck = new boolean[seats];				// false - available, true - booked
		passengers = new HashMap<>();
		displaySeats();
	}
	
	public void displaySeats(){
		System.out.print("\n\t"+String.format("%-18s",operatorName)+"\t\t"+"Bus ID: "+busID+"\n");
		System.out.print("\t"+departureTime+" -> "+arrivalTime+"\t\t\tDate: "+departureDate+"\n");
		System.out.print("\t"+source+"-"+destination+"\n");
		for(int i=0; i<seats; i++){
			if(i%4==0){
				System.out.println();
				System.out.print("\t");
			}
			if(!deck[i])
				System.out.print(String.format("%2d", i+1)+" ");
			else
				System.out.print("XX ");
			if((i+1)%4!=0 && (i+1)%2 == 0)
				System.out.print("  ");
		}
		System.out.println();
	}
	
	public String toString(){
		int hrs = Integer.parseInt(arrivalTime.charAt(0)+""+arrivalTime.charAt(1)+"") - Integer.parseInt(departureTime.charAt(0)+""+departureTime.charAt(1)+"");
		int min = Integer.parseInt(arrivalTime.charAt(3)+""+arrivalTime.charAt(4)+"") - Integer.parseInt(departureTime.charAt(3)+""+departureTime.charAt(4)+"");
		if(min<0){
			min += 60;
			hrs -= 1;
		}
		String temp =("\n\t"+departureTime+"-"+arrivalTime+"\t\t\t"+"₹"+price+
		"\n\t"+hrs+"h"+min+"m"+"\t\t\t"+freeSeats+" Seats"+
		"\n\t"+operatorName+"\t\t"+busID+"("+type+")\n"+
		"\n\t- - - - - - - - - - - - - - - - - - - - -");
		return temp;
	}
	
	
}
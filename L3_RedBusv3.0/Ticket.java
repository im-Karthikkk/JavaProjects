import java.util.*;
import java.io.*;
class Ticket implements Serializable{
	private static final long serialVersionUID = 927l;
	
	String contact;
	String date;		// from bus
	String time;		// from bus
	int ticketID;
	int busNo;			// from bus
	String operatorName;// from bus
	TreeMap<Integer, Passenger> passengerInfo;	// Seatno, Passenger
	int price;
	String from;        // from bus
	String to;			// from bus
	String type;        // from bus
	String status;
	
	Ticket(Bus b, String contact, TreeMap<Integer, Passenger> pInfo, int ticketID){
		this.date = b.departureDate;
		this.time = b.departureTime;
		this.ticketID = ticketID;
		this.busNo = b.busID;
		this.operatorName = b.operatorName;
		this.passengerInfo = pInfo;
		this.price = pInfo.size()*b.price;
		this.from = b.source;
		this.to = b.destination;
		this.type = b.type;
		this.status = "BOOKED";
	}
	public String toString(){
		String temp = "\n\t";
		temp += String.format("%-32s%-8s%-10s%5s%-20s", from+" -> "+to,"Date: ",date,"","Ticket No. : "+ticketID);
		temp += "\n\t"+String.format("%-32s%-8s%-10s%5s%-20s", operatorName,"Time: ",time,"","Fare: ₹"+price);
		temp += "\n\t"+String.format("%6s%3s%18s%5s%-17s", "SeatNo", "", "Passenger", "","Status: "+this.status);
		for(Integer i: passengerInfo.keySet()){
			temp += "\n\t"+String.format("%6s%3s%18s", i, "",passengerInfo.get(i).name);
		}
		temp += "\n\n\t- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -";
		return temp;
	}
}
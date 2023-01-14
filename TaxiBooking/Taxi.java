import java.util.*;
class Taxi{
	static int taxiID = 1; 
	
	int taxi_id;
	int earning;
	int freeTime;
	char location;
	ArrayList<Ride> rides;
	
	Taxi(){
		this.earning = 0;
		this.freeTime = 0;
		this.location = 'A';
		this.taxi_id = taxiID++;
		this.rides = new ArrayList<>();
		Ride.cabs.add(this);
	}
	Taxi(String s){
		this.earning = 0;
		this.freeTime = 0;
		this.location = 'A';
		this.taxi_id = 0;
	}
	
	public boolean isAvailable(int pickupTime, char pickup){
		return (freeTime+Math.abs(pickup - location)<=pickupTime);
	}
	public String toString(){
		String s = "Taxi ID: "+taxi_id+"\t\t"+"Total Earnings: "+earning+"\n";
		if(this.earning>0){
			s += "Booking ID\tCustomer ID\tFrom\tTo\tPickup At\tDrop At\t\tFare\n";
			for(Ride r: rides )
				s += r.toString();
		}
		return s;
	}
	public void printDetails(){
		System.out.println("ID -> "+taxi_id);
		System.out.println("Earnings -> "+earning);
		System.out.println("Free time -> "+freeTime);
		System.out.println("Current Location -> "+location+"\n");
	}
}
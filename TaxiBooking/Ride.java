import java.util.*;
class Ride{
	static int bookid = 1;
	static ArrayList<Taxi> cabs = new ArrayList<>();
	
	int booking_id;
	char pickup;
	char drop;
	int pickupTime;
	int dropTime;
	int fare;
	int taxi_id;
	
	Ride(char pickup, char drop, int pickupTime){
		this.pickup = pickup;
		this.drop = drop;
		this.pickupTime = pickupTime;
		this.booking_id = bookid++;
		this.book();
	}
	
	public void book(){
		int nearest = Integer.MAX_VALUE;
		int minEarning = Integer.MAX_VALUE;
		Taxi temp = new Taxi("dummy");
		for(Taxi t: cabs){
			if(t.isAvailable(pickupTime, pickup) && Math.abs(this.pickup-t.location)<=nearest && t.earning < minEarning){
				temp = t;
				nearest = Math.abs(this.pickup-t.location);
				minEarning = temp.earning;
			}
		}
		if(temp.taxi_id == 0)
			System.out.println("Booking Rejected");
		else{
			this.fare = (Math.abs(this.pickup - this.drop)*15-5)*10 + 100;
			temp.earning += fare;
			this.taxi_id = temp.taxi_id;
			dropTime = pickupTime + Math.abs(this.pickup - this.drop);
			temp.freeTime = dropTime;
			temp.location = drop;
			temp.rides.add(this);
			System.out.println("Taxi-"+temp.taxi_id+" is allotted");
		}
	}
	public String toString(){
		String s = "";
		s += booking_id+"\t\t"+booking_id+"\t\t"+pickup+"\t"+drop+"\t"+pickupTime+"\t\t"+dropTime+"\t\t"+fare+"\n";
		return s;
	}
}
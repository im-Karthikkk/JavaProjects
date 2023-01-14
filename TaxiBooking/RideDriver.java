import java.util.*;
class RideDriver{
	
	public static void main(String[] args){
		Taxi T1 = new Taxi();
		Taxi T2 = new Taxi();
		Taxi T3 = new Taxi();
		Taxi T4 = new Taxi();
		Ride r1 = new Ride('A', 'E', 10);
		
		Ride r2 = new Ride('A', 'E', 10);
		
		Ride r3 = new Ride('A', 'E', 10);
		Ride r4 = new Ride('A', 'E', 10);
		Ride r5 = new Ride('E', 'A', 14);
		System.out.println();
		for(Taxi t: Ride.cabs)
			System.out.println(t);
	}
}
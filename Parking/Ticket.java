class Ticket{
	String id;
	Vehicle v;
	
	Ticket(ParkingLot p, int f, int s, Vehicle v){
		this.id = p.ID+"_"+f+"_"+s;
		this.v = v; 
	}
													/////////
}
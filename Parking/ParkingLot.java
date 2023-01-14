public class ParkingLot{
	
	public String ID;
	public int floor;
	public int slots;
	public boolean[][] map; 
	
	ParkingLot(String id, int f, int s){
		this.ID = id;									/////////
		this.floor = f;
		this.slots = s;
		
		
	}
	public int getFloors(){
		return this.floor;
	}
	public void setAttr(String id, int f, int s){
		this.ID = id;
		this.floor = f;
		this.slots = s;
		this.map = new boolean[floor][slots];
	}
	
	public void print(){
		for(int i=0; i<floor; i++){
			for(int j=0; j<slots; j++)
				System.out.print(map[i][j]?"1":"0"+" ");
			System.out.println();
		}
	}
}
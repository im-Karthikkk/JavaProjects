import java.util.*;
import java.io.*;
import java.time.*;
class Record{
	
	int id;
	int quantity;
	int amount;
	String fuelType;
	LocalDateTime dt;
	
	Record(int id, int quantity, int amount, String fuelType, LocalDateTime dt){
		this.id = id;
		this.quantity = quantity;
		this.amount = amount;
		this.fuelType = fuelType;
		this.dt = dt;
	}
	
	public String toString(){
		String temp = "\n\t\t"+ dt + " " + fuelType + " " + quantity + " : " +amount;
		return temp;
	}
}
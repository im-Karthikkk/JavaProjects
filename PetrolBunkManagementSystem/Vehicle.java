import java.util.*;
import java.io.*;

class Vehicle{

	String regNo;
	String type;
	HashMap<Integer, Record> records;
	
	Vehicle(String regNo, String type){
		this.regNo = regNo;
		this.type = type;
		records = new HashMap<>();
	}
	public String toString(){
		String temp = "\n\t"+regNo+" "+type;
		for(Record r : records.values()){
			temp += r.toString();
		}
		return temp;
	}
}
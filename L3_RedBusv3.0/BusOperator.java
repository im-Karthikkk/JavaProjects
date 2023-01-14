import java.util.*;
import java.io.*;
class BusOperator implements Serializable{
	private static final long serialVersionUID = 927l;
	
	String name;
	String operatorID;
	String password;
	HashMap<Integer, Bus> busDepot;
	
	BusOperator(String name, String id, String password){
		this.name = name;
		this.operatorID = id;
		this.password = password;
		busDepot = new HashMap<>();
	}
	
}
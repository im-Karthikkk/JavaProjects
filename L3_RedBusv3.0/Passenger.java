import java.util.*;
import java.io.*;
class Passenger implements Serializable{
	String name;
	int age;
	String gender;
	
	Passenger(String name, int age, String gender){
		this.name = name;
		this.age = age;
		this.gender = gender;
	}
}
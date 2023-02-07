import java.util.*;
import java.io.*;
class MyList implements Serializable{
	int id;
	String name;
	HashMap<Integer, Card> cards;	// cardID, card
	
	MyList(int listID, String name){
		this.id = listID;
		this.name = name;
		cards = new HashMap<>();
	}
	
	public String toString(){
		String temp = "\n\t+------------------------------+--------------------------------------------------+";
		temp += String.format("\n\t| %-28s | %-48s |", "List ID: "+id, "Name: "+name);
		temp += "\n\t+------------------------------+--------------------------------------------------+";
		if(cards.isEmpty())
			return temp;
		temp += String.format("\n\t| %-10s | %-15s | %-30s | %-15s |", "Card ID", "Name", "Description", "User");
		temp += "\n\t+------------+-----------------+--------------------------------+-----------------+";
		for(Card c : cards.values()){
			temp += c.toString();
			temp +=	"\n\t+------------+-----------------+--------------------------------+-----------------+";
		}
		return temp;
	}
}
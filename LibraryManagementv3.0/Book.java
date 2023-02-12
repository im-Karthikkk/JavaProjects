import java.util.*;
import java.io.*;
class Book implements Serializable{
	private static final long serialVersionUID = 921l;
	int bookID;
	String title;
	String authors;
	String publishers;
	String copyID;
	String dueDate;
	
	Book(int bookID, String title, String authors, String publishers, String copyID){
		this.bookID = bookID;
		this.title = title;
		this.authors = authors;
		this.publishers = publishers;
		this.copyID = copyID;
	}
	
	public String toString(){
		return String.format("%15s%10d%15s%30s%30s",copyID,bookID,title,authors,publishers);
		//return copyID+" "+bookID+" "+title+" "+authors+" "+publishers;
	}
}
import java.util.*;
import java.io.*;
import java.util.regex.*;
class Library implements Serializable{
	static Scanner sc = new Scanner(System.in);
	private static final long serialVersionUID = 921l; 
	
	String libraryID;
	int rackCount;
	boolean noAdminPresent;
	ArrayList<Book> booksOfLib;					//  Shows all the books that library owns
	HashMap<String, Admin> adminDB;				//  HashMap <adminID, Admin>
	HashMap<String, User> userDB;				//  HashMap <userID, User>
	HashMap<Integer, Book> booksAtLib;			//  HashMap <rackNo, Book> shows the books currently present at lib
	
	Library(String libID, int noOfRacks){
		libraryID = libID;
		rackCount = noOfRacks;
		booksAtLib = new HashMap<>();
		userDB = new HashMap<>();
		adminDB = new HashMap<>();
		booksOfLib = new ArrayList<>();
		noAdminPresent =true;
	}
	
	public void createDatabase() throws Exception{
		File f = new File(libraryID+"DB.txt");
		f.createNewFile();
		String s = String.format("%15s%10s%15s%30s%30s%10s%20s%15s","Copy ID","Book ID","Title","Authors","Publishers", "Rack", "User ID", "Due Date\n");
		FileOutputStream fis = new FileOutputStream(f);
		fis.write(s.getBytes());
	}
	public void writeOnDatabase(String s) throws Exception{
		File f = new File(libraryID+"DB.txt");
		FileOutputStream fis = new FileOutputStream(f, true);
		fis.write(s.getBytes());
	}
	public boolean validate(String s){						// Complex Password validation 
		String[] patterns = {"[A-Za-z0-9]*[a-z][A-Za-z0-9]*", "[A-Za-z0-9]*[A-Z][A-Za-z0-9]*", "[A-Za-z0-9]*[0-9][A-Za-z0-9]*", ".{6}"};
		for(int i=0; i<patterns.length; i++){
			Pattern p = Pattern.compile(patterns[i]);
			Matcher m = p.matcher(s);
			if(!m.find())
				return false;
		}
		return true;
	}
	public boolean authenticate(String id, String pass){		// Authentication
		String encrypted = encryptPass(pass);
		if(this.userDB.containsKey(id)){
			if(this.userDB.get(id).password.equals(encrypted))
				return true;
			else 
				return false;
		}
		else if(this.adminDB.containsKey(id)){
			if(adminDB.get(id).password.equals(encrypted))
				return true;
			else 
				return false;
		}
		else
			return false;
	}
	public String encryptPass(String s){			// Encryption
		String encrypted = "";
		for(int i=0; i<s.length(); i++){
			char temp = ' ';
			if(s.charAt(i)=='Z'){
					temp = 'A';
			}
			else if(s.charAt(i)=='z'){
					temp = 'a';
			}
			else if(s.charAt(i)=='9'){
					temp = '0';
			}
			else
				temp = (char)(s.charAt(i)+1);
			encrypted += temp;
		}
		return encrypted;
	}
	
	public void addUser(String userID, String name, String password){
		User u = null;
		if(validate(password)){
			u = new User(userID, name, encryptPass(password));
			userDB.put(userID, u);
			System.out.println("User added successfully");
		}
		else{
			System.out.println("Password must meet the necessary eligibilty criteria, please try again with a new password");
		}
	}
	public void addAdmin(String userID, String name, String password){ 			// To add multiple admins
		if(validate(password)){
			Admin a = new Admin(userID, name, encryptPass(password));
			adminDB.put(userID, a);
			System.out.println("Admin added successfully");
		}
		else
			System.out.println("Please try again with new password");
	}
	public void createAdmin(String userID, String name, String password){		// To create the first admin
		if(noAdminPresent){
			if(validate(password)){
				Admin a = new Admin(userID, name, encryptPass(password));
				noAdminPresent = false;
				adminDB.put(userID, a);
				System.out.println("Admin created successfully");
			}
			else
				System.out.println("Please try again with new password");
		}
		else		
			System.out.println("Can't create more admins, please try different command");
	}
	public void displayPrompt(String userID, String password){
		String s = "";
		if(adminDB.containsKey(userID)){
			System.out.println(
			"\n\tSelect any one option: "+
			"\n\t1. Add Admin         : add_admin <adminID> <name> <password>"+
			"\n\t2. Add Book          : add_book <bookId> <title> <author> <publisher> <copyIDs>"+
			"\n\t3. Remove Book       : remove_book <copyId>"+
			"\n\t4. Search            : search <attribute> <value>"+
			"\n\t5. View Database     : view_db"+
			"\n\t6. Logout            : logout");
			s = sc.next();
			while(!s.equals("logout")){
				switch(s){
					
					case "add_admin":
					String adminId = sc.next();
					String name = sc.next();
					String pass = sc.next();
					addAdmin(adminId, name, pass);
					break;
					
					case "add_book":
					int bookId = sc.nextInt();
					String title = sc.next();
					String author = sc.next();
					String publisher = sc.next();
					String copyIds = sc.next();
					addBook(bookId, title, author, publisher, copyIds);
					break;
					
					case "remove_book":
					String copyId = sc.next();
					removeBook(copyId);
					break;
					
					case "view_db":
					try{
						viewDatabase();
					}catch(Exception e){e.printStackTrace();}
					break;
					
					case "search":
					String attribute = sc.next();
					String attrValue = sc.next();
					search(attribute, attrValue);
					break;
					
				}
				System.out.println(
				"\n\tSelect any one option: "+
				"\n\t1. Add Admin         : add_admin <adminID> <name> <password>"+
				"\n\t2. Add Book          : add_book <bookId> <title> <author> <publisher> <copyIDs>"+
				"\n\t3. Remove Book       : remove_book <copyId>"+
				"\n\t4. Search            : search <attribute> <value>"+
				"\n\t5. View Database     : view_db"+
				"\n\t6. Logout            : logout");
				s = sc.next();
			}
		}
		else if(userDB.containsKey(userID)){
			System.out.println(
			"\n\tSelect any one option: "+
			"\n\t1. Borrow Book       : borrow_book <bookID> <dueDate>"+
			"\n\t2. Borrow Book Copy  : borrow_book_copy <copyID> <dueDate>"+
			"\n\t3. Return Book Copy  : return_book_copy <copyID>"+
			"\n\t4. Print Borrowed    : print_borrowed"+
			"\n\t5. Search            : search <attribute> <value>"+
			"\n\t6. Logout            : logout");
			s = sc.next();
			while(!s.equals("logout")){/////
				switch(s){
					case "borrow_book":
					int bookId = sc.nextInt();
					String dueDate = sc.next();
					borrowBook(bookId, userID, dueDate);
					break;
					
					case "borrow_book_copy":
					String copyId = sc.next();
					dueDate = sc.next();
					borrowBookCopy(copyId, userID, dueDate);
					break;
					
					case "return_book_copy":
					copyId = sc.next();
					returnBookCopy(copyId, userID);
					break;
					
					case "print_borrowed":
					try{
						printBorrowed(userID);
					}catch(Exception e){e.printStackTrace();}
					break;
					
					case "search":
					String attribute = sc.next();
					String attrValue = sc.next();
					search(attribute, attrValue);
					break;
				}
				System.out.println(
				"\n\tSelect any one option: "+			// Copy paste remaining
				"\n\t1. Borrow Book       : borrow_book <bookID> <dueDate>"+
				"\n\t2. Borrow Book Copy  : borrow_book_copy <copyID> <dueDate>"+
				"\n\t3. Return Book Copy  : return_book_copy <copyID>"+
				"\n\t4. Print Borrowed    : print_borrowed"+
				"\n\t5. Search            : search <attribute> <value>"+
				"\n\t6. Logout            : logout");
				s = sc.next();
			}
		}
		else
			System.out.println("Enter valid user ID and password");
	}
	
	public void viewDatabase() throws Exception{
		Scanner ip = new Scanner(new File(this.libraryID+"DB.txt"));
		while(ip.hasNextLine()){
			String temp = ip.nextLine();
				System.out.println(temp);
		}
	}
	public void borrowBook(int bookID, String userID, String dueDate){////////
		Book b=null;
		boolean notPresent=true;
		int tempRackNo=0;
		int min=1000;
		//String writeUp = "";
		User tempUser = userDB.get(userID);
		if(userDB.get(userID).holding.size() == 5){
			System.out.println("Overlimit");
			return;
		}
		else{
			for(Book bb: booksOfLib){
				if(bb.bookID==bookID){
					notPresent = false;
					break;
				}
			}
			if(notPresent){
				System.out.println("Invalid Book ID");
				return;
			}
			else{
				for(int i=1; i<=rackCount; i++){
					if(booksAtLib.get(i)!=null && booksAtLib.get(i).bookID==bookID){
						b = booksAtLib.get(i);
						tempRackNo = i;
					}
				}
				if(b==null){
					System.out.println("Not Available");
					return;
				}
				else{
					for(int i=1; i<=rackCount; i++){
						if(booksAtLib.get(i)!=null && booksAtLib.get(i).bookID==bookID){
							b = booksAtLib.get(i);
							tempRackNo = i;
							break;
						}
					}
					b.dueDate = dueDate;
					tempUser.holding.add(b);
					booksAtLib.remove(tempRackNo);
					try{
						String writeUp = String.format("%15s%10d%15s%20s", b.copyID, b.bookID, dueDate, "Borrowed\n");
						tempUser.writeOnFile(writeUp);
						writeUp="";
						writeUp = String.format("%15s%10d%15s%30s%30s%10d%20s%15s", b.copyID, b.bookID, b.title, b.authors, b.publishers, -1, userID, dueDate+"\n"); 
						writeOnDatabase(writeUp);
					}catch(Exception e){e.printStackTrace();}
					System.out.println("Book borrowed from rack: "+tempRackNo);
				}
			}
		}
	}
	public void borrowBookCopy(String copyID, String userID, String dueDate){
		Book b=null;
		boolean notPresent=true;
		int tempRackNo=0;
		//String writeUp = "";
		User tempUser = userDB.get(userID);
		if(userDB.get(userID).holding.size() == 5){
			System.out.println("Overlimit");
			return;
		}
		else{
			for(Book bb: booksOfLib){
				if(bb.copyID.equals(copyID)){
					notPresent = false;
					break;
				}
			}
			if(notPresent){
				System.out.println("Invalid Book Copy ID");
				return;
			}
			else{
				for(int i=1; i<=rackCount; i++){
					if(booksAtLib.get(i)!=null && booksAtLib.get(i).copyID.equals(copyID)){
						b = booksAtLib.get(i);
						tempRackNo = i;
					}
				}
				if(b==null){
					System.out.println("Not Available");
					return;
				}
				else{									// Book borrowed
					b.dueDate = dueDate;
					tempUser.holding.add(b);
					booksAtLib.remove(tempRackNo);
					try{
					String ss = String.format("%15s%10d%15s%20s", b.copyID, b.bookID, dueDate, "Borrowed\n");
					tempUser.writeOnFile(ss);
					String str = String.format("%15s%10d%15s%30s%30s%10d%20s%15s", b.copyID, b.bookID, b.title, b.authors, b.publishers, -1, userID, dueDate+"\n"); 
					writeOnDatabase(str);
					}catch(Exception e){e.printStackTrace();}
					System.out.println("Borrowed book copy from rack: "+tempRackNo);
				}
			}
		}
	}
	public void returnBookCopy(String copyID, String userID){
		boolean notAvailable = true;
		Book b = null;
		int tempRackNo=0;
		String writeUp = "";
		for(Book bb: booksOfLib){
			if(bb.copyID.equals(copyID)){
				notAvailable = false;
				b = bb;
				break;
			}
		}
		if(notAvailable){
			System.out.println("Invalid Book Copy ID");
			return;
		}
		else{
			if(userDB.get(userID).holding.contains(b)){
				for(int i=1; i<=rackCount; i++){
					if(!booksAtLib.containsKey(i)){
						b.dueDate = "-";
						booksAtLib.put(i, b);
						tempRackNo=i;
						break;
					}
				}
				userDB.get(userID).holding.remove(b);
				try{
					writeUp = String.format("%15s%10d%15s%30s%30s%10d%20s%15s", b.copyID, b.bookID, b.title, b.authors, b.publishers, tempRackNo, userID, " - "+"\n"); 
					writeOnDatabase(writeUp);
					writeUp = String.format("%15s%10d%15s%20s", b.copyID, b.bookID, b.dueDate, "Returned\n");
					userDB.get(userID).writeOnFile(writeUp);
				}catch(Exception e){e.printStackTrace();}
				System.out.println("Returned book copy "+copyID+" and added to rack: "+tempRackNo);
			}
			else
				System.out.println("Can't return the book that you dont have");
		}
	}
	
	public void search(String attribute, String attrValue){
		//String s = sc.nextLine();
		String authorId = "";
		String publisherId = "";
		int bookid=0;
		//System.out.println("words[0] -> "+words[0]+" "+words[1]);
		if(attribute.equals("book_id")){
			bookid = Integer.parseInt(attrValue);
			for(Book b: booksOfLib){
				if(b.bookID == bookid)
					System.out.print("Book Copy: "+b+"\n");
			}
		}
		else if(attribute.equals("author_id")){
			authorId = attrValue;
			for(Book b: booksOfLib){
				if(b.authors.contains(authorId))
					System.out.print("Book Copy: "+b+"\n");
			}
		}
		else if(attribute.equals("publisher_id")){
			publisherId = attrValue;
			for(Book b: booksOfLib){
				if(b.publishers.contains(publisherId))
					System.out.print("Book Copy: "+b+"\n");
			}
		}
		else
			System.out.println("Invalid search command");
	}
	
	public void printBorrowed(String userID) throws Exception{
		Scanner ip = new Scanner(new File(userID+".txt"));
		while(ip.hasNextLine()){
			String temp = ip.nextLine();
			if(temp.contains("Borrowed"))
				System.out.println(temp);
		}
	}
	public void addBook(int bookID, String title, String authors, String publishers, String copyIDs){
		boolean rackNotAvailable = true;	//	ADD New Books to Library
		String[] copyID = copyIDs.split(",");
		if(copyID.length <= this.rackCount-this.booksOfLib.size()){
			rackNotAvailable = false;
		}
		/* for(int i=1; i<=rackCount; i++){
			if(!booklist.containsKey(i))
				rackNotAvailable = false;
		} */
		
		if(rackNotAvailable){
			System.out.println("Rack Not Available");
			return;
		}
		else{
			System.out.print("Added Book to racks: ");
			for(int i=0; i<copyID.length; i++){
				Book b = new Book(bookID, title, authors, publishers, copyID[i]);
				booksOfLib.add(b);
				int j=0;
				for(j=1; j<=rackCount; j++){	// Fetch the first free rack
					if(!booksAtLib.containsKey(j))
						break;
				}
				booksAtLib.put(j, b);
				System.out.print(j+" ");
			}
			System.out.println();
		}
	}	
	public void removeBook(String bookCopyID){		// REMOVE Book from the library
		boolean notPresent = true;
		int tempRackNo=0;
		Book b=null;
		for(Integer it: booksAtLib.keySet()){
			if(booksAtLib.get(it).copyID.equals(bookCopyID)){
				notPresent = false;
				tempRackNo = it;
				break;
			}
		}
		if(notPresent){
			System.out.println("Invalid Book Copy ID");
		}
		else{
			for(Book tempB: booksOfLib){
				if(tempB.copyID.equals(bookCopyID)){
					b = tempB;
					break;
				}
			}
			booksOfLib.remove(b);
			booksAtLib.remove(tempRackNo);
			System.out.println("Removed book copy: "+b.copyID+" from rack: "+tempRackNo);
		}
	}
	public void createLibraryFile() throws Exception{
		File ff = new File(libraryID+".lib");
		ff.createNewFile();
		FileOutputStream f = new FileOutputStream(ff);
		ObjectOutputStream os = new ObjectOutputStream(f);
		os.writeObject(this);
	}
}
import java.util.*;
import java.io.*;
class MyTrello implements Serializable{

	static Scanner sc = new Scanner(System.in);
	static MyTrello mt = null;
	private static final long  serialVersionUID = 602l;
	
	int boardIDGen;
	int cardIDGen;
	int userIDGen;
	int listIDGen;
	
	HashMap<Integer, Board> boards;
	HashMap<Integer, User> users;
	
	MyTrello(){
		boards = new HashMap<>();
		users = new HashMap<>();
		boardIDGen = 10000;
		cardIDGen = 100;
		userIDGen = 10;
		listIDGen = 1000;
	}
	public static void start() throws Exception{
		try{
		File f = new File("MyTrello.mt");
		
		if(f.exists()){
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);
			mt = (MyTrello)ois.readObject();
		}
		else{
			mt = new MyTrello();
		}
		}catch(Exception e){e.printStackTrace();}
		mt.window();
	}
	public void createBoard(String name){
		Board b = new Board(boardIDGen++, name);
		boards.put(b.id, b);
		System.out.println("\tCreated Board: "+b.id);
	}
	public void createList(int boardID, String name){
		MyList l = new MyList(listIDGen++, name);
		boards.get(boardID).lists.put(l.id, l);
		System.out.println("\tCreated List: "+l.id);
	}
	public void createCard(int listID, String name){
		Card c = new Card(cardIDGen++, name);
		MyList l = null;
		for(Board b: boards.values()){
			if(b.lists.containsKey(listID)){
				l = b.lists.get(listID);
				break;
			}
		}
		l.cards.put(c.id, c);
		System.out.println("\tCreated Card: "+c.id);
	}
	public void createUser(String name, String email){
		User u = new User(userIDGen++, name, email);
		users.put(u.id, u);
		System.out.println("\tCreated User: "+u.id);
	}
	public void showCard(int id){
		for(Integer b: boards.keySet()){
			for(Integer l: boards.get(b).lists.keySet()){
				if(boards.get(b).lists.get(l).cards.containsKey(id)){
					System.out.println(boards.get(b).lists.get(l).cards.get(id));
					return;
				}
			}
		}
		System.out.println("\tInvalid Card ID... please try again later with correct ID");
	}
	public void showList(int id){
		for(Board b: boards.values()){
			if(b.lists.containsKey(id)){
				System.out.println(b.lists.get(id));
				return;
			}
		}
		System.out.println("\tInvalid List ID... please try again later with correct ID");
	}
	public void showBoard(int id){
		if(boards.containsKey(id)){
			System.out.println(boards.get(id));
			return;
		}
		System.out.println("\tInvalid Board ID... please try again later with correct ID");
	}
	public void show(){
		if(boards.isEmpty()){
			System.out.println("\tNo boards");
			return;
		}
		for(Board b: boards.values())
			System.out.println(b);
	}
	public void updateName(char c, int id, String newName){
		switch(c){
			case 'B':
			if(boards.containsKey(id)){
				boards.get(id).name = newName;
			}
			else
				System.out.println("\n\tInvalid board ID...");
			break;
			
			case 'C':
		out:for(Board b: boards.values()){
				for(MyList l: b.lists.values()){
					if(l.cards.containsKey(id)){
						l.cards.get(id).name = newName;
						return;
					}
				}
			}
			System.out.println("\n\tInvalid card ID...");
			break;
			
			case 'L':
			for(Board b: boards.values()){
				if(b.lists.containsKey(id)){
					b.lists.get(id).name = newName;
					return;
				}
			}
			System.out.println("\n\tInvalid list ID...");
			break;
		}
	}
	public void updatePrivacy(int boardID, String privacy){
		if(boards.containsKey(boardID)){
			boards.get(boardID).privacy = privacy;
		}
		else
			System.out.println("\n\tInvalid board ID...");
	}
	public void toggleUser(int cardID, String userEmail, boolean assign){
		int userID = 0;
		if(assign){
			for(User u: users.values()){
				if(u.email.equals(userEmail)){
					userID = u.id;
					break;
				}
			}
			for(Board b: boards.values()){
				for(MyList l: b.lists.values()){
					if(l.cards.containsKey(cardID)){
						if(!b.members.containsKey(userID)){
							System.out.println("No such member available to assign for the given card...");
							return;
						}
						l.cards.get(cardID).assignedUser = users.get(userID).name;
					}
				}
			}
			return;
		}
		for(Board b: boards.values()){
			for(MyList l: b.lists.values()){
				if(l.cards.containsKey(cardID)){
					l.cards.get(cardID).assignedUser = "Unassigned";
				}
			}
		}
	}
	public void addMember(int boardID, int userID){
		if(!users.containsKey(userID)){
			System.out.println("\tInvalid user ID ... please try again later...");
			return;
		}
		boards.get(boardID).members.put(userID, users.get(userID));
	}
	public void removeMember(int boardID, int userID){
		if(!users.containsKey(userID)){
			System.out.println("\tInvalid user ID ... please try again later...");
			return;
		}
		Board b = boards.get(boardID);
		if(b.members.containsKey(userID)){
			b.members.remove(userID);
		}
		else{
			System.out.println("\tNo such user in the given board... please try again later...");
		}
	}
	public void moveCard(int cardID, int listID){
		Card c = null;
		for(Board b: boards.values()){
			for(MyList l : b.lists.values()){
				if(l.cards.containsKey(cardID)){
					c = l.cards.get(cardID);
					l.cards.remove(cardID);
				}
			}
		}
		if(c==null){
			System.out.println("\tInvalid Card ID... please try again later...");
			return;
		}
		for(Board b: boards.values()){
			if(b.lists.containsKey(listID)){
				b.lists.get(listID).cards.put(c.id, c);
				return;
			}
		}
		System.out.println("\tInvalid List ID... please try again later...");
	}
	public void updateDescription(int cardID, String description){
		Card c = null;
	out:for(Board b: boards.values()){
			for(MyList l : b.lists.values()){
				if(l.cards.containsKey(cardID)){
					c = l.cards.get(cardID);
					break out;
				}
			}
		}
		if(c==null){
			System.out.println("\tInvalid Card ID... please try again later...");
			return;
		}
		c.description = description;
	}
	public void window() throws Exception{
		System.out.print("\n\tWelcome to MyTrello, please enter a command: ");
		String choice = "";
		//sc.nextLine();
		choice = sc.nextLine();
		while(!choice.equalsIgnoreCase("exit"))
		{			
			
			String[] command = choice.split(" ");
			
			
			switch(command[0]){
				
				case "SHOW":
				if(command.length==1){
					mt.show();
					break;
				}
				switch(command[1]){
					
					case "BOARD":
					//System.out.print("\n\tEnter board ID: ");
					int boardID = Integer.parseInt(command[2]);
					mt.showBoard(boardID);
					break;
					
					case"LIST":
					//System.out.print("\n\tEnter list ID: ");
					int listID = Integer.parseInt(command[2]);
					mt.showList(listID);
					break;
					
					case "CARD":
					//System.out.print("\n\tEnter card ID: ");
					int cardID = Integer.parseInt(command[2]);
					mt.showCard(cardID);
					break;
					
					default:
					//System.out.println("\n\tPlease enter a valid command...");
					break;
				}
				break;
				
				case "BOARD":
				switch(command[1]){
					case "CREATE": 
					mt.createBoard(command[2]);
					break;
					
					case "DELETE":
					if(boards.containsKey(Integer.parseInt(command[2])))
						mt.boards.remove(Integer.parseInt(command[2]));
					else
						System.out.println("\n\tInvalid board ID...");
					break;
					
				}
				if(command.length<=3)break;
				switch(command[2]){
					case "NAME":
					int boardID = Integer.parseInt(command[1]);
					String newName = "";
					for(int i = 3; i<command.length; i++){
						newName += command[i]+" ";
					}
					mt.updateName('B', boardID, newName);
					break;
					
					case "PRIVACY":
					boardID = Integer.parseInt(command[1]);
					String privacy = command[3];
					mt.updatePrivacy(boardID, privacy);
					break;
					
					case "ADD_MEMBER":
					boardID = Integer.parseInt(command[1]);
					int userID = Integer.parseInt(command[3]);
					mt.addMember(boardID, userID);
					break;
					
					case "REMOVE_MEMBER":
					boardID = Integer.parseInt(command[1]);
					userID = Integer.parseInt(command[3]);
					mt.removeMember(boardID, userID);
					break;
					
				}
				break;
				
				case "LIST":
				switch(command[1]){
					case "CREATE": 
					if(command.length==3){System.out.println("\n\tPlease enter a valid command...");break;}
					mt.createList(Integer.parseInt(command[2]), command[3]);
					break;
					
					case "DELETE":
					for(Board b: boards.values()){
						if(b.lists.containsKey(Integer.parseInt(command[2]))){
							b.lists.remove(Integer.parseInt(command[2]));
							break;
						}
					}
					break;
				}
				if(command.length<=3)break;
				switch(command[2]){
					case "NAME":
					int listID = Integer.parseInt(command[1]);
					String newName = "";
					for(int i = 3; i<command.length; i++){
						newName += command[i]+" ";
					}
					mt.updateName('L', listID, newName);
					break;
					
					default:
					//System.out.println("\n\tPlease enter a valid command...");
					break;
				}
				break;
				
				case "CARD":
				switch(command[1]){
					case "CREATE": 
					if(command.length==3){System.out.println("\n\tPlease enter a valid command...");break;}
					mt.createCard(Integer.parseInt(command[2]), command[3]);
					break;
					
					case "DELETE":
				out:for(Board b: mt.boards.values()){
						for(MyList l : b.lists.values()){
							if(l.cards.containsKey(Integer.parseInt(command[2]))){
								l.cards.remove(Integer.parseInt(command[2]));
								break out;
							}
						}
					}
					break;
					
				}
				if(command.length<=3 && !command[2].equals("UNASSIGN"))break;
				switch(command[2]){
					
					case "UNASSIGN":
					mt.toggleUser(Integer.parseInt(command[1]), "", false);
					break;
					
					case "NAME":
					int cardID = Integer.parseInt(command[1]);
					String newName = "";
					for(int i = 3; i<command.length; i++){
						newName += command[i]+" ";
					}
					mt.updateName('C', cardID, newName);
					break;
					
					case "DESCRIPTION":
					cardID = Integer.parseInt(command[1]);
					String description = "";
					for(int i = 3; i<command.length; i++){
						description += command[i]+" ";
					}
					mt.updateDescription(cardID, description);
					break;
					
					case "ASSIGN":
					mt.toggleUser(Integer.parseInt(command[1]), command[3], true);
					break;
					
					case "MOVE":
					mt.moveCard(Integer.parseInt(command[1]), Integer.parseInt(command[3]));
					break;
					
				}
				break;
				
				case "USER":
				System.out.print("\n\tEnter name: ");
				String name = sc.next();
				System.out.print("\n\tEnter email: ");
				String email = sc.next(); 
				mt.createUser(name, email);
				break;
				
				default:
				//System.out.println("\n\tPlease enter a valid command...");
			}
			System.out.print("\n\tWelcome to MyTrello, please enter a command: ");
			sc.nextLine();
			System.out.print("\t");
			choice = sc.nextLine();
		}
		mt.createTrelloFile();
	}
	public void createTrelloFile(){
		try{
		File f = new File("MyTrello.mt");
		f.createNewFile();
		FileOutputStream fos = new FileOutputStream(f);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(this);
		}catch(Exception e){e.printStackTrace();}
	}
}
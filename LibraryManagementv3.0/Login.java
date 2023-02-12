import java.util.*;
import java.io.*;
class Login implements Serializable{
	static Scanner sc = new Scanner(System.in);
	private static final long serialVersionUID = 921l;
	static boolean libNotCreated = true;
	static Library lib1;
	
	public static void start(){
		
		if(libNotCreated){
			String id = sc.next();
			int rackCount = sc.nextInt();
			lib1 = new Library(id, rackCount);
			try{
				lib1.createDatabase();
			}catch(Exception e){e.printStackTrace();}
			System.out.println("Created library "+id+" with "+rackCount+" racks");
			libNotCreated = false;
		}
		else
			System.out.println("Please enter valid command");
	} 
	public static void openLibrary(){
		String s = sc.next();
		try{
			File f = new File(s+".lib");
			FileInputStream ff = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(ff);
			if(f.exists()){
				lib1 = (Library)ois.readObject();
				System.out.println("Library "+lib1.libraryID+" with "+lib1.rackCount+" racks is active now");
			}
			else{
				System.out.println("File not found, enter correct libraryID");
			}
		}catch(Exception e){e.printStackTrace();}
	}
	public static void entry(){
		String id = "";
		String pass = "";
		String name = "";
		
		System.out.println(
		"\n\t1. Sign up as new user  : new_user"+
		"\n\t2. Sign in as user      : user_login"+
		"\n\t3. Sign in as admin     : admin_login"+
		"\n\t4. Create Admin         : create_admin"+
		"\n\t5. Create Library       : create_library <library_id> <no. of racks>"+
		"\n\t6. Open Existing Library: open_library <file_name>"+
		"\n\t7. Exit                 : exit");
		
		String s = sc.next();
		while(!s.equals("exit")){
			
			switch(s){
				
				case "new_user":
				if(lib1!=null){
					System.out.println("Enter user ID: ");
					id = sc.next();
					System.out.println("Enter name");
					name = sc.next();
					System.out.println("Enter password");
					pass = sc.next();
					lib1.addUser(id, name, pass);
					
				}
				else 
					System.out.println("Create a library first");
				break;
			
				case "admin_login":
				case "user_login":
				if(lib1!=null){
					System.out.println("Enter user ID: ");
					id = sc.next();
					System.out.println("Enter password: ");
					pass = sc.next();
					lib1.displayPrompt(id, pass);
				}
				else 
					System.out.println("Create a library first");
				break;
			
				case "create_admin":
				if(lib1!=null){
					System.out.println("Enter admin ID: ");
					id = sc.next();
					System.out.println("Enter name");
					name = sc.next();
					System.out.println("Enter password");
					pass = sc.next();
					lib1.createAdmin(id, name, pass);
				}
				else 
					System.out.println("Create a library first");
				break;
			
				case "create_library":
				start();
				break;
				
				case "open_library":
				openLibrary();
				break;
			}
			System.out.println(
			"\n\t1. Sign up as new user  : new_user"+
			"\n\t2. Sign in as user      : user_login"+
			"\n\t3. Sign in as admin     : admin_login"+
			"\n\t4. Create Admin         : create_admin"+
			"\n\t5. Create Library       : create_library <library_id> <no. of racks>"+
			"\n\t6. Open Existing Library: open_library <file_name>"+
			"\n\t7. Exit                 : exit");
			s = sc.next();
		}
		System.out.println(
		"\n\tDo you want to save the library ?"+
		"\n\t1. Yes : Y/y"+
		"\n\t2. No  : N/n");
		s = sc.next();
		if(s.equals("Y") || s.equals("y")){
			try{
				if(lib1!=null)
					lib1.createLibraryFile();
			}catch(Exception e){e.printStackTrace();}
		}
	}
}
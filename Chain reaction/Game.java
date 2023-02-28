import java.util.*;
class Game{
	static Scanner sc = new Scanner(System.in);
	static String currentFX = "";
	
	int m;
	int n;
	String[][] grid;
	char[] players;
	boolean firstCycleDone = false;
	String[] fx; /// for color effects
	// char assigned -> X O Z G N 8 # $
	Game(int r, int c, int no){
		m = r;
		n = c;
		grid = new String[m][n];
		for(String[] a: grid)
			Arrays.fill(a, "");
		players = new char[no];
		switch(no){
			case 8:
			this.players[7] = '9';
			case 7:
			this.players[6] = '7';
			case 6:
			this.players[5] = 'G';
			case 5:
			this.players[4] = 'Z';
			case 4:
			this.players[3] = '$';
			case 3:
			this.players[2] = '#';
			case 2:
			this.players[1] = 'O';
			this.players[0] = 'X';
		}
		fx = new String[8];///{"color b0", "color c0", "color e0", "color f0", "color 90", "color ob", "color 0c", "color 0a"};
		fx[0] = "color 0b";
		fx[1] = "color 0c";
		fx[2] = "color 06";
		fx[3] = "color 03";
		fx[4] = "color 0a";
		fx[5] = "color 0e";
		fx[6] = "color b0";
		fx[7] = "color e0";
		display("color");
	}
	
	public static void start(int row, int col, int no){
		Game g = new Game(row, col, no);
		int turn  = 0;
		
		while(!g.checkWin(g.firstCycleDone)){
			
			char c = g.players[turn];
			if(c=='~'){
				turn = (turn+1)%g.players.length;
				continue;
			}
			
			String once = c+"";
			String twice = c+""+c;
			String thrice = c+""+c+""+c;
			System.out.println("\tPlayer "+(g.players[turn])+"'s turn: ");
			System.out.print("\tEnter i: ");
			int i = sc.nextInt() - 1;
			System.out.print("\tEnter j: ");
			int j = sc.nextInt() - 1;
			while(i<0 || j<0 || i>=g.m || j>=g.n || (!g.grid[i][j].equals("") && !g.grid[i][j].equals(once) && !g.grid[i][j].equals(twice) && !g.grid[i][j].equals(thrice))){
				System.out.println("\n\tInvalid i or(or and) j... please try again...");
				System.out.print("\tEnter i: ");
				i = sc.nextInt() - 1;
				System.out.print("\tEnter j: ");
				j = sc.nextInt() - 1;
			}
			g.fill(g.players[turn], i, j);
			for(int k=0; k<g.players.length; k++)
				g.checkIfDead(k, g.firstCycleDone);
			if(turn ==g.players.length-1)g.firstCycleDone = true;
			turn = (turn+1)%g.players.length;
			currentFX = g.fx[turn];
			g.display(currentFX);
			
		}
		char x = g.getWinner();
		System.out.println("\tPlayer "+x+" won !!!\n\n\tPress Enter to Exit...");
		sc.nextLine();
		sc.nextLine();
		try{
			new ProcessBuilder("cmd", "/c", "color 07").inheritIO().start().waitFor();
		}
		catch(Exception e){e.printStackTrace();}
		
		
	}
	public char getWinner(){
		for(int i=0; i<m; i++){
			for(int j=0; j<n; j++){
				if(grid[i][j]!="")
					return grid[i][j].charAt(0);
			}
		}
		return 'V';
	}
	public void fill(char c, int i, int j){
		try{
			Thread.sleep(69);
		}catch(Exception e){}
		display(currentFX);
		if(checkWin(firstCycleDone))return;
		if(i<0 || j<0 || i>=m || j>=n)return;
		String position = "";
		if((i==0 && j==0) || (i==0 && j==n-1) || (i==m-1 && j==0) || (i==m-1 && j==n-1))
			position = "corner";
		else if(i==0 || j==0 || j==n-1 || i==m-1)
			position = "edge";
		else
			position = "center";
		
		String temp ="";
		switch(position){
			
			case "corner":
			if(grid[i][j].length()==1){
				grid[i][j] = "";
				fill(c, i, j+1);
				fill(c, i, j-1);
				fill(c, i+1, j);
				fill(c, i-1, j);
			}
			else{
				for(int k =0; k<grid[i][j].length(); k++)
					temp += c;
				grid[i][j] = temp+c;
			}
			break;
			
			case "edge":
			if(grid[i][j].length()==2){
				grid[i][j] = "";
				fill(c, i, j+1);
				fill(c, i, j-1);
				fill(c, i+1, j);
				fill(c, i-1, j);
			}
			else{
				for(int k =0; k<grid[i][j].length(); k++)
					temp += c;
				grid[i][j] = temp+c;
			}
			break;
			
			case "center":
			if(grid[i][j].length()==3){
				grid[i][j] = "";
				fill(c, i, j+1);
				fill(c, i, j-1);
				fill(c, i+1, j);
				fill(c, i-1, j);
			}
			else{
				for(int k =0; k<grid[i][j].length(); k++)
					temp += c;
				grid[i][j] = temp+c;
			}
			break;
			
		}
	}
	public boolean checkWin(boolean firstCycleDone){
		if(!firstCycleDone)return false;
		char first = (char)0;
	out:for(int i=0; i<m; i++){
			for(int j=0; j<n; j++){
				if(!grid[i][j].equals(""))
					first = grid[i][j].charAt(0);
					break out;
			}
		}
		for(int i=0; i<m; i++){
			for(int j=0; j<n; j++){
				if(!grid[i][j].equals("") && !grid[i][j].contains(first+""))
					return false;
			}
		}
		return true;
	}
	public void checkIfDead(int ind, boolean firstCycleDone){
		if(!firstCycleDone)return;
		char c = players[ind];
		boolean dead = true;
		for(int i=0; i<m; i++){
			for(int j=0; j<n; j++){
				if(grid[i][j].contains(c+""))
					dead = false;
			}
		}
		if(dead)
			players[ind] = '~';
	}
	public void display(String s){
		
		try{
			new ProcessBuilder("cmd", "/c", s).inheritIO().start().waitFor();
			//Thread.sleep(300);
		}
		catch(Exception e){e.printStackTrace();}
		try{
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		}
		catch(Exception e){e.printStackTrace();}
		System.out.println("\n");
		for(int i=0; i<m; i++){
			System.out.print("\t+");
			for(int c=0; c<n; c++)
				System.out.print("-----+");
			System.out.print("\n\t|");
			for(int j=0; j<n; j++){
				System.out.print(String.format(" %3s |", grid[i][j]));
			}
			System.out.println();
		}
		System.out.print("\t+");
		for(int c=0; c<n; c++)
			System.out.print("-----+");
		System.out.print("\n\n");
	}
}
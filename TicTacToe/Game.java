import java.util.*;
class Game{
	static Scanner sc = new Scanner(System.in);
	char[][] board;
	String p1;
	String p2;
	
	Game(String p1, String p2){
		board = new char[4][4];
		this.p1 = p1;
		this.p2 = p2;
		for(int i=1; i<4; i++)
			Arrays.fill(board[i], '-');
		System.out.println("Player 1: "+p1);
		System.out.println("Player 2: "+p2);
		display();
		startGame();
	}
	public void startGame(){
		String temp = this.p2; 
		String s = "";
		String ans = "";
		boolean exit = false;
		while((!checkGameOver() && !checkWin()) || !s.equalsIgnoreCase("exit")){
			char c = 'a';
			if(temp.equals(this.p2)){
				temp = this.p1;
				c = 'X';
			}
			else{
				temp = this.p2;
				c = 'O';
			}
			System.out.print("\t"+temp+"'s turn: ");
			s = sc.nextLine();
			if(s.equalsIgnoreCase("exit")){
				exit = true;
				break;
			}
			String[] nums = s.split(" ");
			int i = Integer.parseInt(nums[0]);
			int j = Integer.parseInt(nums[1]);
			while(i>3 || j>3 || i<1 || j<1 || board[i][j]!='-'){
				System.out.println("\tInvalid move");
				System.out.print("\t"+temp+"'s turn: ");
				s = sc.nextLine();
				if(s.equalsIgnoreCase("exit"))
					break;
				nums = s.split(" ");
				i = Integer.parseInt(nums[0]);
				j = Integer.parseInt(nums[1]);
			}
			board[i][j] = c;
			
			display();
			if(checkWin() || checkGameOver()){
				break;
			}
		}
		
		boolean rowCheck = true;
		for(int i=1; i<4 ;i++){
			rowCheck=true;
			char c = 'X';
			for(int j=1; j<4 ;j++){
				if(board[i][j]!= c)
					rowCheck = false;
			}
			if(rowCheck)
				break;
		}
		boolean colCheck=true;
		for(int j=1; j<4 ;j++){
			colCheck=true;
			char c = 'X';
			for(int i=1; i<4 ;i++){
				if(board[i][j]!= c)
					colCheck = false;
			}
			if(colCheck)
				break;
		}
		boolean diaCheck = (board[1][1]=='X' && board[2][2]=='X' && board[3][3]=='X') || 
						   (board[3][3]=='X' && board[2][2]=='X' && board[1][1]=='X');
						   
		boolean rowCheck2 = true;
		for(int i=1; i<4 ;i++){
			rowCheck2=true;
			char c = 'O';
			for(int j=1; j<4 ;j++){
				if(board[i][j]!= c)
					rowCheck2 = false;
			}
			if(rowCheck2)
				break;
		}
		boolean colCheck2=true;
		for(int j=1; j<4 ;j++){
			colCheck2=true;
			char c = 'O';
			for(int i=1; i<4 ;i++){
				if(board[i][j]!= c)
					colCheck2 = false;
			}
			if(colCheck2)
				break;
		}
		boolean diaCheck2 = (board[1][1]=='O' && board[2][2]=='O' && board[3][3]=='O') || 
						   (board[3][3]=='O' && board[2][2]=='O' && board[1][1]=='O');
		//System.out.println("r -> "+rowCheck+" c -> "+colCheck+" d -> "+diaCheck);
		if(rowCheck || colCheck || diaCheck){
			System.out.println(p1+" won the game");
			return;
		}
		else if(rowCheck2 || colCheck2 || diaCheck2){
			System.out.println(p2+" won the game");
			return;
		}
		if(checkGameOver() || s.equalsIgnoreCase("exit")){
			System.out.println("Game Over");
			return;
		}
	}
	public void display(){
		try{
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		}catch(Exception e){}
		System.out.print("\n\t+---+---+---+");
		for(int i=1; i<4; i++){
			System.out.print("\n\t");
			System.out.print("| ");
			for(int j=1;j<4 ;j++)
				System.out.print(board[i][j]+" | ");
			System.out.print("\n\t+---+---+---+");
		}
		System.out.println("\n");
	}
	public boolean checkGameOver(){
		boolean noSpotLeft = true;
		for(int i=1; i<4 ; i++){
			for(int j=1; j<4; j++){
				if(board[i][j]=='-')
					noSpotLeft = false;
			}
		}
		return noSpotLeft;
	}
	public boolean checkWin(){
		boolean rowCheck=true;
		for(int i=1; i<4 ;i++){
			rowCheck=true;
			char c = board[i][1];
			if(c=='-')
				c = 'A';
			for(int j=2; j<4 ;j++){
				if(board[i][j]!= c)
					rowCheck = false;
			}
			if(rowCheck)
				break;
		}
		boolean colCheck=true;
		for(int j=1; j<4 ;j++){
			colCheck=true;
			char c = board[1][j];
			if(c=='-')
				c = 'A';
			for(int i=2; i<4 ;i++){
				if(board[i][j]!= c)
					colCheck = false;
			}
			if(colCheck)
				break;
		}
		boolean diaCheck = (board[1][1]=='X' && board[2][2]=='X' && board[3][3]=='X') || 
						   (board[1][1]=='O' && board[2][2]=='O' && board[3][3]=='O') ||
						   (board[3][3]=='X' && board[2][2]=='X' && board[1][1]=='X') ||
						   (board[3][3]=='O' && board[2][2]=='O' && board[1][1]=='O');
		//System.out.println("r -> "+rowCheck+" c -> "+colCheck+" d -> "+diaCheck);
		return (rowCheck || colCheck || diaCheck);
	}
	public static void main(String[] args){
		String s1 = sc.nextLine();
		String s2 = sc.nextLine();
		String[] x = s1.split(" ");
		String[] o = s2.split(" ");
		Game g1 = new Game(x[1], o[1]);
	}
}
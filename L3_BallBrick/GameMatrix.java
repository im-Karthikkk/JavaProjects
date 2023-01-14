import java.util.Scanner;
import java.io.*;
class GameMatrix{
	
	static Scanner sc = null;
	static Scanner ip = new Scanner(System.in);
	String[][] board;
	int ballCount;
	int ballRow;
	int ballColumn;
	int baseLeft;
	int baseRight;
	boolean nextRight;
	boolean baseIsCounted;
	int brickStrength;
	boolean rCreated;
	{
		try{
			sc = new Scanner(new File("input.txt"))
		}
		catch(Exception e){}
	}
	GameMatrix(int n){
		board = new String[n][n];
		this.ballCount=0;
		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				if(i==0 || j==0 || i==n-1 || j==n-1)
					board[i][j] = ""+'W';
			}
		}
		for(int j=1; j<n-1; j++)
			board[n-1][j]='G'+"";
		this.ballRow = n-1;
		this.ballColumn = n/2;
		baseLeft = n/2;
		baseRight = n/2;
		nextRight = true;
		baseIsCounted=false;
		brickStrength=0;
		rCreated = false;
		board[ballRow][ballColumn]='o'+"";
	}
	public static void startGame(){
		System.out.print("Enter size of the NxN matrix: ");
		int n =sc.nextInt();
		GameMatrix gm = new GameMatrix(n);
		gm.setBoard();
		gm.traversal();	
	}
	public void display(){
		for(int i=0; i<board.length; i++){
			for(int j=0; j<board[0].length; j++){
				if(board[i][j]!= null){
					if(board[i][j].length()==2)
						System.out.print(board[i][j]+" ");
					else
						System.out.print(board[i][j]+"  ");
				}
				else
					System.out.print("   ");
			}
			System.out.println();
		}
		System.out.println("Ball Count is "+this.ballCount);
	}
	public void setBoard(){
		System.out.print("Enter the bricks position and the brick type: ");
		int i = sc.nextInt();
		int j = sc.nextInt();
		String ch = sc.next();
		board[i][j] = ch;
		System.out.println("Do you want to continue(Y or N)?");
		char choice = sc.next().charAt(0);
		while(choice == 'Y' || choice =='y'){
			System.out.print("Enter the bricks position and the brick type: ");
			i = sc.nextInt();
			j = sc.nextInt();
			ch = sc.next();
			if(ch =="R"){
				if(!rCreated)
					board[i][j] = ch;
				else
					System.out.println("Can't add any more R bricks");
			}
			else
				board[i][j]=ch;
			System.out.println("Do you want to continue(Y or N)?");
			choice = sc.next().charAt(0);
		}
		System.out.println("Enter ball count: ");
		this.ballCount = sc.nextInt();
		this.display();
	}
	public void updateBallPosition(int i, int j, boolean lost){
		
		if(lost){
				this.ballRow = board.length-1;
				this.ballColumn = board.length/2;
				ballCount -=1;
				return;
		}
		if(board[i][j]!=null && !board[i][j].equals("o")){
			if(!baseIsCounted && !board[i][j].equals("_"))
				ballCount -= 1;
			else 
				return;
		}
		this.ballRow = i;
		this.ballColumn = j;
		for(j=1; j<board.length-1; j++)
			board[board.length-1][j]="G";
		if(baseIsCounted){
			for(int jj = baseLeft; jj<=baseRight; jj++)
				board[ballRow][jj] = "_";
		}
		board[ballRow][ballColumn]="o";
	}
	public boolean checkIfWon(){
		boolean won = true;
		for(int i=1; i<board.length-1; i++){
			for(int j=1; j<board.length-1; j++){
				if(board[i][j]!=null)
					won = false;
			}
		}
		return won;
	}
	public void powerBrick(String br, int nextRow, int nj, boolean direct){
		switch(br){
			case "DE":
				for(int jj=1; jj<board.length-1; jj++){
					board[nextRow][jj] = null;
				}
			break;
			
			case "DS":
				board[nextRow][nj] = null;
				nj -= 1;
				nextRow += 1;
				if(board[nextRow][nj]!=null && !board[nextRow][nj].equals("W") && !board[nextRow][nj].equals("G") && !board[nextRow][nj].equals("o"))
					board[nextRow][nj] = null;
				nextRow -= 1;
				if(board[nextRow][nj]!=null && !board[nextRow][nj].equals("W") && !board[nextRow][nj].equals("G") && !board[nextRow][nj].equals("o"))
					board[nextRow][nj] = null;
				nextRow -= 1;
				if(board[nextRow][nj]!=null && !board[nextRow][nj].equals("W") && !board[nextRow][nj].equals("G") && !board[nextRow][nj].equals("o"))
					board[nextRow][nj] = null;
				nj += 1;
				if(board[nextRow][nj]!=null && !board[nextRow][nj].equals("W") && !board[nextRow][nj].equals("G") && !board[nextRow][nj].equals("o"))
					board[nextRow][nj] = null;
				nj += 1;
				if(board[nextRow][nj]!=null && !board[nextRow][nj].equals("W") && !board[nextRow][nj].equals("G") && !board[nextRow][nj].equals("o"))
					board[nextRow][nj] = null;
				nextRow += 1;
				if(board[nextRow][nj]!=null && !board[nextRow][nj].equals("W") && !board[nextRow][nj].equals("G") && !board[nextRow][nj].equals("o"))
					board[nextRow][nj] = null;
				nextRow += 1;
				if(board[nextRow][nj]!=null && !board[nextRow][nj].equals("W") && !board[nextRow][nj].equals("G") && !board[nextRow][nj].equals("o"))
					board[nextRow][nj] = null;
				nj -= 1;
				if(board[nextRow][nj]!=null && !board[nextRow][nj].equals("W") && !board[nextRow][nj].equals("G") && !board[nextRow][nj].equals("o"))
					board[nextRow][nj] = null;
				nextRow -=1;
			break;
			
			case "B":
			board[nextRow][nj] = null;
			baseIsCounted =true;
				if(nextRight){
					this.baseRight++;
					nextRight = false;
				}
				else{
					this.baseLeft--;
					nextRight = true;
				}
			System.out.println("bR -> "+baseRight+" bL -> "+baseLeft);
			break;
				
			case "N":
			board[nextRow][nj] = null;
			if(nextRow-1>0)
				powerBrick(board[nextRow-1][nj], nextRow-1, nj, false);
			break;
			
			case "E":
			board[nextRow][nj] = null;
			if(nj+1<board.length-1)
				powerBrick(board[nextRow][nj+1], nextRow, nj+1, false);
			break;
			
			case "WE":
			board[nextRow][nj] = null;
			if(nj-1>0)
				powerBrick(board[nextRow][nj-1], nextRow, nj-1, false);
			break;
			
			case "S":
			board[nextRow][nj]=null;
			if(nextRow+1<board.length-1)
				powerBrick(board[nextRow+1][nj], nextRow+1, nj, false);
			break;
			
			case "D":
			board[nextRow][nj] = null;
			ballCount++;
			break;
			
			case "R":
			if(direct)
				brickStrength++;
			else 
				board[nextRow][nj] = null;
			break;
			
			default:
			board[nextRow][nj] = null;
			break;
		}		
	}
	public void traversal(){
		System.out.print("Enter the direction in which the ball needs to travel: ");
		String direction = ip.next();
		boolean lost = true;
		int nextRow=0; 
		boolean notFirstTime=false;
		int nextCol =0;
		boolean pathClear = true;
		while(!direction.equals("stop")){
			
			if(ballCount==0){
				System.out.println("GAME OVER..!!!");
				break;
			}
			if(notFirstTime){
				System.out.print("Enter the direction in which the ball needs to travel: ");
				direction = ip.next();
			}
			switch(direction){
			
				case "ST":
				for(int i=ballRow-1; i>0; i--){
					if(board[i][ballColumn]!=null){
						try{	
							if(Integer.parseInt(board[i][ballColumn])==1){
								board[i][ballColumn] = null;
							}
							else{ 
								board[i][ballColumn] = ""+(Integer.parseInt(board[i][ballColumn])-1);
							}
						}catch(NumberFormatException e){
							powerBrick(board[i][ballColumn], i, ballColumn, true);
						}
						updateBallPosition(ballRow, ballColumn, false);
						break;
					}
				}
				//display();
				break;
			
				case "LD":										// LEFT DIAGONAL
				nextRow = ballRow-1;
				nextCol = ballColumn-1;
				pathClear =true;
				while(nextCol>0 && nextRow>0){
					if(board[nextRow][nextCol]!=null && !board[nextRow][nextCol].equals("W")){
						pathClear = false;
						break;
					}
					nextCol--;
					nextRow--;
				}
				if(pathClear){
					//System.out.println("Path Clear");
					nextRow = this.ballRow - this.ballColumn;
					nextCol = 0;
					lost = true;
					for(int j=1; j<board.length-1; j++){
						if(board[nextRow][j]!=null){
							try{
								if(Integer.parseInt(board[nextRow][j])==1){
									board[nextRow][j] = null;
								}
								else{ 
									board[nextRow][j] = ""+(Integer.parseInt(board[nextRow][j])-1);
								}
							}catch(NumberFormatException e){
								powerBrick(board[nextRow][j], nextRow, j, true);
							}
							nextCol = j;
							lost = false;
							break;
						}
					}
					if(nextCol==0)
						nextCol++;
				}
				else{
					//System.out.println("Path NOT Clear");
					board[nextRow][nextCol] = null;
					lost = false;
				}
				updateBallPosition(ballRow, nextCol, lost);
				//display();
				break;
			
				case "RD":									// RIGHT DIAGONAL
				nextRow = ballRow-1;
				nextCol = ballColumn+1;
				pathClear =true;
				while(nextCol<board.length-1){
					if(board[nextRow][nextCol]!=null && !board[nextRow][nextCol].equals("W")){
						pathClear = false;
						break;
					}
					nextCol++;
					nextRow--;
				}
				if(pathClear){
					nextRow = this.ballColumn;
					nextCol = 0;
					lost = true;
					for(int j =board.length-2; j>0; j--){
						if(board[nextRow][j]!= null){
							try{	
								if(Integer.parseInt(board[nextRow][j])==1){
									board[nextRow][j] = null;
								}
								else{ 
									board[nextRow][j] = ""+(Integer.parseInt(board[nextRow][j])-1);
								}
							}catch(NumberFormatException e){
								powerBrick(board[nextRow][j], nextRow, j, true);
							}
							nextCol = j;
							lost = false;
							break;
						}
					}
				}
				else{
					board[nextRow][nextCol] = null;
					lost = false;
				}
				updateBallPosition(ballRow, nextCol, lost);
				//display();
				break;
			}
			if(checkIfWon()){
				display();
				System.out.println("YOU WON ... HURRAYYY..!!!");
				break;
			}
			
			if(shiftDown()){
				display();
				System.out.println("GAME OVER ..!!");
				break;
			}
			display();
			notFirstTime = true;
		}
	}
	public boolean shiftDown(){
		boolean lose = false;
		for(int jj=1; jj<board.length-1; jj++){
			if(board[board.length-2][jj]!=null)
				lose = true;
		}
		for(int p=board.length-3; p>0; p--){
				for(int q=1; q<board.length-1; q++){
					board[p+1][q] = board[p][q];
				}
			}
			for(int jj=1; jj<board.length-1; jj++){
				board[1][jj]=null;
			}
		if(lose)
			return true;
		else
			return false;
	}
	public static void main(String[] args){
		startGame();
	}
}
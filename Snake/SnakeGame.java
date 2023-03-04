import java.util.*;
import java.io.*;
class SnakeGame{
	static Scanner sc = new Scanner(System.in);
	static String[] names;
	static Integer[] scores;

	static{
		try{
			File f = new File("HS_names.txt");			
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);
			names = (String[])ois.readObject();	
			
			f = new File("HS_scores.txt");			
			fis = new FileInputStream(f);
			ois = new ObjectInputStream(fis);
			scores = (Integer[])ois.readObject();	
			//System.out.println("\tScore table active now...");
			//Thread.sleep(1000);
			int n = 5;
			for(int i=0; i<n-1; i++){
			for(int j=0; j<n-i-1; j++){
				if(scores[j] > scores[j+1]){
					int temp = scores[j];
					scores[j] = scores[j+1];
					scores[j+1] = temp;
					String temp2 = names[j];
					names[j] = names[j+1];
					names[j+1] = temp2;
				}
			}
		}
		}
		catch(Exception e){}
	}
	
	int m;
	int n;
	char[][] grid;
	int[] fruit;
	ArrayList<int[]> blocks;
	int fruitsEaten;
	LinkedList<int[]> snake;
	int mode;
	
	
	SnakeGame(int m, int n, int mode){
		this.m = m;
		this.n = n;
		this.mode = mode;
		grid = new char[m][n];
		fruit = new int[2];
		blocks = new ArrayList<>();
		fruitsEaten = 0;
		for(int i=0; i<m; i++)
			Arrays.fill(grid[i], ' ');
		snake = new LinkedList<>();
		int i = 1;
		int j = n/2;
		int len = mode==2?3:9;
		for(int k=0; k<len; k++){
			j += 1;
			//System.out.println("\ti -> "+i+" j -> "+j);
			snake.add(new int[]{i, j});
			if(snake.size()==1)
				grid[i][j] = 'X';
			else
				grid[i][j] = 'O';
		}
		generateFruit();
		display();
		play();
	}
	
	void generateBlocks(){
		//blocks.add(new int[]{10, 32});
	}
	
	void updateBoard(){
		for(int i=0; i<m; i++)
			Arrays.fill(grid[i], ' ');
		grid[fruit[0]][fruit[1]] = '@';
		for(int k=0; k<blocks.size(); k++){
			int[] temp = blocks.get(k);
			grid[temp[0]][temp[1]] = '#';
		}
		for(int k=0; k<snake.size(); k++){
			int[] temp = snake.get(k);
			//System.out.println("i -> "+temp[0]+" j -> "+temp[1]);
			if(k==0)
				grid[temp[0]][temp[1]] = 'X';
			else
				grid[temp[0]][temp[1]] = 'O';
		}
		
	}
	
	void display(){
		try{
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		}catch(Exception e){}
		System.out.print("\n\n\n\t+");
		for(int i=0; i<2*n; i++)
			System.out.print("-");
		System.out.print("+");
		for(int i=0; i<m; i++){
			System.out.print("\n\t|");
			for(int j=0; j<n; j++){
				System.out.print(grid[i][j]+" ");
			}
			System.out.print("|");
		}
		System.out.print("\n\t+");
		for(int i=0; i<2*n; i++)
			System.out.print("-");
		System.out.print("+\n\n\n");
		//System.out.print("size -> "+snake.size());
	}
	
	void play(){
		if(mode==1)generateBlocks();
		long start = System.currentTimeMillis();
		String choice  = sc.next();
		while(!choice.equalsIgnoreCase("exit")){
			for(int i=0; i<choice.length();i++){
				char c = choice.charAt(i);
				if(c!='w' && c!='s' && c!='a' && c!='d')continue;
				if(!crawl(c)){
					try{
					new ProcessBuilder("cmd", "/c", "color C0").inheritIO().start().waitFor();
					}catch(Exception e){}
					System.out.print("\n\tGame Over ...better luck next time\n\tPress enter to exit...");
					sc.nextLine();
					sc.nextLine();
					try{
					new ProcessBuilder("cmd", "/c", "color 07").inheritIO().start().waitFor();
					}catch(Exception e){}
					return;
				}
				display();
				try{Thread.sleep(69);}catch(Exception e){}
			}
			if(fruitsEaten==5 && mode == 1){
				long end = System.currentTimeMillis();
				System.out.println("\n\tYou've completed the game... Time taken : "+((end-start)/1000)+"s");
				updateScores((int)(end-start)/1000);
				break;
			}
			choice = sc.next();
		}
	}
	void updateScores(int time){
		if(time>=scores[4]){
			showScoreTable();
			return;
		}
		int n = 4;
		
		System.out.print("\n\tEnter 'okk' to exit...");
		String dummy = sc.next();
		while(!dummy.equalsIgnoreCase("okk")){
			System.out.print("\n\tEnter 'okk' to exit...");
			dummy = sc.next();
		}
		System.out.print("\n\tNew Record !!! Hurray...Enter your name: ");
		String tempstr = sc.next();
		names[4] = tempstr;
		scores[4] = time;
		//int n = 4;
		for(int i=0; i<n-1; i++){
			for(int j=0; j<n-i-1; j++){
				if(scores[j]> scores[j+1]){
					int temp = scores[j];
					scores[j] = scores[j+1];
					scores[j+1] = temp;
					String temp2 = names[j];
					names[j] = names[j+1];
					names[j+1] = temp2;
				}
			}
		}
		showScoreTable();
	}
	void showScoreTable(){
		System.out.print("\n\t+----------------------------+---------------+");
		System.out.print("\n\t|    Name                    |  Score (secs) |");
		System.out.print("\n\t+----------------------------+---------------+");
		for(int i=0; i<5; i++){
			System.out.print("\n\t"+String.format("|    %-20s    |   %-10d  |", names[i], scores[i]));
			System.out.print("\n\t+----------------------------+---------------+");
		}
		System.out.println("\n\n");
	}
	void generateFruit(){
		ArrayList<int[]> spaces = new ArrayList<>();
		for(int i=0; i<m; i++){
			for(int j=0; j<n; j++){
				if(grid[i][j]==' ')
					spaces.add(new int[]{i,j});
			}
		}
		long ind = System.currentTimeMillis()%spaces.size();
		
		///////////////
		/* 
		int i = (int)System.currentTimeMillis()%m;
		int j = (int)System.currentTimeMillis()%n;
		
		boolean a = (i+1>=m || j+1>=n || j-1<0 || i-1<0) || grid[i-1][j]!=' ' || grid[i+1][j]!=' ';
		boolean b = (i+1>=m || j+1>=n || j-1<0 || i-1<0) || grid[i][j-1]!=' ' || grid[i][j+1]!=' ';
		while(grid[i][j]!=' ' || a || b){
			i = (int)System.currentTimeMillis()%m;
			j = (int)System.currentTimeMillis()%n;
			a = (i+1>=m || j+1>=n || j-1<0 || i-1<0) || grid[i-1][j]!=' ' || grid[i+1][j]!=' ';
			b = (i+1>=m || j+1>=n || j-1<0 || i-1<0) || grid[i][j-1]!=' ' || grid[i][j+1]!=' ';
		} */
		fruit[0] = spaces.get((int)ind)[0];
		fruit[1] = spaces.get((int)ind)[1];
		grid[fruit[0]][fruit[1]] = '@';
	}
	void grow(){
		int[] temp = new int[2];
		int[] tail = snake.peekLast();
		if(tail[0]-1>0 && grid[tail[0]-1][tail[1]]=='O'){  //// ArrayIndexOutOfBoundsException to be handled
			temp[0] = tail[0]+1;
			temp[1] = tail[1];
		}
		else if(tail[0]+1<this.m && grid[tail[0]+1][tail[1]]=='O'){
			temp[0] = tail[0]-1;
			temp[1] = tail[1];
		}
		else if(tail[1]+1<this.n && grid[tail[0]][tail[1]+1]=='O'){
			temp[0] = tail[0];
			temp[1] = tail[1] - 1;
		}
		else if(tail[1]-1>0 && grid[tail[0]][tail[1]-1]=='O'){
			temp[0] = tail[0];
			temp[1] = tail[1] + 1;
		}
		//System.out.println("\ni -> "+temp[0]+" j -> "+temp[1]+"\n");
		if(temp[0]>=this.m)temp[0] = temp[0]%this.m;
		if(temp[1]>=this.n)temp[1] = temp[1]%this.n; 
		if(temp[0]<0)temp[0] = this.m-1;
		if(temp[1]<0)temp[1] = this.n-1;
		snake.add(temp);
	}
	boolean crawl(char c){
		int[] temp = new int[2];
		int[] head = snake.peekFirst();
		switch(c){
			
			case 'w':
			temp[0] = head[0]-1;
			temp[1] = head[1];
			if(temp[0]<0)temp[0] = this.m-1;
			if(grid[temp[0]][temp[1]]=='O')return false;
			snake.addFirst(temp);
			snake.removeLast();
			break;
			
			case 'd':
			temp[0] = head[0];
			temp[1] = head[1]+1;
			if(temp[1]>=this.n)temp[1] = temp[1]%this.n;    ////
			if(grid[temp[0]][temp[1]]=='O')return false;
			snake.addFirst(temp);
			snake.removeLast();
			break;
			
			case 's':
			temp[0] = head[0]+1;
			temp[1] = head[1];
			if(temp[0]>=this.m)temp[0] = temp[0]%this.m;
			if(grid[temp[0]][temp[1]]=='O')return false;
			snake.addFirst(temp);
			snake.removeLast();
			break;
			
			case 'a':
			temp[0] = head[0];
			temp[1] = head[1]-1;
			if(temp[1]<0)temp[1] = this.n-1;
			if(grid[temp[0]][temp[1]]=='O')return false;
			snake.addFirst(temp);
			snake.removeLast();
			break;
		}
		
		head = snake.peekFirst();
		if( head[0]==fruit[0] && head[1]==fruit[1] ){     // snake has eaten the fruit
			fruitsEaten++;
			generateFruit();
			if(this.mode == 2)
				grow();
		}
		updateBoard();
		return true;
	}
	
	public static void main(String[] args){
		System.out.print("\n\tPlease select one option: "+
		"\n\t  1. Sandclock"+
		"\n\t  2. Freestyle\n\t");
		
		
		
		int n = sc.nextInt();
		while(n!=1 && n!=2){System.out.print("\n\tPlease enter a valid input...\n\t");n = sc.nextInt();}
		SnakeGame sg = null;
		
		switch(n){
			case 1: sg = new SnakeGame(30, 72, 1); break;
			
			case 2:
			sg = new SnakeGame(6,12, 2);
			break;
		}
		
		
		
		try{
			File f = new File("HS_names.txt");
			if(!f.exists())f.createNewFile();
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(names);
			f = new File("HS_scores.txt");
			f.createNewFile();
			oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(scores);
		}
		catch(Exception e){}
		
	}
}
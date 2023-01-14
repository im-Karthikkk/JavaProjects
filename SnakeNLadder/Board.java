import java.util.*;
import java.io.*;
class Board{
	static Scanner ip = new Scanner(System.in);
	HashMap<Integer, Integer> snakes;
	HashMap<Integer, Integer> ladders;
	Player[] playerList;
	int index=0;
	//static File f = new File("SnakeNLadderIO.txt");
	//static FileOutputStream fw = new FileOutputStream(f, true);
	
	Board(){
		snakes = new HashMap<>();
		ladders = new HashMap<>();
	}
	
	public void setSnakes(int head, int tail){
		snakes.put(head, tail);
	}
	public void setLadders(int start, int end){
		ladders.put(start, end);
	}
	public void setGame(int n){
		playerList = new Player[n];
	}
	public void addPlayer(String p){
		playerList[index++]= new Player(p);
	}
	
	public void startGame(boolean random) throws Exception{
		File f = new File("SnakeNLadderIO.txt");
		FileOutputStream fw = new FileOutputStream(f, true);
		boolean gameIsOn = true;
		int turn=0;
		int lucky=0;
		while(gameIsOn){
			int diceValue=0;
			if(random){
				diceValue = (int)(1 + Math.round(Math.random()*5));
				fw.write((playerList[turn].name +" rolled a "+diceValue+"\n").getBytes());
			}
			else{
				diceValue = ip.nextInt();
				System.out.println(playerList[turn].name +" rolled a "+diceValue);
				fw.write((playerList[turn].name +" rolled a "+diceValue+"\n").getBytes());
				while(diceValue <=0 || diceValue>6){
					System.out.println("Invalid Dice Input... Please try again");
					fw.write(("Invalid Dice Input... Please try again\n").getBytes());
					diceValue = ip.nextInt();
					fw.write((diceValue+"\n").getBytes());
					System.out.println(playerList[turn].name +" rolled a "+diceValue);
					fw.write((playerList[turn].name +" rolled a "+diceValue+"\n").getBytes());
				}
				if(diceValue==6){
					System.out.println("Congrats roll again...");
					fw.write(("Congrats roll again...\n").getBytes());
					lucky = ip.nextInt();
					fw.write((lucky+"\n").getBytes());
					System.out.println(playerList[turn].name +" rolled a "+lucky);
					fw.write((playerList[turn].name +" rolled a "+lucky+"\n").getBytes());
					while(lucky <=0 || lucky>6){
					System.out.println("Invalid Dice Input... Please try again");
					fw.write("Invalid Dice Input... Please try again\n".getBytes());
					lucky = ip.nextInt();
					fw.write((lucky+"\n").getBytes());
					System.out.println(playerList[turn].name +" rolled a "+lucky);
					fw.write((playerList[turn].name +" rolled a "+lucky+"\n").getBytes());
					}
					diceValue += lucky;
					fw.write((diceValue+"\n").getBytes());
				}
			}
			int pos = 0;
			int oldPos = playerList[turn].position;
			if(playerList[turn].position+diceValue <=100)		// 
				playerList[turn].position += diceValue;
			pos = playerList[turn].position;
			for(Integer k: this.snakes.keySet()){      // check for snake
				if(pos==(k)){
					playerList[turn].position = snakes.get(k);
					pos = playerList[turn].position;
				}
			}
			for(Integer l: this.ladders.keySet()){     // check if stepped on ladder
				if(pos==(l)){
					playerList[turn].position = ladders.get(l);
					pos = playerList[turn].position;
				}
			}
			System.out.println(playerList[turn].name+" rolled a "+diceValue+" and moved from "+oldPos+" to "+playerList[turn].position);
			fw.write((playerList[turn].name+" rolled a "+diceValue+" and moved from "+oldPos+" to "+playerList[turn].position+"\n").getBytes());
			for(Player p: playerList){			// checks if anyone has won
				if(p.position == 100){
					System.out.println(p.name +" wins the game");
					fw.write((p.name +" wins the game"+"\n").getBytes());
					gameIsOn = false;
					break;
				}
			}
			turn = (turn+1)%playerList.length;   // rotate turns
		}
	}
}
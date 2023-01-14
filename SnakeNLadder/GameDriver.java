import java.util.*;
import java.io.*;

class GameDriver{
	
	public static void main(String[] args) throws Exception{
		File f = new File("SnakeNLadderIO.txt");
		f.createNewFile();
		FileOutputStream fw = new FileOutputStream(f, true);
		Scanner ip = new Scanner(System.in);
		Board b1 = new Board();
		System.out.println("Enter no of snakes: ");
		fw.write("Enter no of snakes: \n".getBytes());
		int snakes = ip.nextInt();
		fw.write((snakes+"\n").getBytes());
		System.out.println("Enter snakes head and tail: ");
		fw.write("Enter snakes head and tail: \n".getBytes());
		for(int i=0; i<snakes; i++){
			int head = ip.nextInt();
			int tail = ip.nextInt();
			b1.setSnakes(head, tail);
			fw.write((head+" "+tail+"\n").getBytes());
		}
		System.out.println("Enter no of ladders: ");
		fw.write("Enter no of ladders: \n".getBytes());
		int ladders = ip.nextInt();
		fw.write((ladders+"\n").getBytes());
		System.out.println("Enter ladders start and end: ");
		fw.write("Enter ladders start and end: \n".getBytes());
		for(int i=0; i<ladders; i++){
			int start = ip.nextInt();
			int end = ip.nextInt();
			b1.setLadders(start, end);
			fw.write((start+" "+end+"\n").getBytes());
		}
		System.out.println("Enter no of players: ");
		fw.write("Enter no of players: \n".getBytes());
		int playerCount = ip.nextInt();
		fw.write((playerCount+"\n").getBytes());
		b1.setGame(playerCount);
		System.out.println("Enter players name: ");
		fw.write("Enter players name: ".getBytes());
		for(int i=0; i<playerCount; i++){
			String pName = ip.next();
			fw.write((pName+"\n").getBytes());
			b1.addPlayer(pName);
		}
		System.out.println("Select Dice Value Input: \n\t1.Randomly generated\n\t2.User input" );
		fw.write("Select Dice Value Input: \n\t1.Randomly generated\n\t2.User input".getBytes());
		int dice = ip.nextInt();
		fw.write((dice+"\n").getBytes());
		boolean random = true;
		if(dice == 2)
			random = false;
		b1.startGame(random);
		fw.write("\n\n".getBytes());
	}
}
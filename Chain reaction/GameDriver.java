import java.util.*;
class GameDriver{
	static Scanner sc = new Scanner(System.in);
	public static void main(String[] args){
		System.out.print("Enter rows: ");
		int r = sc.nextInt();
		System.out.print("Enter columns: ");
		int c = sc.nextInt();
		while(r<2 || c<2 || r>12 || c>12){
			System.out.print("Invalid rows or columns...");
			System.out.print("Enter rows: ");
			r = sc.nextInt();
			System.out.print("Enter columns: ");
			c = sc.nextInt();
		}
		System.out.print("Number of players : ");
		int n = sc.nextInt();
		while(n<2 || n>6){
			System.out.println("Only 2 to 6 players allowed...please enter an appropriate value...");
			n = sc.nextInt();
		}
		Game.start(r, c, n);
	}
}
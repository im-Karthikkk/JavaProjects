import java.util.*;
class Cube{
	static Scanner sc = new Scanner(System.in);
	int n;
	int[][] front;
	int[][] right;
	int[][] top;
	int[][] bottom;
	int[][] left;
	int[][] back;
	
	Cube(){
		n = 3;
		front = new int[n][n];
		right = new int[n][n];
		top = new int[n][n];
		bottom = new int[n][n];
		left = new int[n][n];
		back = new int[n][n];
		for(int sw = 1; sw<7; sw++){
			int[][] temp = null;
			switch(sw){
			
			case 1: temp = front;  break;
			case 2: temp = right;  break;
			case 3: temp = top;    break;
			case 4: temp = bottom; break;
			case 5: temp = left;   break;
			case 6: temp = back;   break;

			}
			for(int i=0; i<n; i++){
				for(int j = 0; j<n; j++)
					temp[i][j] = sw;
			}
		}
		start();
		
	}
	
	void rotateOrientation(int see){
		int[][] temp = null;
		switch(see)
		{
			// see top
			case 1:
			temp = front;
			front = top;
			rotate(back);
			rotate(back);
			top = back;
			rotate(bottom);
			rotate(bottom);
			back = bottom;
			bottom = temp;
			rotate(left);
			rotate(right);
			rotate(right);
			rotate(right);
			break;
		
			// see bottom
			case 2:
			temp = front;
			front = bottom;
			rotate(back);
			rotate(back);
			bottom = back;
			rotate(top);
			rotate(top);
			back = top;
			top = temp;
			rotate(right);
			rotate(left);
			rotate(left);
			rotate(left);
			break;
			
			// see left
			case 3:
			temp = front;
			front = left;
			left = back;
			back = right;
			right = temp;
			rotate(top);
			rotate(top);
			rotate(top);
			rotate(bottom);
			break;
			
			// see right
			case 4:
			temp = front;
			front = right;
			right = back;
			back = left;
			left = temp;
			rotate(top);
			rotate(bottom);
			rotate(bottom);
			rotate(bottom);
			break;
			
		}
		
		
	}
	void start(){
		display2();
		//help();
		int choice  = sc.nextInt();
		while(choice != 26){
			switch(choice){
				case 1 : display2();break;
				case 2 : rotateOrientation(1); break; // st -> see top;
				case 3 : rotateOrientation(2); break; // sb -> see bottom;
				case 4 : rotateOrientation(3); break; // sl -> see left;
				case 5 : rotateOrientation(4); break; // sr -> see right;
				case 6 : display2();      break;
				
				case 7 : topRowC();       break; // u
				case 8 : topRowAC();      break; // ux
				case 9 : midRowC();       break; // 
				case 10: midRowAC();      break; // 
				case 11: bottomRowC();    break; // dx
				case 12: bottomRowAC();   break; // d
				
				case 13: leftColC();      break; // lx
				case 14: leftColAC();     break; // l
				case 15: midColC();       break;
				case 16: midColAC();      break;
				case 17: rightColC();     break; // r
				case 18: rightColAC();    break; // rx
				
				case 19: frontC();        break; // f
				case 20: frontAC();       break; // fx 
				case 21: midC();          break;
				case 22: midAC();         break; 
				case 23: backC();         break; // bx
				case 24: backAC();        break; // b
					
				case 25: help();          break;
			}
			display2();
			choice = sc.nextInt();
		}
	}
	void help(){
		System.out.print("\n\tWelcome to Rubiks world...select one option\n"+
		
		"\n\t1.  Display front face                    7.  Rotate top row clockwise"+
		"\n\t2.  Bring top to front                    8.  Rotate top row anti clockwise"+
		"\n\t3.  Bring bottom to front                 9.  Rotate mid row clockwise"+
		"\n\t4.  Bring left to front                   10. Rotate mid row anti clockwise"+
		"\n\t5.  Bring right to front                  11. Rotate bottom row clockwise"+
		"\n\t6.  Display back face                     12. Rotate bottom row anti clockwise\n"+
		
		// "\n\t7.  Rotate top row clockwise"+
		// "\n\t8.  Rotate top row anti clockwise"+
		// "\n\t9.  Rotate mid row clockwise"+
		// "\n\t10. Rotate mid row anti clockwise"+
		// "\n\t11. Rotate bottom row clockwise"+
		// "\n\t12. Rotate bottom row anti clockwise\n"+
		
		"\n\t13. Rotate left column clockwise          19. Rotate front block clockwise"+
		"\n\t14. Rotate left column anti clockwise     20. Rotate front block anti clockwise"+
		"\n\t15. Rotate mid column clockwise           21. Rotate mid block clockwise"+
		"\n\t16. Rotate mid column anti clockwise      22. Rotate mid block anti clockwise"+
		"\n\t17. Rotate right column clockwise         23. Rotate back block clockwise"+
		"\n\t18. Rotate right column anti clockwise    24. Rotate back block anti clockwise\n"+
		
		// "\n\t19. Rotate front block clockwise"+
		// "\n\t20. Rotate front block anti clockwise"+
		// "\n\t21. Rotate mid block clockwise"+
		// "\n\t22. Rotate mid block anti clockwise"+
		// "\n\t23. Rotate back block clockwise"+
		// "\n\t24. Rotate back block anti clockwise\n"+
		
		"\n\t25. Show Commands"+
		"\n\t26. Exit\n\n\t");
	}
	void display(int sw){
		// 1 - front
		// 2 - right
		// 3 - top
		// 4 - bottom
		// 5 - left
		// 6 - back
		int[][] temp = null;
		switch(sw){
			
			case 1: temp = front;  break;
			case 2: temp = right;  break;
			case 3: temp = top;    break;
			case 4: temp = bottom; break;
			case 5: temp = left;   break;
			case 6: temp = back;   break;

		}
		
		System.out.print("\n\t+---+---+---+\n\t");
		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				System.out.print("| "+temp[i][j]+" ");
			}
			System.out.print("|\n\t+---+---+---+\n\t");
		}
	}
	
	void display2(){
		try{
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		}catch(Exception e){}
		System.out.print("\n\t                +---+---+---+");
		for(int i=0; i<n; i++){
			System.out.print("\n\t                ");
			for(int j=0; j<n; j++){
				System.out.print("| "+top[i][j]+" ");
			}
			System.out.print("|");
			System.out.print("\n\t                +---+---+---+");
		}
		System.out.println();
		System.out.print("\n\t+---+---+---+   +---+---+---+   +---+---+---+   +---+---+---+");
		for(int i=0; i<n; i++){
			System.out.print("\n\t");
			
			for(int j=0; j<n; j++){
				System.out.print("| "+left[i][j]+" ");
			}
			System.out.print("|   ");
			
			for(int j=0; j<n; j++){
				System.out.print("| "+front[i][j]+" ");
			}
			System.out.print("|   ");
			
			for(int j=0; j<n; j++){
				System.out.print("| "+right[i][j]+" ");
			}
			System.out.print("|   ");
			
			for(int j=0; j<n; j++){
				System.out.print("| "+back[i][j]+" ");
			}
			System.out.print("|   ");
			
			System.out.print("\n\t+---+---+---+   +---+---+---+   +---+---+---+   +---+---+---+");
		}
		
		
		System.out.println();
		System.out.print("\n\t                +---+---+---+");
		for(int i=0; i<n; i++){
			System.out.print("\n\t                ");
			for(int j=0; j<n; j++){
				System.out.print("| "+bottom[i][j]+" ");
			}
			System.out.print("|");
			System.out.print("\n\t                +---+---+---+");
		}
		System.out.println();
		help();
		//System.out.println();
	}
	void topRowC(){                   // front elements go to the left
		
		int temp1 = front[0][0];
		int temp2 = front[0][1];
		int temp3 = front[0][2];
		
		front[0][0] = right[0][0];
		front[0][1] = right[0][1];
		front[0][2] = right[0][2];
		
		right[0][0] = back[0][0];
		right[0][1] = back[0][1];
		right[0][2] = back[0][2];
		
		back[0][0] = left[0][0];
		back[0][1] = left[0][1];
		back[0][2] = left[0][2];
		
		left[0][0] = temp1;
		left[0][1] = temp2;
		left[0][2] = temp3;
			
		rotate(top);
	}
	
	void midRowC(){						// front elements go to the left
		
		int temp1 = front[1][0];
		int temp2 = front[1][1];
		int temp3 = front[1][2];
		
		front[1][0] = right[1][0];
		front[1][1] = right[1][1];
		front[1][2] = right[1][2];
		
		right[1][0] = back[1][0];
		right[1][1] = back[1][1];
		right[1][2] = back[1][2];
		
		back[1][0] = left[1][0];
		back[1][1] = left[1][1];
		back[1][2] = left[1][2];
		
		left[1][0] = temp1;
		left[1][1] = temp2;
		left[1][2] = temp3;
		
		
		
		
	}
	
	void bottomRowC(){						// front elements go to the left
		
		int temp1 = front[2][0];
		int temp2 = front[2][1];
		int temp3 = front[2][2];
		
		front[2][0] = right[2][0];
		front[2][1] = right[2][1];
		front[2][2] = right[2][2];
		
		right[2][0] = back[2][0];
		right[2][1] = back[2][1];
		right[2][2] = back[2][2];
		
		back[2][0] = left[2][0];
		back[2][1] = left[2][1];
		back[2][2] = left[2][2];
		
		left[2][0] = temp1;
		left[2][1] = temp2;
		left[2][2] = temp3;
		
		
		
		
		
		rotate(bottom);
		rotate(bottom);
		rotate(bottom);
	}
	
	void topRowAC(){  					// front elements go to the right 
		
		int temp1 = front[0][0];
		int temp2 = front[0][1];
		int temp3 = front[0][2];
		
		front[0][0] = left[0][0];
		front[0][1] = left[0][1];
		front[0][2] = left[0][2];
		
		left[0][0] = back[0][0];
		left[0][1] = back[0][1];
		left[0][2] = back[0][2];
		
		back[0][0] = right[0][0];
		back[0][1] = right[0][1];
		back[0][2] = right[0][2];
		
		right[0][0] = temp1;
		right[0][1] = temp2;
		right[0][2] = temp3;
		
		rotate(top);
		rotate(top);
		rotate(top);
	}
	void midRowAC(){  					// front elements go to the right
		
		int temp1 = front[1][0];
		int temp2 = front[1][1];
		int temp3 = front[1][2];
		
		front[1][0] = left[1][0];
		front[1][1] = left[1][1];
		front[1][2] = left[1][2];
		
		left[1][0] = back[1][0];
		left[1][1] = back[1][1];
		left[1][2] = back[1][2];
		
		back[1][0] = right[1][0];
		back[1][1] = right[1][1];
		back[1][2] = right[1][2];
		
		right[1][0] = temp1;
		right[1][1] = temp2;
		right[1][2] = temp3;
	}
	void bottomRowAC(){  					// front elements go to the right
		
		int temp1 = front[2][0];
		int temp2 = front[2][1];
		int temp3 = front[2][2];
		
		front[2][0] = left[2][0];
		front[2][1] = left[2][1];
		front[2][2] = left[2][2];
		
		left[2][0] = back[2][0];
		left[2][1] = back[2][1];
		left[2][2] = back[2][2];
		
		back[2][0] = right[2][0];
		back[2][1] = right[2][1];
		back[2][2] = right[2][2];
		
		right[2][0] = temp1;
		right[2][1] = temp2;
		right[2][2] = temp3;
		
		rotate(bottom);
	}
	
	void leftColC(){ 			// front face elements go to the top
	
		int temp1 = front[0][0];
		int temp2 = front[1][0];
		int temp3 = front[2][0];
		
		front[0][0] = bottom[0][0];
		front[1][0] = bottom[1][0];
		front[2][0] = bottom[2][0];
		
		bottom[0][0] = back[0][0];
		bottom[1][0] = back[1][0];
		bottom[2][0] = back[2][0];
		
		back[0][0] = top[0][0];
		back[1][0] = top[1][0];
		back[2][0] = top[2][0];
		
		top[0][0] = temp1;
		top[1][0] = temp2;
		top[2][0] = temp3;
		
		rotate(left);
		rotate(left);
		rotate(left);
		
	}
	void midColC(){ 					// front face elements go to the top
		
		int temp1 = front[0][1];
		int temp2 = front[1][1];
		int temp3 = front[2][1];
		
		front[0][1] = bottom[0][1];
		front[1][1] = bottom[1][1];
		front[2][1] = bottom[2][1];
		
		bottom[0][1] = back[0][1];
		bottom[1][1] = back[1][1];
		bottom[2][1] = back[2][1];
		
		back[0][1] = top[0][1];
		back[1][1] = top[1][1];
		back[2][1] = top[2][1];
		
		top[0][1] = temp1;
		top[1][1] = temp2;
		top[2][1] = temp3;
		
	}
	void rightColC(){ 					// front face elements go to the top
		
		int temp1 = front[0][2];
		int temp2 = front[1][2];
		int temp3 = front[2][2];
		
		front[0][2] = bottom[0][2];
		front[1][2] = bottom[1][2];
		front[2][2] = bottom[2][2];
		
		bottom[0][2] = back[0][2];
		bottom[1][2] = back[1][2];
		bottom[2][2] = back[2][2];
		
		back[0][2] = top[0][2];
		back[1][2] = top[1][2];
		back[2][2] = top[2][2];
		
		top[0][2] = temp1;
		top[1][2] = temp2;
		top[2][2] = temp3;
		
		rotate(right);
	}
	
	
	void leftColAC(){							// front -> bottom
		
		int temp1 = front[0][0];
		int temp2 = front[1][0];
		int temp3 = front[2][0];
		
		front[0][0] = top[0][0];
		front[1][0] = top[1][0];
		front[2][0] = top[2][0];
		
		top[0][0] = back[0][0];
		top[1][0] = back[1][0];
		top[2][0] = back[2][0];
		
		back[0][0] = bottom[0][0];
		back[1][0] = bottom[1][0];
		back[2][0] = bottom[2][0];
		
		bottom[0][0] = temp1;
		bottom[1][0] = temp2;
		bottom[2][0] = temp3;
		
		rotate(left);
	}
	void midColAC(){							// front -> bottom
		
		int temp1 = front[0][1];
		int temp2 = front[1][1];
		int temp3 = front[2][1];
		
		front[0][1] = top[0][1];
		front[1][1] = top[1][1];
		front[2][1] = top[2][1];
		
		top[0][1] = back[0][1];
		top[1][1] = back[1][1];
		top[2][1] = back[2][1];
		
		back[0][1] = bottom[0][1];
		back[1][1] = bottom[1][1];
		back[2][1] = bottom[2][1];
		
		bottom[0][1] = temp1;
		bottom[1][1] = temp2;
		bottom[2][1] = temp3;
			
	}
	void rightColAC(){							// front -> bottom
		
		int temp1 = front[0][2];
		int temp2 = front[1][2];
		int temp3 = front[2][2];
		
		front[0][2] = top[0][2];
		front[1][2] = top[1][2];
		front[2][2] = top[2][2];
		
		top[0][2] = back[0][2];
		top[1][2] = back[1][2];
		top[2][2] = back[2][2];
		
		back[0][2] = bottom[0][2];
		back[1][2] = bottom[1][2];
		back[2][2] = bottom[2][2];
		
		bottom[0][2] = temp1;
		bottom[1][2] = temp2;
		bottom[2][2] = temp3;
		
		rotate(right);
		rotate(right);
		rotate(right);
		
	}
	
	void frontC(){
		
		int temp1 = top[2][0];
		int temp2 = top[2][1];
		int temp3 = top[2][2];
		
		top[2][0] = left[2][2];
		top[2][1] = left[1][2];
		top[2][2] = left[0][2];
	
		left[0][2] = bottom[0][0];
		left[1][2] = bottom[0][1];
		left[2][2] = bottom[0][2];
		
		bottom[0][0] = right[2][0];
		bottom[0][1] = right[1][0];
		bottom[0][2] = right[0][0];
		
		right[0][0] = temp1;
		right[1][0] = temp2;
		right[2][0] = temp3;
		
		rotate(front);
		
	}
	void midC(){

		int temp1 = top[1][0];
		int temp2 = top[1][1];
		int temp3 = top[1][2];
		
		top[1][0] = left[2][1];
		top[1][1] = left[1][1];
		top[1][2] = left[0][1];
		
		left[0][1] = bottom[1][0];
		left[1][1] = bottom[1][1];
		left[2][1] = bottom[1][2];
		
		bottom[1][0] = right[2][1];
		bottom[1][1] = right[1][1];
		bottom[1][2] = right[0][1];
		
		right[0][1] = temp1;
		right[1][1] = temp2;
		right[2][1] = temp3;
		
	}
	void backC(){
		
		int temp1 = top[0][0];
		int temp2 = top[0][1];
		int temp3 = top[0][2];
		
		top[0][0] = left[2][0];
		top[0][1] = left[1][0];
		top[0][2] = left[0][0];
		
		left[0][0] = bottom[2][0];
		left[1][0] = bottom[2][1];
		left[2][0] = bottom[2][2];
		
		bottom[2][0] = right[2][2];
		bottom[2][1] = right[1][2];
		bottom[2][2] = right[0][2];
		
		right[0][2] = temp1;
		right[1][2] = temp2;
		right[2][2] = temp3;
			
		rotate(back);
		rotate(back);
		rotate(back);
		
	}
	void frontAC(){
		
		int temp1 = top[2][0];
		int temp2 = top[2][1];
		int temp3 = top[2][2];
		
		top[2][0] = right[0][0];
		top[2][1] = right[1][0];
		top[2][2] = right[2][0];
		
		right[0][0] = bottom[0][2]; 
		right[1][0] = bottom[0][1];
		right[2][0] = bottom[0][0];
		
		bottom[0][2] = left[2][2];
		bottom[0][1] = left[1][2];
		bottom[0][0] = left[0][2];
		
		left[2][2] = temp1;
		left[1][2] = temp2;
		left[0][2] = temp3;
		
		rotate(front);
		rotate(front);
		rotate(front);
	}
	void midAC(){
		
		int temp1 = top[1][0];
		int temp2 = top[1][1];
		int temp3 = top[1][2];
		
		top[1][0] = right[0][1];
		top[1][1] = right[1][1];
		top[1][2] = right[2][1];
		
		right[0][1] = bottom[1][2];
		right[1][1] = bottom[1][1];
		right[2][1] = bottom[1][0];
		
		bottom[1][0] = left[0][1];
		bottom[1][1] = left[1][1];
		bottom[1][2] = left[2][1];
		
		left[2][1] = temp1;
		left[1][1] = temp2;
		left[0][1] = temp3;
		
	}
	void backAC(){
		
		int temp1 = top[0][0];
		int temp2 = top[0][1];
		int temp3 = top[0][2];
		
		top[0][0] = right[0][2];
		top[0][1] = right[1][2];
		top[0][2] = right[2][2];
		
		right[0][2] = bottom[2][2];
		right[1][2] = bottom[2][1];
		right[2][2] = bottom[2][0];
		
		bottom[2][0] = left[0][0];
		bottom[2][1] = left[1][0];
		bottom[2][2] = left[2][0];
		
		left[2][0] = temp1;
		left[1][0] = temp2;
		left[0][0] = temp3;
		
		rotate(back);
	}
	
	
	void rotate(int[][] matrix) {
        int n = matrix.length;
        int ni=0; 
        int nj =0;

        int[][] m2 = new int[n][n];
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                ni = j;
                nj = n-1-i;
                m2[ni][nj] = matrix[i][j]; 
            }
        }
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                matrix[i][j] = m2[i][j]; 
            }
        }
    }
	
	public static void main(String[] args){
		Cube c = new Cube();
	}
}
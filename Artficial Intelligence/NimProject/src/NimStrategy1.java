import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * CS 780 [Artificial Intelligence] - Final Project: Nim
 * 
 * @author Jonathan Wong
 * @author Angelica Contero
 * @author Steven Jimenez
 * 
 */
public class Nim {

	static int stacks = 3;
	static int stacksRemaining;
	static int stackSize = 5;
	static int[] muffins;
	static String winner;
	static String[][] board;
	static Scanner scan;

	
	public static void main(String[] args) {

		buildGame();
		printIntro();
		runGame();

	} // main

	public static void runGame() {

		while (true) {

			playerOne();
			if (victory("Player 1"))
				break;
			playerTwo();
			if (victory("Monkey"))
				break;
		}

		System.out.println(winner + " has won the game!");
	} // runGame

	public static void playerOne() {
		int stackChoice = 0;
		int numMuffins = 0;
		System.out.println("Player 1's turn:");
		printBoard();
		//while (true) {
			try {
				System.out.println("Select a stack, 1, 2 or 3: ");
				stackChoice = scan.nextInt();
				while(stackChoice<1 || stackChoice >3){
					System.out.println("You must select stack 1, 2 or 3! Try again:");
					stackChoice = scan.nextInt();
				}
				System.out.println("Choose how many muffins to take: ");
				numMuffins = scan.nextInt(); //WRITE CHECK CODE TO MAKE SURE VALID NUMBER WAS CHOSEN!
				updateBoard(stackChoice,numMuffins);
				System.out.println("Updated Board:");
				printBoard();
			} catch (InputMismatchException e) {
				System.out.println("Choice must be an integer.");
				scan.next();
				//continue;
			}
			System.out.println();
		//}
	} // playerOne

	public static void playerTwo() {
		System.out.println("Monkey's turn...");
		boolean winningBoard = isItAWinningState(); //check if current board is a winning state;
		if(winningBoard){ //then your goal is to make the board a losing state for your opponent
			System.out.println("is winningState so using decision maker");
			DecisionMaker d = new DecisionMaker();
			Gameboard move = new Gameboard();
			move = d.chooseMoveMethod1(muffins, move);
			muffins = move.currentMuffins; //this represents the monkey's turn
			System.out.println("board state is winning state = " + move.winningState);
		}
		else{//otherwise, just take 1 muffin from any available stack with muffins.
			System.out.println("is NOT winningState so making random move");
			Random generator = new Random();
			int num = generator.nextInt(3);
			while(muffins[num]<=0){
				num = generator.nextInt(3);
			}
			muffins[num] -= 1;
		}
			
		System.out.println("Monkey has made his choice!");
		System.out.println("Board after Monkey's move:");
		printBoard();
		System.out.println();
	} // playerTwo

	public static boolean victory(String name) {
		boolean b = true;
		for (int i = 0; i < muffins.length; i++)
			if (muffins[i] > 0)
				b = false;
		if (b) {
			winner = name;
		}
		return b;
	} // victoryCheck
	
	public static void updateBoard(int stack, int muffinsTaken){
		stack = stack - 1;
		muffins[stack] = muffins[stack] - muffinsTaken;
	} //updateBoard

	public static void printBoard() {
		for (int i = 0; i < stacks; i++)
			System.out.print(muffins[i] + " ");
		System.out.println();
	} // printBoard

	public static void printIntro() {
		System.out.println("Nim, version 1.0");
		System.out.println(" Written by Jonathan Wong, Angelica Contero, and Steven Jimenez!");
		System.out.println(" Strategy #1\n");
	} // printIntro

	public static void buildGame() {

		scan = new Scanner(System.in);
		stacksRemaining = stacks;
		muffins = new int[stacks];
		board = new String[stackSize][stacks];
		for (int i = 0; i < muffins.length; i++)
			muffins[i] = stackSize;

		for (int i = 0; i < stacks; i++)
			for (int j = stackSize - 1; j >= 0; j--) {
				board[j][i] = "[o] ";
			}

	} // buildGame
	
	
	/**
	 * Function isItAWinningState is used for the Nim game with 3 stacks of n items. Each stack has n items, it could be unique
	 * or it can also be different
	 *   
	 *  Each parameter is an integer that represents the number of items on each stack
	 *  If the stack is empty then we pass in a 0
	 *  
	 *  The function calculates the xor value using the ^ operator which returns a decimal value
	 *  
	 *  If that value is 0, it is considered a losing state
	 *  If that value is not 0, it is considered a winning state
	 *   
	 * @param stackNumber1   Number of items on stack 1 
	 * @param stackNumber2	 Number of items on stack 2
	 * @param stackNumber3   Number of items on stack 3
	 * @return boolean value, true ~> winning state, false ~> losing state
	 */
	public static boolean isItAWinningState(){
		//If value of operator is greater than 0
		if( (muffins[0]^muffins[1]^muffins[2]) > 0){
			return true;
		}
		//If value of operator is zero
		return false;
	} //isItAWinningState

} // Nim

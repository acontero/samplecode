import java.util.Random;


public class DecisionMaker {
	Gameboard node; //Each gameboard is a state of the muffin stacks, representing a possible move for one's turn

	public DecisionMaker(){
		
	}
	
	public Gameboard chooseMoveMethod1(int[] muffins, Gameboard root){
		//goal is to make next state a losing state (so we want to make all columns 0 in the XOR function
		Gameboard result = new Gameboard();
		int[] testArray = new int[3];
		testArray = resetArray(muffins);
		
		for(int i = 0; i<3; i++){ //consider each stack separately
			System.out.println("Checking col " + i);
			testArray = resetArray(muffins);
			printBoard(testArray);
			
			for(int j = testArray[i]; j>0; j--){
				testArray[i]--; 
				printBoard(testArray);
				if(!isItAWinningState(testArray)){
					System.out.println("Found next move!");
					result.currentMuffins = testArray;
					result.winningState = false;
					return result;
				}
			}
		}	
		return result;
	}
	
	/**
	 * This method follows the game playing strategy where the monkey chooses the move that 
	 * leaves the monkey's opponent with 4n muffins left, where n = 1,2,3, etc.
	 * @param args
	 */
	public Gameboard chooseMoveMethod2(int[] muffins, Gameboard root){
		//goal is to make next state a losing state (so we want to make all columns 0 in the XOR function
		Gameboard result = new Gameboard();
		int[] testArray = new int[3];
		testArray = resetArray(muffins);
		boolean foundOptimalMove = false;
		
		for(int i = 0; i<3; i++){ //consider each stack separately
			System.out.println("Checking col " + i);
			testArray = resetArray(muffins);
			printBoard(testArray);
			
			for(int j = testArray[i]; j>0; j--){
				testArray[i]--; 
				printBoard(testArray);
				if(isOptimalAmount(testArray)){
					System.out.println("Found next move!");
					result.currentMuffins = testArray;
					result.winningState = false;
					foundOptimalMove = true;
					return result;
				}
			}
		}	
		if(foundOptimalMove==false){
			//if no optimal move is found, just take 1 muffin from any available stack with muffins.
			testArray = resetArray(muffins);
			System.out.println("Optimal move not found so making random move");
			Random generator = new Random();
			int num = generator.nextInt(3);
			while(testArray[num]<=0){
				num = generator.nextInt(3);
			}
			testArray[num] -= 1;
			result.currentMuffins = testArray;
			result.winningState = false; //not really relevant for this strategy anyways
		}
		return result;
	}

	public boolean isOptimalAmount(int[] testArray) {
		int sum = 0;
		for(int i = 0; i<3; i++){
			sum += testArray[i];
		}
		
		if(sum%4==0){
			System.out.println("isOptimalAmount = true");
			return true;
		}
		else{
			System.out.println("isOptimalAmount = false");
			return false;
		}
	}

	public int[] resetArray(int[] muffins) {
		int[] array = new int[3];
		for(int i = 0; i<3; i++){
			array[i] = muffins[i];
		}
		return array;
	}

	public static boolean isItAWinningState(int[] muffins){
		//If value of operator is greater than 0
		if( (muffins[0]^muffins[1]^muffins[2]) > 0){
			return true;
		}
		//If value of operator is zero
		return false;
	} //isItAWinningState
	
	public static void printBoard(int[] board) {
		for (int i = 0; i < 3; i++)
			System.out.print(board[i] + " ");
		System.out.println();
	} // printBoard
	

}

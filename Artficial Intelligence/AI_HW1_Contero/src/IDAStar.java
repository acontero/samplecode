import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.sql.Timestamp;
/**
 * Iterative Deepening A* Search Algorithm
 * NOTE: as of now, this program only works for the first puzzle.  Please cut
 * the other puzzles out of the input before running.
 * 
 * @author Angelica Contero
 * @version HW1 - FALL 2013
 * @since  9/25/13
 */

public class IDAStar {

	private static BufferedReader inStream;
	private static PrintWriter outStream;
	
	/**
	 * Sets the input and output files as inStream and outStream, respectively
	 *
	 * @param infile	Input file
	 * @param outfile	Output file
	 */
	public static void setInputOuput(String inFile, String outFile)
	{
		try
		{
			FileInputStream fis = new FileInputStream(inFile); 
			InputStreamReader in = new InputStreamReader(fis, "UTF-8");
			inStream = new BufferedReader(in);
			
			FileOutputStream fos = new FileOutputStream(outFile); 
			OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");
			outStream = new PrintWriter(out);
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	} // end setIO
	
	/**
	 * Prints string to outStream
	 *
	 * @param s		The string to print
	 */
	public static void display(String s)
	{
		outStream.print(s);
	}
	
	/**
	 * Prints string to outStream and moves to next line
	 *
	 * @param s		The string to print
	 */
	public static void displayln(String s)
	{
		outStream.println(s);
	}
	
	/**
	 * Closes the I/O streams
	 *
	 */
	public static void closeIO()
	{
		try
		{
			inStream.close();
			outStream.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	} // end closeIO

	
	
	
	
///////////////*****************************************************************//////////////
	public static int [][] goalArray = new int [3][3];
	public static void main(String[] argv) throws IOException{
		setInputOuput(argv[0], argv[1]);
		String oneLine = null;
		
		int [][] tilesArray = new int [3][3];
 		
		//Read in Goal Array first
		oneLine = inStream.readLine();
	 	for(int r=0; r<3; r++){
	 		String tiles[] = oneLine.split(" ");
	 		for(int c=0; c<3; c++){
	 			goalArray[r][c]=Integer.parseInt(tiles[c]);
	 		}	
	 		oneLine = inStream.readLine();
		}
	 	displayln("Goal Array:");
 		printArray(goalArray);
		
 		int level = 0; //level of difficulty. There are 4 levels.
		while((oneLine = inStream.readLine()) !=null){
 			for(int r=0; r<3; r++){
 				String tiles[] = oneLine.split(" ");
 				for(int c=0; c<3; c++){
 					tilesArray[r][c]=Integer.parseInt(tiles[c]);
 				}
 				oneLine = inStream.readLine();
 			}
 			//displayln("State Array of n:");
 			//printArray(tilesArray);
 			int h = calculateManhattanH(tilesArray);
 			Gameboard startNode = new Gameboard(null,0,h,tilesArray);
 			findBestSolution(startNode,++level);
		}//end while loop
		closeIO();
	}
	
	public static int calculateManhattanH(int[][] tilesArray) {
		int h = 0;
		int[] index; 
		int indexRow;
		int indexCol;
		int manhattan = 0;
		for(int r = 0; r<3; r++){
			for(int c = 0; c<3; c++){
				if(goalArray[r][c] != tilesArray[r][c]){
					int findNum = goalArray[r][c];
					index = findIndex(findNum,tilesArray);
					indexRow = index[0];
					indexCol = index[1];
					manhattan = manhattan + ((Math.abs(r-indexRow)) + (Math.abs(c-indexCol))); //update manhattan h value
				}
			}
		}
		return manhattan;
	}
	
	public static int[] findIndex(int n,int[][] a){
		int[] answer = new int[2];
		for(int r=0; r<3; r++){
			for(int c=0; c<3; c++){
				if(a[r][c]==n){
					answer[0]=r;
					answer[1]=c;
					return answer; 
				}
			}
		}
		return null;
	}

	static int compareArrayToGoal(int[][] tilesArray) {
		int h = 0; //h = the number of differences between this array and the goal array
		for(int r = 0; r<3; r++){
			for(int c = 0; c<3; c++){
				if(goalArray[r][c]!=tilesArray[r][c]) h++;
			}
		}
		return h;
	}
	
	static boolean compareTwoArrays(int[][] firstArray,int[][] secondArray) {
		for(int r = 0; r<3; r++){
			for(int c = 0; c<3; c++){
				if(firstArray[r][c]!=secondArray[r][c]) return false;
			}
		}
		return true;
	}
	
	static Gameboard stackContains(Stack<Gameboard> stackList,Gameboard currentNode) {
		for(Gameboard x : stackList){
			if(compareTwoArrays(x.getState(),currentNode.getState())==true){
				//displayln("Duplicate found! And it is:");
				//printArray(currentNode.getState());
				return x; //return the gameboard duplicate that's already in the queue
			}
		}
		return null;
	}

	static void findBestSolution(Gameboard startNode, int levelofDifficulty){
		//Gameboard startNode = start;
		Result myResult = new Result();
		myResult.levelOfDifficulty = levelofDifficulty;
		//java.util.Date date= new java.util.Date();
		long startTime = System.currentTimeMillis();
		//generate nodes: up, down, left, right and insert into priority queue
		Stack<Gameboard> Q = new Stack<Gameboard>();
		Stack<Gameboard> C = new Stack<Gameboard>();
		int L =  startNode.getEvalFxn();
	    int count = 1;
		//First, add first state node n to open queue
		Q.add(startNode);

		
		
		while(!Q.empty()){
			startNode = Q.pop();
			if(compareArrayToGoal(startNode.getState())==0){ //if we've reached the goal, store the new L
				//int x = startNode.getEvalFxn();
				//if(x<L) L=x;
				List<Gameboard> mySolutionList = new ArrayList<Gameboard>();
				myResult.g = startNode.getCostThusFar();
				long totalTime = System.currentTimeMillis()-startTime;
				myResult.totalTime = totalTime;
				myResult.numOfNodesExpanded = Q.size()+C.size() +1;
				mySolutionList.add(startNode);
				Gameboard parent = startNode.getParent();
				while(parent !=null){
					mySolutionList.add(parent);
					parent = parent.getParent();
				}
				myResult.sequenceOfNodes = mySolutionList; //store solution in reverse order
			}
			else{
				//put it in the C stack;
				C.push(startNode);
				//expand the parentNode
				Gameboard[] children = expandParentNode(startNode);
				int numNewNodes = 0;
				for(int x=0; x<4; x++){ //check if any child node is null, for the count
					if(children[x]!=null)
						numNewNodes++;
	 			}
				//if all new child nodes are null, then we are at the bottom of the tree
				//in that case, don't increase g or count
				if(numNewNodes!=0){ 
					count = count + numNewNodes;
				}
				
				PriorityQueue<Gameboard> toBeAdded = new PriorityQueue<Gameboard>();
				PriorityQueue<Gameboard> nodesExceedingL = new PriorityQueue<Gameboard>();
				//Now, consider each child node
				System.out.println("Reached this point");
				for(int x=0; x<4; x++){ 
					Gameboard toEdit = null;
					if(children[x]!=null){ //if node is not null
						if((toEdit = stackContains(C,children[x]))==null){ //and it is not a duplicate of a node in C
							//then check to see if it's f(n) is < our current L;
							if(children[x].getEvalFxn()>L){
								System.out.println("This child node's f(n) is: " + children[x].getEvalFxn());
								System.out.println("L is " + L);
								nodesExceedingL.add(children[x]);
							}
							if(children[x].getEvalFxn()<L){
								toBeAdded.add(children[x]); //if it is, add it to Q!
							}
						}
					}
					//if any of these are not true, discard the node.  We don't care about it.
				}
				System.out.println("Reached this point too!");
				//take the smallest of the nodes whose f(n) exceeded L and make its f(n) the new L
				if(nodesExceedingL.size()>0) L = nodesExceedingL.poll().getEvalFxn();
//				Gameboard[]  a = null;
//				a = (Gameboard[]) nodesExceedingL.toArray();
//				L = a[a.length-1].getEvalFxn();				
//				
				//the rest will be added to the stack
				List<Gameboard> sortList = new ArrayList<Gameboard>();
				for(int i = 0; i<toBeAdded.size(); i++){
					sortList.add(toBeAdded.poll());
				}
				for(int i = sortList.size()-1; i>=0; i--){
					Q.push(sortList.get(i));
				}
			}//end else
		}//end while loop
		displayln("Finished!!!");
		
		displayln("Solution Results: ");
		displayln("Level of difficulty: " + myResult.levelOfDifficulty);
		displayln("nodeCount thus far " + count);
		displayln("numberOfNodesInAllQueues: " + myResult.numOfNodesExpanded);
		displayln("g value: " + myResult.g);
		displayln("totalTime taken to get OPTIMAL solution: " + myResult.totalTime);
		long totalTimeForProgramToRun = System.currentTimeMillis()-startTime;
		displayln("time taken to run this program: " + totalTimeForProgramToRun);
		displayln("Solution Sequence: ");
		
		//NOTE: For some reason my IDA* algorithm is only working properly for the easy puzzle.
		//For the rest of the puzzles, I am getting a null pointer exception in the for loop condition check below.
		//I ran of time before I was able to debug this.
		Stack<Gameboard>answer = new Stack();
		for(int i = 0; i< myResult.sequenceOfNodes.size(); i++){
			if(myResult.sequenceOfNodes.get(i)!=null)
				answer.push(myResult.sequenceOfNodes.get(i));
		}
		int counter = 0;
		while(!answer.empty()){
			displayln("Step #" + (++counter) + ":");
			printArray(answer.pop().getState());
		}
		myResult.stepsInSolution = counter;
		
	}//end method

	static Gameboard[] expandParentNode(Gameboard parentNode){
		int g = parentNode.getCostThusFar();
		g++;
		int[][] parentNodeState = copyArray(parentNode.getState());
		
		findSpace(parentNode);
		int[] spaceCoordinates = copySpaceLocArray(parentNode.getSpaceLocation());
		int r = spaceCoordinates[0];
		int c = spaceCoordinates[1];
		Gameboard child1 = null;
		Gameboard child2 = null;
		Gameboard child3 = null;
		Gameboard child4 = null;
		
		//get up node, child1 
		if(r>0){
			int[][]tempArrayA = copyArray(parentNodeState);
			//displayln("Parent node:");
			//printArray(tempArrayA);
			int tempValue = tempArrayA[r-1][c];
			tempArrayA[r-1][c] = 0;
			tempArrayA[r][c] = tempValue;
			int h = calculateManhattanH(tempArrayA);
			child1 = new Gameboard(parentNode,g,h,tempArrayA);
			//if(child1!=null) printArray(child1.getState());
		}
		// get down node, child2
		if(r<2){
			int[][]tempArrayB = copyArray(parentNodeState);
			//displayln("Parent node:");
			//printArray(tempArrayB);
			int temp = tempArrayB[r+1][c];
			tempArrayB[r+1][c] = 0;
			tempArrayB[r][c] = temp;
			int h = calculateManhattanH(tempArrayB);
			child2 = new Gameboard(parentNode,g,h,tempArrayB);
			//if(child2!=null) printArray(child2.getState());
		}
		// get left node, child3
		if(c>0){
			int[][]tempArrayC = copyArray(parentNodeState);
			//displayln("Parent node:");
			//printArray(tempArrayC);
			int temp = tempArrayC[r][c-1];
			tempArrayC[r][c-1] = 0;
			tempArrayC[r][c] = temp;
			int h = calculateManhattanH(tempArrayC);
			child3 = new Gameboard(parentNode,g,h,tempArrayC);
			//if(child3!=null) printArray(child3.getState());
		}
		// get right node, child4
		if(c<2){
			int[][]tempArrayD = copyArray(parentNodeState);
			//displayln("Parent node:");
			//printArray(tempArrayD);
			int temp = tempArrayD[r][c+1];
			tempArrayD[r][c+1] = 0;
			tempArrayD[r][c] = temp;
			int h = calculateManhattanH(tempArrayD);
			child4 = new Gameboard(parentNode,g,h,tempArrayD);	
			//if(child4!=null) printArray(child4.getState());
		}
		
		Gameboard[] returnArray = {child1,child2,child3,child4};
		return returnArray;
	}
	
	private static int[][] copyArray(int[][] copyFrom) {
		int[][] copyTo = new int[3][3];
		for(int r=0; r<3; r++){
			for(int c=0; c<3; c++){
				copyTo[r][c] = copyFrom[r][c];
			}
		}
		return copyTo;
	}
	
	private static int[] copySpaceLocArray(int[] copyFrom) {
		int[] copyTo = new int[2];
		for(int r=0; r<2; r++){
			copyTo[r] = copyFrom[r];
		}
		return copyTo;
	}

	static void findSpace(Gameboard node){
		for(int r=0;r<3;r++){
			for(int c=0;c<3;c++){
				if(node.getState()[r][c]==0){
					node.setSpaceLocation(r,c);
					return;
				}
			}
		}
	}
	
	static void printArray(int[][]array){
		for(int r = 0; r<array.length; r++){
			for(int c = 0; c<array.length; c++){
				display(array[r][c] + " ");
			}
			displayln("");
		}
		displayln("");
	}
	
}//end class



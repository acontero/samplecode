import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.sql.Timestamp;
/**
 * A* algorithm using out-of-place heuristic function
 * @author Angelica Contero
 * @version HW1 - FALL 2013
 * @since  9/25/13
 */


public class AStarOutOfPlace {
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

	
	
	
	
///////////////*****************************************************************///////////////
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
 			int h = compareArrayToGoal(tilesArray);
 			Gameboard startNode = new Gameboard(null,0,h,tilesArray);
 			findBestSolution(startNode,++level);
		}//end while loop
		closeIO();
	}
	
	static int compareArrayToGoal(int[][] tilesArray) {
		int h = 0;
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
	
	static Gameboard queueContains(PriorityQueue<Gameboard> queue,
			Gameboard currentNode) {
		//Iterator <Gameboard> x = queue.iterator();
		for(Gameboard x : queue){
			if(compareTwoArrays(x.getState(),currentNode.getState())==true){
				//displayln("Duplicate found! And it is:");
				//printArray(currentNode.getState());
				return x; //return the gameboard duplicate that's already in the queue
			}
		}
		return null;
	}

	static void findBestSolution(Gameboard startNode, int level){
		if(level==4){
			displayln("Solution cannot be found for level: " + level);
			return;
		}
		//Gameboard startNode = start;
		Result myResult = new Result();
		myResult.levelOfDifficulty = level;
		//java.util.Date date= new java.util.Date();
		long startTime = System.currentTimeMillis();
		//generate nodes: up, down, left, right and insert into priority queue
		PriorityQueue<Gameboard> queueOpen = new PriorityQueue<Gameboard>(100);
	    PriorityQueue<Gameboard> queueClosed = new PriorityQueue<Gameboard>(100);
	    int count = 1;
		//First, add first state node n to open queue
		queueOpen.add(startNode);

		startNode = queueOpen.poll();
		//while we have not reached our goal, consider the best node in queueOpen
		//while(node.getState()!=goalArray)
		while(compareArrayToGoal(startNode.getState())!=0){
			if(queueContains(queueClosed,startNode)==null) queueClosed.add(startNode); //add the node to queueClosed and check it's neighbors
			int gvalue = startNode.getCostThusFar()+1;
			//expand the parentNode
			Gameboard[] children = expandParentNode(startNode);
			int numNewNodes = 0;
			for(int x=0; x<4; x++){ //consider each child node
				if(children[x]!=null)
					numNewNodes++;
 			}
			//if all new child nodes are null, then we are at the bottom of the tree
			//in that case, don't increase g or count
			if(numNewNodes!=0){ 
				//displayln("#nodes just created = " + numNewNodes);
				//displayln("Current gvalue = " + gvalue);
				count = count + numNewNodes;
			}
			
			//Now, consider each child node
			for(int x=0; x<4; x++){ 
				Gameboard toEdit = null;
				Gameboard toEditToo = null;
				if(children[x]!=null){
					//locate duplicates in queueClosed
					
					//if found, update that node and move it to queueOpen
					if((toEdit = queueContains(queueClosed,children[x]))!=null && gvalue<toEdit.getCostThusFar()){
						queueClosed.remove(toEdit);
						toEdit.updateCostThusFar(gvalue);
						toEdit.setParent(startNode);
						if(queueContains(queueOpen,toEdit)==null) queueOpen.add(toEdit);
					}
					
					//locate duplicates in queueOpen
					//if found, update that node and then put it back in queueOpen
					else if((toEdit=queueContains(queueOpen,children[x]))!=null && gvalue<toEdit.getCostThusFar()){
						queueOpen.remove(toEdit);
						toEdit.updateCostThusFar(gvalue);
						toEdit.setParent(startNode);
						queueOpen.add(toEdit);
					}

					else if((toEdit = queueContains(queueOpen,children[x])) == null && (toEditToo = queueContains(queueClosed,children[x])) == null){
						children[x].updateCostThusFar(gvalue);
						queueOpen.add(children[x]);
					}
				}//end if !null
 			}//end for loop	
			startNode = queueOpen.poll(); //get best node from queueOpen
			//displayln("The polled node at gvalue=" + gvalue);
			//printArray(startNode.getState());
			myResult.g = gvalue; //keep updating g as you go along
		}//end while loop
		displayln("Finished!!!");
		List<Gameboard> mySolutionList = new ArrayList<Gameboard>();
		mySolutionList.add(startNode);
		Gameboard parent = startNode.getParent();
		while(parent !=null){
			mySolutionList.add(parent);
			parent = parent.getParent();
		}
		myResult.sequenceOfNodes = mySolutionList; //store solution in reverse order
		long totalTime = System.currentTimeMillis()-startTime;
		myResult.totalTime = totalTime;
		myResult.numOfNodesExpanded = queueClosed.size()+queueOpen.size() +1;
		
		displayln("Solution Results: ");
		displayln("Level of difficulty: " + myResult.levelOfDifficulty);
		displayln("numberOfExpandedNodes: " + myResult.numOfNodesExpanded);
		displayln("g value: " + myResult.g);
		displayln("totalTime taken to get solution: " + myResult.totalTime);
		displayln("Solution Sequence: ");
		int counter = 0;
		for(int w=myResult.sequenceOfNodes.size()-1; w>=0; w--){
			displayln("Step #" + (++counter) + ":");
			printArray(myResult.sequenceOfNodes.get(w).getState());
		}
		myResult.stepsInSolution = counter;

		return;
	}//end method

	static Gameboard[] expandParentNode(Gameboard parentNode){
		int g = parentNode.getCostThusFar();
		g++;
		int[][] parentNodeState = copyArray(parentNode.getState());
		
		findSpace(parentNode);
		int[] spaceCoordinates = copySpaceLocArray(parentNode.getSpaceLocation());
		int r = spaceCoordinates[0];
		int c = spaceCoordinates[1];
		//displayln("The space is located at: " + r + "," + c);
		//displayln(" ");
		Gameboard child1 = null;
		Gameboard child2 = null;
		Gameboard child3 = null;
		Gameboard child4 = null;
		
		//displayln("child nodes:");
		//get up node, child1 
		if(r>0){
			int[][]tempArrayA = copyArray(parentNodeState);
			//displayln("Parent node:");
			//printArray(tempArrayA);
			int tempValue = tempArrayA[r-1][c];
			tempArrayA[r-1][c] = 0;
			tempArrayA[r][c] = tempValue;
			int h = compareArrayToGoal(tempArrayA);
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
			int h = compareArrayToGoal(tempArrayB);
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
			int h = compareArrayToGoal(tempArrayC);
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
			int h = compareArrayToGoal(tempArrayD);
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
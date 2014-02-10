import java.io.*;
import java.util.*;

/**
 * This program implements the EM algorithm for learning the parameters for a model.  I can
 * perform this algorithm on any of the 5 different data sets included int his project.
 * Each data set varies in the percentages of missing data: 10%, 30%, 50%, 70% and 100%.
 * It continues running until there is a convergence, represented by < 0.0001 change in the probabilities 
 * of the data points in the updated data set, between iterations (I look for the largest difference between 
 * any two paralleled data points between the two sets).
 * 
 * @author Angelica Contero
 * @version HW2 - FALL 2013
 * @since  9/25/13
 */

public class Main {
	private static BufferedReader inStream;
	private static PrintWriter outStream;

	/**
	 * This is the main section of the program and is where the EM
	 * algorithm is implemented.
	 */	
	public static String [][] gender = new String [200][1];
	public static String [][] weight = new String [200][1];
	public static String [][] height = new String [200][1];
	
	public static float Gprob[] = new float[2];
	public static float WgivenGprob[][] = new float[2][2];
	public static float HgivenGprob[][] = new float[2][2];
	
	public static float comparison = 100;
	public static float[][] lastRun = {}; 
 	public static int [][] updatedDataSet = new int[400][3]; 
 	public static boolean[][] missingOrNot;
 	public static float[][] probabilitiesOfEachDataPoint;
 	
	public static void main(String[] argv) throws IOException{
		
		setInputOuput(argv[0], argv[1]);
		
		//Before starting, we assign probabilities for the following 
		//probability tables: P(G), P(W|G) and P(H|G) 
		//which we'll need for later.
	 	
		//PARAMETERS FOR THE LEARNING:
		//Parameters for TEST #1
	 	Gprob[0] = 0.7f;
	 	Gprob[1] = 0.3f;
	 	
	 	
	 	WgivenGprob[0][0] = 0.8f; // W|G 
	 	WgivenGprob[0][1] = 0.4f;  // W|~G 
	 	WgivenGprob[1][0] = 0.2f; // ~W|G
	 	WgivenGprob[1][1] = 0.6f; //~W|~G
	 	
	 	
	 	HgivenGprob[0][0] = 0.7f; // H|G 
	 	HgivenGprob[0][1] = 0.3f; // H|~G 
	 	HgivenGprob[1][0] = 0.3f; // ~H|G
	 	HgivenGprob[1][1] = 0.7f; //~H|~G
	 	
		//Parameters for TEST #2
		//increased larger one by .1 and decreased smaller one by .1
//	 	Gprob[0] = 0.8f;
//	 	Gprob[1] = 0.2f;
//	 	
//	 	
//	 	WgivenGprob[0][0] = 0.9f; // W|G 
//	 	WgivenGprob[0][1] = 0.5f;  // W|~G 
//	 	WgivenGprob[1][0] = 0.1f; // ~W|G
//	 	WgivenGprob[1][1] = 0.5f; //~W|~G
//	 	
//	 	
//	 	HgivenGprob[0][0] = 0.8f; // H|G 
//	 	HgivenGprob[0][1] = 0.2f; // H|~G 
//	 	HgivenGprob[1][0] = 0.2f; // ~H|G
//	 	HgivenGprob[1][1] = 0.8f; //~H|~G
	 	
		//Parameters for TEST #3
		//decreased larger one by .1 and increased smaller one by .1
//	 	Gprob[0] = 0.6f;
//	 	Gprob[1] = 0.4f;
//	 	
//	 	
//	 	WgivenGprob[0][0] = 0.7f; // W|G 
//	 	WgivenGprob[0][1] = 0.5f;  // W|~G 
//	 	WgivenGprob[1][0] = 0.3f; // ~W|G
//	 	WgivenGprob[1][1] = 0.5f; //~W|~G
//	 	
//	 	
//	 	HgivenGprob[0][0] = 0.6f; // H|G 
//	 	HgivenGprob[0][1] = 0.4f; // H|~G 
//	 	HgivenGprob[1][0] = 0.4f; // ~H|G
//	 	HgivenGprob[1][1] = 0.6f; //~H|~G
	 	
	 	//Parameters for TEST #4
		//subtracted .5 from larger number, added .5 to the smaller number
//	 	Gprob[0] = 0.2f;
//	 	Gprob[1] = 0.8f;
//	 	
//	 	
//	 	WgivenGprob[0][0] = 0.3f; // W|G 
//	 	WgivenGprob[0][1] = 0.9f;  // W|~G 
//	 	WgivenGprob[1][0] = 0.7f; // ~W|G
//	 	WgivenGprob[1][1] = 0.1f; //~W|~G
//	 	
//	 	
//	 	HgivenGprob[0][0] = 0.2f; // H|G 
//	 	HgivenGprob[0][1] = 0.8f; // H|~G 
//	 	HgivenGprob[1][0] = 0.8f; // ~H|G
//	 	HgivenGprob[1][1] = 0.2f; //~H|~G
		
		//Parameters for TEST #5		
		//everything is .5
//	 	Gprob[0] = 0.5f;
//	 	Gprob[1] = 0.5f;
//	 	
//	 	
//	 	WgivenGprob[0][0] = 0.5f; // W|G 
//	 	WgivenGprob[0][1] = 0.5f;  // W|~G 
//	 	WgivenGprob[1][0] = 0.5f; // ~W|G
//	 	WgivenGprob[1][1] = 0.5f; //~W|~G
//	 	
//	 	
//	 	HgivenGprob[0][0] = 0.5f; // H|G 
//	 	HgivenGprob[0][1] = 0.5f; // H|~G 
//	 	HgivenGprob[1][0] = 0.5f; // ~H|G
//	 	HgivenGprob[1][1] = 0.5f; //~H|~G	 	

	 	
	 	//print out initial parameters for this learning:
	 	displayln("Initial Parameters for this Learning:");
	 	displayln("");
	 	displayln("initial P(G=0) = "+Gprob[0]);
	 	displayln("initial P(G=1) = "+Gprob[1]);
	 	
	 	displayln("initial P(W=0|G=0) = "+ WgivenGprob[0][0]);
	 	displayln("initial P(W=0|G=1) = "+ WgivenGprob[0][1]);
	 	displayln("initial P(W=1|G=0) = "+ WgivenGprob[1][0]);
	 	displayln("initial P(W=1|G=1) = "+ WgivenGprob[1][1]);
	 	
	 	displayln("initial P(H=0|G=0) = "+ HgivenGprob[0][0]);
	 	displayln("initial P(H=0|G=1) = "+ HgivenGprob[0][1]);
	 	displayln("initial P(H=1|G=0) = "+ HgivenGprob[1][0]);
	 	displayln("initial P(H=1|G=1) = "+ HgivenGprob[1][1]);
	 	displayln("");
	 	
	 	//Now, read in input data	
		String oneLine = null;
 		
		oneLine = inStream.readLine();
		oneLine = inStream.readLine();
 		
	 	for(int r=0; r<200; r++){
	 		for(int c=0; c<1; c++){
		 		String threeDataPoints[] = oneLine.split("");
		 		gender[r][c]=threeDataPoints[1];
		 		weight[r][c]=threeDataPoints[3];
		 		height[r][c]=threeDataPoints[5];
	 		}
	 		oneLine = inStream.readLine();
		}
	 	//print out input to make sure it's been read in correctly-
	 	//displayln("Input:");
	 	//printInput(gender,weight,height);
	 	
	 	//Now, go through input columns and simultaneously create a new array 
	 	//with 3 columns this time, which one for each: G, W and H.  For any missing data 
	 	//points, create two new rows with the estimated missing parameters.
	 	//THIS IS THE E STEP OF THE EM ALGORITHM
	 	
	 	int numOfMissingDataPoints = 0;
	 	int currentPosition = 0;

	 	for(int r=0; r<200; r++){
	 		//if gender point at this row is missing, make two new rows in updatedDataSet table
 			if(gender[r][0].equals("-")){ 
 				//update count of numOfMissingDataPoints
 				numOfMissingDataPoints++;
 				//for first row, estimate G = 0
 				updatedDataSet[currentPosition][0] = 0;
 				updatedDataSet[currentPosition][1] = Integer.parseInt(weight[r][0]);
		 		updatedDataSet[currentPosition][2] = Integer.parseInt(height[r][0]);
		 		
		 		//for second row, estimate G = 1
		 		currentPosition++;
 				updatedDataSet[currentPosition][0] = 1;
 				updatedDataSet[currentPosition][1] = Integer.parseInt(weight[r][0]);
		 		updatedDataSet[currentPosition][2] = Integer.parseInt(height[r][0]);
 			}
 			else{
		 		updatedDataSet[currentPosition][0] = Integer.parseInt(gender[r][0]);
		 		updatedDataSet[currentPosition][1] = Integer.parseInt(weight[r][0]);
		 		updatedDataSet[currentPosition][2] = Integer.parseInt(height[r][0]);
	 		}
 			//move to the next row in the new array 
 			currentPosition++;
		}
	 	//make the rest of the entries in updatedDataSet = 999 to indicate no data
	 	for(int r=currentPosition; r<400; r++){
	 		for(int c = 0; c<3; c++){
	 			updatedDataSet[r][c] = 999;
	 		}
	 		
	 	}
	 	displayln("Number of missing data points = "+numOfMissingDataPoints);
	 	displayln("");
	 	//print updatedDataTable array to check if everything has worked thus far
	 	//printArray(updatedDataSet,numOfMissingDataPoints);
	 	
	 	//Create a boolean array that tracks which rows of the new data set below to respective missing
	 	//data points in the original input file
	 	missingOrNot = new boolean[currentPosition][1];//initialize # of rows to be = to # of rows in updatedDataSet
	 	int currPos = 0;//reset counter
	 	//update an array called missingOrNot that tracks if the data point stems from a missing data point or not
	 	for(int r=0; r<200; r++){//iterate through original table again
	 		//if gender point at this row is missing, make two new rows in missingOrNot table
 			if(gender[r][0].equals("-")){ 
 				//for first row
 				missingOrNot[currPos][0]=true;
		 		//for second row
		 		currPos++;
		 		missingOrNot[currPos][0]=true;
 			}
 			else{//if not missing data point
 				missingOrNot[currPos][0]=false;
 			}
 			//move to the next row in the new array 
 			currPos++;
		}
	 	//print missingOrNot array to check if everything has worked thus far
	 	//printBoolArray(missingOrNot,numOfMissingDataPoints);
	 	
	 	
	 	//JOINT PROBABILITY TABLE (not needed)
//	 	//Then use the three tables of initial starting parameters, and fill in the fourth table
//	 	//(the joint probability table) accordingly.  
//	 	
//	 	//The binary numbers array hold numbers 0-7 in binary representation to represent the 
//	 	//different combinations of G,H,W that we will use when calculating the probability of each 
//	 	//cell in the joint probability table.
//	 	int binaryNumbers[][] = 
//	 		{{0,0,0,},
//	 		{0,0,1},
//	 		{0,1,0},
//	 		{0,1,1},
//	 		{1,0,0},
//	 		{1,0,1},
//	 		{1,1,0},
//	 		{1,1,1}}; 
//	 	float jointProbabilityTable[][] = new float [2][4];
//	 	
//	 	int first;
//		int second;
//		int third;
//		int count = 0;
//		
//	 	for (int r = 0; r<2; r++){
//	 		for(int c = 0; c<4; c++){
//	 			first = binaryNumbers[count][0];
//	 			second = binaryNumbers[count][1];
//	 			third = binaryNumbers[count][2];
//	 			jointProbabilityTable[r][c] = (Gprob[first])*(WgivenGprob[second][first])*(HgivenGprob[third][first]);	
//	 			count++;
//	 		}
//	 		
//	 	}
//	 	printFloatArray(jointProbabilityTable,4);
	 	
	 	
	 	//NOW RUN THE PROGRAM UNTIL COMPARSION <.0001
	 	//This comparison value is the difference between the current updated data table and the 
	 	//table from the last iteration, if it exists, which we will call lastRun
	 	int numIterations = 0;
	 	while(comparison>=.0001){
	 		numIterations++;
		 	//Now, calculate the probabilities of each row of the new data set and store 
		 	//value in probabilitiesOfEachDataPoint array;
		 	//Store and reference the HashMap called probabilitiesList
		 	Map<String,Float> probabilitiesList = new HashMap<String,Float>();//stores probabilities for the non-missing data rows
		 	Map<String,Float> missingProbabilitiesList = new HashMap<String,Float>();//stores probabilities for the missing data rows
		 	probabilitiesOfEachDataPoint = new float[200+numOfMissingDataPoints][1];//fill in new table
		 	String currentDataPoint = null;
		 	int currentRow = 0;
		 	
		 	for(int r=0; r<200; r++){
		 		//if gender point at this row is missing
	 			if(gender[r][0].equals("-")){ 
	 				//DO THE FOLLOWING TWICE, SINCE THERE ARE TWO ROWS:
	 				for(int i = 0; i<2; i++){
	 					//System.out.println("Missing Data Point:");
	 					currentDataPoint = i+(weight[r][0])+(height[r][0]);
	 					//System.out.println("currentDataPoint = " + currentDataPoint);
		 				//check if this data point is already in the hash
		 	 			//if it it, take the value and store in the probabilities array
		 				if(missingProbabilitiesList.containsKey(currentDataPoint)) 
		 			 		probabilitiesOfEachDataPoint[currentRow][0] = missingProbabilitiesList.get(currentDataPoint);
		 			 	else{ //if not, calculate it and then put it in the hash and store in the probabilities array
		 			 		float prob = calculateProbability(currentDataPoint,true); //calculate probability
		 			 		missingProbabilitiesList.put(currentDataPoint,prob); //store the calculated probability in the hash
		 			 		probabilitiesOfEachDataPoint[currentRow][0] = prob; //store in the probabilities array
		 		 		}
		 				currentRow++;
	 				}
	 				
	 			}
	 			else{ //otherwise:
			 		currentDataPoint = (gender[r][0])+(weight[r][0])+(height[r][0]);
			 		//System.out.println("currentDataPoint = " + currentDataPoint);
			 		//check if this data point is already in the hash
		 			//if it it, take the value and store in the probabilities array
			 		if(probabilitiesList.containsKey(currentDataPoint)) 
				 		probabilitiesOfEachDataPoint[currentRow][0] = probabilitiesList.get(currentDataPoint);
				 	else{ //if not, calculate it and then put it in the hash and store in the probabilities array
				 		float prob = calculateProbability(currentDataPoint,false); //calculate probability
				 		probabilitiesList.put(currentDataPoint,prob); //store the calculated probability in the hash
				 		probabilitiesOfEachDataPoint[currentRow][0] = prob; //store in the probabilities array
			 		}
			 		currentRow++;
	 			}
	 			
			}
		 	//displayln("num of rows = "+currentRow);
		 	//printFloatArray(probabilitiesOfEachDataPoint,1);
		 	
		 	//COMPARE TO LAST RUN
		 	//Compare new updated data table with lastRun
		 	compareWithLastRun();
		 	displayln("# ITERATIONS = "+numIterations);
		 	displayln("COMPARISON = "+ comparison);
		 	displayln("");
		 	
		 	//Now, save new data table as lastRun so that next time we iterate through the program, 
		 	//we can compare our new probabilities with those of the lastRun.
		 	lastRun = new float[200+numOfMissingDataPoints][1];
		 	for(int i = 0; i<probabilitiesOfEachDataPoint.length; i++){
		 		lastRun[i][0] = probabilitiesOfEachDataPoint[i][0];
		 	}
		 	
		 	//Now calculate new values for each of the helper tables.
		 	//THIS IS THE M STEP OF THE EM ALGORITHM  
		 	
		 	displayln("Newly calculated values for next iteration:");
		 	float GcountZero = calculateNewProb(0,0);
		 	Gprob[0] = GcountZero/200;
		 	displayln("new P(G=0) = "+Gprob[0]);
		 	float GcountOne = calculateNewProb(0,1);
		 	Gprob[1] = GcountOne/200;
		 	displayln("new P(G=1) = "+Gprob[1]);
		 	
		 	//System.out.println("GcountZero = "+GcountZero);
		 	//System.out.println("GcountOne = "+GcountOne);
		 	
		 	float w1 = calculateNewGivenGProb(1,0,0);
		 	WgivenGprob[0][0] = w1/GcountZero; // W|G 
		 	displayln("new P(W=0|G=0) = "+ WgivenGprob[0][0]);
		 	
		 	float w2 = calculateNewGivenGProb(1,0,1);
		 	WgivenGprob[0][1] = w2/GcountOne;  // W|~G 
		 	displayln("new P(W=0|G=1) = "+ WgivenGprob[0][1]);
		 	
		 	float w3 = calculateNewGivenGProb(1,1,0);
		 	WgivenGprob[1][0] = w3/GcountZero; // ~W|G
		 	displayln("new P(W=1|G=0) = "+ WgivenGprob[1][0]);
		 	
		 	float w4 = calculateNewGivenGProb(1,1,1);
		 	WgivenGprob[1][1] = w4/GcountOne; //~W|~G
		 	displayln("new P(W=1|G=1) = "+ WgivenGprob[1][1]);
		 	
		 	//calculate new probabilities for HgivenG
		 	float h1 = calculateNewGivenGProb(2,0,0);
		 	HgivenGprob[0][0] = h1/GcountZero; // H|G 
		 	displayln("new P(H=0|G=0) = "+ HgivenGprob[0][0]);
		 	
		 	float h2 = calculateNewGivenGProb(2,0,1);
		 	HgivenGprob[0][1] = h2/GcountOne; // H|~G 
		 	displayln("new P(H=0|G=1) = "+ HgivenGprob[0][1]);
		 	
		 	float h3 = calculateNewGivenGProb(2,1,0);
		 	HgivenGprob[1][0] = h3/GcountZero; // ~H|G
		 	displayln("new P(H=1|G=0) = "+ HgivenGprob[1][0]);
		 	
		 	float h4 = calculateNewGivenGProb(2,1,1);
		 	HgivenGprob[1][1] = h4/GcountOne; //~H|~G
		 	displayln("new P(H=1|G=1) = "+ HgivenGprob[1][1]);
		 	displayln("");
		 	
		 	//Repeat program until change in probabilities between the lastRun and probabilitiesOfEachDataPoint is <0.0001 
		 	//between iterations.  This will mean the program has converged successfully and the data set is accurate enough.
	 	} 	
		 	//Once finished, repeat the ENTIRE program with new initial estimates (must do this manually).  
		 	
		closeIO();
	}

	/**
	 * Compare the current updated data set with the one from the last iteration,
	 * which is called lastRun.
	 */
	public static void compareWithLastRun(){
		float difference = 0;
		float temp = 0;
		if(lastRun.length==0) return;
		for (int i = 0; i < probabilitiesOfEachDataPoint.length; i++){
				temp = difference += Math.abs(probabilitiesOfEachDataPoint[i][0] - lastRun[i][0]);
				if(temp>difference) difference = temp;
		}
		comparison = difference;
		return;
	}
	
	/**
	 * Sets the input and output files as inStream and outStream, respectively
	 *
	 * @param x	column in which you'll find G
	 * @param y	the value we are seeking to add probabilities for
	 * 
	 * @return sum 	This is the new probability
	 */
	public static float calculateNewProb(int x,int y){
		float sum = 0;
		for(int i = 0; i<updatedDataSet.length; i++){
			if(updatedDataSet[i][x]==y && missingOrNot[i][0]==true){
				//System.out.println(probabilitiesOfEachDataPoint[i][0]);
				sum += probabilitiesOfEachDataPoint[i][0];
			}
			else if(updatedDataSet[i][x]==y && missingOrNot[i][0]==false){
				//System.out.println("1.0");
				sum += 1.0;
			}
			//else System.out.println("skip");
		}
		return sum;
	}
	
	/**
	 * Sets the input and output files as inStream and outStream, respectively
	 *
	 * @param x	column in which you'll find W or H, depending on which you're calculating
	 * @param y	the value in W|G or H|G we are seeking to add probabilities for
	 * @param z the g value in W|G or H|G we are seeking to add probabilities for
	 * 
	 * @return sum 	This is the new probability
	 */
	public static float calculateNewGivenGProb(int x,int y, int z){//col, w or h value, g value
		float sum = 0;
		for(int i = 0; i<updatedDataSet.length; i++){
			if(updatedDataSet[i][x]==y && updatedDataSet[i][0]==z && missingOrNot[i][0]==true){
				//System.out.println(probabilitiesOfEachDataPoint[i][0]);
				sum += probabilitiesOfEachDataPoint[i][0];
			}
			else if(updatedDataSet[i][x]==y && updatedDataSet[i][0]==z && missingOrNot[i][0]==false){
				//System.out.println("1.0");
				sum += 1.0;
			}
			//else System.out.println("skip");
		}
		return sum;
	}
	
	/**
	 * Calculates the probability of a data point for the updated data set.
	 *
	 * @param x			The corresponding String in the original input table, 000, or 011, etc.
	 * @param missing	Whether or not the data point is missing
	 * 
	 * @return answer 	This is the new probability
	 */
	public static float calculateProbability(String x, boolean missing) {
		float answer = 0;
		float numerator = 0;
		float denominator = 0;
		if(missing == true){ //if part of a missing data point
			//P(G=0)P(W|G)P(H|G) + P(G=1)(W|G)P(H|G), where W and H are 0 or 1 depending on String x
			int Gindex = Integer.parseInt(""+x.charAt(0));
			int Windex = Integer.parseInt(""+x.charAt(1));
			int Hindex = Integer.parseInt(""+x.charAt(2));
			
			numerator = (Gprob[Gindex] * WgivenGprob[Windex][Gindex] * HgivenGprob[Hindex][Gindex]);
			//System.out.println("numerator" + numerator);
			denominator = (Gprob[0] * WgivenGprob[Windex][0] * HgivenGprob[Hindex][0]) + (Gprob[1] * WgivenGprob[Windex][1] * HgivenGprob[Hindex][1]);
			//System.out.println("denominator = " + denominator);
			answer = numerator/denominator;
		}
		//Otherwise it's: P(G)P(W|G)P(H|G), where G, W and H are 0 or 1 depending on String x
		else{ 
			int Gindex = Integer.parseInt(""+x.charAt(0));
			int Windex = Integer.parseInt(""+x.charAt(1));
			int Hindex = Integer.parseInt(""+x.charAt(2));
			
			answer = (Gprob[Gindex] * WgivenGprob[Windex][Gindex] * HgivenGprob[Hindex][Gindex]);
		}	
		//System.out.println("answer = " + answer);
		return answer;
		
	}

	/**
	 * Prints the String arrays of input that were read in.
	 * This is just a check to make sure data was read in correctly
	 *
	 * @param gender	The gender array to print
	 * @param weight	The weight array to print
	 * @param height	The height array to print
	 */
	public static void printInput(String[][]gender,String[][]weight,String[][]height){
		displayln("Gender  Weight  Height");
		for(int r = 0; r<200; r++){
			for(int c = 0; c<1; c++){
				display(gender[r][c] + " " + weight[r][c] + " " + height[r][c]);
			}
			displayln("");
		}
		displayln("");
	}
	
	/**
	 * Prints an int array
	 *
	 * @param array		The array to print
	 * @param numRowsToAdd	Number of rows added add to the standard length of 200
	 */
	static void printArray(int[][]array, int numRowsToAdd){
		int arrayLength = 200 + numRowsToAdd;
		displayln("Gender  Weight  Height");
		for(int r = 0; r<arrayLength; r++){
			for(int c = 0; c<3; c++){
				if(array[r][c]!=999) //if there is real data to to print, print it
				display(array[r][c] + " ");
			}
			displayln("");
		}
		displayln("");
	}
	
	/**
	 * Prints a float array
	 *
	 * @param array		The array to print
	 * @param cols		Number of columns in the array
	 */
	static void printFloatArray(float[][]array,int cols){
		for(int r = 0; r<array.length; r++){
			for(int c = 0; c<cols; c++){
				if(array[r][c]!=999)
				display(array[r][c] + " ");
			}
			displayln("");
		}
		displayln("");
	}
	
	/**
	 * Prints a boolean array
	 *
	 * @param array			The array to print
	 * @param numRowsToAdd	Number of rows added add to the standard length of 200
	 */
	static void printBoolArray(boolean[][]array, int numRowsToAdd){
		int arrayLength = 200 + numRowsToAdd;
		displayln("Missing Or Not:");
		for(int r = 0; r<arrayLength; r++){
			for(int c = 0; c<1; c++){
				display(array[r][c] + " ");
			}
			displayln("");
		}
		displayln("");
	}
	
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
}


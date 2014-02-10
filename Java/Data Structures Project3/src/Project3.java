import java.io.*;
/**
 * @author      Angelica Contero
 * @version     CS313 Project 3
 * @since       2012-12-12
 */

/**
 * The design of my program was based on a linked list representation of the map and an array implementation of the relevant adjacency list.
 * Each node is a region on the map and contains, within the node, the color assignment for that reason.  In order to backtrack easily,
 * I made it a doubly linked list.  The linked list implementation allows for easy modification of the map for future applications of this program.
 */

public class Project3 {
	public static void main(String[] args) throws IOException{
		//list of possible colors
		String[] colorNames = {" ", "Red", "Yellow", "Blue", "Green"};
		//colors represented as integers
		int[] colorList = {0,1,2,3,4};
		
		//read in file
		if (args.length==0) System.out.println ("No file specified.");
		else{
			FileReader theFile;
			BufferedReader inFile;
			String oneLine;
			try{
				theFile = new FileReader(args[0]);
				inFile = new BufferedReader(theFile);
				//while not end of input
				while((oneLine = inFile.readLine()) !=null){
					//build a linked list
					String numNodes[] = oneLine.split(" ");
					System.out.println("Number of nodes: " + numNodes[0]);
					int numOfNodes = Integer.parseInt(numNodes[0]);
					LinkedList v = new LinkedList();
					//add the number of vertex nodes indicated in the input file, all to one linked list
					for(int i = 0; i<numOfNodes; i++){
						Node y = new Node();
						v.append(y);	
					}
					//create adjacency table
					int[][] a = new int [numOfNodes][numOfNodes];
					//Read in the adjacency table
					for(int r = 0; r<numOfNodes; r++){
						//read in each line of the input table
						oneLine = inFile.readLine();
						String[] line = oneLine.split(" ");
						for(int c = 0; c<numOfNodes; c++){
							a[r][c] = Integer.parseInt(line[c]);
						}
					}
					//print adjacency table
					printArray(a,numOfNodes);
					
					//now try to assign colors and check adjacency table as you move along, 
					//backtracking when necessary
					boolean valid;
					int colorCount=0;
					int x;
					int n = 0;//n represents which node I am currently in
					Node p = v.first.getNext();
					while(p.getNext()!=null){
						colorCount++;
						x = colorList[colorCount];
						p.setColor(x);
						//check if current selected color would be valid for this node
						valid = v.checkAdjacency(x,n,a,numOfNodes); 
						if(valid==false){
							if(colorCount<4&&colorCount>0){
								continue; //will try next color when re-enter while loop
							}
							else{//backtrack to previous node
								if(n>0){
									v.printProgressThusFar(n);
									p.setColor(0);
									p = p.getPrevious();
									n--;
									int z = p.getColor();
									while(z==4){//if previous node is at last color, then go to previous one, etc.
										p.setColor(0);
										if(n>0){
											p = p.getPrevious();
											n--;
										}
										else break;
										z = p.getColor();
									}//when you reach a previous node whose color you can increment, go back through the while loop again			
									colorCount = z;
								}
							}
						}
						else{//if(good==true) then just keep going with filling in colors
							p = p.getNext();
							n++;
							colorCount=0;//reset color back to red for next node
						}
					}//while (p.getNext()!=null)
					//print the solution that has been found
					v.printList();
				}//end while loop
			}//end try loop
			catch (Exception e){ 
				e.printStackTrace();
				System.out.println("An error has occurred!");
			}
		}//end else
	}//end main
	
	/**
	 * Prints the adjacency table as a 2D array
	 *
	 * @param array		The adjacency array
	 * @param count		Size of the adjacency array
	 */
	static void printArray(int[][]array, int count){
		System.out.println("Adjacency Array:");
		for(int r = 0; r<count; r++){
			for(int c = 0; c<count; c++){
				System.out.print(array[r][c]);
			}
			System.out.println();
		}
		System.out.println();
	}
}//end class

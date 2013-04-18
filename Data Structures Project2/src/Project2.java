import java.io.*;
/**
 * @author      Angelica Contero 
 * @version     CS313 Project 2                         
 * @since       2012-11-21          
 */

/**
 * The design of my program was based on the linked implementation of the Binary Trees class discussed in lecture.
 * I designed the drawTree recursive method based on powers of 2.  I offset each subtree by a power of 2 number of columns 
 * to the left or right of the previous root node. 
 * I also drew in slash marks right above the node values for a clean and clear aesthetic look.
 */
public class Project2 {
	//15,3,16,2,1,4,19,17,28
	public static void main(String[] args) throws IOException{
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
					//build a binary search tree
					String firstLine[] = oneLine.split(" ");
					//print out line of input
					System.out.print("Input: ");
					for(int i = 0; i<firstLine.length; i++){
						System.out.print(firstLine[i] + " ");
					}
					System.out.println();
					
					BinaryTree t = new BinaryTree();
					for (int i = 0; i<firstLine.length; i++){
						t = BinaryTree.insert(t, Integer.parseInt(firstLine[i]));
					}
					
					//plots input into a character array using recursion
					int row = 9;
					int col = 80;
					char picture[][] = new char[row][col];
					
					//NOTE: To make the following method call more generalized for any height tree, instead of 16, would use the following code: 
					//offsetCalculation = (int)Math.pow(2,BinaryTree.height(t)); and instead of 32: offsetCalculation*2
					//picture = BinaryTree.drawTree(t, picture, 0, offsetCalculation, OffsetCalculation*2, 'x');
					picture = BinaryTree.drawTree(t, picture, 0, 16, 32, 'x');
					
					//print the array to display the tree;
					BinaryTree.displayTree(picture, row, col);
					inFile.readLine();
				}//end while
			}//end try loop
			catch (Exception e){ 
				e.printStackTrace();
				System.out.println("An error has occurred!");
			}
		}//end else
	}//end main
}//end class
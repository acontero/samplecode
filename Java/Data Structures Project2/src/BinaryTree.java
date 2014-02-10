public class BinaryTree {
	/**
	 * This class is a linked implementation of a BinaryTree abstract data type.
	 * Each Binary Tree is comprised of Binary Nodes and each Binary node is a 1-2 digit integer.
	 */
	
	private BinaryNode root;
	
	/** default constructor */
	public BinaryTree(){root=null;}
	
	/** Constructor for 1 element value */
	public BinaryTree(Object x){
		root = new BinaryNode(x);
	}
	
	/**
	 * Checks if the Binary Tree is empty
	 * 
	 * @return          	true if empty, false if not
	 */
	public boolean isEmpty(){return (root==null);}
	
	/**
	 * Makes Binary Tree empty
	 */
	public void makeEmpty(){root=null;}
	
	/**
	 * Checks if the Binary Tree is empty
	 *
	 * @return          	count of the nodes in this Binary Tree as an integer
	 */
	public int nodeCount(){return BinaryNode.nodeCount(root);}
	 
	/**
	 * Calculates the height of the Binary Tree
	 * 
	 * @return          	height of Binary Tree as an integer
	 */
	public int height(){return BinaryNode.height(root);}
	
	/**
	 * Gets the element of the root Binary Node
	 *
	 * @return          	element stored in the Binary Node as an Object
	 */
	public Object getRootObj() throws BinaryTreeException{
		if(root==null) throw new BinaryTreeException("Empty tree.");
		else 
			return root.element;
	}
	
	/**
	 * Sets the element of the root Binary Node
	 *
	 * @param x			  	Object to set element to 	
	 */
	public void setRootObj(Object x) throws BinaryTreeException{
		if(root==null) throw new BinaryTreeException("Empty tree.");
		else 
			root.element = x;
	}
	
	/**
	 * Gets the Binary Tree to the left of current Binary Node
	 * 	 
	 * @return          	subtree to the left of current Binary Node as a Binary Tree
	 */
	public BinaryTree getLeft() throws BinaryTreeException{
		if(root==null) throw new BinaryTreeException("Empty tree.");
		else{
			BinaryTree t = new BinaryTree();
			t.root = root.left;
			return t;
		}
	}
	
	/**
	 * Sets the left subtree of the current Binary Node
	 *
	 * @param t			  	Binary Tree to set as left subtree of current Binary Node	
	 */
	public void setLeft(BinaryTree t) throws BinaryTreeException{
		if(root == null) throw new BinaryTreeException ("Empty tree.");
		else
			root.left = t.root;
	}
	
	/**
	 * Gets the Binary Tree to the right of current Binary Node
	 * 	 
	 * @return          	subtree to the right of current Binary Node as a Binary Tree
	 */
	public BinaryTree getRight() throws BinaryTreeException{
		if(root==null) throw new BinaryTreeException("Empty tree.");
		else{
			BinaryTree t = new BinaryTree();
			t.root = root.right;
			return t;
		}
	}
	
	/**
	 * Sets the right subtree of the current Binary Node
	 *
	 * @param t			  	Binary Tree to set as right subtree of current Binary Node	
	 */
	public void setRight(BinaryTree t) throws BinaryTreeException{
		if(root == null) throw new BinaryTreeException ("Empty tree.");
		else
			root.right = t.root;
	}
	
	/**
	 * Creates the runtime exception for the Binary Tree class	
	 */
	public class BinaryTreeException extends RuntimeException{
		public BinaryTreeException(String err){
			super(err);
		}
	}
	
	/**
	 * Prints out all the Binary Nodes of a Binary Tree in numeric order
	 *
	 * @param t			  	Binary Tree to be printed
	 */
	public static void inorder(BinaryTree t) throws BinaryTreeException{
		if(!t.isEmpty()){
			inorder(t.getLeft());
			System.out.print(t.getRootObj() + " ");
			inorder(t.getRight());
		}
	}
	
	/**
	 * Inserts Objects into Binary Tree to create a proper Binary search Tree
	 *
	 * @param t			  	Binary Tree to insert Object into
	 * 
	 * @param x				Object to be inserted
	 * 
	 * @return 				returns the modified Binary Tree	
	 */
	public static BinaryTree insert(BinaryTree t, Object x){
		if(t.isEmpty())
			return new BinaryTree(x);
		else{
			if(((Integer)t.getRootObj()).intValue()<((Integer)x).intValue()) t.setRight(insert(t.getRight(),x));
			else
				t.setLeft(insert(t.getLeft(),x));
			return t;
		}
	}
	
	/**
	 * Plots the Binary Tree into a 2-dimensional array
	 *
	 * @param t			  	Binary Tree to be plotted
	 * @param picture		2-dimensional character array to plot Binary Tree into
	 * @param currRow		current row number
	 * @param offset		amount to offset by for placement of next Binary Node
	 * @param place			current array column reference point for placement of Binary Nodes
	 * @param slah			designated symbol to insert to indicate/show connecting Binary Nodes when printed out to console
	 * 
	 * @return 				modified character array	
	 */
	public static char[][] drawTree(BinaryTree t, char[][] picture, int currRow, int offset, int place, char slash){
		if(!t.isEmpty()){
			String x = t.getRootObj().toString();
			picture[currRow][place]=x.charAt(0);
			//if there is a second digit, add it to the next column
			if(x.length()>1){
				picture[currRow][place+1]=x.charAt(1);
			}
			//now add the appropriate slash line above the number
			if(currRow>0)picture[currRow-1][place]=slash;
			currRow=currRow+2;
			offset = offset/2;
			
			//recursive calls
			drawTree(t.getLeft(), picture, currRow, offset, (place-(offset)-1), '/');
			drawTree(t.getRight(), picture, currRow, offset, (place+(offset)+1), '\\');
		}
		return picture;
	}
	
	/**
	 * Prints out 2-dimensional character array illustrating the Binary Tree
	 *
	 * @param array			2-dimensional character array to plot Binary Tree into
	 * @param row		  	number of rows in the array
	 * @param currRow		number of columns in the array 
	 */
	public static void displayTree(char array[][], int row, int col){
		for(int r = 0; r<row; r++){
			for(int c = 0; c<col; c++){
				if(array[r][c]=='\0'){
					System.out.print(" ");
				}
				else System.out.print(array[r][c]);
			}
			System.out.println();
		}
		System.out.println();
	}
}

public class BinaryNode {
	/**
	 * This class is a Node class.
	 * Each Binary Node comprises of a 1-2 digit number.
	 */
	
	Object element;
	BinaryNode left, right;
	
	/** default constructor */
	BinaryNode(){ this(null); }
	

	/** Constructor for 1 element value */
	BinaryNode(Object e){this(e,null,null);}
	
	/** Constructor for the element value, the BinaryNode to the left and the BinaryNode to the right */
	BinaryNode(Object e, BinaryNode ln, BinaryNode rn){
		element = e;
		left = ln;
		right = rn;
	}
	
	/**
	 * Counts the number of Binary Nodes in the Binary Tree with root node n
	 *
	 * @param n			  	root node	
	 * 
	 * @return          	count of the nodes in this Binary Tree as an integer
	 */
	static int nodeCount(BinaryNode n){
		if(n==null) return 0;
		else return 1 + nodeCount(n.left) + nodeCount(n.right);
	}
	
	/**
	 * Measures height of Binary Tree with root node n
	 *
	 * @param n			  	root node	
	 * 
	 * @return          	height of the Binary Tree as an integer
	 */
	static int height(BinaryNode n){
		if(n==null) return -1;
		else
			return 1 + Math.max(height(n.left), height(n.right));
	}
}

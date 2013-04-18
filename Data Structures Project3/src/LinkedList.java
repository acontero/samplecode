
public class LinkedList {
	
	/**
	 * This class is a doubly linked list of nodes. Each node representing a node on a map
	 * with a particular color assigned to it.
	 */
	
	/** First node in linked list - dummy node */
	public Node first = new Node();
	
	/** Last node in linked list */
	public Node last = first;
	
	/** Size of linked list */
	private int size = 0;
	
	/** Get first node of the linked list */
	public Node getFirst(){
		return first;
	}
	
	/** Get last node of the linked list */
	public Node getLast(){
		return last;
	}
	
	/** Get size of the linked list */
	public int getSize(){
		return size;
	}
	
	/**
	 * Appends a map node to the LinkedList
	 *
	 * @param i		Node to be appended
	 */
	public void append(Node i) {
		Node toAdd = new Node(i);
		last.setNext(toAdd);
		toAdd.setPrevious(last);
		last = toAdd;
		size++;
	}
	
	/**
	 * Checks if the current color selected for a particular node is 
	 * a violation of the adjacency table. This method traverses the adjacency table
	 * for the data that pertains to this particular node.
	 *
	 * @param x			  	The color selected for the current node, as an integer
	 * @param i				The current node number
	 * @param a				The adjacency table as an integer array
	 * @param numOfNodes	The number of nodes in this map
	 * 
	 * @return 				true if valid, false if not
	 */
	boolean checkAdjacency(int x,int i,int[][]a, int numOfNodes){
		for(int c=0; c<numOfNodes; c++){
			if(a[i][c]==1){
				//check that adjacent node
				boolean good = checkNode(c,x);
				if(good == false) return false;
			}
		}
		return true;
	}
	
	/**
	 * Helper method for the checkAdjaceny method. It checks the adjacent nodes 
	 * to see if they have been assigned the same color selected for the current node
	 *
	 * @param c			  	The adjacent node number
	 * @param x 			The color assigned to the current node, as an integer
	 *
	 * @return 				true if they have the same color, false if they do not
	 */
	boolean checkNode(int c, int x){
		Node t = this.first;
		for(int n = 0; n<=c; n++){
			t = t.getNext();
		}
		//if the adjacent node has the same color return false
		if(t.getColor()==x) return false;
		else return true;
	}
	
	/**
	 * Prints the final solution list of nodes and their color assignments.
	 */
	void printList(){
		System.out.println("FINAL SOLUTION:");
		String[] colorNames = {" ", "Red", "Yellow", "Blue", "Green"};
		Node p = this.first.getNext();
		int count = 0;
		while(p.getNext()!=null){
			int z = p.getColor();
			System.out.print("Node " + count + " has color " + colorNames[z]);
			count++;
			p = p.getNext();
			System.out.println();
		}
		System.out.println();
	}
	
	/**
	 * Prints the node responsible for the backtracking step and
	 * prints the list of nodes with their current color assignments.
	 *
	 * @param n		The node responsible for the backtracking
	 */
	void printProgressThusFar(int n){
		System.out.println("The node that has caused the backtracking is Node " + n);
		System.out.println("Nodes processed so far:");
		String[] colorNames = {" ", "Red", "Yellow", "Blue", "Green"};
		Node p = this.first.getNext();
		int count = 0;
		while(p.getNext()!=null){
			int z = p.getColor();
			if(z!=0){
				System.out.println("Node " + count + " has color " + colorNames[z]);
			}
			count++;
			p = p.getNext();
		}
		System.out.println();
	}
}

public class Node {

	/**
	 * This class is a Node class where each node represents a region on a map.
	 * Each node holds a color assignment, and pointers to the previous and next
	 * nodes in a linked list.
	 */
	
	private int color;
	private Node next;
	private Node previous;
	
	/** default constructor */
	public Node(){
		this(0,null,null);
	}
	
	/** Constructor for 1 subsequent node in the linked list */
	public Node(Node n){
		color = 0;
		next = n;
		previous = null;
	}
	
	/** Constructor for 1 color, 1 subsequent node and 1 previous node in the linked list */
	public Node(int x, Node n, Node p){
		color = x;
		next = n;
		previous = p;
	}
	
	/** Set method to set color */
	void setColor(int c){
		color = c;
	}
	
	/** Set method to set next node */
	void setNext(Node newNext){
		next = newNext;
	}
	
	/** Set method to set previous node */
	void setPrevious(Node newPrevious){
		previous = newPrevious;
	}
	
	/** Get method to get color */
	int getColor(){
		return color;
	}
	
	/** Get method to get next node */
	Node getNext(){
		return next;
	}
	
	/** Get method to get previous node */
	Node getPrevious(){
		return previous;
	}
	
}

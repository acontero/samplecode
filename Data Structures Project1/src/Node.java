
public class Node {

	/**
	 * This class is a Node class.
	 * Each node comprises of several digits of a large number.
	 * Ultimately, each node has up to 3 digits.
	 */
	
	private Integer element;
	private Node next;
	
	/** default constructor */
	Node(){
		this(null,null);
	}
	
	/** Constructor for 1 element value */
	public Node(Integer e){
		element = e;
		next = null;
	}
	
	/** Constructor for 1 element value and the next node */
	public Node(Integer e, Node n){
		element = e;
		next = n;
	}
	
	/** Set method to set element value of node */
	void setElement(Integer newElem){
		element = newElem;
	}
	
	/** Set method to set next node */
	void setNext(Node newNext){
		next = newNext;
	}
	
	/** Get method to get element value */
	Integer getElement(){
		return element;
	}
	
	/** Get method to get next node */
	Node getNext(){
		return next;
	}
	
}

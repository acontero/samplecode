

public class LargeNumbers {
	
	/**
	 * This class is a linked list of nodes, each node comprising of several digits of a large number.
	 * Ultimately, each node has up to 3 digits.
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
	 * Appends an Integer data element to this linked list.
	 * 
	 * @param i	  the data element to be appended.
	 *            
	 */
	public void append(Integer i) {
		Node toAdd = new Node(i);
		last.setNext(toAdd);
		last = toAdd;
		size++;
	}
	
	/**
	 * Reverse a linked list.           
	 */
	public void reverse(){ //reverse
		Node p,q;
		if (first != null){
			p = first.getNext();
			first.setNext(null);
			while (p!=null){
				q = p.getNext();
				p.setNext(first);
				first = p;
				p=q;
			}
		}
	}
	
	/**
	 * Print to the console a linked list in reverse.       
	 */
	public void printReverse(){
		//make a copy of our linked list
		LargeNumbers temp = new LargeNumbers();
		Node k = this.first.getNext();
		while (k!=null){
			temp.append(k.getElement());
			k = k.getNext();
		}
		temp.reverse();
		Node p = temp.first;
		if(p != null){
			System.out.print(p.getElement().toString());
			if(p.getNext().getElement() != null){
				System.out.print(",");
				p = p.getNext();
			}
			else{
				System.out.println();
				return;
			}
			//Instructions for first node only
			//print the first node as is, no zero place holders needed
		}
		else return; //return because there is nothing in the node to print. First node = null.
		while (p != null){ //continue printing out nodes with the following 0 place holder conditions
			Integer n = p.getElement();
			String number = p.getElement().toString();
			if(n==0 || n<10){ //print two zero place holders before value
				System.out.print("00" + number);
			}
			else if(n<100){ //print out one zero place holder before value
				System.out.print("0" + number);
			}
			else System.out.print(number); //no zero place holders needed here. Just print value.
			//decide whether a comma is necessary
			if(p.getNext().getElement() != null){
				p = p.getNext();
				System.out.print(",");
			}
			else{
				System.out.println();
				return;
			}
		}
	}
	
	/**
	 * Print to the console a linked list.       
	 */
	public void print(){
		Node p = getFirst().getNext();
		if(p != null){
			System.out.print(p.getElement().toString());
			if(p.getNext() != null)
				System.out.print(",");
			p = p.getNext();
			//Instructions for first node only
			//print the first node as is, no zero place holders needed
		}
		else return; //return because there is nothing in the node to print. First node = null.
		while (p != null){ //continue printing out nodes with the following 0 place holder conditions
			Integer n = p.getElement();
			String number = p.getElement().toString();
			if(n==0 || n<10){ //print two zero place holders before value
				System.out.print("00" + number);
			}
			else if(n<100){ //print out one zero place holder before value
				System.out.print("0" + number);
			}
			else System.out.print(number); //no zero place holders needed here. Just print value.
			
			//decide whether a comma is necessary
			if(p.getNext() != null){
				p = p.getNext();
				System.out.print(",");
			}
			else{
				System.out.println();
				return;
			}
		}
	}
}
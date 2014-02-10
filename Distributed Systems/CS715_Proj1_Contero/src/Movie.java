/**
 * Movie object class.
 */
public class Movie {
	int movieNumber;
	int capacity = 6;
	Object speaker = new Object();
	
	public Movie() {
		
	}

	public synchronized int getCurrentCapacity(){
		return capacity;
	}
	
	public synchronized void takeSeat(){
		capacity = capacity - 1;
	}
}

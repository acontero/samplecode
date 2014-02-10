import java.io.*;
import java.net.*;

public class Client {
	public static boolean[] connectedClients = new boolean[20]; 
	static Thread visitorList[] = new Thread[20];
    static Thread speakerList[] = new Thread[3];
    
    public static long startTime = System.currentTimeMillis();

    //Tried taking out Client class's main and running this method below instead to see 
  	//if that would get rid of all my null pointer exceptions but it did not.
    //public static void createThreads(){
	public static void main(String[] argv){
	
		//Create 20 Visitor and 3 Speaker threads
		//Have them all try to connect to the server.
	     
        //Instantiate visitors and start threads
		for (int i = 0; i < 20; i++) {
		    visitorList[i] = new  Thread(new Visitor(i));
		    visitorList[i].start();
		}
		
		//Instantiate speakers and start threads
		for (int i = 0; i < 3; i++) {
		    speakerList[i] = new  Thread(new Speaker(i));
		    speakerList[i].start();
		}	
		
		while(Clock.noMoreConnectionsAllowed==true){
			
		}
	}

	public static void updateConnectedClients(int x) {
		connectedClients[x] = true;
	}
		
} //Client class

/**
 * This class is the main server which takes care of all connection requests (rendezvous).  Then it will spawn 
 * child server threads which will carry out the extended rendezvous with the clients. 
 * 
 * @author Angelica Contero
 * @version CS715 Project #2 - FALL 2013
 * @since  12/21/13
 */

import java.io.*;
import java.net.*;

public class MainServer {
	
	private static int port=4444; 
	public static ServerSocket listener; 
	public static Socket clientSocket = null;
	static Clock clock = new Clock();
	public static Thread clockThread = new Thread(clock);
	
	
	// Listen for incoming connections and handle them
	public static void main(String[] argv){
		clockThread.start();
		msg("Starting Clock thread");	
		
		//Tried taking out Client class's main and running this method below instead to see 
		//if that would get rid of all my null pointer exceptions but it did not.
		//Client.createThreads();
	}
	
	public static void openServer(){
		msg("Opening ServerSocket");
		//establish new server
		try {
			listener = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//listen for connections and accept when a connection is attempted.
	//Then transfer the connection over to a spawned RendezvousThread.
	public static void listenForConnections(){
		msg("Listening for connections");
	    try {
			clientSocket = listener.accept();
			msg("Got a connection! Transferring to RendezvousThread...");
			Thread t = new Thread(new RendezvousThread(clientSocket,clock));
    	    t.start();          
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	}
	
	/**
	 * Prints out message to console.
	 *
	 * @param m			  message to be printed   
	 */
	public static void msg(String m) {
		System.out.println("[" +(System.currentTimeMillis()-Clock.startTime)+ "]" + " Main Server" + ": "+m);
	}

	

}

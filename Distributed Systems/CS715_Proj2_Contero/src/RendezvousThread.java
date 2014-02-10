import java.io.*;
import java.net.*;

public class RendezvousThread implements Runnable{
	String name = "RendezvousThread";
	Clock sharedClock;
	Socket clientSocket;
	String typeClient;
	
	public RendezvousThread(Socket s, Clock clock){
		this.clientSocket = s;
		this.sharedClock = clock;
	}
	/** MovieProtocol Thread run method */
	public void run() {
		
		try {
			
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
	        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	        String outputLine;
	        
	        
	        String fromClient = in.readLine();
	        //receive info from connecting client about what kind of thread it is
            //if it's the wrong type, then close the clientSocket.
	    	//otherwise, update the counts
	        	if(sharedClock.endOfDay==true) out.println("sorry");
    	    	if(fromClient.equals("visitor")){
    	    		if(sharedClock.okToAddClient("visitor")){
    	    			sharedClock.numConnections++;
    	    			sharedClock.numVisitors++;
    	    			typeClient = "visitor";
    	    			out.println("VALIDATED");
    	    			msg("I'm paired with a " + typeClient);
    	    		}
    	    	}
    	    	else if(fromClient.equals("speaker")){
    	    		if(sharedClock.okToAddClient("speaker")){
    	    			sharedClock.numConnections++;
    	    			sharedClock.numSpeakers++;
    	    			typeClient = "speaker";
    	    			out.println("VALIDATED");
    	    			msg("I'm paired with a " + typeClient);	
    	    		}
    	    	}
    	    	else{
    	    		msg("Disconnecting");
    	    		out.println("Bye");
    	    		clientSocket.close();
    	    	}
    	    	
    	    	while(true){
    	    		if(sharedClock.checkIntro() == true){
    	    			out.println("INTRO");
    	    		}
    	    		if(sharedClock.checkMoviePlaying() == true){
    	    			out.println("MOVIEPLAYING");
    	    		}
    	    		if(sharedClock.checkSessionOver() == true){
    	    			out.println("done");
    	    			return;
    	    		}	
    	    		out.println("waiting");
    	    	}
    	    	 	    	
    	    	
    	    	//FIRST GET SPEAKERS/VISITORS TO GET VALIDATED
    	    	
    	    	//NOW KEEP CHECKING CLOCK TO KNOW WHICH TIME PERIOD WE ARE CURRENTLY IN
    	    	//IF THIS THREAD IS PARTNERED WITH A SPEAKER,
    	    	//MAKE SURE TO PROMPT THE SPEAKER WHEN NECESSARY
    	    	//IF THIS THREAD IS PARTNERED WITH A VISITOR, 
    	    	//MAKE SURE TO PROMPT THE VISITOR WHEN NECESSARY
    	    	
    	    	//AND WHEN IT'S TIME FOR Q&A
    	    	//IF YOU'RE PARTNERED VISITOR IS SELECTED FOR QUESTION ASKING
    	    	//STORE IN A QUEUE IN THE CLIENT CLASS
    	    	
    	    	//IF YOU'RE PARTNERED WITH A SPEAKER, 
    	    	//REMOVE A VISITOR FROM THE QUEUE IN THE CLIENT CLASS 
    	    	//TO SIMULATE ANSWERING A QUESTION
    	    	
    	    	//FINALLY, GET THE 3 SESSIONS TO WORK AND 
    	    	
    	    	
    	    	
    	    	
    	    	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	public void msg(String m) {
		System.out.println("[" +(System.currentTimeMillis()-Clock.startTime)+ "] RendezvousThread: "+m);
	}
	
}


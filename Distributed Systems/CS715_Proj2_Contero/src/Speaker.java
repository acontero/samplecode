import java.io.*;
import java.net.*;

/**
 * The speaker threads are run on the client side of this program and execute concurrently with
 * the visitor threads assigned to its session.
 */
public class Speaker implements Runnable {

	public Socket connection;
	public String type = "speaker";
	public int id = 0;
	String fromServer;
	PrintWriter out;
	BufferedReader in;
	
	/** Constructor */
	public Speaker(int x){
		id = x;
	}
	
	/** Visitor Thread run method */
	public void run() {
		try {
			tryToConnect();
			if(fromServer.equals("VALIDATED")){
				msg("Connected Successfully and was Validated");
				
			}
			else{
				msg("Waiting for another session to connect");
				while(!fromServer.equals("VALIDATED")) tryToConnect();
			}
			
			fromServer = in.readLine();
			while(!fromServer.equals(null)){
				if(fromServer.equals("sorry"))return; //it's the end of the day, go home
				if(fromServer.equals("INTRO")){
					msg("Am delivering intro right now.");
				}
				if(fromServer.equals("done"))return;
				fromServer = in.readLine();
			}	
		} catch (IOException e) {
				e.printStackTrace();
		}

		
	}
	
	public void msg(String m) {
		System.out.println("[" +(System.currentTimeMillis()-Client.startTime)+ "] Speaker "+id+": "+m);
	}
	
	public void tryToConnect() throws UnknownHostException, IOException {
		connection = new Socket("", 4444);
		out = new PrintWriter(connection.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		msg("sending over info now!");
		out.println(type);

		fromServer = in.readLine();

		msg("received from Server: " + fromServer);
	}
}

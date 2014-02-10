import java.io.*;
import java.net.*;

/**
 * The visitor threads are run on the client side of this program and execute concurrently with each other and alongside
 * a speaker thread.
 */
public class Visitor implements Runnable {
	public Socket connection;
	public String type = "visitor";
	public int id = 0;
	String fromServer;
	PrintWriter out;
	BufferedReader in;
		
	/** Constructor */
	public Visitor(int x){
		id = x;
	}
	
	/** Visitor Thread run method */
	public void run() {
		try {
			tryToConnect();
			if(!fromServer.equals(null) && fromServer.equals("VALIDATED")){
				msg("Connected Successfully and was Validated");
				Client.updateConnectedClients(id);
				msg("connectedClients array index = " +id+ " " +Client.connectedClients[id]);
				
			}
			else{
				msg("Waiting for another session to connect");
				while(!fromServer.equals("VALIDATED")) tryToConnect();
			}
			
			//validated threads should continue
			fromServer = in.readLine();
			while(!fromServer.equals(null)){
				if(fromServer.equals("sorry"))return; //it's the end of the day, go home
				if(fromServer.equals("MOVIEPLAYING")){
					msg("Am watching movie now.");
				}
				if(fromServer.equals("done"))return;
				fromServer = in.readLine();
			}
				
	
		}catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void msg(String m) {
		System.out.println("[" +(System.currentTimeMillis()-Client.startTime)+ "] Visitor "+id+": "+m);
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


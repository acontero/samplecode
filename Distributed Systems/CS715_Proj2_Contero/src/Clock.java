import java.io.IOException;
import java.net.Socket;

/**
 * An instance of the clock thread class is created by the Main Server.  The clock thread can control
 * the number of main server connections (6 visitors and 1 speaker per session in this case).
 * After the session is over, the clock thread can close the connection for some period of time (break)
 * and then open again in order to allow the next visitors and speaker to connect to the main server for 
 * the next session.
 * 
 */ 
public class Clock implements Runnable{
	
	String nameOfThread = null;
	Socket server; 
	
	public int maxConnections = 7;
	public int numConnections = 0;
	public int maxVisitors = 6;
	public int maxSpeakers = 1;
	public int numVisitors = 0;
	public int numSpeakers = 0;
	public static boolean noMoreConnectionsAllowed = false;
	
	public boolean INTRO = false;
	public boolean MOVIEPLAYING = false;
	public boolean QASESSION = false;
	public boolean ASKINGQ = false;
	public boolean sessionOver = false;
	public boolean endOfDay = false;
	//One hour and a half is represented by 9000 ms. (6000 for 1 hour, 1500 for 15 min.)
	public static long startTime = System.currentTimeMillis();
	
	/** Constructor */
	public Clock(){
		nameOfThread = "The Clock";
	}
	
	/** Get Method for nameOfThead */
	public String getName(){
		return nameOfThread;
	}
	
	/** Clock Thread run method */
	public void run() {
		try {
			//wait for visitors to arrive
			Thread.sleep(1500); //wait for 15 minutes while visitors and speakers arrive
			
			//FIRST SESSION
			MainServer.openServer();
			System.out.println("Starting Server!");
			while(okToConnect()){
				System.out.println("Ok to listen...");
				MainServer.listenForConnections();
			}
			noMoreConnectionsAllowed = true;

//			while(getCurrentTime() <10500){
				INTRO = true;
				msg("Intro 1 time!");
				Thread.sleep(1500);
				INTRO = false;
				msg("Intro 1 finished");
				MOVIEPLAYING = true;
				msg("Movie 1 time!");
				Thread.sleep(6000);
				MOVIEPLAYING = false;
				msg("Movie 1 finished.");
				Thread.sleep(1500);

			msg("It's the end of session 1 so everybody leave!");
			msg("Ending session 1 now.");
			sessionOver = true;
			numConnections = 0;
			numSpeakers = 0;
			numVisitors = 0;
			noMoreConnectionsAllowed = false;
			Thread.sleep(1500); //break
			
			//SECOND SESSION
			while(okToConnect()){
				MainServer.listenForConnections();
			}
			noMoreConnectionsAllowed = true;
			
			sessionOver = false;
//			while(getCurrentTime() <19500){
				INTRO = true;
				msg("Intro 2 time!");
				Thread.sleep(1500);
				INTRO = false;
				msg("Intro 2 finished");
				MOVIEPLAYING = true;
				msg("Movie 2 time!");
				Thread.sleep(6000);
				MOVIEPLAYING = false;
				msg("Movie 2 finished.");
				Thread.sleep(1500);
//			}
			msg("It's the end of session 2 so everybody leave!");
			msg("Ending session 2 now.");
			sessionOver = true;
			numConnections = 0;
			numSpeakers = 0;
			numVisitors = 0;
			noMoreConnectionsAllowed = false;
			Thread.sleep(1500); //break
			
			//THIRD SESSION
			while(okToConnect()){
				MainServer.listenForConnections();
			}
			noMoreConnectionsAllowed = true;
			
			sessionOver = false;
//			while(getCurrentTime() <21000){
				INTRO = true;
				msg("Intro 3 time!");
				Thread.sleep(1500);
				INTRO = false;
				msg("Intro 3 finished");
				MOVIEPLAYING = true;
				msg("Movie 3 time!");
				Thread.sleep(6000);
				MOVIEPLAYING = false;
				msg("Movie 3 finished.");
				Thread.sleep(1500);
//			}
			msg("It's the end of session 3 so everybody leave!");
			msg("Ending session 3 now.");
			sessionOver = true;
			numConnections = 0;
			numSpeakers = 0;
			numVisitors = 0;
			noMoreConnectionsAllowed = false;
		
			msg("Closing server for the day.");
			endOfDay = true;
			MainServer.clientSocket.close();
			return;
			//entire day should have taken 22500
			//end of day now:
			//so print out array so I can see who has been to a session:			

			
		} catch (InterruptedException | IOException e) {
			msg(" has been interrupted.");
		}
	}
	
//	public void notifySession() throws InterruptedException{
//		synchronized(session){
//			session.notifyAll();
//		}
//	}
//	
//	public void notifyConnectToServer() throws InterruptedException{
//		synchronized(connectToServer){
//			connectToServer.notifyAll();
//		}
//	}
	
	public int getNumConnections(){
		return this.numConnections;
	}

	
	/** Get current time, which is the time from the startTime of this clock */
	public long getCurrentTime(){
		return System.currentTimeMillis()-this.startTime;
	}
	
	/**
	 * Prints out message to console.
	 *
	 * @param m			  message to be printed   
	 */
	public void msg(String m) {
		System.out.println("[" +(System.currentTimeMillis()-this.startTime)+ "] "+getName()+": "+m);
	}

	public synchronized boolean okToAddClient(String type) {
		if(numConnections + 1 <= maxConnections){
			if(type.equals("visitor")){
				if(numVisitors + 1 <= maxVisitors) return true;
			}
			if(type.equals("speaker")){
				if(numSpeakers + 1 <= maxSpeakers) return true;
			}
		}
		return false;
	}
	
	/**This method checks if a new connection is allowable.
	 * 
	 *  return 	true if numConnections + 1 <= maxConnections, false otherwise
	 */
	public synchronized boolean okToConnect() {
		if(numConnections + 1 <= maxConnections){
			return true;
		}
		else return false;
	}

	public synchronized boolean checkIntro() {
		return INTRO;
	}

	public synchronized boolean checkMoviePlaying() {
		return MOVIEPLAYING;
	}

	public synchronized boolean checkSessionOver() {
		return sessionOver;
	}
}






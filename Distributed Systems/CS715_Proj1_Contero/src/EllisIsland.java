import java.util.Vector;
/**
 * Using monitors, synchronized methods and blocks, and a vector of synchronized objects, 
 * this program models EllisIsland, it's museum, theater, lobby and a dressing room. 
 * It's visitors are represented as threads).  The program includes a clock thread that keeps track
 * of the schedule and time as well as a speaker.  There are 3 movie sessions a day, each of which include
 * a 15 minutes intro by the speaker, a 60 min. movie and a 15 min. Q&A where visitors can ask questions.  Those
 * visitors that do not get into a movie, due to lack of space, wait in the museum until the end of the day.
 * At the end of the day, everyone goes home.
 *  
 * @author Angelica Contero
 * @version CS715 Project - FALL 2013
 * @since  11/8/13
 */

public class EllisIsland {
	public static Movie movie1 = new Movie();
	public static Movie movie2 = new Movie();
	public static Movie movie3 = new Movie();
	public static Museum museum = new Museum();
	public static Theater theater = new Theater();
	public static Lobby lobby = new Lobby();
	public static DressingRoom dressingRoom = new DressingRoom();
	public static PauseClock pause = new PauseClock();
	public static Vector waitingQuestionAskers = new Vector();
	public static boolean QandAStarted = false;
	public static boolean okToStart = false;
	
	public static void main(String[] argv){
		int theaterCapacity = 6;
		int numSessionsPerDay = 3;
		Thread clock = new Thread(new Clock());
		clock.start();
		
		Thread visitorList[] = new Thread[20];
		//Instantiate Visitors and start threads
		//Visitors are assigned numbers when instantiated here to be part of their nameOfThread
		for (int i = 0; i < visitorList.length; i++) {
		    visitorList[i] = new Thread(new Visitor(i));
		    visitorList[i].start();
		}
		
		Thread speaker = new Thread(new Speaker());
		speaker.start();
	}
	
	/** Synchronized method to set boolean QandAStarted flag to true.
	 *  return  true, if successfully changed flag to true. 
	 *  return  false otherwise
	 *  
	 */
	public synchronized static boolean startQandA(){
		if(QandAStarted==false){
			//System.out.println("QandAStarted is being changed to TRUE");
			QandAStarted = true;
			return true;
		}
		else return false;
	}
	
	/** Synchronized method to set boolean QandAStarted to false. */
	public synchronized static void endQandA(){
		//void because only 1 thread, the clock, will call it.
		//so probably doesn't need to be synchronized either in that case...
		if(QandAStarted==true){
			//System.out.println("QandAStarted is being changed to FALSE by the clock");
			QandAStarted = false;
		}
	}
	
	/** Synchronized method to check okToStart flag and change it to true if needed. 
	 *  return  true, if successfully changed okToStart to true. 
	 *  return  false otherwise
	 *  
	 */
	public synchronized static boolean okToStartSessions(){
		if(okToStart==false){
			//System.out.println("okToStart is being changed to TRUE");
			okToStart = true;
			return true;
		}
		else return false;
	}

}

import java.util.*;
/**
 * Visitor thread class.
 */
public class Visitor implements Runnable{
	public static long time = System.currentTimeMillis(); 

	Clock clock;
	String nameOfThread;
	int numOfThread;
	int movieNumberAcquired;
	boolean askQuestion = decideIfAskQuestion();
	
    /** Constructor */
	public Visitor(int i){
		nameOfThread = "Visitor"+"-"+i;
		numOfThread = i;
	}
	
	/** Get Method for nameOfThead */
	public String getName(){
		return nameOfThread;
	}
	
	/** Visitor Thread run method */
	public void run(){
		try {
			waitInLobby();
			boolean gotMovie = enterRoom();//will find a movie and wait for movie to start
			if(gotMovie){
				if(askQuestion) askQuestion();//some visitors will ask questions
				else waitForSessionToEnd();//the others will wait until the session is over
			}
			//if didn't get a movie seat, wait in the museum until the end of the day
			else waitInMuseum();
			
		} catch (InterruptedException e) {
			msg(" has been interrupted.");
		}	
	}//end run method
	
	/** Visitor method to wait on the lobby object. */
	public void waitInLobby() throws InterruptedException{
		synchronized(EllisIsland.lobby){
			EllisIsland.lobby.wait();
		}
	}
	
	/** Visitor method to wait on the theater object. */
	public void waitForSessionToEnd() throws InterruptedException{
		synchronized(EllisIsland.theater){
			EllisIsland.theater.wait();
		}
	}
	
	/** Customer tries to find a movie session that is not full, 
	 * otherwise he/she waits in  museum*/
	public boolean enterRoom() throws InterruptedException{
		while(true){
			try{
				if(EllisIsland.movie1.getCurrentCapacity()>0){
					EllisIsland.movie1.takeSeat();
					msg("is waiting for Movie1");
					movieNumberAcquired = 1;
					synchronized(EllisIsland.movie1){
						EllisIsland.movie1.wait();
					}
					break;
				}
		
				else if(EllisIsland.movie2.getCurrentCapacity()>0){
					EllisIsland.movie2.takeSeat();
					msg("is waiting for Movie2");
					movieNumberAcquired = 2;
					synchronized(EllisIsland.movie2){
						EllisIsland.movie2.wait();
					}
					break;
				}
				
				else if(EllisIsland.movie3.getCurrentCapacity()>0){
					EllisIsland.movie3.takeSeat();
					msg("is waiting for Movie3");
					movieNumberAcquired = 3;
					synchronized(EllisIsland.movie3){
						EllisIsland.movie3.wait();
					}
					break;
				}
				
				else{
					//1 visitor will notify clock if all movie theaters are filled
					if(EllisIsland.movie1.getCurrentCapacity()==0 &&
							EllisIsland.movie2.getCurrentCapacity()==0 &&
							EllisIsland.movie3.getCurrentCapacity()==0){
						checkIfReadyToStart();
					}
					return false;
				}
			}catch(InterruptedException e){
				msg(" has been interrupted.");
				continue;
			}//end catch
		}//end while(true)		
		return true;
	}//end method
	
	/** Visitor method to notify the clock. */
	public void notifyTheClock(){
		synchronized(EllisIsland.pause){
			EllisIsland.pause.notify();
		}
	}
	
	/** Visitor helper method to check if session can start, and notifies the speaker. */
	public void checkIfReadyToStart() throws InterruptedException{
		if(EllisIsland.okToStartSessions()){
			notifyTheClock();
			msg("notified the clock all movies are full.");
		}
	}
	
	/** Visitor method to notify the speaker. */
	public void notifyTheSpeaker(){
		synchronized(EllisIsland.dressingRoom){
			EllisIsland.dressingRoom.notify();
		}
	}
	
	/** Visitor method to determine if the visitor will ask a question during Q&A 
	 *  return 	true if this visitor will answer a question
	 *  return 	false otherwise
	 */
	public boolean decideIfAskQuestion(){
		Random random = new Random();
		boolean result = random.nextBoolean();
		return result;
	}
	
	/** Visitor method to wait on a question object and be inserted into the line of 
	 *  waiting question-askers. Once question is answered, the visitor will wait for 
	 *  session to end.
	 */
	public void askQuestion(){
		msg("wants to ask a question so is getting in line");
		Object 	question = new Object();
		synchronized(question) {
			EllisIsland.waitingQuestionAskers.addElement(question);
			if(EllisIsland.QandAStarted==false){
				if(EllisIsland.startQandA()){
					notifyTheSpeaker();
					msg("notified the speaker to start");
				}
			}
			
			while(true){
				try{
					question.wait();
					msg("had question answered, and will wait for end of session.");
					waitForSessionToEnd();
					return;
				}catch(InterruptedException e){
					msg("has been interrupted");
					continue;
				}
			}
		}
		
	}
	
	/** Visitor method to wait on the museum object. */
	public void waitInMuseum(){
		while(true){
			try{
				msg("is waiting at the museum for everyone to finish");
				movieNumberAcquired = 0;
				synchronized(EllisIsland.museum){
					EllisIsland.museum.wait();
					break;
				}
			}catch(InterruptedException e){
				msg(" has been interrupted.");
				continue;
			}
		}
	}
	
	/**
	 * Prints out message to console.
	 *
	 * @param m			  message to be printed   
	 */
	public void msg(String m) {
		System.out.println("[" +(System.currentTimeMillis()-clock.startTime)+ "] "+getName()+": "+m);
	}
	
}

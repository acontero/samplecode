/**
 * Speaker thread class.
 */
public class Speaker implements Runnable{
	public static long time = System.currentTimeMillis(); 

	Clock clock;
	String nameOfThread = "The Speaker";
	
	/** Constructor */
	public Speaker(){
	}
	
	/** Get Method for nameOfThead */
	public String getName(){
		return nameOfThread;
	}
	
	/** Speaker Thread run method */
	//based on the project instructions story, I have the speaker sleep for the duration of the intro then notify the clock when he/she is done
	//so that the clock and start the movie and track its duration (sleep for 6000 ms)
	//I also have the speaker take a break, as described in the project instructions, for 1000 ms.
	public void run(){
		try {
			for(int i = 1; i<4; i++){
				waitToGiveIntro(i);
				//give intro
				Thread.sleep(1500);//performs intro is 15 min = 1500 ms
				msg("finished intro for movie"+i);
				//signals clock
				notifyTheClock();
				msg("just notified the clock that intro"+i+" is done");
				//takes a break
				Thread.sleep(1000); //takes a break for 10 minutes
				//then waits for Q&A
				waitForQAndA(i);//will be notified by a visitor
				answerQuestions();
			}
			//wait in museum until the end of the day, which is determined by the clock!
			waitInMuseum();
	
		} catch (InterruptedException e) {
			msg(" has been interrupted.");
		}	
	}//end run method
	
	/** Speaker method to wait on dressingRoom object until time to give intro 
	 * 
	 *  @param n		session number   
	 */
	public void waitToGiveIntro(int n){
		while(true){
			try {	
				synchronized(EllisIsland.dressingRoom){
					EllisIsland.dressingRoom.wait();
					//delivers intro
					msg("Starting intro for movie"+n+"!");
					break;
				}
			}catch(InterruptedException e){
				msg(nameOfThread + " has been interrupted.");
				continue;
			}
		}	
	}
	
	/** Speaker method to wait on dressingRoom object until time to start Q&A 
	 * 
	 *  @param n		session number   
	 */
	public void waitForQAndA(int n){
		while(true){
			try {	
				synchronized(EllisIsland.dressingRoom){
					EllisIsland.dressingRoom.wait();
					//delivers intro
					msg("Q&A"+n+" starting!");
					break;
				}
			}catch(InterruptedException e){
				msg(nameOfThread + " has been interrupted.");
				continue;
			}
		}	
	}
	
	/** Speaker method to notify the clock on its pause object */
	public void notifyTheClock() throws InterruptedException{
		synchronized(EllisIsland.pause){
			EllisIsland.pause.notify();
		}
	}
	
	/** Speaker method to answer as many questions on the waitingQuestionAskers vector before 
	 * the session is over.  Once it's over, speaker releases everyone waiting in the line.  
	 */
	public void answerQuestions(){
		while(EllisIsland.QandAStarted){
			while(!EllisIsland.waitingQuestionAskers.isEmpty()){
				synchronized(EllisIsland.waitingQuestionAskers.firstElement()){
					EllisIsland.waitingQuestionAskers.firstElement().notify();
				}
				EllisIsland.waitingQuestionAskers.removeElementAt(0);
				msg("answered a question");
			}
			msg("all questions have been answered!");
			return;
		}
		msg("Q&A is over so must release all from the Q&A line!");
		EllisIsland.waitingQuestionAskers.notifyAll();
	}
	
	/** Speaker method to wait on museum object until the end of the day, 
	 *  as determined by the clock  
	 */
	public void waitInMuseum(){
		while(true){
			try{
				msg("is waiting at the museum for everyone to finish");
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

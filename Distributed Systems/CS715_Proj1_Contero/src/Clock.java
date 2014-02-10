/**
 * Clock thread class.
 */
public class Clock implements Runnable{
	public static long startTime = System.currentTimeMillis();
	
	//One hour and a half is represented by 9000 ms. (6000 for 1 hour, 1500 for 15 min.)
	String nameOfThread;
	
	/** Constructor */
	public Clock(){
		nameOfThread = "The Clock";
	}
	
	/** Get Method for nameOfThead */
	public String getName(){
		return nameOfThread;
	}
	
	/** Speaker Thread run method */
	//based on the project instructions story, I have the speaker simulate the intro by sleeping for 1500 ms
	//the it signals the clock to start the movie, simulated by sleep for 6000 ms.
	//The clock also starts the Q&A session which is simulated by sleep for 1500 ms.
	public void run() {
		try {
			//wait for visitors to arrive
			Thread.sleep(3000);
			//tell people to find a theater and take their seats
			notifyPeople(EllisIsland.lobby); 
			if(EllisIsland.okToStart==false){
				pause();//wait until visitors are settled to get the Speaker
			}
			//SESSIONS
			for(int i = 1; i<4; i++){
				msg("is going to notify speaker it's time to begin session "+i);
				notifySpeaker();//start speaker portion of movie session
				//intro is being done
				msg("is now waiting until intro done");
				pause();//wait until intro is finished
				msg("says intro for movie"+i+" is finished, so now start movie"+i+"!");
				Thread.sleep(6000);//movie plays
				msg("movie"+i+" is over.");
				//movie's over so release people from movie1
				if(i==1) notifyPeople(EllisIsland.movie1);
				else if(i==3) notifyPeople(EllisIsland.movie2);
				else notifyPeople(EllisIsland.movie3);
				Thread.sleep(1500);//Q&A session is 15 minutes = 1500ms
				EllisIsland.endQandA();
				releaseVisitorsInCurrentSession();
				Thread.sleep(1500); //15 min break between sessions
			}
			msg("It's now the end of the day, so everybody go home!");
			releaseEveryone();
		} catch (InterruptedException e) {
			msg(" has been interrupted.");
		}
	}
	
	/** Clock method to notify all visitors in the session
	 *  once the session is over. 
	 */
	public void releaseVisitorsInCurrentSession() {
		synchronized(EllisIsland.theater){
			EllisIsland.theater.notifyAll();
		}
	}

	/** Clock method to notify all visitors and speaker
	 *  waiting in the museum once the session is over. 
	 */
	public void releaseEveryone(){
		synchronized(EllisIsland.museum){
			EllisIsland.museum.notifyAll();
		}
	}
	
	/** Clock method to wait on the pause object. */
	public void pause(){
		while(true){
			try {	
				synchronized(EllisIsland.pause){
					EllisIsland.pause.wait();
					break;
				}
			}catch(InterruptedException e){
				msg(" has been interrupted.");
				continue;
			}
		}
	}
	
	/** Generic Clock method to notify all those waiting on an object. */
	public void notifyPeople(Object block) throws InterruptedException{
		synchronized(block){
			block.notifyAll();
		}
	}
	
	/** Clock method to notify the speaker waiting in dressing room. */
	public void notifySpeaker() throws InterruptedException{
		synchronized(EllisIsland.dressingRoom){
			EllisIsland.dressingRoom.notifyAll();
		}
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
}

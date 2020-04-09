
/* $Id$
 *
 * Revisions:
 *   $Log: Lane.java,v $
 *   Revision 1.52  2003/02/20 20:27:45  ???
 *   Fouls disables.
 *
 *   Revision 1.51  2003/02/20 20:01:32  ???
 *   Added things.
 *
 *   Revision 1.50  2003/02/20 19:53:52  ???
 *   Added foul support.  Still need to update laneview and test this.
 *
 *   Revision 1.49  2003/02/20 11:18:22  ???
 *   Works beautifully.
 *
 *   Revision 1.48  2003/02/20 04:10:58  ???
 *   Score reporting code should be good.
 *
 *   Revision 1.47  2003/02/17 00:25:28  ???
 *   Added disbale controls for View objects.
 *
 *   Revision 1.46  2003/02/17 00:20:47  ???
 *   fix for event when game ends
 *
 *   Revision 1.43  2003/02/17 00:09:42  ???
 *   fix for event when game ends
 *
 *   Revision 1.42  2003/02/17 00:03:34  ???
 *   Bug fixed
 *
 *   Revision 1.41  2003/02/16 23:59:49  ???
 *   Reporting of sorts.
 *
 *   Revision 1.40  2003/02/16 23:44:33  ???
 *   added mechnanical problem flag
 *
 *   Revision 1.39  2003/02/16 23:43:08  ???
 *   added mechnanical problem flag
 *
 *   Revision 1.38  2003/02/16 23:41:05  ???
 *   added mechnanical problem flag
 *
 *   Revision 1.37  2003/02/16 23:00:26  ???
 *   added mechnanical problem flag
 *
 *   Revision 1.36  2003/02/16 21:31:04  ???
 *   Score logging.
 *
 *   Revision 1.35  2003/02/09 21:38:00  ???
 *   Added lots of comments
 *
 *   Revision 1.34  2003/02/06 00:27:46  ???
 *   Fixed a race condition
 *
 *   Revision 1.33  2003/02/05 11:16:34  ???
 *   Boom-Shacka-Lacka!!!
 *
 *   Revision 1.32  2003/02/05 01:15:19  ???
 *   Real close now.  Honest.
 *
 *   Revision 1.31  2003/02/04 22:02:04  ???
 *   Still not quite working...
 *
 *   Revision 1.30  2003/02/04 13:33:04  ???
 *   Lane may very well work now.
 *
 *   Revision 1.29  2003/02/02 23:57:27  ???
 *   fix on pinsetter hack
 *
 *   Revision 1.28  2003/02/02 23:49:48  ???
 *   Pinsetter generates an event when all pins are reset
 *
 *   Revision 1.27  2003/02/02 23:26:32  ???
 *   ControlDesk now runs its own thread and polls for free lanes to assign queue members to
 *
 *   Revision 1.26  2003/02/02 23:11:42  ???
 *   parties can now play more than 1 game on a lane, and lanes are properly released after games
 *
 *   Revision 1.25  2003/02/02 22:52:19  ???
 *   Lane compiles
 *
 *   Revision 1.24  2003/02/02 22:50:10  ???
 *   Lane compiles
 *
 *   Revision 1.23  2003/02/02 22:47:34  ???
 *   More observering.
 *
 *   Revision 1.22  2003/02/02 22:15:40  ???
 *   Add accessor for pinsetter.
 *
 *   Revision 1.21  2003/02/02 21:59:20  ???
 *   added conditions for the party choosing to play another game
 *
 *   Revision 1.20  2003/02/02 21:51:54  ???
 *   LaneEvent may very well be observer method.
 *
 *   Revision 1.19  2003/02/02 20:28:59  ???
 *   fixed sleep thread bug in lane
 *
 *   Revision 1.18  2003/02/02 18:18:51  ???
 *   more changes. just need to fix scoring.
 *
 *   Revision 1.17  2003/02/02 17:47:02  ???
 *   Things are pretty close to working now...
 *
 *   Revision 1.16  2003/01/30 22:09:32  ???
 *   Worked on scoring.
 *
 *   Revision 1.15  2003/01/30 21:45:08  ???
 *   Fixed speling of received in Lane.
 *
 *   Revision 1.14  2003/01/30 21:29:30  ???
 *   Fixed some MVC stuff
 *
 *   Revision 1.13  2003/01/30 03:45:26  ???
 *   *** empty log message ***
 *
 *   Revision 1.12  2003/01/26 23:16:10  ???
 *   Improved thread handeling in lane/controldesk
 *
 *   Revision 1.11  2003/01/26 22:34:44  ???
 *   Total rewrite of lane and pinsetter for R2's observer model
 *   Added Lane/Pinsetter Observer
 *   Rewrite of scoring algorythm in lane
 *
 *   Revision 1.10  2003/01/26 20:44:05  ???
 *   small changes
 *
 * 
 */

import java.awt.*;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.logging.Logger;


public class Lane extends Thread implements PinsetterObserver, Serializable {
	private Party party;
	private Pinsetter setter;
	private HashMap scores;
	private ArrayList subscribers;
	private boolean loading = false;
	private boolean gameIsHalted;

	private boolean partyAssigned;
	private boolean gameFinished;
	private Iterator bowlerIterator;
	private int ball;
	private int bowlIndex;
	private int frameNumber;
	private boolean tenthFrameStrike;

	private int[] curScores;
	private int[][] cumulScores;
	private boolean canThrowAgain;
	
	private int[][] finalScores;
	private int gameNumber;
	
	private Bowler currentThrower;			// = the thrower who just took a throw

	/** Lane()
	 * 
	 * Constructs a new lane and starts its thread
	 * 
	 * @pre none
	 * @post a new lane has been created and its thered is executing
	 */
	public Lane() { 
		setter = new Pinsetter();
		scores = new HashMap();
		subscribers = new ArrayList();

		gameIsHalted = false;
		partyAssigned = false;

		gameNumber = 0;

		setter.subscribe( this );
		
		this.start();
	}

	public void gameFinished() {

		EndGamePrompt egp = new EndGamePrompt( ((Bowler) party.getMembers().get(0)).getNickName() + "'s Party" );
		int result = egp.getResult();
		egp.distroy();


		System.out.println("result was: " + result);

		// TODO: send record of scores to control desk
		if (result == 1) {					// yes, want to play again
			resetScores();
			resetBowlerIterator();

		} else if (result == 2) {// no, dont want to play another game
			Vector printVector;
			EndGameReport egr = new EndGameReport( ((Bowler)party.getMembers().get(0)).getNickName() + "'s Party", party);
			printVector = egr.getResult();
			partyAssigned = false;
			Iterator scoreIt = party.getMembers().iterator();
			party = null;
			partyAssigned = false;

			publish(lanePublish());

			int myIndex = 0;
			while (scoreIt.hasNext()){
				Bowler thisBowler = (Bowler)scoreIt.next();
				ScoreReport sr = new ScoreReport( thisBowler, finalScores[myIndex++], gameNumber );
				sr.sendEmail(thisBowler.getEmail());
				Iterator printIt = printVector.iterator();
				while (printIt.hasNext()){
					if (thisBowler.getNick().equals(printIt.next())){
						System.out.println("Printing " + thisBowler.getNick());
						sr.sendPrintout();
					}
				}

			}
		}

	}

	public void gameStarted() {
		if (bowlerIterator.hasNext()) {
			currentThrower = (Bowler)bowlerIterator.next();

			canThrowAgain = true;
			tenthFrameStrike = false;
			ball = 0;

			setter.ballThrown();		// simulate the thrower's ball hiting
			ball++;

			if (frameNumber == 9){
				finalScores[bowlIndex][gameNumber] = cumulScores[bowlIndex][9];
				try{
					Date date = new Date();
					String dateString = "" + date.getHours() + ":" + date.getMinutes() + " " + date.getMonth() + "/" + date.getDay() + "/" + (date.getYear() + 1900);
					ScoreHistoryFile.addScore(currentThrower.getNick(), dateString, Integer.toString(cumulScores[bowlIndex][9]));
				} catch (Exception e) {
					System.err.println("Exception in addScore. "+ e );
					assert true;
				}
			}


			setter.reset();
			bowlIndex++;

		} else {
			frameNumber++;
			resetBowlerIterator();
			bowlIndex = 0;
			if (frameNumber > 9) {
				gameFinished = true;
				gameNumber++;
			}
		}
	}

	/** run()
	 * 
	 * entry point for execution of this lane 
	 */
	@Override
	public void run() {
		
		while (true) {
			if (partyAssigned && !gameFinished) {	// we have a party on this lane, 
								// so next bower can take a throw
			
				while (gameIsHalted) {
					try {
						sleep(10);
					} catch (Exception e) { assert true ;}
				}
				gameStarted();
			} else if (partyAssigned && gameFinished) {
				gameFinished();
			}
			
			
			try {
				sleep(10);
			} catch (Exception e) {
				assert true;
			}
		}
	}

	public void checkThrow(PinsetterEvent pe) {

		if ((pe.totalPinsDown() != 10) && (pe.getThrowNumber() == 2 && !tenthFrameStrike)) {
			canThrowAgain = false;
			//publish( lanePublish() );
		}

		if (pe.getThrowNumber() == 3) {
			canThrowAgain = false;
			//publish( lanePublish() );
		}

	}
	
	/** recievePinsetterEvent()
	 * 
	 * recieves the thrown event from the pinsetter
	 *
	 * @pre none
	 * @post the event has been acted upon if desiered
	 * 
	 * @param pe 		The pinsetter event that has been received.
	 * 
	 */
	public void receivePinsetterEvent(PinsetterEvent pe) {
		
			if (pe.pinsDownOnThisThrow() >=  0) {			// this is a real throw
				markScore(currentThrower, frameNumber + 1, pe.getThrowNumber(), pe.pinsDownOnThisThrow());
	
				// next logic handles the ?: what conditions dont allow them another throw?
				// handle the case of 10th frame first
				if (frameNumber == 9) {
					if (pe.totalPinsDown() == 10) {
						setter.resetPins();
						if(pe.getThrowNumber() == 1) {
							tenthFrameStrike = true;
						}
					}

					checkThrow(pe);

				} else { // its not the 10th frame
			
					if (pe.pinsDownOnThisThrow() == 10 || pe.getThrowNumber() == 2) {		// threw a strike
						canThrowAgain = false;
						//publish( lanePublish() );
					}
				}
			}
	}
	
	/** resetBowlerIterator()
	 * 
	 * sets the current bower iterator back to the first bowler
	 * 
	 * @pre the party as been assigned
	 * @post the iterator points to the first bowler in the party
	 */
	private void resetBowlerIterator() {
		bowlerIterator = (party.getMembers()).iterator();
	}

	/** resetScores()
	 * 
	 * resets the scoring mechanism, must be called before scoring starts
	 * 
	 * @pre the party has been assigned
	 * @post scoring system is initialized
	 */
	private void resetScores() {
		Iterator bowlIt = (party.getMembers()).iterator();

		while ( bowlIt.hasNext() ) {
			int[] toPut = new int[25];
			for ( int i = 0; i != 25; i++){
				toPut[i] = -1;
			}
			scores.put( bowlIt.next(), toPut );
		}
		
		
		
		gameFinished = false;
		frameNumber = 0;
	}
		
	/** assignParty()
	 * 
	 * assigns a party to this lane
	 * 
	 * @pre none
	 * @post the party has been assigned to the lane
	 * 
	 * @param theParty		Party to be assigned
	 */
	public void assignParty( Party theParty ) {
		party = theParty;
		resetBowlerIterator();
		partyAssigned = true;
		
		curScores = new int[party.getMembers().size()];
		cumulScores = new int[party.getMembers().size()][10];
		finalScores = new int[party.getMembers().size()][128]; //Hardcoding a max of 128 games, bite me.
		gameNumber = 0;
		
		resetScores();
	}

	public void assignSavedParty( LaneEvent arg ) {
		party = arg.getParty();
		resetBowlerIterator();
		partyAssigned = true;
		frameNumber = arg.getFrameNum()-1;
		ball = arg.getBall();
		currentThrower = arg.getBowler();
		bowlIndex = arg.getIndex();
		scores = arg.getScore();
		loading = true;
		curScores = arg.getCurScores();
		cumulScores = arg.getCumulScore();

		finalScores = new int[party.getMembers().size()][128]; //Hardcoding a max of 128 games, bite me.
		gameNumber = 0;
	}

	/** markScore()
	 *
	 * Method that marks a bowlers score on the board.
	 * 
	 * @param cur		The current bowler
	 * @param frame	The frame that bowler is on
	 * @param ball		The ball the bowler is on
	 * @param score	The bowler's score 
	 */
	private void markScore( Bowler cur, int frame, int ball, int score ){
		int[] curScore;
		int index =  ( (frame - 1) * 2 + ball);

		curScore = (int[]) scores.get(cur);

	
		curScore[ index - 1] = score;
		scores.put(cur, curScore);
		getScore( cur, frame );
		publish( lanePublish() );
	}

	/** lanePublish()
	 *
	 * Method that creates and returns a newly created laneEvent
	 * 
	 * @return		The new lane event
	 */
	private LaneEvent lanePublish(  ) {
		LaneEvent laneEvent = new LaneEvent(party, cumulScores, scores, curScores, ball);
		laneEvent.setIndex(bowlIndex);
		laneEvent.setFrameNum(frameNumber+1);
		laneEvent.setMechProb(gameIsHalted);
		laneEvent.setBowler(currentThrower);
		if(loading){
			laneEvent.setLoading(true);
			loading = false;
		}
		return laneEvent;
	}

	private int countStrikes(int[] curScore, int i) {

		int strikeballs = 0;
		//This ball is the first ball, and was a strike.
		//If we can get 2 balls after it, good add them to cumul.
		if (curScore[i+2] != -1) {
			strikeballs = 1;
			if(curScore[i+3] != -1 || curScore[i+4] != -1) {
				//Still got em.
				strikeballs = 2;
			}
		}

		return  strikeballs;

	}

	private void ontwoStrikes(int[] curScore, int i) {
		cumulScores[bowlIndex][i/2] += 10;
		if(curScore[i+1] != -1) {
			cumulScores[bowlIndex][i/2] += curScore[i+1] + cumulScores[bowlIndex][(i/2)-1];
			if (curScore[i+2] != -1){
				if( curScore[i+2] != -2){
					cumulScores[bowlIndex][(i/2)] += curScore[i+2];
				}
			} else {
				if( curScore[i+3] != -2){
					cumulScores[bowlIndex][(i/2)] += curScore[i+3];
				}
			}
		} else {
			if ( i/2 > 0 ){
				cumulScores[bowlIndex][i/2] += curScore[i+2] + cumulScores[bowlIndex][(i/2)-1];
			} else {
				cumulScores[bowlIndex][i/2] += curScore[i+2];
			}
			if (curScore[i+3] != -1){
				if( curScore[i+3] != -2){
					cumulScores[bowlIndex][(i/2)] += curScore[i+3];
				}
			} else {
				cumulScores[bowlIndex][(i/2)] += curScore[i+4];
			}
		}
	}

	private void scoreDecide(int[] curScore, int i) {

		if ( i/2 == 0 ) {
			//First frame, first ball.  Set his cumul score to the first ball
			if(curScore[i] != -2){
				cumulScores[bowlIndex][i/2] += curScore[i];
			}
		} else if (i/2 != 9){
			//add his last frame's cumul to this ball, make it this frame's cumul.
			if(curScore[i] != -2){
				cumulScores[bowlIndex][i/2] += cumulScores[bowlIndex][i/2 - 1] + curScore[i];
			} else {
				cumulScores[bowlIndex][i/2] += cumulScores[bowlIndex][i/2 - 1];
			}
		}

	}

	private void scoreNormalThrow(int[] curScore, int i) {
		if (i == 18){
			cumulScores[bowlIndex][9] += cumulScores[bowlIndex][8];
		}
		if(curScore[i] != -2){
			cumulScores[bowlIndex][9] += curScore[i];
		}
	}

	private void normalThrow(int[] curScore, int i) {

		if( i%2 == 0 && i < 18){
			scoreDecide(curScore,i);
		} else if (i < 18 && curScore[i] != -1 && i > 2 && curScore[i] != -2){
			cumulScores[bowlIndex][i/2] += curScore[i];
		}
		if (i/2 == 9){
			scoreNormalThrow(curScore,i);
		} else if (i/2 == 10 && curScore[i] != -2) {
			cumulScores[bowlIndex][9] += curScore[i];
		}

	}

	/** getScore()
	 *
	 * Method that calculates a bowlers score
	 * 
	 * @param Cur		The bowler that is currently up
	 * @param frame	The frame the current bowler is on
	 * 
	 * @return			The bowlers total score
	 */
	private int getScore( Bowler Cur, int frame) {
		int[] curScore;
		int strikeballs = 0;
		int totalScore = 0;
		curScore = (int[]) scores.get(Cur);
		Arrays.fill(cumulScores[bowlIndex],0 ,10, 0);

		int current = 2*(frame - 1)+ball-1;
		//Iterate through each ball until the current one.
		for (int i = 0; i != current+2; i++){
			//Spare:
			if( i%2 == 1 && curScore[i - 1] + curScore[i] == 10 && i < current - 1 && i < 19){
				//This ball was a the second of a spare.  
				//Also, we're not on the current ball.
				//Add the next ball to the ith one in cumul.
				cumulScores[bowlIndex][(i/2)] += curScore[i+1] + curScore[i];
			} else if( i < current && i%2 == 0 && curScore[i] == 10  && i < 18){
				strikeballs = countStrikes(curScore,i);
				//This ball is the first ball, and was a strike.
				//If we can get 2 balls after it, good add them to cumul.

				if (strikeballs == 2){
					//Add up the strike.
					//Add the next two balls to the current cumulscore.
					ontwoStrikes(curScore, i);

				} else {
					break;
				}
			}else { 
				//We're dealing with a normal throw, add it and be on our way.
				normalThrow(curScore, i);
			}
		}
		return totalScore;
	}

	/** isPartyAssigned()
	 * 
	 * checks if a party is assigned to this lane
	 * 
	 * @return true if party assigned, false otherwise
	 */
	public boolean isPartyAssigned() {
		return partyAssigned;
	}
	
	/** isGameFinished
	 * 
	 * @return true if the game is done, false otherwise
	 */
	public boolean isGameFinished() {
		return gameFinished;
	}

	/** subscribe
	 * 
	 * Method that will add a subscriber
	 * 
	 * @param adding	Observer that is to be added
	 */

	public void subscribe( LaneObserver adding ) {
		subscribers.add( adding );
	}

	/** unsubscribe
	 * 
	 * Method that unsubscribes an observer from this object
	 * 
	 * @param removing	The observer to be removed
	 */
	
	public void unsubscribe( LaneObserver removing ) {
		subscribers.remove( removing );
	}

	/** publish
	 *
	 * Method that publishes an event to subscribers
	 * 
	 * @param event	Event that is to be published
	 */

	public void publish( LaneEvent event ) {
		if( !subscribers.isEmpty() ) {
			Iterator eventIterator = subscribers.iterator();
			
			while ( eventIterator.hasNext() ) {
				( (LaneObserver) eventIterator.next()).receiveLaneEvent( event );
			}
		}
	}

	/**
	 * Accessor to get this Lane's pinsetter
	 * 
	 * @return		A reference to this lane's pinsetter
	 */

	public Pinsetter getPinsetter() {
		return setter;	
	}

	/**
	 * Pause the execution of this game
	 */
	public void pauseGame() {
		gameIsHalted = true;
		publish(lanePublish());
	}
	
	/**
	 * Resume the execution of this game
	 */
	public void unPauseGame() {
		gameIsHalted = false;
		publish(lanePublish());
	}

	public void saveGame() throws IOException {
		gameIsHalted = true;
		LaneEvent le = lanePublish();
		le.savetoDB();
		ArrayList<LaneEvent> currentSavedGames = new ArrayList<>();
		try {
			FileInputStream fis = new FileInputStream("gameSaveData.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			currentSavedGames = (ArrayList<LaneEvent>) ois.readObject();
			System.out.println("Number of saved games: " + currentSavedGames.size());
		}
		 catch (FileNotFoundException | ClassNotFoundException e) {
			File file = new File("gameSaveData.ser");
		}
		catch (EOFException e){
			System.out.println("The Serial was empty and hence a new array is created.");
		}

		System.out.println(currentSavedGames.toString());
		currentSavedGames.add(le);
		System.out.println(currentSavedGames.toString());

		//Writing the updated array:
		try {
			FileOutputStream outFile = new FileOutputStream("gameSaveData.ser");
			ObjectOutputStream outObjectFile = new ObjectOutputStream(outFile);
			outObjectFile.writeObject(currentSavedGames);
			System.out.println("Succesfully Saved");
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			assert true;
		}
	}

}

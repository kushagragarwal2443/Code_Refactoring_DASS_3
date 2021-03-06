/*  $Id$
 *
 *  Revisions:
 *    $Log: LaneEvent.java,v $
 *    Revision 1.6  2003/02/16 22:59:34  ???
 *    added mechnanical problem flag
 *
 *    Revision 1.5  2003/02/02 23:55:31  ???
 *    Many many changes.
 *
 *    Revision 1.4  2003/02/02 22:44:26  ???
 *    More data.
 *
 *    Revision 1.3  2003/02/02 17:49:31  ???
 *    Modified.
 *
 *    Revision 1.2  2003/01/30 21:21:07  ???
 *    *** empty log message ***
 *
 *    Revision 1.1  2003/01/19 22:12:40  ???
 *    created laneevent and laneobserver
 *
 *
 */

import java.util.HashMap;

public class LaneEvent {

	private final Party p;
	int frame;
	final int ball;
	final Bowler bowler;
	final int[][] cumulScore;
	final HashMap score;
	final int index;
	final int frameNum;
	final int[] curScores;
	final boolean mechProb;
	
	public LaneEvent(Party pty, int[][] params, Bowler theBowler, int[][] theCumulScore, HashMap theScore) {
		
		p = pty;
		index = params[0][0];
		bowler = theBowler;
		cumulScore = theCumulScore;
		score = theScore;
		curScores = params[3];
		frameNum = params[1][0];
		ball = params[2][0];

		if(params[4][0] == 1) {
			mechProb = true;
		}

		else {
			mechProb = false;
		}
	}
	
	public boolean isMechanicalProblem() {
		return mechProb;
	}
	
	public int getFrameNum() {
		return frameNum;
	}
	
	public HashMap getScore( ) {
		return score;
	}


	public int[] getCurScores(){ 
		return curScores;
	}
	
	public int getIndex() {
		return index;
	}

	public int getFrame( ) {
		return frame;
	}

	public int getBall( ) {
		return ball;
	}
	
	public int[][] getCumulScore(){
		return cumulScore;
	}

	public Party getParty() {
		return p;
	}
	
	public Bowler getBowler() {
		return bowler;
	}

}
 

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

	private Party p;
	int frame;
	int ball;
	Bowler bowler;
	int[][] cumulScore;
	HashMap score;
	int index;
	int frameNum;
	int[] curScores;
	boolean mechProb;
	
	public LaneEvent( Party pty, int[][] theCumulScore, HashMap theScore, int[] theCurScores, int theBall) {
		p = pty;
		cumulScore = theCumulScore;
		score = theScore;
		curScores = theCurScores;
		ball = theBall;
	}

	public void setParty(Party pty){
		p = pty;
	}

	public void setScores(int[][] theCumulScore,int[] theCurScores) {
		cumulScore = theCumulScore;
		curScores = theCurScores;
	}

	public void setScore(HashMap theScore) {
		score = theScore;
	}

	public void setBall(int theBall) {
		ball = theBall;
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
	
	public void setIndex(int theIndex){
		index = theIndex;
	}
	
	public void setFrameNum(int theframeNum){
		frameNum = theframeNum;
	}

	public void setMechProb(boolean mechProblem) {
		mechProb = mechProblem;
	}

	public void setBowler(Bowler theBowler) {
		bowler = theBowler;
	}
	
}
 

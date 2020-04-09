/*
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

public class SavedGameDataFile {

	private SavedGameDataFile() {
		throw new IllegalStateException("Utility class");
	}

	private static final String savedDataFile = "gameSaveData.ser";


	public static Vector getPartyNames()
		throws IOException {
		Vector names = new Vector();

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

		for(int i=0;i<currentSavedGames.size();i++){
			Bowler bowl = (Bowler) currentSavedGames.get(i).getParty().getMembers().firstElement();
			names.add(bowl.getNickName());
		}

		return names;

	}

}

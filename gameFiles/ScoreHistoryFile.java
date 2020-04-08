/*
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */

import java.util.*;
import java.io.*;

public class ScoreHistoryFile {

	private ScoreHistoryFile() {
		throw new IllegalStateException("Utility class");
	}

	private static final String SCOREHISTORY_DAT = "SCOREHISTORY.DAT";

	public static void addScore(String nick, String date, String score)
		throws IOException {

		String data = nick + "\t" + date + "\t" + score + "\n";

		try (RandomAccessFile out = new RandomAccessFile(SCOREHISTORY_DAT, "rw")) {
			out.skipBytes((int) out.length());
			out.writeBytes(data);
			out.close();
		} catch (Exception e) {
			System.out.println("Error Reading Score History File");
		}
	}

	public static Vector getScores(String nick)
		throws IOException {
		Vector scores = new Vector();

		try (BufferedReader in = new BufferedReader(new FileReader(SCOREHISTORY_DAT))) {
			String data;
			while ((data = in.readLine()) != null) {
				// File format is nick\tfname\te-mail
				String[] scoredata = data.split("\t");
				//"Nick: scoredata[0] Date: scoredata[1] Score: scoredata[2]
				if (nick.equals(scoredata[0])) {
					scores.add(new Score(scoredata[0], scoredata[1], scoredata[2]));
				}
			}
			return scores;
		} catch (Exception e) {
			System.out.println("Error reading scorehistory file");
			return new Vector();
		}

	}


	public static Vector getallScores()
			throws IOException {
		Vector scores = new Vector();

		try (BufferedReader in = new BufferedReader(new FileReader(SCOREHISTORY_DAT))) {
			String data;
			while ((data = in.readLine()) != null) {
				// File format is nick\tfname\te-mail
				String[] scoredata = data.split("\t");
				//"Nick: scoredata[0] Date: scoredata[1] Score: scoredata[2]

				scores.add(new Score(scoredata[0], scoredata[1], scoredata[2]));

			}
			return scores;
		} catch (Exception e) {
			System.out.println("Error reading scorehistory file");
			return new Vector();
		}

	}

}

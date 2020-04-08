import javax.swing.*;
import java.util.*;
import java.io.*;


public class GameHistoryFile {

    private GameHistoryFile() {
        throw new IllegalStateException("Utility class");
    }

    private static final String GAMEHISTORY_DAT = "GAMEHISTORY.DAT";

    public static void addGame(Party party, int frame, int ball, Bowler bowler, int[][] cumulScore, HashMap score, int index, int frameNum, int[] curScores, boolean mechProblem)
            throws IOException {

        Vector Members = party.getMembers();
        Vector<String> membersNick = new Vector<String>();

        for(int i = 0; i < Members.size(); i++) {
            Bowler member = (Bowler) Members.get(i);
            membersNick.add((String)member.getNickName());
        }

        String currentBowler = bowler.getNickName();


        try (RandomAccessFile out = new RandomAccessFile(GAMEHISTORY_DAT, "rw")) {
            out.skipBytes((int) out.length());
            //out.writeBytes(data);
        } catch (Exception e) {
            System.out.println("Error Reading Game History File");
        }
    }
}

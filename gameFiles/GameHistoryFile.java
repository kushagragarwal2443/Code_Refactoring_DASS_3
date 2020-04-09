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

        String data = "\n- - - - - - - - - - - - - - - - - - - - - - - - - -- - - - - - - - - - - - - - - - - -\nMembers :\n";

        Vector Members = party.getMembers();

        for(int i = 0; i < Members.size(); i++) {
            Bowler member = (Bowler)Members.get(i);
            data += member.getNickName() + '\t';
            data += member.getFullName() + '\t';
            data += member.getEmail() + '\t';
            data += "\n";
        }

        data += "Frame : " + Integer.toString(frame) + "\n";
        data += "Ball : "+Integer.toString(ball);
        data += "\nCurrent Bowler : "+ bowler.getNickName() + "\t" + bowler.getFullName() + "\t" + bowler.getEmail();

        data += "\nCumul Score : \n";

        for(int i = 0; i < Members.size(); i++){
            for(int j = 0;j < frameNum;j++){
                data += Integer.toString(cumulScore[i][j]) +"\t";
            }
            data += '\n';
        }

        data += "Score :\n";

        for(int i = 0;i < Members.size(); i++) {
            int[] scores = (int[])score.get(Members.get(i));
            for(int j = 0; j < 21;j++){
                data += Integer.toString(scores[j]) + '\t';
            }
            data += "\n";
        }

        data += "Index : "+Integer.toString(index);
        data += "\nFrame Number : "+Integer.toString(frameNum);
        data += "\nCurrent Scores : ";

        for (int i = 0;i < curScores.length;i++){
            data += Integer.toString(curScores[i])+ "\t";
        }

        data += "\nProblem : " + mechProblem;
        data += "\n- - - - - - - - - - - - - - - - - - - - - - - - - -- - - - - - - - - - - - - - - - - -\n";

        try (RandomAccessFile out = new RandomAccessFile(GAMEHISTORY_DAT, "rw")) {
            out.skipBytes((int) out.length());
            out.writeBytes(data);
        } catch (Exception e) {
            System.out.println("Error Reading Game History File");
        }
    }
}

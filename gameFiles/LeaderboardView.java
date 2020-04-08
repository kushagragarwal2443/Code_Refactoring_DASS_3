
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import java.util.*;

public class LeaderboardView implements ActionListener {

    private final JFrame win;
    private final JButton sortHL;
    private final JButton topPlayer;
    private final JButton sortLH;
    private final JButton close;
    //private final JList partyList;
    private final JList allScores;
    //private final Vector party;
    private Vector scores_db;


    public LeaderboardView() {

        win = new JFrame("Leaderboard");
        win.getContentPane().setLayout(new BorderLayout());
        ((JPanel) win.getContentPane()).setOpaque(false);

        JPanel colPanel = new JPanel();
        colPanel.setLayout(new GridLayout(1, 2));

        // Result Database
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new FlowLayout());
        resultPanel.setBorder(new TitledBorder("Result"));

        try {
            scores_db = new Vector(ScoreHistoryFile.getallScores());
        } catch (Exception e) {
            System.err.println("File Error");
            scores_db = new Vector();
        }

        allScores = new JList(vectorFormat(scores_db));
        allScores.setVisibleRowCount(10);
        allScores.setFixedCellWidth(240);
        JScrollPane LeaderboardPane = new JScrollPane(allScores);
        LeaderboardPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //allScores.addListSelectionListener(this);
        resultPanel.add(LeaderboardPane);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1));

        Insets buttonMargin = new Insets(4, 4, 4, 4);

        sortHL = new JButton("Sort: High to Low");
        JPanel sortButtonHL = new JPanel();
        sortButtonHL.setLayout(new FlowLayout());
        sortHL.addActionListener(this);
        sortButtonHL.add(sortHL);

        sortLH = new JButton("Sort: Low to High");
        JPanel sortButtonLH = new JPanel();
        sortButtonLH.setLayout(new FlowLayout());
        sortLH.addActionListener(this);
        sortButtonLH.add(sortLH);

        topPlayer = new JButton("Top Player");
        JPanel topPlayerPanel = new JPanel();
        topPlayerPanel.setLayout(new FlowLayout());
        topPlayer.addActionListener(this);
        topPlayerPanel.add(topPlayer);

        close = new JButton("Close");
        JPanel finishedPanel = new JPanel();
        finishedPanel.setLayout(new FlowLayout());
        close.addActionListener(this);
        finishedPanel.add(close);

        buttonPanel.add(sortButtonHL);
        buttonPanel.add(sortButtonLH);
        buttonPanel.add(topPlayerPanel);
        buttonPanel.add(finishedPanel);

        // Clean up main panel
        //colPanel.add(resultsPanel);
        colPanel.add(resultPanel);
        colPanel.add(buttonPanel);

        win.getContentPane().add("Center", colPanel);

        win.pack();

        // Center Window on Screen
        Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();
        win.setLocation(
                ((screenSize.width) / 2) - ((win.getSize().width) / 2),
                ((screenSize.height) / 2) - ((win.getSize().height) / 2));
        win.show();

    }

    public static Vector vectorFormat(Vector scores) {

        Vector<String> temp_score_db = new Vector<String>();
        Iterator value = scores.iterator();
        while(value.hasNext()) {
            Score temp = (Score) value.next();
            temp_score_db.add(temp.resultString());
        }

        return temp_score_db;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(sortHL)) {
            sortOnScore("high");
            allScores.setListData(vectorFormat(scores_db));
        }
        if (e.getSource().equals(sortLH)) {
            sortOnScore("low");
            allScores.setListData(vectorFormat(scores_db));
        }
        if (e.getSource().equals(topPlayer)) {
            sortOnScore("high");
            Score maxScore = (Score)scores_db.get(0);
            Vector topPlayers = new Vector<Score>();
            int maxvalue = Integer.parseInt(maxScore.getScore());
            for(int index = 0;index < scores_db.size();index ++){

                Score players = (Score)scores_db.get(index);

                if(Integer.parseInt(players.getScore()) == maxvalue){
                    topPlayers.add(scores_db.get(index));
                }

                else {
                    break;
                }

            }

            TopPlayerView topPlayerView = new TopPlayerView(topPlayers);
        }
        if(e.getSource().equals(close)) {
            win.hide();
        }
    }

    private void sortOnScore(String type) {

        if(type == "low")
            Collections.sort(scores_db);

        if(type == "high") {
            Comparator comparator = Comparator.reverseOrder();
            Collections.sort(scores_db, comparator);
        }
    }
}
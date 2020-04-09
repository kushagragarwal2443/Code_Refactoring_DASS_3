import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Vector;

public class LoadSavedGameView implements ActionListener {

    private final JFrame win;
    private final JButton loadThisGame;
    private final JButton close;
    //private final JList partyList;
    private final JList allScores;
    //private final Vector party;
    private Vector scores_db;


    public LoadSavedGameView() {

        win = new JFrame("Load Saved Game");
        win.getContentPane().setLayout(new BorderLayout());
        ((JPanel) win.getContentPane()).setOpaque(false);

        JPanel colPanel = new JPanel();
        colPanel.setLayout(new GridLayout(1, 2));

        // Result Database
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new FlowLayout());
        resultPanel.setBorder(new TitledBorder("Saved Game List"));

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


        loadThisGame = new JButton("Load Selected Game");
        JPanel loadThisGamePanel = new JPanel();
        loadThisGamePanel.setLayout(new FlowLayout());
        loadThisGame.addActionListener(this);
        loadThisGamePanel.add(loadThisGame);

        close = new JButton("Close");
        JPanel finishedPanel = new JPanel();
        finishedPanel.setLayout(new FlowLayout());
        close.addActionListener(this);
        finishedPanel.add(close);


        buttonPanel.add(loadThisGamePanel);
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

        if (e.getSource().equals(loadThisGame)) {

            assert true;
        }
        if(e.getSource().equals(close)) {
            win.hide();
        }
    }

}
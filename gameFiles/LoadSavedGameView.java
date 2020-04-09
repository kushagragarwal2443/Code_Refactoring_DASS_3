import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

public class LoadSavedGameView implements ActionListener {

    private final JFrame win;
    private final JButton loadThisGame;
    private final JButton close;
    //private final JList partyList;
    private final JList savedGameList;
    //private final Vector party;
    private Vector partyNames_db;
    private HashSet lanes;


    public LoadSavedGameView(HashSet laneInput) {

        win = new JFrame("Load Saved Game");
        win.getContentPane().setLayout(new BorderLayout());
        ((JPanel) win.getContentPane()).setOpaque(false);
        lanes = laneInput;
        JPanel colPanel = new JPanel();
        colPanel.setLayout(new GridLayout(1, 2));

        // Result Database
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new FlowLayout());
        resultPanel.setBorder(new TitledBorder("Saved Game List"));

        try {
            partyNames_db = new Vector(SavedGameDataFile.getPartyNames());
        } catch (Exception e) {
            System.err.println("File Error");
            partyNames_db = new Vector();
        }

        savedGameList = new JList(partyNames_db);
        savedGameList.setVisibleRowCount(10);
        savedGameList.setFixedCellWidth(240);
        JScrollPane LeaderboardPane = new JScrollPane(savedGameList);
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

    public void assignSavedLane(LaneEvent arg) {
        Iterator it = lanes.iterator();

        if (it.hasNext()) {
            Lane curLane = (Lane) it.next();

            if (! curLane.isPartyAssigned()) {
                System.out.println("ok... assigning this party");
                curLane.assignSavedParty(arg);
            }
        }
        //publish(new ControlDeskEvent(getPartyQueue()));
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(loadThisGame)) {

            String s = (String) savedGameList.getSelectedValue();
            System.out.println("The Selected Value is :" + s);
            LaneEvent temp = null;
            if(!s.isEmpty()){
                try {
                    temp = SavedGameDataFile.returnLaneEvent(s);
                } catch (IOException ioException) {
                    temp = null;
                }
            }
            if(!temp.equals(null)){
                assignSavedLane(temp);
            }
            win.hide();
        }
        if(e.getSource().equals(close)) {
            win.hide();
        }
    }

}
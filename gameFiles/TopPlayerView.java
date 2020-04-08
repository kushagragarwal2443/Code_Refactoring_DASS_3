import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import java.util.*;


public class TopPlayerView implements ActionListener{

    private final JFrame win;
    private final JButton close;
    private Vector<Score> topPlayers;
    private final JList allScores;

    public TopPlayerView(Vector players) {
        topPlayers = players;

        win = new JFrame("Top Player");
        win.getContentPane().setLayout(new BorderLayout());
        ((JPanel) win.getContentPane()).setOpaque(false);

        JPanel colPanel = new JPanel();
        colPanel.setLayout(new GridLayout(1, 2));

        // Result Database
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new FlowLayout());
        resultPanel.setBorder(new TitledBorder("Player and Score"));

        allScores = new JList(LeaderboardView.vectorFormat(players));
        allScores.setVisibleRowCount(5);
        allScores.setFixedCellWidth(200);
        JScrollPane LeaderboardPane = new JScrollPane(allScores);
        LeaderboardPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        resultPanel.add(LeaderboardPane);


        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 1));

        Insets buttonMargin = new Insets(4, 4, 4, 4);


        close = new JButton("Close");
        JPanel finishedPanel = new JPanel();
        finishedPanel.setLayout(new FlowLayout());
        close.addActionListener(this);
        finishedPanel.add(close);


        buttonPanel.add(finishedPanel);

        // Clean up main panel
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

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(close)) {
            win.hide();
        }
    }

}

/**
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LaneStatusView implements ActionListener, LaneObserver, PinsetterObserver {

	private final JPanel jp;

	private final JLabel curBowler;
    private final JLabel pinsDown;
	private final JButton viewLane;
	private final JButton viewPinSetter;
	private final JButton maintenance;

	private final PinSetterView psv;
	private final LaneView lv;
	private final Lane lane;
	final int laneNum;

	boolean laneShowing;
	boolean psShowing;

	public LaneStatusView(Lane lane, int laneNum ) {

		this.lane = lane;
		this.laneNum = laneNum;

		laneShowing=false;
		psShowing=false;

		psv = new PinSetterView( laneNum );
		Pinsetter ps = lane.getPinsetter();
		ps.subscribe(psv);

		lv = new LaneView( lane, laneNum );
		lane.subscribe(lv);


		jp = new JPanel();
		jp.setLayout(new FlowLayout());
		JLabel cLabel = new JLabel( "Now Bowling: " );
		curBowler = new JLabel( "(no one)" );
		JLabel fLabel = new JLabel( "Foul: " );
        JLabel foul = new JLabel(" ");
		JLabel pdLabel = new JLabel( "Pins Down: " );
		pinsDown = new JLabel( "0" );

		// Button Panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());

		Insets buttonMargin = new Insets(4, 4, 4, 4);

		viewLane = new JButton("View Lane");
		JPanel viewLanePanel = new JPanel();
		viewLanePanel.setLayout(new FlowLayout());
		viewLane.addActionListener(this);
		viewLanePanel.add(viewLane);

		viewPinSetter = new JButton("Pinsetter");
		JPanel viewPinSetterPanel = new JPanel();
		viewPinSetterPanel.setLayout(new FlowLayout());
		viewPinSetter.addActionListener(this);
		viewPinSetterPanel.add(viewPinSetter);

		maintenance = new JButton("     ");
		maintenance.setBackground( Color.GREEN );
		JPanel maintenancePanel = new JPanel();
		maintenancePanel.setLayout(new FlowLayout());
		maintenance.addActionListener(this);
		maintenancePanel.add(maintenance);

		viewLane.setEnabled( false );
		viewPinSetter.setEnabled( false );


		buttonPanel.add(viewLanePanel);
		buttonPanel.add(viewPinSetterPanel);
		buttonPanel.add(maintenancePanel);

		jp.add( cLabel );
		jp.add( curBowler );
//		jp.add( fLabel );
//		jp.add( foul );
		jp.add( pdLabel );
		jp.add( pinsDown );
		
		jp.add(buttonPanel);

	}

	public JPanel showLane() {
		return jp;
	}
	
	public void actionPinSetterView() {
		
		if (!psShowing) {
			psv.show();
			psShowing=true;
		} else if (psShowing) {
			psv.hide();
			psShowing=false;
		}
		
	}
	
	public void actionViewLane() {
		
		if (!laneShowing) {
			lv.show();
			laneShowing=true;
		} else if (laneShowing) {
			lv.hide();
			laneShowing=false;
		}
		
	}
  // Moved PinSetter View and LaneView to seperate methods
	public void actionPerformed( ActionEvent e ) {
		if ( lane.isPartyAssigned() ) { 
			if (e.getSource().equals(viewPinSetter)) {
				actionPinSetterView();
			}
		}
		if (e.getSource().equals(viewLane)) {
			if ( lane.isPartyAssigned() ) { 
				actionViewLane();
			}
		}
		if (e.getSource().equals(maintenance)) {
			if ( lane.isPartyAssigned() ) {
				lane.unPauseGame();
				maintenance.setBackground( Color.GREEN );
			}
		}
	}

	public void receiveLaneEvent(LaneEvent le) {
		curBowler.setText( ( (Bowler)le.getBowler()).getNickName() );
		if ( le.isMechanicalProblem() ) {
			maintenance.setBackground( Color.RED );
		}	
		if (!lane.isPartyAssigned()) {
			viewLane.setEnabled( false );
			viewPinSetter.setEnabled( false );
		} else {
			viewLane.setEnabled( true );
			viewPinSetter.setEnabled( true );
		}
	}

	public void receivePinsetterEvent(PinsetterEvent pe) {
		pinsDown.setText( (Integer.valueOf(pe.totalPinsDown())).toString() );
//		foul.setText( ( new Boolean(pe.isFoulCommited()) ).toString() );
		
	}

}

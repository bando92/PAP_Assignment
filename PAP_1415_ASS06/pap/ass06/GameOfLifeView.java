package pap.ass06;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class GameOfLifeView extends JFrame implements ActionListener {


	private JButton startButton;
	private JButton stopButton;
	private JTextField state;
	private GameOfLifePanel setPanel;
	private ArrayList<InputListener> listeners;

	public GameOfLifeView(int w, int h)	{
		super("Game Of Life Viewer");
		setSize(w,h);
		listeners = new ArrayList<InputListener>();
		
		startButton = new JButton("start");
		stopButton = new JButton("stop");
		JPanel controlPanel = new JPanel();
		controlPanel.add(startButton);
		controlPanel.add(stopButton);

		setPanel = new GameOfLifePanel(w,h); 
		setPanel.setSize(w,h);

		JPanel infoPanel = new JPanel();
		state = new JTextField(20);
		state.setText("Press Start");
		state.setEditable(false);
		infoPanel.add(new JLabel("State"));
		infoPanel.add(state);
		JPanel cp = new JPanel();
		LayoutManager layout = new BorderLayout();
		cp.setLayout(layout);
		cp.add(BorderLayout.NORTH,controlPanel);
		cp.add(BorderLayout.CENTER,setPanel);
		cp.add(BorderLayout.SOUTH, infoPanel);
		setContentPane(cp);		
		
		startButton.addActionListener(this);
		stopButton.addActionListener(this);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void setUpdated(final GameOfLifeSet set){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				setPanel.updateImage(set.getImage());
			}
		});
	}
	
	public void changeState(final String s){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				state.setText(s);
			}
		});
	}

	public void addListener(InputListener l){
		listeners.add(l);
	}
	

	public void actionPerformed(ActionEvent ev){
		String cmd = ev.getActionCommand(); 
		if (cmd.equals("start")){
			notifyStarted();
		} else if (cmd.equals("stop")){
			notifyStopped();
		}
	}

	private void notifyStarted(){
		for (InputListener l: listeners){
			l.started();
		}
	}
	
	private void notifyStopped(){
		for (InputListener l: listeners){
			l.stopped();
		}
	}


}

package temperature_monitoring;


import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class TempMonitoringView extends JFrame implements ActionListener {

	private JButton startButton;
	private JButton stopButton;
	private JTextField alarm;
	private JTextField AverageUserTemp;
	private JTextField ms;
	private JTextField AverageTempStream;
	private JTextField MaxTemp;
	private JTextField MinTemp;
	private ArrayList<InputListener> listeners;
	private boolean refresh;

	public TempMonitoringView(){
		super("Temperature Monitoring");
		this.refresh = false;
		this.setSize(1000, 150);
		listeners = new ArrayList<InputListener>();
		
		startButton = new JButton("start");
		stopButton = new JButton("stop");
		JPanel controlPanel = new JPanel();
		controlPanel.add(startButton);
		controlPanel.add(stopButton);

		
		JPanel alarmPanel = new JPanel();
		AverageUserTemp = new JTextField(20);
		AverageUserTemp.setText("10");
		AverageUserTemp.setEditable(true);
		alarmPanel.add(new JLabel("AverageUserTemp"));
		alarmPanel.add(AverageUserTemp);
		
		ms = new JTextField(20);
		ms.setText("1000");
		ms.setEditable(true);
		alarmPanel.add(new JLabel("ms"));
		alarmPanel.add(ms);
		
		alarm = new JTextField(20);
		alarm.setText("");
		alarm.setEditable(false);
		alarmPanel.add(new JLabel("Alarm"));
		alarmPanel.add(alarm);
		
		JPanel centerPanel = new JPanel();
		
		AverageTempStream = new JTextField(20);
		AverageTempStream.setText("");
		AverageTempStream.setEditable(false);
		centerPanel.add(new JLabel("AverageTempStream"));
		centerPanel.add(AverageTempStream);
		
		MaxTemp = new JTextField(20);
		MaxTemp.setText("");
		MaxTemp.setEditable(false);
		centerPanel.add(new JLabel("MaxTemp"));
		centerPanel.add(MaxTemp);
		
		MinTemp = new JTextField(20);
		MinTemp.setText("");
		MinTemp.setEditable(false);
		centerPanel.add(new JLabel("MinTemp"));
		centerPanel.add(MinTemp);
		
		
		JPanel cp = new JPanel();
		LayoutManager layout = new BorderLayout();
		cp.setLayout(layout);
		cp.add(BorderLayout.NORTH,controlPanel);
		cp.add(BorderLayout.CENTER,centerPanel);
		cp.add(BorderLayout.SOUTH, alarmPanel);
		
		setContentPane(cp);	
		
		startButton.addActionListener(this);
		stopButton.addActionListener(this);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void changeAlarm(final String s){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				alarm.setText(s);
			}
		});
	}
	
	public void changeAverageTempStream(final String s){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				if(refresh)
					AverageTempStream.setText(s);
			}
		});
	}
	
	public void changeMaxTemp(final String s){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				if(refresh)
					MaxTemp.setText(s);
			}
		});
	}
	
	public void changeMinTemp(final String s){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				if(refresh)
					MinTemp.setText(s);
			}
		});
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
	
	public void addListener(InputListener l){
		listeners.add(l);
	}

	public void setRefresh(boolean refresh) {
		this.refresh = refresh;
	}

	public int getSoglia(){
		return Integer.parseInt(AverageUserTemp.getText());
	}
	
	public int getMs(){
		return Integer.parseInt(ms.getText());
	}
}
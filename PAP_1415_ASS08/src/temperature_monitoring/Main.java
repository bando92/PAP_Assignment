package temperature_monitoring;

public class Main {

	public static void main(String[] args) {
		
		TempMonitoringView view = new TempMonitoringView();
		Controller controller = new Controller(view);
		view.addListener(controller);
		view.setVisible(true);
	}

}

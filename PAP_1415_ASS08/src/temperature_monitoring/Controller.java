package temperature_monitoring;

import rx.Observable;
import rx.Subscriber;

public class Controller implements InputListener {

	private TempMonitoringView view;
	private AverageTempStream sensor;
	private boolean flag, alarm;
	private double max, min;
	private long t0;
	
	public Controller(TempMonitoringView view){
		this.view = view;
		this.flag = false;
		this.alarm = false;
		this.max = Double.MIN_VALUE;
		this.min = Double.MAX_VALUE;
	}
	
	public void started(){
		view.setRefresh(true);
		if(!flag){
			Observable<Double> average = Observable.create((Subscriber<? super Double> subscriber) -> {
				sensor = new AverageTempStream(20, subscriber);
				sensor.start();
			});
			flag = true;
			average.subscribe(//onNext
					(Double d) -> {
						view.changeAverageTempStream(""+d);
						if(d > view.getSoglia()){
							if(alarm){
								if(System.currentTimeMillis() - t0 > view.getMs())
									view.changeAlarm("Allarme!!!!!!!");
							}else{
								t0 = System.currentTimeMillis();
								alarm = true;
							}
						}
						else{
							alarm = false;
							t0 = 0;
						}
						
						
						if(d < min){
							min = d;
							view.changeMinTemp(""+min);
						}
						if(d > max){
							max = d;
							view.changeMaxTemp(""+max);
						}
					},
					
					
					//onError
					(Throwable t) -> {System.out.println("Errore: "+t);},
					//onCompleted
					() -> {
						System.out.println("Done.");
					});
			
		}
		else{
			
		}
	}

	public void stopped() {
		view.setRefresh(false);
	}

}

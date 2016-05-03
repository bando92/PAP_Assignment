package temperature_monitoring;

import rx.Observable;
import rx.Subscriber;

public class AverageTempStream extends Thread {

	private int maxVariation;
	private Subscriber<? super Double> subscriber;

	public AverageTempStream(int maxVariation, Subscriber<? super Double> subscriber) {
		this.maxVariation = maxVariation;
		this.subscriber = subscriber;
	}

	public void run(){
		Observable<Double> s1 = Observable.create((Subscriber<? super Double> subscriber) -> {
			new TempStream(0, 40, 200, subscriber, maxVariation).start();
		});
		Observable<Double> s2 = Observable.create((Subscriber<? super Double> subscriber) -> {
			new TempStream(0, 40, 250, subscriber, maxVariation).start();
		});
		Observable<Double> s3 = Observable.create((Subscriber<? super Double> subscriber) -> {
			new TempStream(0, 40, 300, subscriber, maxVariation).start();
		});
		
		Observable<Double> average = Observable.zip(s1, s2, s3, (a, b, c) -> (a + b + c) / 3);
		
		average.subscribe(
				//onNext
				(Double d) -> {
					subscriber.onNext(d);
				},
				
				
				//onError
				(Throwable t) -> {System.out.println("Errore: "+t);},
				//onCompleted
				() -> {
					System.out.println("Done.");
				});
	}
}

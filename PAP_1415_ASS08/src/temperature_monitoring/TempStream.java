package temperature_monitoring;

import java.util.Random;

import rx.Subscriber;
import rx.Observable;

public class TempStream extends Thread {

	private int freq;
	private TempSensor sensor;
	private Subscriber<? super Double> subscriber;
	private int maxVariation;
	private int min, max;

	public TempStream(int min, int max, int freq,
			Subscriber<? super Double> subscriber, int maxVariation) {
		this.freq = freq;
		this.sensor = new TempSensor(min, max, 0.05);
		this.subscriber = subscriber;
		this.maxVariation = maxVariation;
		this.min = min;
		this.max = max;
	}

	public void run() {
		while (true) {
			try {
				boolean flag = false;
				double val = sensor.getCurrentValue();
				double val_prec = 0;

				if (!flag && val > min && val < max) {
					subscriber.onNext(val);
					val_prec = val;
					flag = true;
				} else {
					if (val < val_prec + maxVariation) {
						subscriber.onNext(val);
						val_prec = val;
					}
				}
				Thread.sleep(freq);
			} catch (Exception ex) {
				subscriber.onError(ex);
			}
		}
	}

}

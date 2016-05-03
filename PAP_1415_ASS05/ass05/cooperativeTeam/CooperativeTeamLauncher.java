package pap.ass05.cooperativeTeam;

import java.util.concurrent.Semaphore;

public class CooperativeTeamLauncher {

	public static void main(String[] args) throws InterruptedException {
			Semaphore sem1 = new Semaphore(0);
			Semaphore sem2 = new Semaphore(0);
			Semaphore sem3 = new Semaphore(0);
			Semaphore semW1 = new Semaphore(1);
			Semaphore semW2 = new Semaphore(1);
			
			UnsafeCounter c1 = new UnsafeCounter(0);
			UnsafeCounter c2 = new UnsafeCounter(0);
			UnsafeCounter c3 = new UnsafeCounter(0);
			Worker1 w1 = new Worker1(c1,sem1,semW1);
			Worker2 w2 = new Worker2(c2,sem2,semW2);
			Worker3 w3 = new Worker3(c1,c3,sem1,sem3);
			Worker4 w4 = new Worker4(c2,c3,sem2,sem3);
			Worker5 w5 = new Worker5(c3,sem3,semW1,semW2);
			w1.start();
			w2.start();
			w3.start();
			w4.start();
			w5.start();
			w1.join();
			w2.join();
			w3.join();
			w4.join();
			w5.join();
		}

	}


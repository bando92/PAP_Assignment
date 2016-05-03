package pap.ass05.cooperativeTeam;

import java.util.concurrent.Semaphore;

public class Worker1 extends Thread{
	
	private UnsafeCounter c1;
	private Semaphore sem1;
	private Semaphore semW1;
	
	public Worker1(UnsafeCounter c1, Semaphore sem1, Semaphore semW1){
		this.c1 = c1;
		this.sem1 = sem1;
		this.semW1 = semW1;
	}

	public void run(){
		while(true)
		{
			try {
				semW1.acquire();
				c1.inc();
				sem1.release();
				//System.out.println("Worker1 says: Work Done!");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

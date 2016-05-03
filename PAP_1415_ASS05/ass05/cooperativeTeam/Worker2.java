package pap.ass05.cooperativeTeam;

import java.util.concurrent.Semaphore;

public class Worker2 extends Thread{

	private UnsafeCounter c2;
	private Semaphore sem2;
	private Semaphore semW2;
	
	public Worker2(UnsafeCounter c2, Semaphore sem2, Semaphore semW2){
		this.c2 = c2;
		this.sem2 = sem2;
		this.semW2 = semW2;
	}
	
	public void run(){
		while(true)
		{
			try {
				semW2.acquire();
				c2.inc();
				sem2.release();
				//System.out.println("Worker2 says: Work Done!");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

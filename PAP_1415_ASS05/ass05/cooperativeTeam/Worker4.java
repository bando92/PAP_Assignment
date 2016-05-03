package pap.ass05.cooperativeTeam;

import java.util.concurrent.Semaphore;

public class Worker4 extends Thread{
	
	private UnsafeCounter c2;
	private UnsafeCounter c3;
	private Semaphore sem2;
	private Semaphore sem3;
		
		public Worker4(UnsafeCounter c2,UnsafeCounter c3,Semaphore sem2,Semaphore sem3){
			this.c2 = c2;
			this.c3 = c3;
			this.sem2 = sem2;
			this.sem3 = sem3;
		}
	
	public void run(){
		while(true)
		{
			try {
				sem2.acquire();
				System.out.println("c2 value: " + c2.getValue());
				c3.inc();
				sem3.release();
				//System.out.println("Worker4 says: Work Done!");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

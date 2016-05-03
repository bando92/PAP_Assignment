package pap.ass05.cooperativeTeam;

import java.util.concurrent.Semaphore;

public class Worker3 extends Thread{

	private UnsafeCounter c1;
	private UnsafeCounter c3;
	private Semaphore sem1;
	private Semaphore sem3;
	
	public Worker3(UnsafeCounter c1,UnsafeCounter c3,Semaphore sem1,Semaphore sem3){
		this.c1 = c1;
		this.c3 = c3;
		this.sem1 = sem1;
		this.sem3 = sem3;
	}
	
	public void run(){
		while(true)
		{
			try {
				sem1.acquire();
				System.out.println("c1 value: " + c1.getValue());
				c3.inc();
				sem3.release();
				//System.out.println("Worker3 says: Work Done!");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

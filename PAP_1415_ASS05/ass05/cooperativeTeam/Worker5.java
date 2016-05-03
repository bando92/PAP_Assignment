package pap.ass05.cooperativeTeam;

import java.util.concurrent.Semaphore;

public class Worker5 extends Thread{

	private UnsafeCounter c3;
	private Semaphore sem3;
	private Semaphore semW1;
	private Semaphore semW2;
	
	public Worker5(UnsafeCounter c3,Semaphore sem3,Semaphore semW1,Semaphore semW2){
		this.c3 = c3;
		this.sem3 = sem3;
		this.semW1 = semW1;
		this.semW2 = semW2;
	}
	
	public void run(){
		while(true)
		{
			try {
				sem3.acquire(2);
				System.out.println("c3 value: " + c3.getValue());
				sleep(1000);
				semW1.release();
				semW2.release();
				//System.out.println("Worker5 says: Work Done!");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

package pap.ass06;

public class Counter {
	private int cont;

	public Counter(int inizialize){
		this.cont = inizialize;
	}
	
	public synchronized void inc(){
		cont++;
	}
	
	public synchronized void dec(){
		cont--;
	}
	
	public synchronized int getValue(){
		return cont;
	}
}

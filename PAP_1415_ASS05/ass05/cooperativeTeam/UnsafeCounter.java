package pap.ass05.cooperativeTeam;

public class UnsafeCounter {
	private int cont;

	public UnsafeCounter(int inizialize){
		this.cont = inizialize;
	}
	
	public void inc(){
		cont++;
	}
	
	public int getValue(){
		return cont;
	}
}

package ass08.gameoflife;

public class WorkerMsg {

	private int x;
	private int y;
	private Grid aliveGrid;
	
	public WorkerMsg(int x, int y, Grid alive) {
		this.x = x;
		this.y = y;
		this.aliveGrid = alive;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Grid getAliveGrid() {
		return aliveGrid;
	}
	

}

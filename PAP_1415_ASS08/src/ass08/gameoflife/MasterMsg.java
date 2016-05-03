package ass08.gameoflife;

public class MasterMsg {
	
	private int x;
	private int y;
	private Grid oldGrid;
	private GameOfLifeSet set;
	
	public MasterMsg(int x, int y, Grid oldGrid, GameOfLifeSet set) {
		this.x = x;
		this.y = y;
		this.oldGrid = oldGrid;
		this.set = set;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public Grid getOldGrid() {
		return oldGrid;
	}
	public GameOfLifeSet getSet() {
		return set;
	}

}

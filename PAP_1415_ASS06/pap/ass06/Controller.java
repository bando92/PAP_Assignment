package pap.ass06;

public class Controller implements InputListener {

	private GameOfLifeView view;
	private GameOfLifeSet set;
	private Flag stopFlag;
	private Grid grid;
	
	public Controller(GameOfLifeSet set, GameOfLifeView view, Grid grid){
		this.set = set;
		this.view = view;
		this.grid = grid;
	}
	
	public void started(){		
		stopFlag = new Flag();	

		int poolSize= Runtime.getRuntime().availableProcessors()+1;
		new GameOfLifeService(set,view,stopFlag,poolSize,grid).start();
	}

	public void stopped() {
		stopFlag.set();
	}

}
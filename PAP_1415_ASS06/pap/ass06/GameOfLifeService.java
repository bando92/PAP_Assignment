package pap.ass06;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class GameOfLifeService extends Thread {
	
	private int poolSize;
	private ExecutorService executor;
	private int w;
	private int h;
	private Flag stopFlag;
	private GameOfLifeView view;
	private GameOfLifeSet set;
	private Grid oldGrid;
	private Grid newGrid;
	private Counter counter;
	
	public GameOfLifeService (GameOfLifeSet set, GameOfLifeView view, Flag stopFlag,
								int poolSize, Grid grid)	{
		this.w = set.getSizeX();
		this.h = set.getSizeY();
		this.stopFlag = stopFlag;
		this.set = set;
		this.view = view;
		this.oldGrid = grid;
		this.poolSize = poolSize;
		this.counter = new Counter(0);
	}

	public void run(){
		try {
			
			for (int x = 0; x < w; x++){
				for (int y=0; y < h; y++)	{
					set.computePoint(x, y, oldGrid.getSingleCell(x, y));
					if(oldGrid.getSingleCell(x, y))
						counter.inc();
				}
	    	}
			newGrid = new Grid(w,h);
			view.setUpdated(set);
			view.changeState("Start with " + counter.getValue() + " dots");
			sleep(500);
			do {
				executor = Executors.newFixedThreadPool(poolSize);
		
				for (int x = 0; x < w; x++){
					for (int y=0; y < h; y++)	{
						executor.execute(new ComputeMatrixTask(x, y, oldGrid, newGrid, set, counter));
					}
		    	}
		    	executor.shutdown();
		    	executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
		    	view.setUpdated(set);
		    	
		    	if (!stopFlag.isSet()){
		    		view.changeState("Number of alive dots: " + counter.getValue());
		    	} else {
		    		view.changeState("interrupted");
		    	}
		    	sleep(100);
		    	
		    	oldGrid.setGrid(newGrid.getGrid());
		    	newGrid = new Grid(w,h);
			} while (!stopFlag.isSet());
		} catch(Exception ex){
			ex.printStackTrace();
			log(ex.getMessage());
		}
	}
	private void log(String msg){
		synchronized(System.out){
			System.out.println(msg);
		}
	}

}

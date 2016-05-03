package pap.ass06;

import com.sun.xml.internal.ws.spi.db.OldBridge;

public class ComputeMatrixTask implements Runnable {

	private int x;
	private int y;
	private Grid oldGrid;
	private Grid newGrid;	
	private GameOfLifeSet set;
	private int xsize;
	private int ysize; 
	private Counter counter;
	
	public ComputeMatrixTask(int x, int y, Grid oldGrid, Grid newGrid, GameOfLifeSet set, Counter counter) {
		this.x = x;
		this.y = y;
		this.oldGrid = oldGrid;
		this.newGrid = newGrid;
		this.set = set;
		xsize = set.getSizeX();
		ysize = set.getSizeY();
		this.counter = counter;
	}
	
	@Override
	public void run() {	
		try {
			boolean alive = isAlive(x,y);
			if(alive && oldGrid.getSingleCell(x, y) == false) {
				counter.inc();
			}
			else {
				if(!alive && oldGrid.getSingleCell(x, y))
					counter.dec();
			}
			newGrid.setSingleCell(x, y, alive);
			set.computePoint(x, y, alive);
		} catch (Exception e)	{
			e.printStackTrace();
		}
	}

	private boolean isAlive(int x,int y){	
		return oldGrid.getSingleCell(x, y) && getNeighborCount(xsize, ysize, x, y) == 2 || getNeighborCount(xsize, ysize, x, y) == 3;
	}
	
	private int getNeighborCount(int xsize,int ysize, int x, int y){
		int nc = 0;
		
		//RIGA SOPRASTANTE
		//angolo sup sx
		if (x > 1 && y > 1 ) { if(oldGrid.getSingleCell(x-1, y-1)) nc++; }
		
		//centro sup mid
		if (y > 1 ) { if(oldGrid.getSingleCell(x, y-1)) nc++; }
		
		//angolo sup dx
		if ( x < xsize - 1 && y > 1 ) { if(oldGrid.getSingleCell(x+1, y-1)) nc++; }
		
		//RIGA CENTRALE
		//centro mid sx
		if (x > 1) { if(oldGrid.getSingleCell(x-1, y)) nc++; }
		//centro mid dx
		if (x < xsize - 1) { if(oldGrid.getSingleCell(x+1, y)) nc++; }
		
		//RIGA SOTTOSTANTE
		if (x > 1 && y < ysize - 1 ) { if(oldGrid.getSingleCell(x-1, y+1)) nc++; }
		
		if (y < ysize - 1) { if(oldGrid.getSingleCell(x, y+1)) nc++; }
		
		if (x < xsize - 1 && y < ysize - 1) { if(oldGrid.getSingleCell(x+1, y+1)) nc++; }
		
		return nc;
	}
	
	private void log(String msg){
		synchronized(System.out){
			System.out.println(msg);
		}
	}
}

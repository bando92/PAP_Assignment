package ass08.gameoflife;

import akka.actor.UntypedActor;

public class WorkerActor extends UntypedActor {

		
	private Grid oldGrid;
	private int x;
	private int y;
	private GameOfLifeSet set;
	private int xsize;
	private int ysize;
	private Grid aliveGrid;

	@Override
	public void onReceive(Object msg) throws Exception {
		
		if (msg instanceof MasterMsg)
		{
			MasterMsg mastermsg = (MasterMsg) msg;
			oldGrid = mastermsg .getOldGrid();
			x = mastermsg .getX();
			y = mastermsg .getY(); 
			set = mastermsg.getSet();
			
			xsize = set.getSizeX();
			ysize = set.getSizeY();
			
			aliveGrid = new Grid(xsize, ysize);
			
			try {
				for(int i = x; i < y; i++){
					for(int j = 0; j < ysize; j++){
						aliveGrid.setSingleCell(i, j, isAlive(i,j));
						set.computePoint(i, j, aliveGrid.getSingleCell(i, j));
					}
				}
				
				getSender().tell(new WorkerMsg(x, y, aliveGrid), getSelf());
			} catch (Exception e)	{
				e.printStackTrace();
			}
		}
	}
	
	private boolean isAlive(int x,int y){	
		return oldGrid.getSingleCell(x, y) && getNeighborCount(xsize, ysize, x, y) == 2 
				|| getNeighborCount(xsize, ysize, x, y) == 3;
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

}
